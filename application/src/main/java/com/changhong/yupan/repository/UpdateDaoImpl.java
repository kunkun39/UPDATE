package com.changhong.yupan.repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.update.domain.ProductUpdate;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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

    private Map<String, List<ProductUpdate>> cache = new HashMap<String, List<ProductUpdate>>();

    /***********************************************缓存相关***********************************************************/

    public void cleanCache() {
        cache.clear();
    }

    public List<ProductUpdate> findProductUpdate(String model, String updateWay, String version) {
        List<ProductUpdate> updates = new ArrayList<ProductUpdate>();
        String cacheKey = model + "|" + updateWay + "|" + version;
        if (cache.get(cacheKey) != null) {
            return cache.get(cacheKey);
        }

        getHibernateTemplate().setCacheQueries(true);
        if ("1".equals(updateWay) || "2".equals(updateWay)) {
            String hql = "from ProductUpdate u where u.updateWay = ? and u.product.model = ? and (u.softwareVersion = ? or u.updateType = '2')";
            updates =  getHibernateTemplate().find(hql, new Object[]{updateWay, model, version});

        } else if ("3".equals(updateWay)) {
            String hql = "from ProductUpdate u where u.updateWay = ? and u.product.model = ? and (u.dvbVersion = ? or u.updateType = '2')";
            updates =  getHibernateTemplate().find(hql, new Object[]{updateWay, model, version});

        } else if ("4".equals(updateWay)) {
            String hql = "from ProductUpdate u where u.updateWay = ? and u.appPackage = ? and u.appVersion = ? ";
            updates =  getHibernateTemplate().find(hql, new Object[]{updateWay, model, version});

        } else if ("5".equals(updateWay)) {
            String[] parameters = StringUtils.delimitedListToStringArray(version, "|");
            String hql = "from ProductUpdate u where u.updateWay = ? and u.product.model = ? and u.programName = ? and u.programVersion = ?";
            updates =  getHibernateTemplate().find(hql, new Object[]{updateWay, model, parameters[0], parameters[1]});

        }

        if (!updates.isEmpty()) {
            for (ProductUpdate update : updates) {
                update.generateCacheData();
            }
            cache.put(cacheKey, updates);
        }
        return updates;
    }
}
