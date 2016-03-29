package com.changhong.update.web.controller.product;

import com.changhong.update.service.ProductService;
import com.changhong.update.web.facade.dto.ProductDTO;
import com.changhong.update.web.facade.dto.ProductUpdateHistoryDTO;
import com.changhong.update.web.facade.dto.UpdateWayOption;
import com.changhong.yupan.utils.ExternalUrlGetterUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.zip.InflaterInputStream;

/**
 * User: Jack Wang
 * Date: 14-4-11
 * Time: 上午9:15
 */
public class ProductUpdateHistoryFormController extends SimpleFormController {

    private ProductService productService;

    private String validateURL;

    public ProductUpdateHistoryFormController() {
        setCommandClass(ProductUpdateHistoryDTO.class);
        setCommandName("productUpdate");
        setFormView("/backend/product/productupdateform");
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        String updateWayFilter = ServletRequestUtils.getStringParameter(request, "updateWayFilter", "");
        String versionFilter = ServletRequestUtils.getStringParameter(request, "versionFilter", "");
        request.setAttribute("updateWayFilter", updateWayFilter);
        request.setAttribute("versionFilter", versionFilter);

        request.setAttribute("productUpdateWays", UpdateWayOption.getAllOptions());

        int productId = ServletRequestUtils.getIntParameter(request, "productId", -1);
        ProductDTO productDTO = productService.obtainProductById(productId);
        request.setAttribute("product", productDTO);

        int productUpdateId = ServletRequestUtils.getIntParameter(request, "productUpdateId", -1);
        if (productUpdateId > 0) {
            return productService.obtainProductUpdateHistoryById(productUpdateId);
        }
        return new ProductUpdateHistoryDTO();
    }

    @Override
    protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
        String updateWay = ServletRequestUtils.getStringParameter(request, "updateWay", "");

        if ("1".equals(updateWay) || "2".equals(updateWay) || "3".equals(updateWay)) {
            String softwareVersion = ServletRequestUtils.getStringParameter(request, "softwareVersion", "");
            String updateType = ServletRequestUtils.getStringParameter(request, "updateType", "");
            String guJianVersion = ServletRequestUtils.getStringParameter(request, "guJianVersion", "");
            String guJianVersionAfter = ServletRequestUtils.getStringParameter(request, "guJianVersionAfter", "");

            if (!StringUtils.hasText(softwareVersion)) {
                errors.rejectValue("softwareVersion", "update.software.version.empty");
            } else {
                try {
                    int number = Integer.valueOf(softwareVersion);
                    if (number < 1 || number > 20) {
                        errors.rejectValue("softwareVersion", "update.software.version.range");
                    }
                } catch (NumberFormatException e) {
                    errors.rejectValue("softwareVersion", "update.software.version.number");
                }
            }
            if (!StringUtils.hasText(updateType)) {
                errors.rejectValue("updateType", "update.type.empty");
            }
            if (StringUtils.hasText(guJianVersion)) {
                try {
                    Double number = Double.valueOf(guJianVersion);
                } catch (NumberFormatException e) {
                    errors.rejectValue("guJianVersion", "update.gujian.version.number");
                }
            }
            if (StringUtils.hasText(guJianVersionAfter)) {
                try {
                    Double number = Double.valueOf(guJianVersionAfter);
                } catch (NumberFormatException e) {
                    errors.rejectValue("guJianVersionAfter", "update.gujian.version.number");
                }
            }

            String fromFilter = ServletRequestUtils.getStringParameter(request, "fromFilter", "");
            String toFilter = ServletRequestUtils.getStringParameter(request, "toFilter", "");
            if (StringUtils.hasText(fromFilter) && StringUtils.hasText(toFilter)) {
                if (toFilter.compareTo(fromFilter) < 0) {
                    errors.rejectValue("toFilter", "update.filter.range.notvalidate");
                }
            }
        }

        //验证其他的类型
        if ("1".equals(updateWay) || "2".equals(updateWay)) {

        } else if ("3".equals(updateWay)) {
            String dvbProviderCode = ServletRequestUtils.getStringParameter(request, "dvbProviderCode", "");
            String dvbVersion = ServletRequestUtils.getStringParameter(request, "dvbVersion", "");
            String caDependVersion = ServletRequestUtils.getStringParameter(request, "caDependVersion", "");
            if (StringUtils.hasText(dvbProviderCode)) {
                if (dvbProviderCode.length() < 4) {
                    errors.rejectValue("dvbProviderCode", "update.dvb.providercode.4length");
                }
            }
            if (!StringUtils.hasText(dvbVersion)) {
                errors.rejectValue("dvbVersion", "update.dvb.version.empty");
            } else {
                try {
                    Double number = Double.valueOf(dvbVersion);
                } catch (NumberFormatException e) {
                    errors.rejectValue("dvbVersion", "update.dvb.version.number");
                }
            }
            if (StringUtils.hasText(caDependVersion)) {
                try {
                    Double number = Double.valueOf(caDependVersion);
                } catch (NumberFormatException e) {
                    errors.rejectValue("caDependVersion", "update.dvb.dependversion.number");
                }
            }

        } else if ("4".equals(updateWay)) {
            String appPackage = ServletRequestUtils.getStringParameter(request, "appPackage", "");
            String appVersionRange = ServletRequestUtils.getStringParameter(request, "appVersionRange", "");
            String appVersion = ServletRequestUtils.getStringParameter(request, "appVersion", "");
            if (!StringUtils.hasText(appPackage)) {
                errors.rejectValue("appPackage", "update.app.package.empty");
            }
            if (!StringUtils.hasText(appVersionRange)) {
                errors.rejectValue("appVersionRange", "update.app.versionrange.empty");
            } else {
                String[] ranges = StringUtils.delimitedListToStringArray(appVersionRange, "-");
                if (ranges.length != 2) {
                    errors.rejectValue("appVersionRange", "update.app.versionrange.notright");
                } else {
                    try {
                        int small = Integer.valueOf(ranges[0]);
                        int big = Integer.valueOf(ranges[1]);
                        if (small > big) {
                            errors.rejectValue("appVersionRange", "update.app.versionrange.notright");
                        }
                    } catch (NumberFormatException e) {
                        errors.rejectValue("appVersionRange", "update.app.versionrange.notright");
                    }
                }
            }
            if (!StringUtils.hasText(appVersion)) {
                errors.rejectValue("appVersion", "update.app.version.empty");
            } else {
                try {
                    Double number = Double.valueOf(appVersion);
                } catch (NumberFormatException e) {
                    errors.rejectValue("appVersion", "update.app.version.number");
                }
            }

        } else if ("5".equals(updateWay)) {
            String programName = ServletRequestUtils.getStringParameter(request, "programName", "");
            if (!StringUtils.hasText(programName)) {
                errors.rejectValue("programName", "update.program.name.empty");
            }
            String programVersion = ServletRequestUtils.getStringParameter(request, "programVersion", "");
            if (!StringUtils.hasText(programVersion)) {
                errors.rejectValue("programVersion", "update.program.version.empty");
            } else {
                try {
                    Double number = Double.valueOf(programVersion);
                } catch (NumberFormatException e) {
                    errors.rejectValue("programVersion", "update.program.version.number");
                }
            }
        }

        //检查提交的数据
        int updateId = ServletRequestUtils.getIntParameter(request, "updateId", -1);
        String updateURL = ServletRequestUtils.getStringParameter(request, "updateURL", "");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        if (updateId <= 0) {
            MultipartFile file = multipartRequest.getFile("productUploadFile");
            if ((file == null || file.getSize() == 0) && !StringUtils.hasText(updateURL)) {
                errors.rejectValue("updateURL", "update.both.data.empty");
            }

            validateExternalURL(errors, updateURL);
        } else {
            ProductUpdateHistoryDTO historyDTO = productService.obtainProductUpdateHistoryById(updateId);
            if (historyDTO.getUpdateUploadFileId() > 0) {
                    validateExternalURL(errors, updateURL);
            } else {
                MultipartFile file = multipartRequest.getFile("productUploadFile");
                if ((file == null || file.getSize() == 0) && !StringUtils.hasText(updateURL)) {
                    errors.rejectValue("updateURL", "update.both.data.empty");
                }

                validateExternalURL(errors, updateURL);
            }
        }

        //提交的SN文件列表
        MultipartFile snFile = multipartRequest.getFile("snUploadFileList");
        if (updateId <= 0) {
            if (snFile == null || snFile.getSize() == 0) {
                errors.rejectValue("snUploadFile", "update.sn.file.empty");
            } else {
                String filename = snFile.getOriginalFilename();
                if (!"devices.txt".equals(filename)) {
                    errors.rejectValue("snUploadFile", "update.sn.file.notright");
                }
            }
        } else {
            if (snFile != null && snFile.getSize() > 0) {
                String filename = snFile.getOriginalFilename();
                if (!"devices.txt".equals(filename)) {
                    errors.rejectValue("snUploadFile", "update.sn.file.notright");
                }
            }
        }

        //验证APK升级
        String clientVersion = ServletRequestUtils.getStringParameter(request, "clientVersion", "");
        if (!StringUtils.hasText(clientVersion)) {
            errors.rejectValue("clientVersion", "update.client.version.empty");
        } else {
            try {
                Double.valueOf(clientVersion);
            } catch (NumberFormatException e) {
                errors.rejectValue("clientVersion", "update.client.version.notnumber");
            }
        }
    }

    private void validateExternalURL(BindException errors, String updateURL) {
        if (Boolean.valueOf(validateURL) && StringUtils.hasText(updateURL)) {
            String[] urls = StringUtils.delimitedListToStringArray(updateURL, "|");
            for (String url : urls) {
                String actualUrl = "";
                try {
                    actualUrl = ExternalUrlGetterUtils.getActualUrl(url);
                }catch (Exception e) {
                    errors.rejectValue("updateURL", "update.external.url.notvalidate");
                }
                if (!StringUtils.hasText(actualUrl)) {
                    errors.rejectValue("updateURL", "update.external.url.notvalidate");
                }
            }
        }
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        ProductUpdateHistoryDTO updateDTO = (ProductUpdateHistoryDTO) command;
        int productId = ServletRequestUtils.getIntParameter(request, "productId", -1);
        String updateWay = ServletRequestUtils.getStringParameter(request, "updateWay", "");

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("productUploadFile");
        MultipartFile snFile = multipartRequest.getFile("snUploadFileList");
        if (file != null && file.getSize() > 0) {
            updateDTO.setFile(file);
        }
        if (snFile != null && snFile.getSize() > 0) {
            updateDTO.setSnFile(snFile);
        }

        updateDTO.setProductId(productId);
        updateDTO.setUpdateWay(updateWay);
        productService.changeProductUpdateHistoryDetails(updateDTO);

        String updateWayFilter = ServletRequestUtils.getStringParameter(request, "updateWayFilter", "");
        String versionFilter = ServletRequestUtils.getStringParameter(request, "versionFilter", "");
        return new ModelAndView(new RedirectView("productupdatehistory.html?productId="+productId+"&updateWayFilter="+updateWayFilter+"&versionFilter=" +versionFilter));
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setValidateURL(String validateURL) {
        this.validateURL = validateURL;
    }
}
