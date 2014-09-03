package com.changhong.update.web.controller.product;

import com.changhong.update.service.ProductService;
import com.changhong.update.web.facade.dto.CategoryDTO;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 下午3:29
 */
public class ProductCategoryFormController extends AbstractController {

    private ProductService productService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = ServletRequestUtils.getStringParameter(request, "method");
        int categoryId = ServletRequestUtils.getIntParameter(request, "id");

        if ("save".equals(method)) {
            int parentId = ServletRequestUtils.getIntParameter(request, "parentId");
            String name = ServletRequestUtils.getStringParameter(request, "name", "");
            productService.saveOrUpdate(categoryId, name, parentId);

            return new ModelAndView(new RedirectView("productmanagement.html"));
        } else {
            Map<String, Object> model = new HashMap<String, Object>();
            CategoryDTO dto = null;
            if ("add".equals(method)) {
                dto = new CategoryDTO(categoryId);
            } else {
                dto = productService.obtainCategoryById(categoryId);
            }
            model.put("category", dto);
            return new ModelAndView("backend/product/categoryform", model);
        }
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
