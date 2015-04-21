package com.changhong.yunpan.service;

import com.changhong.yupan.service.DeviceUpdateServiceImpl;
import junit.framework.TestCase;
import org.jasypt.util.binary.StrongBinaryEncryptor;

import java.util.Date;

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

    public static void main(String[] args) {
        String path = "/ROOT/ROOT/ROOT/change.mp3";
        int lastIndex = path.lastIndexOf("/");
        int lastIndexDoc = path.lastIndexOf(".");
        String iconFilepath = path.substring(0, lastIndex) + path.substring(lastIndex, lastIndexDoc) + ".wk";
        System.out.println(iconFilepath);

        Date day = new Date(114, 11, 31);
        getWeekIndex(day, 0);

        day = new Date(114, 11, 31);
        getWeekIndex(day, 1);

        day = new Date(114, 11, 31);
        getWeekIndex(day, 2);

        day = new Date(114, 11, 31);
        getWeekIndex(day, 3);

        day = new Date(114, 11, 31);
        getWeekIndex(day, 4);

        day = new Date(114, 11, 31);
        getWeekIndex(day, 5);

        day = new Date(114, 11, 31);
        getWeekIndex(day, 6);

    }

    public static void getWeekIndex(Date day, int plusDays) {
        int oldDay = day.getDate();
        day.setDate(oldDay + plusDays);
        int weekIndex = day.getDay();
        if (weekIndex == 0) {
            weekIndex = 7;
        }
        System.out.println(weekIndex);
    }
}
