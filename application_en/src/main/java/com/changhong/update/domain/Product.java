package com.changhong.update.domain;

import com.changhong.common.domain.EntityBase;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 下午1:43
 */
public class Product extends EntityBase {

    private String name;

    private String model;

    private String description;

    private ProductCategory category;

    public Product() {
    }

    public Product(String name, String model, String description, ProductCategory category) {
        this.name = name;
        this.model = model;
        this.description = description;
        this.category = category;
    }

    /****************************************************GET/SET*******************************************************/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }
}
