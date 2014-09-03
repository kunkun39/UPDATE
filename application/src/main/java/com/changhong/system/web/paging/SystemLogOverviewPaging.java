package com.changhong.system.web.paging;

import com.changhong.system.service.SystemService;
import com.changhong.system.web.facade.dto.SystemActionLogDTO;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-5-5
 * Time: 下午1:25
 */
public class SystemLogOverviewPaging extends AbstractPaging<SystemActionLogDTO> {

    private SystemService systemService;

    private int logYear;

    private int logMonth;

    public SystemLogOverviewPaging(SystemService systemService) {
        this.systemService = systemService;
    }

    public List<SystemActionLogDTO> getItems() {
        return systemService.obtainSystemActionLog(logYear, logMonth, getStartPosition(), getPageSize());
    }

    public long getTotalItemSize() {
        if (totalItemSize >= 0) {
            return totalItemSize;
        }
        totalItemSize = systemService.obtainSystemActionLogSize(logYear, logMonth);
        return totalItemSize;
    }

    public String getParameterValues() {
        return "&logYear=" + getLogYear() + "&logMonth=" + getLogMonth();
    }

    public int getLogYear() {
        return logYear;
    }

    public void setLogYear(int logYear) {
        this.logYear = logYear;
    }

    public int getLogMonth() {
        return logMonth;
    }

    public void setLogMonth(int logMonth) {
        this.logMonth = logMonth;
    }
}


