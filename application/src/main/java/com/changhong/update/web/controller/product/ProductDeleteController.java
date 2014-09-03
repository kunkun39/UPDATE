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
 * Date: 14-5-5
 * Time: 上午9:47
 */
public class ProductDeleteController extends AbstractController {

    private ProductService productService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int productId = ServletRequestUtils.getIntParameter(request, "productId", -1);
        String current = ServletRequestUtils.getStringParameter(request, "current", "");
        String name = ServletRequestUtils.getStringParameter(request, "name", "");
        int categoryId = ServletRequestUtils.getIntParameter(request, "categoryId", -1);

        productService.deleteProduct(productId);

        return new ModelAndView(new RedirectView("productoverview.html?current="+current+"&name="+name+"&categoryId="+categoryId));
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
