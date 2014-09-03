package com.changhong.system.repository;

import com.changhong.system.domain.User;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

/**
 * User: Jack Wang
 * Date: 14-6-28
 * Time: 上午11:08
 */
public class UserDaoImplSpringTest extends AbstractTransactionalDataSourceSpringContextTests {
    UserDaoImpl userDao;
    HibernateTemplate hibernateTemplate;

    protected String[] getConfigLocations() {
        return new String[]{"classpath:database.xml", "classpath:applicationContext.xml"};
    }

    protected void onSetUpInTransaction() throws Exception {
        SessionFactory sf = (SessionFactory) applicationContext.getBean("sessionFactory");
        userDao = new UserDaoImpl();
        userDao.setSessionFactory(sf);
        hibernateTemplate = new HibernateTemplate(sf);
    }

    public void testFindUserByName() {
        UserDetails user = userDao.findUserByName("jack");
        assertNull(user);

        User saveUser = new User("jack", "128", "jackwang", "password");
        hibernateTemplate.saveOrUpdate(saveUser);

        user = userDao.findUserByName("jackwang");
        assertEquals(user.getUsername(), "jackwang");
        assertEquals(user.getPassword(), "password");

        saveUser.setEnabled(false);
        hibernateTemplate.saveOrUpdate(saveUser);
        user = userDao.findUserByName("jackwang");
        assertNull(user);
    }

}
