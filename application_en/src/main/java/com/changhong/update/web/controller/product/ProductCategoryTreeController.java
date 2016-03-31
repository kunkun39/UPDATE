package com.changhong.update.web.controller.product;

import com.changhong.update.service.ProductService;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 下午2:57
 */
public class ProductCategoryTreeController extends AbstractController {

    private ProductService productService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int categoryId = ServletRequestUtils.getIntParameter(request, "id", -1);
        String json = productService.obtainCategoryByParentId(categoryId);

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
        return null;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

}
