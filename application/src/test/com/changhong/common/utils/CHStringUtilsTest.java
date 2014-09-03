package com.changhong.common.utils;

import junit.framework.TestCase;

/**
 * User: Jack Wang
 * Date: 14-7-3
 * Time: 上午10:50
 */
public class CHStringUtilsTest extends TestCase {

    public void testToFixNumberString() {
        String convertValue = "";

        convertValue = CHStringUtils.toFixNumberString(null, 5);
        assertEquals(convertValue, "00000");

        convertValue = CHStringUtils.toFixNumberString("", 5);
        assertEquals(convertValue, "00000");

        convertValue = CHStringUtils.toFixNumberString("1", 5);
        assertEquals(convertValue, "00001");

        convertValue = CHStringUtils.toFixNumberString("11111", 5);
        assertEquals(convertValue, "11111");

        convertValue = CHStringUtils.toFixNumberString("111111", 5);
        assertEquals(convertValue, "111111");
    }
}
