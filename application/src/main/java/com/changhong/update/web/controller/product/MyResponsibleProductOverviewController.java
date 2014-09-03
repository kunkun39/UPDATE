package com.changhong.update.web.controller.product;

import com.changhong.common.utils.SecurityUtils;
import com.changhong.common.web.session.SessionKey;
import com.changhong.update.service.ProductService;
import com.changhong.update.web.facade.dto.ProductDTO;
import com.changhong.update.web.paging.MyProductOverviewPaging;
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
 * Time: 下午12:15
 */
public class MyResponsibleProductOverviewController extends AbstractController {

    private ProductService productService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getSession().setAttribute(SessionKey.BROSWER_LOCATION, "MY");

        Map<String, Object> model = new HashMap<String, Object>();
        int current = ServletRequestUtils.getIntParameter(request, "current", 1);
        String name = ServletRequestUtils.getStringParameter(request, "name", "");
        request.setAttribute("current", current);
        request.setAttribute("name", name);

        MyProductOverviewPaging paging = new MyProductOverviewPaging(productService);
        constructPaging(paging, current, name, SecurityUtils.currectUserId());
        List<ProductDTO> products = paging.getItems();
        model.put("products", products);
        model.put("paging", paging);

        return new ModelAndView("backend/product/myproductoverview", model);
    }

    private void constructPaging(MyProductOverviewPaging paging, int current, String name, int currentUserId) {
        paging.setCurrentPageNumber(current);
        paging.setName(name);
        paging.setCurrentUserId(currentUserId);
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
