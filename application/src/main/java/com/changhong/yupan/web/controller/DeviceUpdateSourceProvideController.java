package com.changhong.yupan.web.controller;

import com.changhong.update.service.DocumentService;
import com.changhong.yupan.domain.DeviceUpdateResponse;
import com.changhong.yupan.service.DeviceUpdateService;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.omg.CORBA.ULongLongSeqHelper;
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
            long beginHandle = System.currentTimeMillis();
            DeviceUpdateResponse updateResponse = deviceUpdateService.obtainUpdateData(getJson);
            long endHandle = System.currentTimeMillis();
            long during = endHandle - beginHandle;
            logger.info("service take " + during + "ms");

            if (updateResponse != null) {
                response.sendRedirect(updateResponse.getDeviceURL());
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
