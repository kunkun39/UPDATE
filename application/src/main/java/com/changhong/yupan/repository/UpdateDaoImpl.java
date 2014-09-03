package com.changhong.yupan.repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.update.domain.ProductUpdate;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-15
 * Time: 下午4:37
 */
@Repository("updateDao")
public class UpdateDaoImpl extends HibernateEntityObjectDao implements UpdateDao {

    public List<ProductUpdate> findProductUpdate(String model, String updateWay, String version) {
        getHibernateTemplate().setCacheQueries(true);
        if ("1".equals(updateWay) || "2".equals(updateWay)) {
            String hql = "from ProductUpdate u where u.updateWay = ? and u.product.model = ? and (u.softwareVersion = ? or u.updateType = '2')";
            return getHibernateTemplate().find(hql, new Object[]{updateWay, model, version});

        } else if ("3".equals(updateWay)) {
            String hql = "from ProductUpdate u where u.updateWay = ? and u.product.model = ? and (u.dvbVersion = ? or u.updateType = '2')";
            return getHibernateTemplate().find(hql, new Object[]{updateWay, model, version});

        } else if ("4".equals(updateWay)) {
            String hql = "from ProductUpdate u where u.updateWay = ? and u.appPackage = ? and u.appVersion = ? ";
            return getHibernateTemplate().find(hql, new Object[]{updateWay, model, version});

        } else if ("5".equals(updateWay)) {
            String[] parameters = StringUtils.delimitedListToStringArray(version, "|");
            String hql = "from ProductUpdate u where u.updateWay = ? and u.product.model = ? and u.programName = ? and u.programVersion = ?";
            return getHibernateTemplate().find(hql, new Object[]{updateWay, model, parameters[0], parameters[1]});

        }

        return null;
    }
}
