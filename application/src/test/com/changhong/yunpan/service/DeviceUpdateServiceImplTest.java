package com.changhong.yunpan.service;

import com.changhong.yupan.service.DeviceUpdateServiceImpl;
import junit.framework.TestCase;

/**
 * User: Jack Wang
 * Date: 14:5:22
 * Time: 下午3:44
 */
public class DeviceUpdateServiceImplTest extends TestCase {

    public void testValidateUsernameInRange() {
        DeviceUpdateServiceImpl impl = new DeviceUpdateServiceImpl();

        String username = "65:88:ff:73:69:47";
        assertTrue(impl.validateUsernameInRange(username, "", ""));
        assertTrue(impl.validateUsernameInRange(username, null, null));

        String fromFilter = "65:88:ff:73:69:47";
        String toFilter = "65:88:ff:73:6B:3A";
        assertTrue(impl.validateUsernameInRange(username, fromFilter, toFilter));

        username = "65:88:ff:73:6B:3A";
        assertTrue(impl.validateUsernameInRange(username, fromFilter, toFilter));

        username = "65:88:ff:73:69:47";
        fromFilter = "65:88:ff:73:69:47";
        toFilter = "65:88:ff:73:69:47";
        assertTrue(impl.validateUsernameInRange(username, fromFilter, toFilter));

        username = "65:88:ff:73:6B:3A";
        fromFilter = "65:88:ff:73:6B:3A";
        toFilter = "65:88:ff:73:6B:3A";
        assertTrue(impl.validateUsernameInRange(username, fromFilter, toFilter));

        username = "65:88:ff:73:69:FF";
        fromFilter = "65:88:ff:73:69:47";
        toFilter = "65:88:ff:73:6B:3A";
        assertTrue(impl.validateUsernameInRange(username, fromFilter, toFilter));

        username = "65:88:ff:73:69:FF";
        assertTrue(impl.validateUsernameInRange(username, fromFilter, toFilter));

        username = "65:88:ff:73:6A:FF";
        assertTrue(impl.validateUsernameInRange(username, fromFilter, toFilter));

        username = "65:88:ff:73:6B:00";
        assertTrue(impl.validateUsernameInRange(username, fromFilter, toFilter));

        username = "65:88:ff:73:6B:FF";
        assertFalse(impl.validateUsernameInRange(username, fromFilter, toFilter));

        username = "65:88:ff:73:6C:00";
        assertFalse(impl.validateUsernameInRange(username, fromFilter, toFilter));
    }
}
