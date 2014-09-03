package com.changhong.update.domain;

import com.changhong.common.domain.EntityBase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 下午1:41
 */
public class ProductCategory extends EntityBase {

    private String name;

    private ProductCategory parent;

    private List<ProductCategory> children;

    private List<Product> products;

    public boolean isLeaf() {
        if (children == null || children.isEmpty()) {
            return true;
        }
        return false;
    }

    public List<ProductCategory> getAllCategoryBelow() {
		List<ProductCategory> all = new ArrayList<ProductCategory>();
		List<ProductCategory> children = this.getChildren();
		if (children != null) {
			all.addAll(children);
			Iterator i = children.iterator();
			while (i.hasNext()) {
				ProductCategory category = (ProductCategory) i.next();
				all.addAll(category.getAllCategoryBelow());
			}
		}
		return all;
	}

    public List<ProductCategory> getAllCategoryBelowWithItself() {
		List<ProductCategory> all = new ArrayList<ProductCategory>();
        all.add(this);
		List<ProductCategory> children = this.getChildren();
		if (children != null) {
			all.addAll(children);
			Iterator i = children.iterator();
			while (i.hasNext()) {
				ProductCategory category = (ProductCategory) i.next();
				all.addAll(category.getAllCategoryBelow());
			}
		}
		return all;
	}

    public List getAllCategoryAbove() {
		List<ProductCategory> all = new ArrayList<ProductCategory>();
		ProductCategory parent = this.getParent();
		if (parent != null) {
			all.addAll(parent.getAllCategoryAbove());
			all.add(parent);
		}
		return all;
	}

    public List<ProductCategory> getAllSitesAboveWithItself() {
		List<ProductCategory> all = new ArrayList<ProductCategory>();
		ProductCategory parent = this.getParent();
		if (parent != null) {
			all.addAll(parent.getAllCategoryAbove());
			all.add(parent);
		}
        all.add(this);
		return all;
	}

    public String getFullCategoryPath() {
        List<ProductCategory> all = getAllSitesAboveWithItself();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < all.size(); i++) {
            ProductCategory site = all.get(i);
            builder.append("/" + site.getName());
        }

        return builder.toString();
    }

    /****************************************************GET/SET*******************************************************/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategory getParent() {
        return parent;
    }

    public void setParent(ProductCategory parent) {
        this.parent = parent;
    }

    public List<ProductCategory> getChildren() {
        return children;
    }

    public void setChildren(List<ProductCategory> children) {
        this.children = children;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
