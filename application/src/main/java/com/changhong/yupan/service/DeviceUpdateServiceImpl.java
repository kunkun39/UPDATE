package com.changhong.yupan.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.changhong.common.utils.CHStringUtils;
import com.changhong.common.web.application.ApplicationEventPublisher;
import com.changhong.update.domain.ProductUpdate;
import com.changhong.yupan.domain.DeviceUpdateResponse;
import com.changhong.yupan.repository.UpdateDao;
import com.changhong.yupan.web.event.ClientInfoUpdateEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-11
 * Time: 下午3:31
 */
@Service("deviceUpdateService")
public class DeviceUpdateServiceImpl implements DeviceUpdateService {

    private static final Log logger = LogFactory.getLog(DeviceUpdateServiceImpl.class);

    @Autowired
    private UpdateDao updateDao;

    @Value("${application.web.url}")
    private String webAddress;

    @Value("${project.update.client.gujian}")
    private boolean updateClientGuJian;


    /***********************************************升级相关***********************************************************/

    public DeviceUpdateResponse obtainUpdateData(String json) {
        long beginHandle = System.currentTimeMillis();

        DeviceUpdateResponse response = null;
        try {
            JSONObject o = JSON.parseObject(json);
            JSONObject client = (JSONObject) o.get("client");
            String datatype = client.getString("datatype");
            String model = client.getString("model");
            String username = client.getString("username");

            if ("1".equals(datatype) || "2".equals(datatype)) {
                response = generateGuJianChaFenUpdate(client, username, datatype, model);

            } else if ("3".equals(datatype)) {
                response = generateDvbUpdate(client, username, datatype, model);

            } else if ("4".equals(datatype)) {
                response = generateAppUpdate(client, datatype, model);

            } else if ("5".equals(datatype)) {
                response = generateBinUpdate(client, datatype, model);
            }

            /******************************************统计部分黄金分割线************************************************/

            if (response != null) {
                //插入用户升级历史记录
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

                long endHandle = System.currentTimeMillis();
                long during = endHandle - beginHandle;
                logger.info("device " + username + " update succesful with way " + datatype + " and take " + during + "ms");
            }

        } catch (JSONException e) {
            logger.error("parse json error", e);
        }

        return response;
    }

    /**
     * {
        "client": {
            "model": "hi3716cv200",
            "datatype": "2",
            "androidsdk": "17",
            "macadress":"1100",
            "signtype": "_ignore",
            "testmode": "true",
            "firmware_diffversion": "1.0"  or "firmware_version": "1.0"
            }
        }
     */
    private DeviceUpdateResponse generateGuJianChaFenUpdate(JSONObject client, String username, String datatype, String model) {
        String androidsdk = client.getString("androidsdk");
        if (!StringUtils.hasText(datatype) || !StringUtils.hasText(model) || !StringUtils.hasText(androidsdk)) {
            return null;
        }

        List<ProductUpdate> updates = updateDao.findProductUpdate(model, datatype, androidsdk);
        if (updates == null || updates.isEmpty()) {
            return null;
        }

        String macadress = client.getString("macadress");
        String signtype = client.getString("signtype");
        String testmode = client.getString("testmode");
        String firmwareversion = "";
        if ("1".equals(datatype)) {
            firmwareversion = client.getString("firmwareversion");
        } else {
            firmwareversion = client.getString("firmware_diffversion");
        }
        String hardwareversion = client.getString("hardwareversion");


        for (ProductUpdate update : updates) {
            boolean passed = true;

            //1 - 如果是普通升级(0)，所有的字段必须比较，
            //2 - 如果是忽略固件版本升级(1), 固件版本不用比较
            //3 - 如果是只对比产品型号，除标记测试字段需要比较，其余的都不用比较
            String firmwareversionD = update.getGuJianVersion();
            String updateType = update.getUpdateType();
            if (!"2".equals(updateType)) {
                if ("0".equals(updateType) && StringUtils.hasText(firmwareversionD)) {
                    String firmwareVersionCompareWayD = update.getVersionCompareWay();

                    if ("1".equals(firmwareVersionCompareWayD)) {
                        if (Double.valueOf(firmwareversion) >= Double.valueOf(firmwareversionD)) {
                            passed = false;
                        }
                    } else if ("2".equals(firmwareVersionCompareWayD)) {
                        //服务端的小数点后一位+整数部分等于客户端
                        firmwareversionD = CHStringUtils.toFixNumberStringAfterDot(firmwareversionD, 4);
                        firmwareversion = CHStringUtils.toFixNumberStringAfterDot(firmwareversion, 4);

                        int dotIndexD = firmwareversionD.indexOf(".");
                        int dotIndex = firmwareversionD.indexOf(".");

                        String prefixD = firmwareversionD.substring(0, dotIndexD + 2);
                        String suffixD = firmwareversionD.substring(dotIndexD + 2);
                        String prefix = firmwareversion.substring(0, dotIndex + 2);
                        String suffix = firmwareversion.substring(dotIndex + 2);

                        boolean canMatch = false;
                        if (prefixD.equals(prefix) && Double.valueOf(suffixD) > Double.valueOf(suffix)) {
                            canMatch = true;
                        }
                        if (!canMatch) {
                            passed = false;
                        }
                    } else {
                        if (!Double.valueOf(firmwareversion).equals(Double.valueOf(firmwareversionD))) {
                            passed = false;
                        }
                    }
                }

                String hardwareversionD = update.getYingJianVersion();
                if (StringUtils.hasText(hardwareversionD) && !hardwareversionD.equals(hardwareversion)) {
                    passed = false;
                }
                String macadressD = update.getMacFilter();
                if (StringUtils.hasText(macadressD) && !macadress.endsWith(macadressD)) {
                    passed = false;
                }
                String signtypeD = update.getSignatureType();
                if (StringUtils.hasText(signtypeD) && !signtypeD.equals(signtype)) {
                    passed = false;
                }

                //验证用户名范围
                String fromFilterD = update.getFromFilter();
                String toFilterD = update.getToFilter();
                if(!validateUsernameInRange(username, fromFilterD, toFilterD)) {
                    passed = false;
                }
            }

            //测试标记什么时候都需要比较
            String testmodeD = update.getTestFlag();
            if (!testmodeD.equals(testmode)) {
                passed = false;
            }

            if (passed) {
                return new DeviceUpdateResponse(update, webAddress);
            }
        }

        return null;
    }

    /**
     * {
        "client": {
            "model": "hi3716cv200",
            "datatype": "1",
            "androidsdk": "17",
            "macadress":" 1100",
            "signtype": "_ignore",
            "testmode": "true",
            "firmwareversion": "1.0",
            "operatorcode":"0001",
            "catype":"01",
            "caversion":"_ignore",
            "refercoreversion":"_ignore"
            }
        }
     */
    private DeviceUpdateResponse generateDvbUpdate(JSONObject client, String username, String datatype, String model) {
        String dtvversion = client.getString("dtvversion");
        if (!StringUtils.hasText(datatype) || !StringUtils.hasText(model) || !StringUtils.hasText(dtvversion)) {
            return null;
        }

        List<ProductUpdate> updates = updateDao.findProductUpdate(model, datatype, dtvversion);

        String androidsdk = client.getString("androidsdk");
        String macadress = client.getString("macadress");
        String signtype = client.getString("signtype");
        String testmode = client.getString("testmode");
        String firmwareversion = client.getString("firmwareversion");
        String hardwareversion = client.getString("hardwareversion");
        String operatorcode = client.getString("operatorcode");
        String catype = client.getString("catype");
        String caversion = client.getString("caversion");
        String refercoreversion = client.getString("refercoreversion");

        for (ProductUpdate update : updates) {
            boolean passed = true;

            String androidsdkD = update.getSoftwareVersion();
            if (StringUtils.hasText(androidsdkD) && !androidsdk.equals(androidsdkD)) {
                passed = false;
            }

            //1 - 如果是普通升级(0)，所有的字段必须比较，
            //2 - 如果是忽略固件版本升级(1), 固件版本不用比较
            //3 - 如果是只对比产品型号，除标记测试字段需要比较，其余的都不用比较
            String firmwareversionD = update.getGuJianVersion();
            String updateType = update.getUpdateType();
            if (!"2".equals(updateType)) {
                if ("0".equals(updateType) && StringUtils.hasText(firmwareversionD)) {
                    String firmwareVersionCompareWayD = update.getVersionCompareWay();
                    if ("1".equals(firmwareVersionCompareWayD)) {
                        if (Double.valueOf(firmwareversion) >= Double.valueOf(firmwareversionD)) {
                            passed = false;
                        }
                    } else if ("2".equals(firmwareVersionCompareWayD)) {
                        //服务端的小数点后一位+整数部分等于客户端
                        firmwareversionD = CHStringUtils.toFixNumberStringAfterDot(firmwareversionD, 4);
                        firmwareversion = CHStringUtils.toFixNumberStringAfterDot(firmwareversion, 4);

                        int dotIndexD = firmwareversionD.indexOf(".");
                        int dotIndex = firmwareversionD.indexOf(".");

                        String prefixD = firmwareversionD.substring(0, dotIndexD + 2);
                        String suffixD = firmwareversionD.substring(dotIndexD + 2);
                        String prefix = firmwareversion.substring(0, dotIndex + 2);
                        String suffix = firmwareversion.substring(dotIndex + 2);

                        boolean canMatch = false;
                        if (prefixD.equals(prefix) && Double.valueOf(suffixD) > Double.valueOf(suffix)) {
                            canMatch = true;
                        }
                        if (!canMatch) {
                            passed = false;
                        }
                    } else {
                        if (!Double.valueOf(firmwareversion).equals(Double.valueOf(firmwareversionD))) {
                            passed = false;
                        }
                    }
                }

                String hardwareversionD = update.getYingJianVersion();
                if (StringUtils.hasText(hardwareversionD) && !hardwareversionD.equals(hardwareversion)) {
                    passed = false;
                }
                String macadressD = update.getMacFilter();
                if (StringUtils.hasText(macadressD) && !macadress.endsWith(macadressD)) {
                    passed = false;
                }
                String signtypeD = update.getSignatureType();
                if (StringUtils.hasText(signtypeD) && !signtypeD.equals(signtype)) {
                    passed = false;
                }
                String operatorcodeD = StringUtils.hasText(update.getDvbProviderCode()) ? update.getDvbProviderCode() : "0000";
                if (!operatorcode.equals(operatorcodeD)) {
                    passed = false;
                }
                String catypeD = update.getCaType();
                if (StringUtils.hasText(catypeD) && !catype.equals(catypeD)) {
                    passed = false;
                }
                String caversionD = update.getCaVersion();
                if (StringUtils.hasText(caversionD) && !caversion.equals(caversionD)) {
                    passed = false;
                }
                String refercoreversionD = update.getCaDependVersion();
                if (StringUtils.hasText(refercoreversionD) && !refercoreversion.equals(refercoreversionD)) {
                    passed = false;
                }

                //验证用户名范围
                String fromFilterD = update.getFromFilter();
                String toFilterD = update.getToFilter();
                if(!validateUsernameInRange(username, fromFilterD, toFilterD)) {
                    passed = false;
                }
            }

            //测试标记什么时候都需要比较
            String testmodeD = update.getTestFlag();
            if (!testmodeD.equals(testmode)) {
                passed = false;
            }

            if (passed) {
                return new DeviceUpdateResponse(update, webAddress);
            }
        }

        return null;
    }

    /**
     * {
            "client": {
                "packagename": "com.test.test",
                "datatype":"4",
                "sdkadapt":"17",
                "signtype": "_ignore",
                "appversioncode":"1",
                "testmode": "true"
            }
        }
     */
    private DeviceUpdateResponse generateAppUpdate(JSONObject client, String datatype, String model) {
        String packagename = client.getString("packagename");
        String appversioncode = client.getString("appversioncode");
        if (!StringUtils.hasText(datatype) || !StringUtils.hasText(packagename) || !StringUtils.hasText(appversioncode)) {
            return null;
        }

        List<ProductUpdate> updates = updateDao.findProductUpdate(packagename, datatype, appversioncode);

        String sdkadapt = client.getString("sdkadapt");
        String signtype = client.getString("signtype");
        String testmode = client.getString("testmode");

        for (ProductUpdate update : updates) {
            boolean passed = true;

            String sdkadaptD = update.getAppVersionRange();
            String[] ranges = StringUtils.delimitedListToStringArray(sdkadaptD, "-");
            try {
                int middle = Integer.valueOf(sdkadapt);
                int small = Integer.valueOf(ranges[0]);
                int big = Integer.valueOf(ranges[1]);
                if (middle < small || middle > big) {
                    passed = false;
                }
            } catch (Exception e) {
                logger.error("app update with number format exception", e);
                passed = false;
            }
            String signtypeD = update.getAppSignatureType();
            if (StringUtils.hasText(signtypeD) && !signtypeD.equals(signtype)) {
                passed = false;
            }
            String testmodeD = update.getTestFlag();
            if (!testmodeD.equals(testmode)) {
                passed = false;
            }

            if (passed) {
                return new DeviceUpdateResponse(update, webAddress);
            }
        }

        return null;
    }

    /**
     * {
        "dataclient": {
            "model": "hi3716cv200",
            "dataname":"hireferko",
            "datatype":"5",
            "signtype": "_ignore",
            "dataversioncode":"1",
            "testmode": "true"
        }
    }
     */
    private DeviceUpdateResponse generateBinUpdate(JSONObject client, String datatype, String model) {
        String dataname = client.getString("dataname");
        String dataversioncode = client.getString("dataversioncode");
        if (!StringUtils.hasText(datatype) || !StringUtils.hasText(model) || !StringUtils.hasText(dataname) || !StringUtils.hasText(dataversioncode)) {
            return null;
        }

        List<ProductUpdate> updates = updateDao.findProductUpdate(model, datatype, dataname + "|" + dataversioncode);

        String signtype = client.getString("signtype");
        String testmode = client.getString("testmode");

        for (ProductUpdate update : updates) {
            boolean passed = true;

            String signtypeD = update.getProgramSignatureType();
            if (StringUtils.hasText(signtypeD) && !signtypeD.equals(signtype)) {
                passed = false;
            }
            String testmodeD = update.getTestFlag();
            if (!testmodeD.equals(testmode)) {
                passed = false;
            }

            if (passed) {
                return new DeviceUpdateResponse(update, webAddress);
            }
        }

        return null;
    }

    //不区分大小写
    public boolean validateUsernameInRange(String username, String fromFilter, String toFitler) {
        boolean passed = false;
        username = username.toUpperCase();

        if (StringUtils.hasText(username)) {
            if (StringUtils.hasText(fromFilter) && StringUtils.hasText(toFitler)) {
                fromFilter = fromFilter.toUpperCase();
                toFitler = toFitler.toUpperCase();
                if (username.toUpperCase().compareTo(fromFilter) >= 0 && username.compareTo(toFitler) <= 0) {
                    passed = true;
                }
            } else if (!StringUtils.hasText(fromFilter) && StringUtils.hasText(toFitler)) {
                toFitler = toFitler.toUpperCase();
                if (username.compareTo(toFitler) <= 0) {
                    passed = true;
                }
            } else if (StringUtils.hasText(fromFilter) && !StringUtils.hasText(toFitler)) {
                toFitler = toFitler.toUpperCase();
                if (username.compareTo(fromFilter) >= 0) {
                    passed = true;
                }
            } else if (!StringUtils.hasText(fromFilter) && !StringUtils.hasText(toFitler)) {
                passed = true;
            }
        }
        return passed;
    }
}
