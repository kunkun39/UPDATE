package com.changhong.update.web.paging;

import com.changhong.update.service.ProductService;
import com.changhong.update.web.facade.dto.ProductDTO;
import com.changhong.system.web.paging.AbstractPaging;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 下午5:30
 */
public class ProductOverviewPaging extends AbstractPaging<ProductDTO> {

    private ProductService productService;

    private String name;

    private int categoryId;

    public ProductOverviewPaging(ProductService productService) {
        this.productService = productService;
    }

    public List<ProductDTO> getItems() {
        return productService.obtainProducts(name, categoryId, getStartPosition(), getPageSize());
    }

    public long getTotalItemSize() {
        if (totalItemSize >= 0) {
            return totalItemSize;
        }
        totalItemSize = productService.obtainProductSize(name, categoryId);
        return totalItemSize;
    }

    public String getParameterValues() {
        return "&name=" + getName() + "&categoryId=" + getCategoryId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}

