package com.changhong.update.web.paging;

import com.changhong.update.service.ProductService;
import com.changhong.update.web.facade.dto.ProductDTO;
import com.changhong.system.web.paging.AbstractPaging;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 下午12:22
 */
public class MyProductOverviewPaging extends AbstractPaging<ProductDTO> {

    private ProductService productService;

    private int currentUserId;

    private String name;

    public MyProductOverviewPaging(ProductService productService) {
        this.productService = productService;
    }

    public MyProductOverviewPaging(int currentPageNumber, String name) {
        super(currentPageNumber);
        this.name = name;
    }

    public List<ProductDTO> getItems() {
        return productService.obtainMyProducts(name, currentUserId, getStartPosition(), getPageSize());
    }

    public long getTotalItemSize() {
        return productService.obtainMyProductSize(name, currentUserId);
    }

    public String getParameterValues() {
        return "&name=" + getName();
    }

    public int getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(int currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

