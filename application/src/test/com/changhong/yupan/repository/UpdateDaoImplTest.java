package com.changhong.yupan.repository;

import com.changhong.common.utils.CHStringUtils;
import com.changhong.common.utils.NumberUtils;
import com.changhong.update.domain.Product;
import com.changhong.update.domain.ProductUpdate;
import junit.framework.TestCase;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Migrady Su.
 * Author:xiandaoyan@126.com
 * Date: 2013-4-2
 * Time: 23:14:30
 */
@RunWith(SpringJUnit4ClassRunner.class)
//指定测试用例的运行器 这里是指定了Junit4
@ContextConfiguration({"/database.xml", "/applicationContext.xml"})
public class UpdateDaoImplTest extends TestCase {
    @Resource
    SessionFactory sessionFactory;

    HibernateTemplate hibernateTemplate;

    @Before
    public void setUp() {
        hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    @After
    public void tearDown() {
        hibernateTemplate = null;
    }

    @Test
    public void testSecondLevelCache() {
        Session session1 = sessionFactory.openSession();
        session1.beginTransaction();
        Product product = new Product();
        product.setName("name");
        product.setModel("mode");
        product.setDescription("description");
        hibernateTemplate.saveOrUpdate(product);
        session1.close();

        Session session2 = sessionFactory.openSession();
        session2.beginTransaction();
        Product load1 = (Product) hibernateTemplate.find("from Product p where p.id = " + product.getId()).get(0);
        session2.close();

        Session session3 = sessionFactory.openSession();
        session3.beginTransaction();
        Product load2 = (Product) session3.get(Product.class, product.getId());
        long secondLevelCacheHitCount = sessionFactory.getStatistics().getSecondLevelCacheHitCount();
        System.out.println("hints " + secondLevelCacheHitCount);
        session3.close();

        Session session4 = sessionFactory.openSession();
        session4.beginTransaction();
        Product load3 = (Product) session4.get(Product.class, product.getId());
        secondLevelCacheHitCount = sessionFactory.getStatistics().getSecondLevelCacheHitCount();
        System.out.println("hints " + secondLevelCacheHitCount);
        session4.close();

    }

    @Test
    public void testInsertManyData() {
        List<Integer> productIds = new ArrayList<Integer>();

        for (int i = 0; i < 100; i++) {
            Product product = new Product();
            product.setName("TEST");
            product.setModel(CHStringUtils.getRandomString(8));
            hibernateTemplate.saveOrUpdate(product);

            productIds.add(product.getId());
        }


        for (int i = 0; i < 21000; i++) {
            ProductUpdate update = new ProductUpdate();
            Product product = new Product();
            try {
                product.setId(productIds.get(NumberUtils.generateRandomNumber(100)));
            } catch (Exception e) {
                product.setId(productIds.get(NumberUtils.generateRandomNumber(100)));
            }
            update.setProduct(product);

            update.setUpdateWay(String.valueOf(NumberUtils.generateRandomNumber(5)));
            update.setUpdateURL("http://dl.vmall.com/c0a8ylt1wx");
            update.setSoftwareVersion(String.valueOf(NumberUtils.generateRandomNumber(10)));
            update.setUpdateType("0");
            update.setMacFilter("");
            update.setSignatureType("");
            update.setTestFlag("false");

            update.setDvbVersion(String.valueOf(NumberUtils.generateRandomNumber(10)));
            update.setDvbProviderCode(NumberUtils.generateRandomNumber(9999) + "");
            update.setCaType("1");
            update.setCaDependVersion("");

            update.setAppPackage("com.changhong." + NumberUtils.generateRandomNumber(10));
            update.setAppVersionRange("1-20");
            update.setAppVersion(NumberUtils.generateRandomNumber(10) + "");
            update.setAppSignatureType("");

            update.setProgramName(CHStringUtils.getRandomString(6));
            update.setProgramVersion(String.valueOf(NumberUtils.generateRandomNumber(10)));
            update.setProgramSignatureType("");

            hibernateTemplate.saveOrUpdate(update);
        }

    }

    @Test
    public void testLoadProductUpdatePerformace() {
        for (int i = 1; i < 500; i++) {
            Object o = hibernateTemplate.find("select max(p.id) from Product p").get(0);
            int maxProductId = (Integer) o;

            int ramdomProductId = NumberUtils.generateRandomNumber(maxProductId) + 1;
            Product product = null;
            List<Product> products = hibernateTemplate.find("from Product p where p.id = " + ramdomProductId);
            if (products.isEmpty()) {
                continue;
            }
            product = products.get(0);

            String model = product.getModel();
            String ramdomUpdateWay = (NumberUtils.generateRandomNumber(4) + 1) + "";
            String packName = "com.changhong." + NumberUtils.generateRandomNumber(9);
            String version = String.valueOf(NumberUtils.generateRandomNumber(10));

            if (ramdomUpdateWay.equals("0")) {
                continue;
            }

            long start = System.currentTimeMillis();

            List<ProductUpdate> update = findProductUpdate(model, ramdomUpdateWay, version);

            long end = System.currentTimeMillis();
            long during = end - start;
            if (during > 10) {
                System.out.println(model + "|" + ramdomUpdateWay + "|" + packName);
                System.out.println("find update sise:" + update.size() + " and spend " + during);
            }
        }
    }

    private List<ProductUpdate> findProductUpdate(String model, String updateWay, String version) {
        if ("1".equals(updateWay) || "2".equals(updateWay)) {
            String hql = "from ProductUpdate u where u.updateWay = ? and u.product.model = ? and u.softwareVersion = ?";
            return hibernateTemplate.find(hql, new Object[]{updateWay, model, version});

        } else if ("3".equals(updateWay)) {
            String hql = "from ProductUpdate u where u.updateWay = ? and u.product.model = ? and u.dvbVersion = ?";
            return hibernateTemplate.find(hql, new Object[]{updateWay, model, version});

        } else if ("4".equals(updateWay)) {
            String hql = "from ProductUpdate u where u.updateWay = ? and u.appPackage = ? and u.appVersion = ?";
            return hibernateTemplate.find(hql, new Object[]{updateWay, model, version});

        } else if ("5".equals(updateWay)) {
            String[] parameters = StringUtils.delimitedListToStringArray(version, "|");
            String hql = "from ProductUpdate u where u.updateWay = ? and u.product.model = ? and u.programName = ? and u.programVersion = ?";
            return hibernateTemplate.find(hql, new Object[]{updateWay, model, parameters[0], parameters[1]});

        }

        return null;
    }
}
