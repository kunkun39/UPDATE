package com.changhong.system.service;

import com.changhong.system.repository.StatisticDao;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Jack Wang
 * Date: 14-5-10
 * Time: 下午4:08
 */
@Service("statisticService")
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private StatisticDao statisticDao;

    public JSONArray obtainDailyClientUpdateAmountByMonth(String productModel, int year, int month) throws JSONException {
        return statisticDao.loadDailyClientUpdateAmountByMonth(productModel, year, month);
    }

    public JSONArray obtainDailyClientUpdateAmountByVersion(String productModel, int year, int month) throws JSONException {
        return statisticDao.loadDailyClientUpdateAmountByVersion(productModel, year, month);
    }
}
