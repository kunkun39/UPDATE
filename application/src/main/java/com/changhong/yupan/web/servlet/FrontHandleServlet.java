package com.changhong.yupan.web.servlet;

import com.changhong.common.web.application.ApplicationEventPublisher;
import com.changhong.yupan.service.DeviceUpdateService;
import org.apache.commons.httpclient.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: Jack Wang
 * Date: 14-11-21
 * Time: 上午9:24
 */
public class FrontHandleServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String getJson = ServletRequestUtils.getStringParameter(request, "json", "");

        if (StringUtils.hasText(getJson)) {
            DeviceUpdateService updateService = (DeviceUpdateService) ApplicationEventPublisher.getCtx().getBean("deviceUpdateService");
            String updateResponseURL = updateService.obtainUpdateData(getJson);

            if (StringUtils.hasText(updateResponseURL)) {
                response.sendRedirect(updateResponseURL);
            } else {
                response.setStatus(HttpStatus.SC_NOT_FOUND);
            }
        }
    }
}
