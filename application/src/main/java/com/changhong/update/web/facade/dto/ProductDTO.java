package com.changhong.update.web.facade.dto;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 下午5:14
 */
public class ProductDTO {

    private int id;

    private String productName;

    private String productModel;

    private String productDescription;

    private int productCategoryId;

    private String productCategoryName;

    public ProductDTO() {
    }

    public ProductDTO(int id, String productName, String productModel, String productDescription, int productCategoryId, String productCategoryName) {
        this.id = id;
        this.productName = productName;
        this.productModel = productModel;
        this.productDescription = productDescription;
        this.productCategoryId = productCategoryId;
        this.productCategoryName = productCategoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }
}
