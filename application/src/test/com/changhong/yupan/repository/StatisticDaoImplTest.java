package com.changhong.yupan.repository;

import com.changhong.system.repository.StatisticDaoImpl;
import junit.framework.TestCase;
import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 14-5-9
 * Time: 下午5:24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/database.xml", "/applicationContext.xml"})
public class StatisticDaoImplTest extends TestCase {
    @Resource
    StatisticDaoImpl statisticDao;

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

    @Transactional
    @Test
    public void testLoadDailyClientUpdateAmountByMonth() throws Exception {
        JSONArray array = statisticDao.loadDailyClientUpdateAmountByMonth("ott", 2014, 5);
        System.out.println(array.toString());
    }

    @Transactional
    @Test
    public void testLoadDailyClientUpdateAmountByVersion() throws Exception {
        JSONArray array = statisticDao.loadDailyClientUpdateAmountByVersion("ott", 2014, 5);
        System.out.println(array.toString());
    }
}
