package com.changhong.update.web.controller.product;

import com.changhong.update.domain.ProductUpdate;
import com.changhong.update.service.ProductService;
import com.changhong.update.web.facade.dto.ProductDTO;
import com.changhong.update.web.facade.dto.ProductUpdateHistoryDTO;
import com.changhong.update.web.facade.dto.UpdateWayOption;
import com.changhong.update.web.paging.ProductUpdateHistoryOverviewPaging;
import org.quartz.SimpleTrigger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 下午4:13
 */
public class ProductUpdateHistoryOverviewController extends AbstractController {

    private ProductService productService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        int productId = ServletRequestUtils.getIntParameter(request, "productId", -1);
        String updateWayFilter = ServletRequestUtils.getStringParameter(request, "updateWayFilter", "");
        String versionFilter = ServletRequestUtils.getStringParameter(request, "versionFilter", "");
        model.put("current", current);
        model.put("productId", productId);
        model.put("updateWayFilter", updateWayFilter);
        model.put("versionFilter", versionFilter);

        model.put("updateWays", UpdateWayOption.getAllOptions());

        ProductDTO dto = productService.obtainProductById(productId);
        model.put("product", dto);

        ProductUpdateHistoryOverviewPaging paging = new ProductUpdateHistoryOverviewPaging(productService);
        constructPaging(paging, current, productId, updateWayFilter, versionFilter);
        List<ProductUpdateHistoryDTO> updates = paging.getItems();
        model.put("updates", updates);
        model.put("paging", paging);

        return new ModelAndView("backend/product/productupdatehistoryoverview", model);
    }

    private void constructPaging(ProductUpdateHistoryOverviewPaging paging, int current, int productId, String updateWayFilter, String versionFilter) {
        paging.setCurrentPageNumber(current);
        paging.setProductId(productId);
        paging.setUpdateWayFilter(updateWayFilter);
        paging.setVersionFilter(versionFilter);
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
