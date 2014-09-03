package com.changhong.update.web.controller.product;

import com.changhong.update.service.ProductService;
import com.changhong.update.web.facade.dto.ProductDTO;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 上午10:14
 */
public class ProductFormController extends SimpleFormController {

    private ProductService productService;

    public ProductFormController() {
        setCommandClass(ProductDTO.class);
        setCommandName("product");
        setFormView("/backend/product/productform");
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        int productId = ServletRequestUtils.getIntParameter(request, "productId", -1);
        String current = ServletRequestUtils.getStringParameter(request, "current", "");
        String name = ServletRequestUtils.getStringParameter(request, "name", "");
        int categoryId = ServletRequestUtils.getIntParameter(request, "categoryId", -1);
        request.setAttribute("current", current);
        request.setAttribute("name", name);
        request.setAttribute("categoryId", categoryId);

        if (productId > 0) {
            return productService.obtainProductById(productId);
        }
        return new ProductDTO();
    }

    @Override
    protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
        String productName = ServletRequestUtils.getStringParameter(request, "productName", "");
        if (!StringUtils.hasText(productName)) {
            errors.rejectValue("productName", "product.name.empty");
        }

        String productModel = ServletRequestUtils.getStringParameter(request, "productModel", "");
        if (!StringUtils.hasText(productModel)) {
            errors.rejectValue("productModel", "product.model.empty");
        } else {
            int productId = ServletRequestUtils.getIntParameter(request, "id", -1);
            int modelResponse = productService.obtainProductModelChecking(productId, productModel);
            if (modelResponse == 1) {
                errors.rejectValue("productModel", "product.model.duplicate");
            } else if (modelResponse == 2) {
                errors.rejectValue("productModel", "product.model.cannot.change");
            }
        }

        String productDescription = ServletRequestUtils.getStringParameter(request, "productDescription", "");
        if (!StringUtils.hasText(productDescription)) {
            errors.rejectValue("productDescription", "product.description.empty");
        }


        int productCategoryId = ServletRequestUtils.getIntParameter(request, "productCategoryId", -1);
        if (productCategoryId <= 0) {
            errors.rejectValue("productCategoryId", "product.category.empty");
        }
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        ProductDTO productDTO = (ProductDTO) command;
        String current = ServletRequestUtils.getStringParameter(request, "current", "");
        String name = ServletRequestUtils.getStringParameter(request, "name", "");
        int categoryId = ServletRequestUtils.getIntParameter(request, "categoryId", -1);

        productService.changeProductDetails(productDTO);

        return new ModelAndView(new RedirectView("productoverview.html?current="+current+"&name="+name+"&categoryId="+categoryId));
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
