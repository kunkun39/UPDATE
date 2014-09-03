package com.changhong.system.service;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * User: Jack Wang
 * Date: 14-5-10
 * Time: 下午4:08
 */
public interface StatisticService {

    JSONArray obtainDailyClientUpdateAmountByMonth(String productModel, int year, int month) throws JSONException;

    JSONArray obtainDailyClientUpdateAmountByVersion(String productModel, int year, int month) throws JSONException;
}
