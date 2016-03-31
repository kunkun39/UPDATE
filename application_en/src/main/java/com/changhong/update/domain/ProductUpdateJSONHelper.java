package com.changhong.update.domain;

import com.changhong.update.service.DocumentPathResolver;
import com.changhong.update.service.DocumentServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * User: Jack Wang
 * Date: 14-4-15
 * Time: 下午12:56
 */
public class ProductUpdateJSONHelper {

    private static final Log logger = LogFactory.getLog(ProductUpdateJSONHelper.class);

    public static final String IgnoreMark = "_ignore";

    public static String generateJSONContent(ProductUpdate update, String webUrl) {
        String updateWay = update.getUpdateWay();

        try {
            if("1".equals(updateWay) || "2".equals(updateWay)) {
                return generateGuJianChaFenUpdateJson(update, webUrl);
            } else if ("3".equals(updateWay)) {
                return generateDvbUpdateJson(update, webUrl);
            } else if ("4".equals(updateWay)) {
                return generateAppUpdateJson(update, webUrl);
            } else if ("5".equals(updateWay)) {
                return generateBinUpdateJson(update, webUrl);
            }

        } catch (Exception e) {
            logger.error("generate json error", e);
        }

        return "";
    }

    private static String generateGuJianChaFenUpdateJson(ProductUpdate update, String webUrl) throws JSONException {
        JSONObject firmware = new JSONObject();
        String model = update.getProduct().getModel();
        String updateWay = update.getUpdateWay();

        JSONObject inner = new JSONObject();
        inner.put("model", model);
        inner.put("datatype", updateWay);
        inner.put("androidsdk", update.getSoftwareVersion());
        inner.put("updatetype", update.getUpdateType());
        inner.put("macfilter", StringUtils.hasText(update.getMacFilter()) ? update.getMacFilter() : IgnoreMark);
        inner.put("signtype", StringUtils.hasText(update.getSignatureType()) ? update.getSignatureType() : IgnoreMark);
        inner.put("testmode", update.getTestFlag());
        inner.put("hardwareversion", StringUtils.hasText(update.getYingJianVersion()) ? update.getYingJianVersion() : IgnoreMark);
        if ("1".equals(updateWay)) {
            inner.put("firmwareversion", StringUtils.hasText(update.getGuJianVersion()) ? update.getGuJianVersion() : IgnoreMark);
        } else {
            inner.put("firmware_diffversion", StringUtils.hasText(update.getGuJianVersion()) ? update.getGuJianVersion() : IgnoreMark);
        }
        inner.put("view", update.getView());

        JSONArray dataurls = new JSONArray();
        UpdateFile file = update.getUpdateFile();
        if (file != null) {
            JSONObject dataurl = new JSONObject();
            dataurl.put("url", webUrl + file.getActualFilePath() + "/" + file.getActualFileName());
            dataurls.put(dataurl);
        }
        String[] urls = StringUtils.delimitedListToStringArray(update.getUpdateURL(), "|");
        if (urls.length > 0) {
            for (String url : urls) {
                JSONObject dataurl = new JSONObject();
                dataurl.put("url", url);
                dataurls.put(dataurl);
            }
        }
        inner.put("dataurls", dataurls);

        String jsonPath = DocumentPathResolver.generateUploadFileNamePath(update);
        String jsonName = DocumentPathResolver.generateJsonFileName(update);
        inner.put("jsonurl", webUrl + jsonPath + "/" + jsonName);

        if ("1".equals(updateWay)) {
            firmware.put("firmware", inner);
        } else {
            firmware.put("firmware_diff", inner);
        }

        return firmware.toString();
    }

    private static String generateDvbUpdateJson(ProductUpdate update, String webUrl) throws JSONException {
        JSONObject firmware = new JSONObject();
        String model = update.getProduct().getModel();
        String updateWay = update.getUpdateWay();

        JSONObject inner = new JSONObject();
        inner.put("model", model);
        inner.put("datatype", updateWay);
        inner.put("operatorcode", StringUtils.hasText(update.getDvbProviderCode()) ? update.getDvbProviderCode() : IgnoreMark);
        inner.put("catype", StringUtils.hasText(update.getCaType()) ? update.getCaType() : IgnoreMark);
        inner.put("caversion", StringUtils.hasText(update.getCaVersion()) ? update.getCaVersion() : IgnoreMark);
        inner.put("refercoreversion", StringUtils.hasText(update.getCaDependVersion()) ? update.getCaDependVersion() : IgnoreMark);
        inner.put("androidsdk", update.getSoftwareVersion());
        inner.put("updatetype", update.getUpdateType());
        inner.put("macfilter", StringUtils.hasText(update.getMacFilter()) ? update.getMacFilter() : IgnoreMark);
        inner.put("signtype", StringUtils.hasText(update.getSignatureType()) ? update.getSignatureType() : IgnoreMark);
        inner.put("testmode", update.getTestFlag());
        inner.put("firmwareversion", StringUtils.hasText(update.getGuJianVersion()) ? update.getGuJianVersion() : IgnoreMark);
        inner.put("hardwareversion", StringUtils.hasText(update.getYingJianVersion()) ? update.getYingJianVersion() : IgnoreMark);
        inner.put("dtvversion", update.getDvbVersion());
        inner.put("view", update.getView());

        JSONArray dataurls = new JSONArray();
        UpdateFile file = update.getUpdateFile();
        if (file != null) {
            JSONObject dataurl = new JSONObject();
            dataurl.put("url", webUrl + file.getActualFilePath() + "/" + file.getActualFileName());
            dataurls.put(dataurl);
        }
        String[] urls = StringUtils.delimitedListToStringArray(update.getUpdateURL(), "|");
        if (urls.length > 0) {
            for (String url : urls) {
                JSONObject dataurl = new JSONObject();
                dataurl.put("url", url);
                dataurls.put(dataurl);
            }
        }
        inner.put("dataurls", dataurls);

        String jsonPath = DocumentPathResolver.generateUploadFileNamePath(update);
        String jsonName = DocumentPathResolver.generateJsonFileName(update);
        inner.put("jsonurl", webUrl + jsonPath + "/" + jsonName);
        firmware.put("dtvapk", inner);

        return firmware.toString();
    }

    private static String generateAppUpdateJson(ProductUpdate update, String webUrl) throws JSONException {
        JSONObject firmware = new JSONObject();
        String model = update.getProduct().getModel();
        String updateWay = update.getUpdateWay();

        JSONObject inner = new JSONObject();
        inner.put("packagename", model);
        inner.put("datatype", updateWay);
        inner.put("sdkadapt", update.getAppVersionRange());
        inner.put("signtype", StringUtils.hasText(update.getAppSignatureType()) ? update.getAppSignatureType() : IgnoreMark);
        inner.put("appversioncode", update.getAppVersion());
        inner.put("testmode", update.getTestFlag());

        JSONArray dataurls = new JSONArray();
        UpdateFile file = update.getUpdateFile();
        if (file != null) {
            JSONObject dataurl = new JSONObject();
            dataurl.put("url", webUrl + file.getActualFilePath() + "/" + file.getActualFileName());
            dataurls.put(dataurl);
        }
        String[] urls = StringUtils.delimitedListToStringArray(update.getUpdateURL(), "|");
        if (urls.length > 0) {
            for (String url : urls) {
                JSONObject dataurl = new JSONObject();
                dataurl.put("url", url);
                dataurls.put(dataurl);
            }
        }
        inner.put("dataurls", dataurls);

        String jsonPath = DocumentPathResolver.generateUploadFileNamePath(update);
        String jsonName = DocumentPathResolver.generateJsonFileName(update);
        inner.put("jsonurl", webUrl + jsonPath + "/" + jsonName);
        firmware.put("app", inner);

        return firmware.toString();
    }

    private static String generateBinUpdateJson(ProductUpdate update, String webUrl) throws JSONException {
        JSONObject firmware = new JSONObject();
        String model = update.getProduct().getModel();
        String updateWay = update.getUpdateWay();

        JSONObject inner = new JSONObject();
        inner.put("model", model);
        inner.put("dataname", update.getProgramName());
        inner.put("datatype", updateWay);
        inner.put("signtype", StringUtils.hasText(update.getProgramSignatureType()) ? update.getProgramSignatureType() : IgnoreMark);
        inner.put("dataversioncode", update.getProgramVersion());
        inner.put("testmode", update.getTestFlag());

        JSONArray dataurls = new JSONArray();
        UpdateFile file = update.getUpdateFile();
        if (file != null) {
            JSONObject dataurl = new JSONObject();
            dataurl.put("url", webUrl + file.getActualFilePath() + "/" + file.getActualFileName());
            dataurls.put(dataurl);
        }
        String[] urls = StringUtils.delimitedListToStringArray(update.getUpdateURL(), "|");
        if (urls.length > 0) {
            for (String url : urls) {
                JSONObject dataurl = new JSONObject();
                dataurl.put("url", url);
                dataurls.put(dataurl);
            }
        }
        inner.put("dataurls", dataurls);

        String jsonPath = DocumentPathResolver.generateUploadFileNamePath(update);
        String jsonName = DocumentPathResolver.generateJsonFileName(update);
        inner.put("jsonurl", webUrl + jsonPath + "/" + jsonName);
        firmware.put("data", inner);

        return firmware.toString();
    }
}
