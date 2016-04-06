package com.changhong.yupan.web.controller;

import com.changhong.yupan.service.DeviceUpdateService;
import org.apache.commons.httpclient.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * User: Jack Wang
 * Date: 14-4-11
 * Time: 下午3:21
 */
public class DeviceUpdateSourceProvideController extends AbstractController {

    private DeviceUpdateService deviceUpdateService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String getJson = ServletRequestUtils.getStringParameter(request, "json", "");

        if (StringUtils.hasText(getJson)) {
            String updateResponseURL = deviceUpdateService.obtainUpdateData(getJson);

            if (StringUtils.hasText(updateResponseURL)) {
                response.setContentType("application/json; charset=utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(updateResponseURL);
                writer.flush();
                writer.close();
            } else {
                response.setStatus(HttpStatus.SC_NOT_FOUND);
            }
        }

        return null;
    }

    public void setDeviceUpdateService(DeviceUpdateService deviceUpdateService) {
        this.deviceUpdateService = deviceUpdateService;
    }

}
