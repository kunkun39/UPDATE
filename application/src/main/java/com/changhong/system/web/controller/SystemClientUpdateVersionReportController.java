package com.changhong.system.web.controller;

import com.changhong.common.utils.CHDateUtils;
import com.changhong.common.utils.SecurityUtils;
import com.changhong.common.web.session.SessionKey;
import com.changhong.update.domain.Product;
import com.changhong.update.service.ProductService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Jack Wang
 * Date: 14-5-11
 * Time: 下午7:49
 */
public class SystemClientUpdateVersionReportController extends AbstractController {

    private ProductService productService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getSession().setAttribute(SessionKey.BROSWER_LOCATION, "STA");

        Map<String, Object> model = new HashMap<String, Object>();
        List<Product> products = productService.obtainAllUserProducts(SecurityUtils.currectUserId());
        model.put("products", products);
//        model.put("reportYear", CHDateUtils.getCurrentYear());
//        model.put("reportMonth", CHDateUtils.getCurrentMonth());

        return new ModelAndView("backend/system/systemclientupdateversionreport", model);
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
