package com.changhong.system.web.facade.assember;

import com.changhong.system.domain.SystemActionLog;
import com.changhong.system.web.facade.dto.SystemActionLogDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-5-5
 * Time: 下午2:18
 */
public class SystemLogAssember {

    public static SystemActionLogDTO toSystemActionLogDTO(SystemActionLog log) {
        final Date time = log.getTimestamp();
        final String userName = log.getUserName();
        String description = userName + " " + log.getAction();
        description = description.replace("<", "&lt;").replace(">", "&gt;");
        return new SystemActionLogDTO(time, userName, description);
    }

    public static List<SystemActionLogDTO> toSystemActionLogDTOList(List<SystemActionLog> logs) {
        List<SystemActionLogDTO> dtos = new ArrayList<SystemActionLogDTO>();
        if (logs != null) {
            for (SystemActionLog log : logs) {
                dtos.add(toSystemActionLogDTO(log));
            }
        }
        return dtos;
    }
}
