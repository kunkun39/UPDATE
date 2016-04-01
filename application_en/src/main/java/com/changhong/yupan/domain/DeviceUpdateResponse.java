package com.changhong.yupan.domain;

import com.alibaba.fastjson.JSONObject;
import com.changhong.common.utils.NumberUtils;
import com.changhong.update.domain.ProductUpdate;
import com.changhong.yupan.utils.ExternalUrlGetterUtils;
import org.springframework.util.StringUtils;

/**
 * User: Jack Wang
 * Date: 14-4-14
 * Time: 下午5:38
 */
public class DeviceUpdateResponse {

    private enum ObtainDataWay {
        EXTERNAL_URL,
        INTERNAL_DATA
    }

    private ObtainDataWay obtainDataWay;

    private String deviceURL;

    private String view;

    private String updateModel;

    //信息用于记录升级后版本
    private String updateVersion;

    private String updateVersionAfter;

    /**
     * 用于固件升级
     */
    public DeviceUpdateResponse(ProductUpdate update, String webAddress) {
        String updateURL = update.getUpdateURL();
        this.view = update.getView();
        this.updateModel = update.getUpdateModel();

        if (StringUtils.hasText(updateURL)) {
            this.obtainDataWay = ObtainDataWay.EXTERNAL_URL;
            String[] urls = StringUtils.delimitedListToStringArray(updateURL, "|");
            int length = urls.length;
            if (length == 1) {
                deviceURL = urls[0];
            } else {
                int i = NumberUtils.generateRandomNumber(length - 1);
                deviceURL = urls[i];
            }

            //判断是否需要缓存,需要解析外链地址
            /**
            if (urlCacheEnable) {
                    String cacheURL = UpdateCacheContrainer.getURLFromCache(deviceURL);
                if (StringUtils.hasText(cacheURL)) {
                    deviceURL = cacheURL;
                } else {
                    String actualURL = ExternalUrlGetterUtils.getActualUrl(deviceURL);
                    UpdateCacheContrainer.putURLInCache(deviceURL, actualURL);
                    deviceURL = actualURL;
                }
            } else {
                deviceURL = ExternalUrlGetterUtils.getActualUrl(deviceURL);
            }
            **/
        } else {
            this.obtainDataWay = ObtainDataWay.INTERNAL_DATA;
            this.deviceURL = webAddress + update.getActualFilePath() + "/" + update.getActualFileName();
        }

        //信息用于记录升级后版本
        this.updateVersion = update.getGuJianVersion();
        this.updateVersionAfter = update.getGuJianVersionAfter();
    }

    /**
     * 用于APK升级
     */
    public DeviceUpdateResponse(ProductUpdate update) {
        this.view = update.getView();
        this.updateModel = update.getUpdateModel();
        this.obtainDataWay = ObtainDataWay.EXTERNAL_URL;
        this.updateVersion = update.getClientVersion();
        this.updateVersionAfter = update.getGuJianVersionAfter();
        this.deviceURL = update.getApkUpdateURL();
    }

    public String obtainUpdateResponse() {
        JSONObject value = new JSONObject();

        value.put("ver", updateVersionAfter);
        value.put("url", deviceURL);
        value.put("view", view);
        value.put("updatemodel", updateModel);

        return value.toJSONString();
    }

    public ObtainDataWay getObtainDataWay() {
        return obtainDataWay;
    }

    public void setObtainDataWay(ObtainDataWay obtainDataWay) {
        this.obtainDataWay = obtainDataWay;
    }

    public String getDeviceURL() {
        return deviceURL;
    }

    public void setDeviceURL(String deviceURL) {
        this.deviceURL = deviceURL;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getUpdateModel() {
        return updateModel;
    }

    public void setUpdateModel(String updateModel) {
        this.updateModel = updateModel;
    }

    public String getUpdateVersion() {
        return updateVersion;
    }

    public void setUpdateVersion(String updateVersion) {
        this.updateVersion = updateVersion;
    }

    public String getUpdateVersionAfter() {
        return updateVersionAfter;
    }

    public void setUpdateVersionAfter(String updateVersionAfter) {
        this.updateVersionAfter = updateVersionAfter;
    }
}
