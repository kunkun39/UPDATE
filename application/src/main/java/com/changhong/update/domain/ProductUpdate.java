package com.changhong.update.domain;

import com.changhong.common.domain.EntityBase;
import org.springframework.util.StringUtils;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 下午2:51
 */
public class ProductUpdate extends EntityBase {

    private UpdateFile updateFile;

    private String updateURL;//这个是外链地址

    private Product product;

    private String updateWay;

    //下面的属性石共有属性
    private String testFlag;

    private String fromFilter;

    private String toFilter;

    private String versionCompareWay;

    //下面是固件升级个差分升级的属性
    private String updateModel;

    private String softwareVersion;

    private String updateType;

    private String macFilter;

    private String signatureType;

    private String guJianVersion;

    private String yingJianVersion;

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

    public ProductUpdate() {
    }

    //下面是固件升级个差分升级的构造方法
    public ProductUpdate(UpdateFile updateFile, Product product, String updateWay, String updateURL,
                         String softwareVersion, String updateType, String macFilter, String signatureType, String testFlag,
                         String guJianVersion, String yingJianVersion, String view, String updateModel,
                         String fromFilter, String toFilter, String versionCompareWay) {
        this.updateFile = updateFile;
        this.product = product;
        this.updateWay = updateWay;
        this.updateURL = updateURL;
        this.softwareVersion = softwareVersion;
        this.updateType = updateType;
        this.macFilter = macFilter;
        this.signatureType = signatureType;
        this.testFlag = testFlag;
        this.guJianVersion = guJianVersion;
        this.yingJianVersion = yingJianVersion;
        this.view = view;
        this.updateModel = updateModel;

        this.fromFilter = fromFilter;
        this.toFilter = toFilter;
        this.versionCompareWay = versionCompareWay;
    }

    //下面是DVB升级的构造方法
    public ProductUpdate(UpdateFile updateFile, Product product, String updateWay, String updateURL,
                         String softwareVersion, String updateType, String macFilter, String signatureType, String testFlag,
                         String guJianVersion, String yingJianVersion, String view, String updateModel,
                         String dvbVersion, String dvbProviderCode, String caType, String caVersion, String caDependVersion,
                         String fromFilter, String toFilter, String versionCompareWay) {
        this.updateFile = updateFile;
        this.product = product;
        this.updateWay = updateWay;
        this.updateURL = updateURL;

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

        this.fromFilter = fromFilter;
        this.toFilter = toFilter;
        this.versionCompareWay = versionCompareWay;
    }

    //二进制APP升级构造方法
    public ProductUpdate(UpdateFile updateFile, Product product, String updateWay, String updateURL, String testFlag,
                         String appPackage, String appVersionRange, String appVersion, String appSignatureType) {
        this.updateFile = updateFile;
        this.product = product;
        this.updateWay = updateWay;
        this.updateURL = updateURL;
        this.testFlag = testFlag;

        this.appPackage = appPackage;
        this.appVersionRange = appVersionRange;
        this.appVersion = appVersion;
        this.appSignatureType = appSignatureType;
    }

    //二进制数据包升级构造方法
    public ProductUpdate(UpdateFile updateFile, Product product, String updateWay, String updateURL, String testFlag,
                         String programName, String programVersion, String programSignatureType) {
        this.updateFile = updateFile;
        this.product = product;
        this.updateWay = updateWay;
        this.updateURL = updateURL;
        this.testFlag = testFlag;

        this.programName = programName;
        this.programVersion = programVersion;
        this.programSignatureType = programSignatureType;
    }

    public static String getUpdateWayName(String updateWay) {
        if ("1".equals(updateWay)) {
            return "1 - 固件升级";
        } else if ("2".equals(updateWay)) {
            return "2 - 差分升级";
        } else if ("3".equals(updateWay)) {
            return "3 - 数字电视应用程序升级";
        } else if ("4".equals(updateWay)) {
            return "4 - 应用程序升级";
        } else {
            return "5 - 二进制数据包升级";
        }
    }

    public String getUpdateWayName() {
        if ("1".equals(this.updateWay)) {
            return "1 - 固件升级";
        } else if ("2".equals(this.updateWay)) {
            return "2 - 差分升级";
        } else if ("3".equals(this.updateWay)) {
            return "3 - 数字电视应用程序升级";
        } else if ("4".equals(this.updateWay)) {
            return "4 - 应用程序升级";
        } else {
            return "5 - 二进制数据包升级";
        }
    }

    public String getUpdateVersionName() {
        if ("1".equals(updateWay)) {
            return StringUtils.hasText(guJianVersion) ? guJianVersion : "_ignore";
        } else if ("2".equals(updateWay)) {
            return StringUtils.hasText(guJianVersion) ? guJianVersion : "_ignore";
        } else if ("3".equals(updateWay)) {
            return dvbVersion;
        } else if ("4".equals(updateWay)) {
            return appVersion;
        } else {
            return programVersion;
        }
    }

    /**
     * 这个方法只能在保存钱调用
     */
    public void adjustMacRange() {
        if (StringUtils.hasText(fromFilter) && !StringUtils.hasText(toFilter)) {
            toFilter = fromFilter;
        }
        if (!StringUtils.hasText(fromFilter) && StringUtils.hasText(toFilter)) {
            fromFilter = toFilter;
        }
    }

    /*************************************************GE/SET***********************************************************/

    public UpdateFile getUpdateFile() {
        return updateFile;
    }

    public void setUpdateFile(UpdateFile updateFile) {
        this.updateFile = updateFile;
    }

    public String getUpdateURL() {
        return updateURL;
    }

    public void setUpdateURL(String updateURL) {
        this.updateURL = updateURL;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getUpdateWay() {
        return updateWay;
    }

    public void setUpdateWay(String updateWay) {
        this.updateWay = updateWay;
    }

    public String getFromFilter() {
        return fromFilter;
    }

    public void setFromFilter(String fromFilter) {
        this.fromFilter = fromFilter;
    }

    public String getToFilter() {
        return toFilter;
    }

    public void setToFilter(String toFilter) {
        this.toFilter = toFilter;
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
}
