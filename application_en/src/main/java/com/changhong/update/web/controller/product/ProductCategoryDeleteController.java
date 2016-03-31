package com.changhong.update.web.controller.product;

import com.changhong.update.service.ProductService;
import org.json.JSONObject;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * User: Jack Wang
 * Date: 14-4-14
 * Time: 下午5:00
 */
public class ProductCategoryDeleteController extends AbstractController {

    private ProductService productService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int categoryId = ServletRequestUtils.getIntParameter(request, "id", -1);
        boolean canDelete = productService.deleteCategory(categoryId);

        response.setContentType("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        json.put("message", canDelete ? "true" : "false");
        PrintWriter writer = response.getWriter();
        writer.write(json.toString());
        writer.flush();
        writer.close();

        return null;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
