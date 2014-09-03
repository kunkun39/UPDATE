package com.changhong.system.web.dwr;

import com.changhong.system.service.StatisticService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Jack Wang
 * Date: 14-5-11
 * Time: 下午8:36
 */
@Service("systemDWRHandler")
public class SystemDWRHandler {

     @Autowired
    private StatisticService statisticService;

    public String obtainDailyClientUpdateAmountByMonth(String productModel, int year, int month) throws JSONException {
        return statisticService.obtainDailyClientUpdateAmountByMonth(productModel, year, month).toString();
    }

    public String obtainDailyClientUpdateAmountByVersion(String productModel, int year, int month) throws JSONException {
        return statisticService.obtainDailyClientUpdateAmountByVersion(productModel, year, month).toString();
    }
}
