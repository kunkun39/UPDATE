package com.changhong.system.web.controller;

import com.changhong.common.utils.CHDateUtils;
import com.changhong.common.web.session.SessionKey;
import com.changhong.system.service.SystemService;
import com.changhong.system.web.facade.dto.SystemActionLogDTO;
import com.changhong.system.web.paging.SystemLogOverviewPaging;
import org.apache.poi.hssf.record.formula.functions.Year;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 14-5-5
 * Time: 下午1:20
 */
public class SystemActionLogManagementController extends AbstractController {

    private SystemService systemService;

    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        int logYear = ServletRequestUtils.getIntParameter(request, "logYear", -1);
        int logMonth = ServletRequestUtils.getIntParameter(request, "logMonth", -1);

        if (logMonth <= 0) {
            logYear = CHDateUtils.getCurrentYear();
        }
        if (logMonth <= 0) {
            logMonth = CHDateUtils.getCurrentMonth();
        }

        request.setAttribute("current", current);
        request.setAttribute("logYear", logYear);
        request.setAttribute("logMonth", logMonth);

        SystemLogOverviewPaging paging = new SystemLogOverviewPaging(systemService);
        constructPaging(paging, current, logYear, logMonth);
        List<SystemActionLogDTO> logs = paging.getItems();
        model.put("logs", logs);
        model.put("paging", paging);

        return new ModelAndView("backend/system/systemactionlogoverview", model);
    }

    private void constructPaging(SystemLogOverviewPaging paging, int current, int year, int month) {
        paging.setCurrentPageNumber(current);
        paging.setLogYear(year);
        paging.setLogMonth(month);
    }

    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }
}
