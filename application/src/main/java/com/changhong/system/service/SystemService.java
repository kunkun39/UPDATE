package com.changhong.system.service;

import com.changhong.system.domain.SystemActionLog;
import com.changhong.system.web.facade.dto.SystemActionLogDTO;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-5-5
 * Time: 下午1:20
 */
public interface SystemService {

    void saveSystemActionLog(SystemActionLog log);

    List<SystemActionLogDTO> obtainSystemActionLog(int year, int month, int startPosition, int pageSize);

    int obtainSystemActionLogSize(int year, int month);
}
