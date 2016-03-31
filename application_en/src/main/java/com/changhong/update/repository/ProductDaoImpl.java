package com.changhong.update.repository;

import com.changhong.common.repository.HibernateEntityObjectDao;
import com.changhong.common.utils.CHStringUtils;
import com.changhong.update.domain.Product;
import com.changhong.update.domain.ProductCategory;
import com.changhong.update.domain.ProductUpdate;
import com.changhong.system.domain.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 下午2:34
 */
@Repository("productDao")
public class ProductDaoImpl extends HibernateEntityObjectDao implements ProductDao {

    /*************************目录******************************/

    public ProductCategory loadProductCategoryById(int id) {
        return (ProductCategory) getHibernateTemplate().find("from ProductCategory p where p.id = ?", new Object[]{id}).get(0);
    }

    public List<ProductCategory> loadCategoryByParentId(int categoryId) {
        StringBuilder builder = new StringBuilder();
        builder.append("from ProductCategory p");
        if (categoryId > 0) {
            builder.append(" where p.parent.id = ?");
        } else {
            builder.append(" where p.parent is null");
        }

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        if (categoryId > 0) {
            query.setParameter(0, categoryId);
        }

        List<ProductCategory> categories = query.list();
        return categories;
    }

    /*************************产品******************************/

    public List<Product> loadProducts(String name, int categoryId, int startPosition, int pageSize) {
        if (!StringUtils.hasText(name) && categoryId < 0) {
            return new ArrayList<Product>();
        }

        StringBuilder builder = new StringBuilder();
        builder.append("from Product p");
        if (StringUtils.hasText(name)) {
            builder.append(" where (p.name like '%" + name + "%' or p.model like '%" + name + "%')");
        }
        if (categoryId > 0) {
            if (builder.toString().contains(" where ")) {
                builder.append(" and p.category.id = " + categoryId);
            } else {
                builder.append(" where p.category.id = " + categoryId);
            }
        }

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setMaxResults(pageSize);
        query.setFirstResult(startPosition);

        List<Product> products = query.list();
        return products;
    }

    public int loadProductSize(String name, int categoryId) {
        if (!StringUtils.hasText(name) && categoryId < 0) {
            return 0;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("select count(p.id) from Product p");
        if (StringUtils.hasText(name)) {
            builder.append(" where (p.name like '%" + name + "%' or p.model like '%" + name + "%')");
        }
        if (categoryId > 0) {
            if (builder.toString().contains(" where ")) {
                builder.append(" and p.category.id = " + categoryId);
            } else {
                builder.append(" where p.category.id = " + categoryId);
            }
        }
        List list =  getHibernateTemplate().find(builder.toString());
        return ((Long)list.get(0)).intValue();
    }

    public List<Product> obtainAllUserProducts(int userId) {
        User user = (User)findById(userId, User.class);
        List<Integer> responsibleCategoryIds = user.getResponsibleCategoriesIds();

        StringBuilder builder = new StringBuilder();
        if (userId == 1) {
            builder.append("from Product p");
        } else {
            builder.append("from Product p where p.category.id in (" + CHStringUtils.convertListToSQLIn(responsibleCategoryIds) + ")");
        }

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());

        List<Product> products = query.list();
        return products;
    }

    public List<Product> loadMyProducts(String name, int currentUserId, int startPosition, int pageSize) {
        User user = (User)findById(currentUserId, User.class);
        List<Integer> responsibleCategoryIds = user.getResponsibleCategoriesIds();

        StringBuilder builder = new StringBuilder();
        builder.append("from Product p where p.category.id in (" + CHStringUtils.convertListToSQLIn(responsibleCategoryIds) + ")");
        if (StringUtils.hasText(name)) {
            builder.append(" and (p.name like '%" + name + "%' or p.model like '%" + name + "%')");
        }

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setMaxResults(pageSize);
        query.setFirstResult(startPosition);

        List<Product> products = query.list();
        return products;
    }

    public int loadMyProductSize(String name, int currentUserId) {
        User user = (User)findById(currentUserId, User.class);
        List<Integer> responsibleCategoryIds = user.getResponsibleCategoriesIds();

        StringBuilder builder = new StringBuilder();
        builder.append("select count(p.id) from Product p where p.category.id in (" + CHStringUtils.convertListToSQLIn(responsibleCategoryIds) + ")");
        if (StringUtils.hasText(name)) {
            builder.append(" and (p.name like '%" + name + "%' or p.model like '%" + name + "%')");
        }
        List list =  getHibernateTemplate().find(builder.toString());
        return ((Long)list.get(0)).intValue();
    }

    public boolean loadProductModelDuplicate(int productId, String model) {
        if (productId < 0) {
            List list = getHibernateTemplate().find("select count(p.id) from Product p where p.model = ?", model);
            return ((Long)list.get(0)).intValue() > 0 ? true : false;
        } else {
            List list = getHibernateTemplate().find("select count(p.id) from Product p where p.model = ? and p.id <> ?",
                    new Object[]{model, productId});
            return ((Long)list.get(0)).intValue() > 0 ? true : false;
        }
    }

    public boolean loadProductContainsUpdates(int productId) {
        List list = getHibernateTemplate().find("select count(u.id) from ProductUpdate u where u.product.id = " + productId);
        return ((Long)list.get(0)).intValue() > 0;
    }

    /*************************升级******************************/

    public List<ProductUpdate> loadProductUpdateByProductId(int productId) {
        return getHibernateTemplate().find("from ProductUpdate u where u.product.id = " + productId);
    }

    public int loadProductUpdateSize(int productId) {
        List list = getHibernateTemplate().find("select count(u.id) from ProductUpdate u where u.product.id = " + productId);
        return ((Long)list.get(0)).intValue();
    }

    public List<ProductUpdate> loadUpdateHisoryByProduct(int productId, String updateWayFilter, String versionFilter, int startPosition, int pageSize) {
        StringBuilder builder = new StringBuilder();
        builder.append("from ProductUpdate p where p.product.id = " + productId);

        if (StringUtils.hasText(updateWayFilter) && !"ALL".equals(updateWayFilter)) {
            builder.append(" and p.updateWay = '" + updateWayFilter + "'");
        }
        if("ALL".equals(updateWayFilter)) {
             builder.append(" and (p.softwareVersion like '" + versionFilter + "%' or p.caVersion like '" + versionFilter + "%' or p.appVersion like '" + versionFilter + "%' or p.programVersion like '" + versionFilter + "%')");
        } else {
            if (StringUtils.hasText(versionFilter)) {
                if ("1".equals(updateWayFilter) || "2".equals(updateWayFilter)) {
                    builder.append(" and p.softwareVersion like '" + versionFilter + "%'");
                } else if ("3".equals(updateWayFilter)) {
                    builder.append(" and p.dvbVersion like '" + versionFilter + "%'");
                } else if ("4".equals(updateWayFilter)) {
                    builder.append(" and p.appVersion like '" + versionFilter + "%'");
                } else if ("5".equals(updateWayFilter)) {
                    builder.append(" and p.programVersion like '" + versionFilter + "%'");
                }
            }
        }
        builder.append(" order by p.timestamp desc");

        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createQuery(builder.toString());
        query.setMaxResults(pageSize);
        query.setFirstResult(startPosition);

        List<ProductUpdate> updates = query.list();
        return updates;
    }

    public int loadUpdateHisoryByProductSize(int productId, String updateWayFilter, String versionFilter) {
        StringBuilder builder = new StringBuilder();
        builder.append("select count(p.id) from ProductUpdate p where p.product.id = " + productId);
        if (StringUtils.hasText(updateWayFilter) && !"ALL".equals(updateWayFilter)) {
            builder.append(" and p.updateWay = '" + updateWayFilter + "'");
        }
        if("ALL".equals(updateWayFilter)) {
            builder.append(" and (p.softwareVersion like '" + versionFilter + "%' or p.caVersion like '" + versionFilter + "%' or p.appVersion like '" + versionFilter + "%' or p.programVersion like '" + versionFilter + "%')");
        } else {
            if (StringUtils.hasText(versionFilter)) {
                if ("1".equals(updateWayFilter) || "2".equals(updateWayFilter)) {
                    builder.append(" and p.softwareVersion like '" + versionFilter + "%'");
                } else if ("3".equals(updateWayFilter)) {
                    builder.append(" and p.dvbVersion like '" + versionFilter + "%'");
                } else if ("4".equals(updateWayFilter)) {
                    builder.append(" and p.appVersion like '" + versionFilter + "%'");
                } else if ("5".equals(updateWayFilter)) {
                    builder.append(" and p.programVersion like '" + versionFilter + "%'");
                }
            }
        }
        List list =  getHibernateTemplate().find(builder.toString());
        return ((Long)list.get(0)).intValue();
    }
}
