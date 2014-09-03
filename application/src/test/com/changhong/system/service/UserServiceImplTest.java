package com.changhong.system.service;

import com.changhong.common.domain.EntityBase;
import com.changhong.system.domain.User;
import com.changhong.system.repository.UserDao;
import com.changhong.system.repository.UserDaoImpl;
import junit.framework.TestCase;

import static org.easymock.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.*;

/**
 * User: Jack Wang
 * Date: 14-7-3
 * Time: 上午10:59
 */
public class UserServiceImplTest extends TestCase {

    private UserServiceImpl userService;


    @Override
    public void setUp() throws Exception {
        userService = new UserServiceImpl();
    }

    public void testChangeUserPassword() {
//        User user = new User();
//        user.setPassword("old");
//
//        UserDao userDao = createMock(UserDao.class);
//        userService.setUserDao(userDao);
//
//        expect(userDao.findById(10, User.class)).andReturn(user);
//        replay(userDao);
//        userService.changeUserPassword(10, "new");
//        verify(userDao);
//
//        assertEquals(user.getPassword(), "new");

        final User user = new User();
        user.setPassword("old");
        UserDao userDao = new UserDaoImpl() {
            @Override
            public EntityBase findById(int id, Class clazz) {
                assertEquals(id, 10);
                assertEquals(clazz.getName(), "com.changhong.system.domain.User");
                return user;
            }
        };
        userService.setUserDao(userDao);


        userService.changeUserPassword(10, "new");
        assertEquals(user.getPassword(), "new");
    }
}
