package com.changhong.update.web.controller.product;

import com.changhong.update.service.ProductService;
import com.changhong.update.web.facade.dto.ProductDTO;
import com.changhong.update.web.paging.ProductOverviewPaging;
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
 * Date: 14-4-9
 * Time: 下午5:00
 */
public class ProductOverviewController extends AbstractController {

    private ProductService productService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        String name = ServletRequestUtils.getStringParameter(request, "name", "");
        int categoryId = ServletRequestUtils.getIntParameter(request, "categoryId", -1);
        request.setAttribute("current", current);
        request.setAttribute("name", name);
        request.setAttribute("categoryId", categoryId);

        ProductOverviewPaging paging = new ProductOverviewPaging(productService);
        constructPaging(paging, current, name, categoryId);
        List<ProductDTO> products = paging.getItems();
        model.put("products", products);
        model.put("paging", paging);

        return new ModelAndView("backend/product/productoverview", model);
    }

    private void constructPaging(ProductOverviewPaging paging, int current, String name, int categoryId) {
        paging.setCurrentPageNumber(current);
        paging.setName(name);
        paging.setCategoryId(categoryId);
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
