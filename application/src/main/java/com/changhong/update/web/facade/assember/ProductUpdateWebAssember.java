package com.changhong.update.web.facade.assember;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.update.domain.Product;
import com.changhong.update.domain.ProductUpdate;
import com.changhong.update.domain.UpdateFile;
import com.changhong.update.service.DocumentPathResolver;
import com.changhong.update.service.DocumentServiceImpl;
import com.changhong.update.web.facade.dto.ProductUpdateHistoryDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-11
 * Time: 上午10:46
 */
public class ProductUpdateWebAssember {

    public static ProductUpdate toProductUpdateDomain(ProductUpdateHistoryDTO updateDTO, UpdateFile updateFile) {
        ProductUpdate update = null;
        if(updateDTO == null) return null;

        String updateWay = updateDTO.getUpdateWay();
        String updateURL = updateDTO.getUpdateURL();
        Product product = (Product) EntityLoadHolder.getUserDao().findById(updateDTO.getProductId(), Product.class);

        if("1".equals(updateWay) || "2".equals(updateWay)) {
            if (updateDTO.getId() > 0) {
                update = (ProductUpdate) EntityLoadHolder.getUserDao().findById(updateDTO.getId(), ProductUpdate.class);
                update.setUpdateURL(updateURL);
                update.setSoftwareVersion(updateDTO.getSoftwareVersion());
                update.setUpdateType(updateDTO.getUpdateType());
                update.setMacFilter(updateDTO.getMacFilter());
                update.setSignatureType(updateDTO.getSignatureType());
                update.setTestFlag(updateDTO.getTestFlag());
                update.setGuJianVersion(updateDTO.getGuJianVersion());
                update.setGuJianVersionAfter(updateDTO.getGuJianVersionAfter());
                update.setYingJianVersion(updateDTO.getYingJianVersion());
                update.setView(updateDTO.getView());
                update.setUpdateModel(updateDTO.getUpdateModel());

                update.setVersionCompareWay(updateDTO.getVersionCompareWay());
                update.setClientVersion(updateDTO.getClientVersion());
                update.setApkUpdateURL(updateDTO.getApkUpdateURL());

                if (updateFile != null) {
                    update.setUpdateFile(updateFile);
                }
            } else {
                update = new ProductUpdate(updateFile, product, updateWay, updateURL, updateDTO.getSoftwareVersion(), updateDTO.getUpdateType(), updateDTO.getMacFilter(), updateDTO.getSignatureType(),
                        updateDTO.getTestFlag(), updateDTO.getGuJianVersion(), updateDTO.getGuJianVersionAfter(), updateDTO.getYingJianVersion(), updateDTO.getView(), updateDTO.getUpdateModel(),
                        updateDTO.getVersionCompareWay(), updateDTO.getClientVersion(), updateDTO.getApkUpdateURL());
            }

        } else if ("3".equals(updateWay)) {
            if (updateDTO.getId() > 0) {
                update = (ProductUpdate) EntityLoadHolder.getUserDao().findById(updateDTO.getId(), ProductUpdate.class);
                update.setSoftwareVersion(updateDTO.getSoftwareVersion());
                update.setUpdateType(updateDTO.getUpdateType());
                update.setMacFilter(updateDTO.getMacFilter());
                update.setSignatureType(updateDTO.getSignatureType());
                update.setTestFlag(updateDTO.getTestFlag());
                update.setGuJianVersion(updateDTO.getGuJianVersion());
                update.setYingJianVersion(updateDTO.getYingJianVersion());
                update.setView(updateDTO.getView());
                update.setUpdateModel(updateDTO.getUpdateModel());

                update.setUpdateURL(updateURL);
                update.setDvbVersion(updateDTO.getDvbVersion());
                update.setDvbProviderCode(updateDTO.getDvbProviderCode());
                update.setCaType(updateDTO.getCaType());
                update.setCaVersion(updateDTO.getCaVersion());
                update.setCaDependVersion(updateDTO.getCaDependVersion());

                update.setClientVersion(updateDTO.getClientVersion());
                update.setApkUpdateURL(updateDTO.getApkUpdateURL());

                update.setVersionCompareWay(updateDTO.getVersionCompareWay());
                if (updateFile != null) {
                    update.setUpdateFile(updateFile);
                }
            } else {
                update = new ProductUpdate(updateFile, product, updateWay, updateURL,
                        updateDTO.getSoftwareVersion(), updateDTO.getUpdateType(), updateDTO.getMacFilter(), updateDTO.getSignatureType(),
                        updateDTO.getTestFlag(), updateDTO.getGuJianVersion(), updateDTO.getGuJianVersionAfter(), updateDTO.getYingJianVersion(), updateDTO.getView(), updateDTO.getUpdateModel(),
                        updateDTO.getDvbVersion(), updateDTO.getDvbProviderCode(), updateDTO.getCaType(), updateDTO.getCaVersion(), updateDTO.getCaDependVersion(),
                        updateDTO.getVersionCompareWay(), updateDTO.getClientVersion(), updateDTO.getApkUpdateURL());
            }

        } else if ("4".equals(updateWay)) {
            if (updateDTO.getId() > 0) {
                update = (ProductUpdate) EntityLoadHolder.getUserDao().findById(updateDTO.getId(), ProductUpdate.class);
                update.setUpdateURL(updateURL);
                update.setTestFlag(updateDTO.getTestFlag());
                update.setAppPackage(updateDTO.getAppPackage());
                update.setAppVersionRange(updateDTO.getAppVersionRange());
                update.setAppVersion(updateDTO.getAppVersion());
                update.setAppSignatureType(updateDTO.getAppSignatureType());

                update.setClientVersion(updateDTO.getClientVersion());
                update.setApkUpdateURL(updateDTO.getApkUpdateURL());
                if (updateFile != null) {
                    update.setUpdateFile(updateFile);
                }
            } else {
                update = new ProductUpdate(updateFile, product, updateWay, updateURL, updateDTO.getTestFlag(),
                        updateDTO.getAppPackage(), updateDTO.getAppVersionRange(), updateDTO.getAppVersion(), updateDTO.getAppSignatureType(),
                        updateDTO.getClientVersion(), updateDTO.getApkUpdateURL());
            }

        } else if ("5".equals(updateWay)) {
            if (updateDTO.getId() > 0) {
                update = (ProductUpdate) EntityLoadHolder.getUserDao().findById(updateDTO.getId(), ProductUpdate.class);
                update.setUpdateURL(updateURL);
                update.setTestFlag(updateDTO.getTestFlag());
                update.setProgramName(updateDTO.getProgramName());
                update.setProgramVersion(updateDTO.getProgramVersion());
                update.setProgramSignatureType(updateDTO.getProgramSignatureType());

                update.setClientVersion(updateDTO.getClientVersion());
                update.setAppVersionRange(updateDTO.getApkUpdateURL());
                if (updateFile != null) {
                    update.setUpdateFile(updateFile);
                }
            } else {
                update = new ProductUpdate(updateFile, product, updateWay, updateURL, updateDTO.getTestFlag(),
                        updateDTO.getProgramName(), updateDTO.getProgramVersion(), updateDTO.getProgramSignatureType(),
                        updateDTO.getClientVersion(), updateDTO.getApkUpdateURL());
            }
        }

        return update;
    }

    public static ProductUpdateHistoryDTO toProductUpdateHistoryDTO(ProductUpdate update) {
        final int id = update.getId();
        final String updateWay = update.getUpdateWay();
        final String updateWayName = update.getUpdateWayName();
        final String updateURL = update.getUpdateURL();
        UpdateFile file = update.getUpdateFile();
        final int uploadFileId = file != null ? file.getId() : -1;
        final String uploadFileName = file != null ? file.getActualFileName() : "";

        if("1".equals(updateWay) || "2".equals(updateWay)) {
            final String softwareVersion = update.getSoftwareVersion();
            final String updateType = update.getUpdateType();
            final String macFilter = update.getMacFilter();
            final String signatureType = update.getSignatureType();
            final String testFlag = update.getTestFlag();
            final String guJianVersion = update.getGuJianVersion();
            final String guJianVersionAfter = update.getGuJianVersionAfter();
            final String yingJianVersion = update.getYingJianVersion();
            final String view = update.getView();
            final String updateModel = update.getUpdateModel();

            final String versionCompareWay = update.getVersionCompareWay();
            final String clientVersion = update.getClientVersion();
            final String apkUpdateURL = update.getApkUpdateURL();

            return new ProductUpdateHistoryDTO(id, updateWay, updateWayName, updateURL, uploadFileId, uploadFileName,
                    softwareVersion, updateType, macFilter, signatureType, testFlag, guJianVersion, guJianVersionAfter, yingJianVersion, view, updateModel,
                    versionCompareWay, clientVersion, apkUpdateURL);

        } else if ("3".equals(updateWay)) {
            final String softwareVersion = update.getSoftwareVersion();
            final String updateType = update.getUpdateType();
            final String macFilter = update.getMacFilter();
            final String signatureType = update.getSignatureType();
            final String testFlag = update.getTestFlag();
            final String guJianVersion = update.getGuJianVersion();
            final String guJianVersionAfter = update.getGuJianVersionAfter();
            final String yingJianVersion = update.getYingJianVersion();
            final String view = update.getView();
            final String updateModel = update.getUpdateModel();

            final String dvbVersion = update.getDvbVersion();
            final String dvbProviderCode = update.getDvbProviderCode();
            final String caType = update.getCaType();
            final String caVersion = update.getCaVersion();
            final String caDependVersion = update.getCaDependVersion();

            final String versionCompareWay = update.getVersionCompareWay();
            final String clientVersion = update.getClientVersion();
            final String apkUpdateURL = update.getApkUpdateURL();

            return new ProductUpdateHistoryDTO(id, updateWay, updateWayName, updateURL, uploadFileId, uploadFileName,
                    softwareVersion, updateType, macFilter, signatureType, testFlag, guJianVersion, guJianVersionAfter, yingJianVersion, view, updateModel,
                    dvbVersion, dvbProviderCode, caType, caVersion, caDependVersion,
                    versionCompareWay ,clientVersion, apkUpdateURL);

        } else if ("4".equals(updateWay)) {
            final String testFlag = update.getTestFlag();
            final String appPackage = update.getAppPackage();
            final String appVersionRange = update.getAppVersionRange();
            final String appVersion = update.getAppVersion();
            final String appSignatureType = update.getAppSignatureType();
            final String clientVersion = update.getClientVersion();
            final String apkUpdateURL = update.getApkUpdateURL();

            return new ProductUpdateHistoryDTO(id, updateWay, updateWayName, updateURL, uploadFileId, uploadFileName, testFlag,
                    appPackage, appVersionRange, appVersion, appSignatureType, clientVersion, apkUpdateURL);

        } else if ("5".equals(updateWay)) {
            final String testFlag = update.getTestFlag();
            final String programName = update.getProgramName();
            final String programVersion = update.getProgramVersion();
            final String programSingureType = update.getProgramSignatureType();
            final String clientVersion = update.getClientVersion();
            final String apkUpdateURL = update.getApkUpdateURL();

            return new ProductUpdateHistoryDTO(id, updateWay, updateWayName, updateURL, uploadFileId, uploadFileName, testFlag,
                    programName, programVersion, programSingureType , clientVersion, apkUpdateURL);
        }

        return null;
    }

    public static ProductUpdateHistoryDTO toProductUpdateHistoryListDTO(ProductUpdate update) {
        final int id = update.getId();
        Product product = update.getProduct();
        final int productId = product.getId();
        final Date uploadTime = update.getTimestamp();

        final String updateWay = update.getUpdateWay();
        final String updateWayName = update.getUpdateWayName();
        final String productModel = product.getModel();
        final String updateVersion = update.getUpdateVersionName();

        UpdateFile file = update.getUpdateFile();
        final int uploadFileId = file != null ? file.getId() : -1;

        String jsonPath = "";
        String dataPath = "";
        String updateCategoryName = "";
        if ("1".equals(updateWay)) {
            jsonPath = "/" + updateWay + "/" + productModel + "/" + DocumentPathResolver.UPDATE_FILENAME_PREFIX_TYPE_1 + updateVersion + ".json";
            if (uploadFileId > 0) {
                dataPath = "/" + updateWay + "/" + productModel + "/" + file.getActualFileName();
            }

        } else if ("2".equals(updateWay)) {
            jsonPath = "/" + updateWay + "/" + productModel + "/" + DocumentPathResolver.UPDATE_FILENAME_PREFIX_TYPE_2 + updateVersion + ".json";
            if (uploadFileId > 0) {
                dataPath = "/" + updateWay + "/" + productModel + "/" + file.getActualFileName();
            }

        } else if ("3".equals(updateWay)) {
            updateCategoryName = StringUtils.hasText(update.getDvbProviderCode()) ? update.getDvbProviderCode() : "0000";
            String dvbProviderCode = updateCategoryName;
            jsonPath = "/" + updateWay + "/" + productModel + "/" + dvbProviderCode + "/" + DocumentPathResolver.UPDATE_FILENAME_PREFIX_TYPE_3 + updateVersion + ".json";
            if (uploadFileId > 0) {
                dataPath = "/" + updateWay + "/" + productModel + "/" + dvbProviderCode + "/" + file.getActualFileName();
            }

        } else if ("4".equals(updateWay)) {
            updateCategoryName = update.getAppPackage();
            String appPackage = updateCategoryName + "_";
            jsonPath = "/" + updateWay + "/" + updateCategoryName + "/" + appPackage + updateVersion + ".json";
            if (uploadFileId > 0) {
                dataPath = "/" + updateWay + "/" + updateCategoryName + "/" + file.getActualFileName();
            }

        } else if ("5".equals(updateWay)) {
            updateCategoryName = update.getProgramName();
            String programName = updateCategoryName + "_";
            jsonPath = "/" + updateWay + "/" + productModel + "/" + updateCategoryName + "/" + programName + updateVersion + ".json";
            if (uploadFileId > 0) {
                dataPath = "/" + updateWay + "/" + productModel + "/" + updateCategoryName + "/" + file.getActualFileName();
            }
        }

        return new ProductUpdateHistoryDTO(id, productId, uploadTime, updateWayName, updateVersion, updateCategoryName, jsonPath, dataPath, uploadFileId);
    }

    public static List<ProductUpdateHistoryDTO> toProductUpdateHistoryDTOList(List<ProductUpdate> updates) {
        List<ProductUpdateHistoryDTO> dtos = new ArrayList<ProductUpdateHistoryDTO>();
        if (updates != null) {
            for (ProductUpdate update : updates) {
                dtos.add(toProductUpdateHistoryListDTO(update));
            }
        }
        return dtos;
    }
}
