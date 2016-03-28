package com.changhong.update.web.facade.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 下午4:22
 */
public class ProductUpdateHistoryDTO {

    private int id;

    private int productId;

    private Date uploadTime;

    private int updateUploadFileId;

    private String updateUploadFileName;

    private MultipartFile file;

    private String updateWay;

    private String updateVersion;

    private String updateWayName;

    private String updateURL;

    private String updateCategoryName;

    private String jsonPath;

    private String dataPath;

    //公共部分数据
    private String testFlag;

    private String versionCompareWay;

    ////下面是固件升级个差分升级的属性
    private String softwareVersion;

    private String updateType;

    private String macFilter;

    private String signatureType;

    private String guJianVersion;

    private String yingJianVersion;

    private String updateModel;

    private String view;

    //下面是DVB升级的属性
    private String dvbVersion;

    private String dvbProviderCode;

    private String caType;

    private String caVersion;

    private String caDependVersion;

    //第三方app升级的属性
    private String appPackage;

    private String appVersionRange;

    private String appVersion;

    private String appSignatureType;

    //二进制数据包升级

    private String programName;

    private String programVersion;

    private String programSignatureType;

    private MultipartFile snFile;

    private String snUploadFile;

    //客户端版本
    private String clientVersion;

    private String apkUpdateURL;

    public ProductUpdateHistoryDTO() {
        updateWay = "1";
        updateType = "0";
        testFlag = "false";
        view = "0";
        updateModel = "1";
        versionCompareWay = "1";
    }

    //用于列表显示
    public ProductUpdateHistoryDTO(int id, int productId, Date uploadTime, String updateWay, String updateVersion,
                                   String updateCategoryName, String jsonPath, String dataPath,
                                   int fileId) {
        this.id = id;
        this.productId = productId;
        this.uploadTime = uploadTime;
        this.updateWay = updateWay;
        this.updateVersion = updateVersion;
        this.updateCategoryName = updateCategoryName;
        this.jsonPath = jsonPath;
        this.dataPath = dataPath;
        this.updateUploadFileId = fileId;
        this.snUploadFile = "devices.txt";
    }

    public ProductUpdateHistoryDTO(int id, String updateWay,  String updateWayName,
                                   String updateURL, int updateUploadFileId, String updateUploadFileName,
                                   String softwareVersion, String updateType, String macFilter, String signatureType,
                                   String testFlag, String guJianVersion, String yingJianVersion, String view, String updateModel,
                                   String versionCompareWay, String clientVersion, String apkUpdateURL) {
        this.id = id;
        this.updateURL = updateURL;
        this.updateWay = updateWay;
        this.updateWayName = updateWayName;
        this.updateUploadFileId = updateUploadFileId;
        this.updateUploadFileName = updateUploadFileName;

        this.softwareVersion = softwareVersion;
        this.updateType = updateType;
        this.macFilter = macFilter;
        this.signatureType = signatureType;
        this.testFlag = testFlag;
        this.guJianVersion = guJianVersion;
        this.yingJianVersion = yingJianVersion;
        this.view = view;
        this.updateModel = updateModel;

        this.versionCompareWay = versionCompareWay;
        this.snUploadFile = "devices.txt";

        this.clientVersion = clientVersion;
        this.apkUpdateURL = apkUpdateURL;
    }

    public ProductUpdateHistoryDTO(int id, String updateWay, String updateWayName,
                                   String updateURL, int updateUploadFileId, String updateUploadFileName,
                                   String softwareVersion, String updateType, String macFilter, String signatureType,
                                   String testFlag, String guJianVersion, String yingJianVersion, String view, String updateModel,
                                   String dvbVersion, String dvbProviderCode, String caType, String caVersion, String caDependVersion,
                                   String versionCompareWay, String clientVersion, String apkUpdateURL) {
        this.id = id;
        this.updateURL = updateURL;
        this.updateWay = updateWay;
        this.updateWayName = updateWayName;
        this.updateUploadFileId = updateUploadFileId;
        this.updateUploadFileName = updateUploadFileName;

        this.softwareVersion = softwareVersion;
        this.updateType = updateType;
        this.macFilter = macFilter;
        this.signatureType = signatureType;
        this.testFlag = testFlag;
        this.guJianVersion = guJianVersion;
        this.yingJianVersion = yingJianVersion;
        this.view = view;
        this.updateModel = updateModel;

        this.dvbVersion = dvbVersion;
        this.dvbProviderCode = dvbProviderCode;
        this.caType = caType;
        this.caVersion = caVersion;
        this.caDependVersion = caDependVersion;

        this.versionCompareWay = versionCompareWay;
        this.snUploadFile = "devices.txt";

        this.clientVersion = clientVersion;
        this.apkUpdateURL = apkUpdateURL;
    }

    public ProductUpdateHistoryDTO(int id, String updateWay, String updateWayName,
                                   String updateURL, int updateUploadFileId, String updateUploadFileName, String testFlag,
                                   String appPackage, String appVersionRange, String appVersion, String appSignatureType,
                                   String clientVersion, String apkUpdateURL) {
        this.id = id;
        this.updateURL = updateURL;
        this.updateWay = updateWay;
        this.updateWayName = updateWayName;
        this.updateUploadFileId = updateUploadFileId;
        this.updateUploadFileName = updateUploadFileName;
        this.testFlag = testFlag;

        this.appPackage = appPackage;
        this.appVersionRange = appVersionRange;
        this.appVersion = appVersion;
        this.appSignatureType = appSignatureType;
        this.snUploadFile = "devices.txt";

        this.clientVersion = clientVersion;
        this.apkUpdateURL = apkUpdateURL;
    }

    public ProductUpdateHistoryDTO(int id, String updateWay, String updateWayName,
                                   String updateURL, int updateUploadFileId, String updateUploadFileName, String testFlag,
                                   String programName, String programVersion, String programSignatureType,
                                   String clientVersion, String apkUpdateURL) {
        this.id = id;
        this.updateURL = updateURL;
        this.updateWay = updateWay;
        this.updateWayName = updateWayName;
        this.updateUploadFileId = updateUploadFileId;
        this.updateUploadFileName = updateUploadFileName;
        this.testFlag = testFlag;

        this.programName = programName;
        this.programVersion = programVersion;
        this.programSignatureType = programSignatureType;
        this.snUploadFile = "devices.txt";

        this.clientVersion = clientVersion;
        this.apkUpdateURL = apkUpdateURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUpdateURL() {
        return updateURL;
    }

    public void setUpdateURL(String updateURL) {
        this.updateURL = updateURL;
    }

    public String getUpdateVersion() {
        return updateVersion;
    }

    public void setUpdateVersion(String updateVersion) {
        this.updateVersion = updateVersion;
    }

    public int getUpdateUploadFileId() {
        return updateUploadFileId;
    }

    public void setUpdateUploadFileId(int updateUploadFileId) {
        this.updateUploadFileId = updateUploadFileId;
    }

    public String getUpdateUploadFileName() {
        return updateUploadFileName;
    }

    public void setUpdateUploadFileName(String updateUploadFileName) {
        this.updateUploadFileName = updateUploadFileName;
    }

    public String getUpdateWay() {
        return updateWay;
    }

    public void setUpdateWay(String updateWay) {
        this.updateWay = updateWay;
    }

    public String getUpdateWayName() {
        return updateWayName;
    }

    public void setUpdateWayName(String updateWayName) {
        this.updateWayName = updateWayName;
    }

    public String getUpdateCategoryName() {
        return updateCategoryName;
    }

    public void setUpdateCategoryName(String updateCategoryName) {
        this.updateCategoryName = updateCategoryName;
    }

    public String getJsonPath() {
        return jsonPath;
    }

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public String getVersionCompareWay() {
        return versionCompareWay;
    }

    public void setVersionCompareWay(String versionCompareWay) {
        this.versionCompareWay = versionCompareWay;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public String getMacFilter() {
        return macFilter;
    }

    public void setMacFilter(String macFilter) {
        this.macFilter = macFilter;
    }

    public String getSignatureType() {
        return signatureType;
    }

    public void setSignatureType(String signatureType) {
        this.signatureType = signatureType;
    }

    public String getTestFlag() {
        return testFlag;
    }

    public void setTestFlag(String testFlag) {
        this.testFlag = testFlag;
    }

    public String getGuJianVersion() {
        return guJianVersion;
    }

    public void setGuJianVersion(String guJianVersion) {
        this.guJianVersion = guJianVersion;
    }

    public String getYingJianVersion() {
        return yingJianVersion;
    }

    public void setYingJianVersion(String yingJianVersion) {
        this.yingJianVersion = yingJianVersion;
    }

    public String getUpdateModel() {
        return updateModel;
    }

    public void setUpdateModel(String updateModel) {
        this.updateModel = updateModel;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getDvbVersion() {
        return dvbVersion;
    }

    public void setDvbVersion(String dvbVersion) {
        this.dvbVersion = dvbVersion;
    }

    public String getDvbProviderCode() {
        return dvbProviderCode;
    }

    public void setDvbProviderCode(String dvbProviderCode) {
        this.dvbProviderCode = dvbProviderCode;
    }

    public String getCaType() {
        return caType;
    }

    public void setCaType(String caType) {
        this.caType = caType;
    }

    public String getCaVersion() {
        return caVersion;
    }

    public void setCaVersion(String caVersion) {
        this.caVersion = caVersion;
    }

    public String getCaDependVersion() {
        return caDependVersion;
    }

    public void setCaDependVersion(String caDependVersion) {
        this.caDependVersion = caDependVersion;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public String getAppVersionRange() {
        return appVersionRange;
    }

    public void setAppVersionRange(String appVersionRange) {
        this.appVersionRange = appVersionRange;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppSignatureType() {
        return appSignatureType;
    }

    public void setAppSignatureType(String appSignatureType) {
        this.appSignatureType = appSignatureType;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getProgramVersion() {
        return programVersion;
    }

    public void setProgramVersion(String programVersion) {
        this.programVersion = programVersion;
    }

    public String getProgramSignatureType() {
        return programSignatureType;
    }

    public void setProgramSignatureType(String programSignatureType) {
        this.programSignatureType = programSignatureType;
    }

    public MultipartFile getSnFile() {
        return snFile;
    }

    public void setSnFile(MultipartFile snFile) {
        this.snFile = snFile;
    }

    public String getSnUploadFile() {
        return snUploadFile;
    }

    public void setSnUploadFile(String snUploadFile) {
        this.snUploadFile = snUploadFile;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getApkUpdateURL() {
        return apkUpdateURL;
    }

    public void setApkUpdateURL(String apkUpdateURL) {
        this.apkUpdateURL = apkUpdateURL;
    }
}
