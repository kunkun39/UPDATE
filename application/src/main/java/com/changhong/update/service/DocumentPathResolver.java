package com.changhong.update.service;

import com.changhong.update.domain.Document;
import com.changhong.update.domain.ProductUpdate;
import org.springframework.util.StringUtils;

/**
 * User: Jack Wang
 * Date: 14-4-15
 * Time: 下午1:55
 */
public class DocumentPathResolver {

    public static final String UPDATE_FILENAME_PREFIX_TYPE_1 = "update_";

    public static final String UPDATE_FILENAME_PREFIX_TYPE_2 = "update_diff_";

    public static final String UPDATE_FILENAME_PREFIX_TYPE_3 = "dtv_";

    public static String generateJsonFileName(ProductUpdate update) {
        String jsonPath = "";
        String updateWay = update.getUpdateWay();
        String updateVersion = update.getUpdateVersionName();
        String updateCategoryName = "";

        if ("1".equals(updateWay)) {
            jsonPath = DocumentPathResolver.UPDATE_FILENAME_PREFIX_TYPE_1 + updateVersion + ".json";

        } else if ("2".equals(updateWay)) {
            jsonPath = DocumentPathResolver.UPDATE_FILENAME_PREFIX_TYPE_2 + updateVersion + ".json";

        } else if ("3".equals(updateWay)) {
            updateCategoryName = update.getDvbProviderCode();
            jsonPath = DocumentPathResolver.UPDATE_FILENAME_PREFIX_TYPE_3 + updateVersion + ".json";

        } else if ("4".equals(updateWay)) {
            updateCategoryName = update.getAppPackage();
            String appPackage = updateCategoryName + "_";
            jsonPath = appPackage + updateVersion + ".json";

        } else if ("5".equals(updateWay)) {
            updateCategoryName = update.getProgramName();
            String programName = updateCategoryName + "_";
            jsonPath = programName + updateVersion + ".json";
        }
        return jsonPath;
    }

    public static String generateDataFileName(Document document, ProductUpdate update) {
        String dataPath = "";
        String updateWay = update.getUpdateWay();
        String updateVersion = update.getUpdateVersionName();
        String updateCategoryName = "";
        String uploadFileName = document.getUploadFileName();
        String[] tokens = StringUtils.delimitedListToStringArray(uploadFileName, ".");
        int actualFileNameSuffixPosition = uploadFileName.lastIndexOf(".");

        if ("1".equals(updateWay)) {
            dataPath = uploadFileName.substring(0, actualFileNameSuffixPosition) + "_" + updateVersion + "." + tokens[tokens.length - 1];

        } else if ("2".equals(updateWay)) {
            dataPath = uploadFileName.substring(0, actualFileNameSuffixPosition) + "_" + updateVersion + "." + tokens[tokens.length - 1];

        } else if ("3".equals(updateWay)) {
            dataPath = uploadFileName.substring(0, actualFileNameSuffixPosition) + "_" + updateVersion + "." + tokens[tokens.length - 1];

        } else if ("4".equals(updateWay)) {
            dataPath = uploadFileName.substring(0, actualFileNameSuffixPosition) + "_" + updateVersion + "." + tokens[tokens.length - 1];

        } else if ("5".equals(updateWay)) {
            dataPath = uploadFileName.substring(0, actualFileNameSuffixPosition) + "_" + updateVersion + "." + tokens[tokens.length - 1];
        }

        return dataPath;
    }

    public static String generateUploadFileNamePath(ProductUpdate update) {
        String updateWay = update.getUpdateWay();
        String productModel = update.getProduct().getModel();
        String updateCategoryName = "";
        String returnPath = "";

        if ("1".equals(updateWay)) {
            returnPath = "/" + updateWay + "/" + productModel;

        } else if ("2".equals(updateWay)) {
            returnPath = "/" + updateWay + "/" + productModel;

        } else if ("3".equals(updateWay)) {
            updateCategoryName = StringUtils.hasText(update.getDvbProviderCode()) ? update.getDvbProviderCode() : "0000";
            returnPath = "/" + updateWay + "/" + productModel + "/" + updateCategoryName;

        } else if ("4".equals(updateWay)) {
            updateCategoryName = update.getAppPackage();
            returnPath = "/" + updateWay + "/" + updateCategoryName;

        } else if ("5".equals(updateWay)) {
            updateCategoryName = update.getProgramName();
            returnPath = "/" + updateWay + "/" + productModel + "/" + updateCategoryName;
        }

        return returnPath;
    }
}
