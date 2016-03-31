package com.changhong.yupan.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 14-4-21
 * Time: 上午9:05
 */
public class UpdateCacheContrainer {

    private static Map<String, String> urlCache = new HashMap<String, String>();

    public static void putURLInCache(String externalURL, String actualURL) {
        urlCache.put(externalURL, actualURL);
    }

    public static String getURLFromCache(String externalURL) {
        return urlCache.get(externalURL);
    }
}
