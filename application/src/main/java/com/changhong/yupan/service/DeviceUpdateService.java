package com.changhong.yupan.service;

import com.changhong.yupan.domain.DeviceUpdateResponse;

/**
 * User: Jack Wang
 * Date: 14-4-11
 * Time: 下午3:30
 */
public interface DeviceUpdateService {

    DeviceUpdateResponse obtainUpdateData(String json);
}
