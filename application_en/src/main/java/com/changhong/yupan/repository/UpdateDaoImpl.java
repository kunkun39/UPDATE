package com.changhong.yupan.repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.update.domain.ProductUpdate;
import com.changhong.update.service.DocumentPathResolver;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 14-4-15
 * Time: 下午4:37
 */
@Repository("updateDao")
public class UpdateDaoImpl extends HibernateEntityObjectDao implements UpdateDao {

    @Value("${project.upload.file.path}")
    private String baseStorePath;

    private Map<String, List<ProductUpdate>> cache = new HashMap<String, List<ProductUpdate>>();
    private Map<String, List<String>> snsCache = new HashMap<String, List<String>>();

    /***********************************************缓存相关***********************************************************/

    public void cleanCache() {
        cache.clear();
        snsCache.clear();
    }

    public boolean isSNInList(String key, String username) {
        List<String> sns = snsCache.get(key);
        if (sns != null && sns.contains(username)) {
            return true;
        }
        return false;
    }

    public List<ProductUpdate> findProductUpdate(String model, String updateWay, String version) {
        List<ProductUpdate> updates = new ArrayList<ProductUpdate>();
        String cacheKey = model + "|" + updateWay + "|" + version;
        if (cache.get(cacheKey) != null) {
            return cache.get(cacheKey);
        }

        getHibernateTemplate().setCacheQueries(true);
        if ("1".equals(updateWay) || "2".equals(updateWay)) {
            String hql = "from ProductUpdate u where u.updateWay = ? and u.product.model = ? and (u.softwareVersion = ? or u.updateType = '2') order by u.id desc";
            updates =  getHibernateTemplate().find(hql, new Object[]{updateWay, model, version});

        } else if ("3".equals(updateWay)) {
            String hql = "from ProductUpdate u where u.updateWay = ? and u.product.model = ? and (u.dvbVersion = ? or u.updateType = '2') order by u.id desc";
            updates =  getHibernateTemplate().find(hql, new Object[]{updateWay, model, version});

        } else if ("4".equals(updateWay)) {
            String hql = "from ProductUpdate u where u.updateWay = ? and u.appPackage = ? and u.appVersion = ? order by u.id desc";
            updates =  getHibernateTemplate().find(hql, new Object[]{updateWay, model, version});

        } else if ("5".equals(updateWay)) {
            String[] parameters = StringUtils.delimitedListToStringArray(version, "|");
            String hql = "from ProductUpdate u where u.updateWay = ? and u.product.model = ? and u.programName = ? and u.programVersion = ? order by u.id desc";
            updates =  getHibernateTemplate().find(hql, new Object[]{updateWay, model, parameters[0], parameters[1]});

        }

        if (!updates.isEmpty()) {
            for (ProductUpdate update : updates) {
                update.generateCacheData();

                List<String> sns = getSNLists(update);
                String snCacheKey = cacheKey + "|" + update.getId();
                snsCache.put(snCacheKey, sns);
            }
            cache.put(cacheKey, updates);
        }
        return updates;
    }

    public List<String> getSNLists(ProductUpdate update) {
        List<String> snLists = new ArrayList<String>();

        String returnPath = DocumentPathResolver.generateUploadFileNamePath(update);
        File directory = new File(baseStorePath + File.separatorChar + returnPath);
        String deviceFile = directory.getAbsolutePath() + File.separatorChar + update.getUpdateVersionName() + "_" + "devices.txt";

        File file = new File(deviceFile);
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);

            String line = "";
            while ((line = br.readLine()) != null) {
                snLists.add(line.toLowerCase());
            }
            br.close();
            isr.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return snLists;
    }
}
