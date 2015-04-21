package com.changhong.yupan.service;


/**
 * User: Jack Wang
 * Date: 14-4-11
 * Time: 下午3:30
 */
public interface DeviceUpdateService {

    String obtainUpdateData(String json);

    void obtainUpdateReport(String json);
}
