package com.changhong.system.service;

import com.changhong.system.domain.SystemActionLog;
import com.changhong.system.repository.SystemDao;
import com.changhong.system.web.facade.assember.SystemLogAssember;
import com.changhong.system.web.facade.dto.SystemActionLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-5-5
 * Time: 下午1:21
 */
@Service("systemService")
public class SystemServiceImpl implements SystemService {

    @Autowired
    private SystemDao systemDao;

    public void saveSystemActionLog(SystemActionLog log) {
        systemDao.persist(log);
    }

    public List<SystemActionLogDTO> obtainSystemActionLog(int year, int month, int startPosition, int pageSize) {
        List<SystemActionLog> logs = systemDao.loadSystemActionLog(year, month, startPosition, pageSize);
        return SystemLogAssember.toSystemActionLogDTOList(logs);
    }

    public int obtainSystemActionLogSize(int year, int month) {
        return systemDao.loadSystemActionLogSize(year, month);
    }
}
