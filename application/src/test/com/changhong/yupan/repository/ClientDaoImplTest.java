package com.changhong.yupan.repository;

import com.changhong.common.utils.CHStringUtils;
import com.changhong.system.domain.ClientUpdateHistory;
import junit.framework.TestCase;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * User: Jack Wang
 * Date: 14-4-29
 * Time: 下午4:12
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/database.xml", "/applicationContext.xml"})
public class ClientDaoImplTest extends TestCase {
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

    /**
     * Range
     * from: 65-88-ff-73-69-47
     * to  : 65-88-ff-73-6B-3A
     */
    @Test
    public void generateActualClientInfo() {
        int id = 0;

        String prefix = "65:88:ff:73:69:";
        int middle = 4;
        int suffix = 6;
        for (int i = 0; i < 185; i++) {
            suffix = suffix + 1;
            int mode = suffix / 16;
            int left = suffix % 16;
            int upper = middle + mode;

            String username1 = decideChar(upper);
            String username2 = decideChar(left);

            String username = prefix + username1 + username2;
            id = id + 1;
            System.out.println("insert into system_client_info values (" + id + ", '2014-05-22 00:00:00', '" + username + "');");
        }

        prefix = "65:88:ff:73:6A:";
        middle = 0;
        suffix = -1;

        for (int i = 0; i < 256; i++) {
            suffix = suffix + 1;
            int mode = suffix / 16;
            int left = suffix % 16;
            int upper = middle + mode;

            String username1 = decideChar(upper);
            String username2 = decideChar(left);

            String username = prefix + username1 + username2;
            id = id + 1;
            System.out.println("insert into system_client_info values (" + id + ", '2014-05-22 00:00:00', '" + username + "');");
        }

        prefix = "65:88:ff:73:6B:";
        middle = 0;
        suffix = -1;
        for (int i = 0; i < 59; i++) {
            suffix = suffix + 1;
            int mode = suffix / 16;
            int left = suffix % 16;
            int upper = middle + mode;

            String username1 = decideChar(upper);
            String username2 = decideChar(left);

            String username = prefix + username1 + username2;
            id = id + 1;
            System.out.println("insert into system_client_info values (" + id + ", '2014-05-22 00:00:00', '" + username + "');");
        }
    }

    private String decideChar(int number) {
        if (number < 10) {
            return number + "";
        } else if (number == 10) {
            return "A";
        } else if (number == 11) {
            return "B";
        } else if (number == 12) {
            return "C";
        } else if (number == 13) {
            return "D";
        } else if (number == 14) {
            return "E";
        } else if (number == 15) {
            return "F";
        }
        return null;
    }

    @Test
    public void testInsertManyData() {
        Session session = hibernateTemplate.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        for (int i = 0; i < 100000; i++) {
            String username = CHStringUtils.getRandomString(17);
            String guJianVersion = getRandomNumber(2) + "";
            ClientUpdateHistory clientUpdateHistory = new ClientUpdateHistory(username, "fake", guJianVersion, "1.1");
            session.save(clientUpdateHistory);

            if (i % 50 == 0) {
                session.flush();
                session.clear();
                System.out.println("finished save " + i);
            }
        }

        transaction.commit();
    }

    @Test
    public void testFindClientPerformance() {
        for (int i = 0; i < 100; i++) {
            int id = getRandomNumber(6);
            ClientUpdateHistory clientUpdateHistory = hibernateTemplate.get(ClientUpdateHistory.class, id);

            Session session = hibernateTemplate.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            long start = System.currentTimeMillis();

            if (clientUpdateHistory == null) {
                SQLQuery insert = session.createSQLQuery("insert system_client(username, gujian_version, sta_year, sta_month, sta_day, sta_hour) values ('" + CHStringUtils.getRandomString(17) + "', '" + CHStringUtils.getRandomString(5) + "', '2014', '5', '1', '23')");
                insert.executeUpdate();
            } else {
                SQLQuery query = session.createSQLQuery("select id, username, gujian_version from system_client where username = '" + clientUpdateHistory.getUsername() + "'");
                List list = query.list();
                Object[] another = (Object[]) list.get(0);
                SQLQuery update = session.createSQLQuery("update system_client set gujian_version = '" + CHStringUtils.getRandomString(5) + "' where id = " + another[0]);
                update.executeUpdate();
            }

            long end = System.currentTimeMillis();
            long during = end - start;
            System.out.println("find and update take time " + during);
            transaction.commit();
        }
    }

    public static int getRandomNumber(int length) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return Integer.valueOf(sb.toString());
    }
}
