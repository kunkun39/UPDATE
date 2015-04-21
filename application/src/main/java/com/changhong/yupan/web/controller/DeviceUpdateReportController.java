package com.changhong.yupan.web.controller;

import com.changhong.yupan.service.DeviceUpdateService;
import org.apache.commons.httpclient.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Jack Wang
 * Date: 15-4-20
 * Time: 下午5:56
 *
 * //插入用户升级历史记录
    if (updateClientGuJian) {
        String guJianVersion = null;
        String guJianVersionAfter = response.getUpdateVersion();
        if ("1".equals(datatype) || "3".equals(datatype)) {
            guJianVersion = client.getString("firmwareversion");
        } else if ("2".equals(datatype)) {
            guJianVersion = client.getString("firmware_diffversion");
        }
        if (StringUtils.hasText(username) && StringUtils.hasText(model) && StringUtils.hasText(guJianVersion) && StringUtils.hasText(guJianVersionAfter)) {
            ApplicationEventPublisher.publish(new ClientInfoUpdateEvent(username, model, guJianVersion, guJianVersionAfter));
        }
    }
 */
public class DeviceUpdateReportController extends AbstractController {

    private DeviceUpdateService deviceUpdateService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String getJson = ServletRequestUtils.getStringParameter(request, "json", "");

        if (StringUtils.hasText(getJson)) {
            deviceUpdateService.obtainUpdateReport(getJson);
        }

        return null;
    }

    public void setDeviceUpdateService(DeviceUpdateService deviceUpdateService) {
        this.deviceUpdateService = deviceUpdateService;
    }
}
