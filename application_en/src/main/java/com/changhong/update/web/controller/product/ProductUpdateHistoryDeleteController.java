package com.changhong.update.web.controller.product;

import com.changhong.update.service.ProductService;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Jack Wang
 * Date: 14-4-17
 * Time: 上午11:43
 */
public class ProductUpdateHistoryDeleteController extends AbstractController {

    private ProductService productService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int productId = ServletRequestUtils.getIntParameter(request, "productId");
        int productUpdateId = ServletRequestUtils.getIntParameter(request, "productUpdateId");
        String updateWayFilter = ServletRequestUtils.getStringParameter(request, "updateWayFilter", "");
        String versionFilter = ServletRequestUtils.getStringParameter(request, "versionFilter", "");

        productService.deleteProductUpdate(productUpdateId);

        return new ModelAndView(new RedirectView("productupdatehistory.html?productId="+productId+"&updateWayFilter="+updateWayFilter+"&versionFilter=" +versionFilter));
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
