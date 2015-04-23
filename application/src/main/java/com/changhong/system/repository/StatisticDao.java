package com.changhong.system.repository;

import com.changhong.common.repository.EntityObjectDao;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * User: Jack Wang
 * Date: 14-5-9
 * Time: 下午5:04
 */
public interface StatisticDao extends EntityObjectDao {

    JSONArray loadDailyClientUpdateAmountByMonth(String productModel, String updateSuccess, int year, int month) throws JSONException;

    JSONArray loadDailyClientUpdateAmountByResult(String productModel, int year, int month) throws JSONException;
}
