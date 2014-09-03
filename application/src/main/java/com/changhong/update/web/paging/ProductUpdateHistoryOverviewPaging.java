package com.changhong.update.web.paging;

import com.changhong.update.service.ProductService;
import com.changhong.update.web.facade.dto.ProductUpdateHistoryDTO;
import com.changhong.system.web.paging.AbstractPaging;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 下午4:21
 */
public class ProductUpdateHistoryOverviewPaging extends AbstractPaging<ProductUpdateHistoryDTO> {

    private ProductService productService;

    private int productId;

    private String updateWayFilter;

    private String versionFilter;

    public ProductUpdateHistoryOverviewPaging(ProductService productService) {
        this.productService = productService;
    }

    public List<ProductUpdateHistoryDTO> getItems() {
        return productService.obtainUpdateHisoryByProduct(productId, updateWayFilter, versionFilter, getStartPosition(), getPageSize());
    }

    public long getTotalItemSize() {
        if (totalItemSize >= 0) {
            return totalItemSize;
        }
        totalItemSize = productService.obtainUpdateHisoryByProductSize(productId, updateWayFilter, versionFilter);
        return totalItemSize;
    }

    public String getParameterValues() {
        return "&productId=" + getProductId() + "&updateWayFilter=" + getUpdateWayFilter() + "&versionFilter=" + getVersionFilter();
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getUpdateWayFilter() {
        return updateWayFilter;
    }

    public void setUpdateWayFilter(String updateWayFilter) {
        this.updateWayFilter = updateWayFilter;
    }

    public String getVersionFilter() {
        return versionFilter;
    }

    public void setVersionFilter(String versionFilter) {
        this.versionFilter = versionFilter;
    }
}
