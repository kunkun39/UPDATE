package com.changhong.update.web.facade.assember;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.update.domain.Product;
import com.changhong.update.domain.ProductCategory;
import com.changhong.update.domain.ProductUpdate;
import com.changhong.update.web.facade.dto.CategoryDTO;
import com.changhong.update.web.facade.dto.ProductDTO;
import com.changhong.update.web.facade.dto.ProductUpdateHistoryDTO;
import com.google.gson.Gson;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 下午2:39
 */
public class ProductWebAssember {

    public static ProductCategory toCategoryDomain(int id, String name, int parentId) {
        ProductCategory category = null;
        if (id > 0) {
            category = (ProductCategory) EntityLoadHolder.getUserDao().findById(id, ProductCategory.class);
            category.setName(name);
        } else {
            category = new ProductCategory();
            category.setName(name);
            ProductCategory parent = new ProductCategory();
            parent.setId(parentId);
            category.setParent(parent);
        }
        return category;
    }

    public static CategoryDTO toCategoryDTOWithFullPath(ProductCategory category){
        int parentId = category.getParent() != null ? category.getParent().getId() : -1;
		return new CategoryDTO(category.getId(), category.getName(), category.getFullCategoryPath(), parentId);
	}

    public static List<CategoryDTO> toCategoryDTOListWithFullPath(List<ProductCategory> categories){
        List<CategoryDTO> dtos = new ArrayList<CategoryDTO>();
        if (categories != null) {
            for (ProductCategory category : categories) {
                dtos.add(toCategoryDTOWithFullPath(category));
            }
        }
        return dtos;
	}

    public static CategoryDTO toCategoryDTO(ProductCategory category){
        int parentId = category.getParent() != null ? category.getParent().getId() : -1;
		return new CategoryDTO(category.getId(), category.getName(), parentId);
	}

    public static List<CategoryDTO> toCategoryDTOList(List<ProductCategory> categories){
        List<CategoryDTO> dtos = new ArrayList<CategoryDTO>();
        if (categories != null) {
            for (ProductCategory category : categories) {
                dtos.add(toCategoryDTO(category));
            }
        }
        return dtos;
	}

    public static String toJson(List<ProductCategory> categories, int categoryId){
		Gson gson = new Gson();
		StringBuffer result = new StringBuffer();
		result.append("[");
		for(ProductCategory child : categories){
			StringBuffer single = new StringBuffer();
			if(child.getId() != categoryId){
				single.append("{");
				single.append("\"data\":" + "\"" + child.getName() + "\",");
				single.append("\"attr\":" + "{\"id\":\"" + child.getId() + "\",\"path\":\"" + child.getFullCategoryPath() + "\"}");
				if(!child.isLeaf()){
					single.append(",\"state\":\"closed\"");
					single.append(",\"children\":[]");
				}
				single.append("},");
				result.append(single);
			}
		}
		if(result.toString().endsWith(",")){
			result.deleteCharAt(result.length() - 1);
		}
		result.append("]");

		return result.toString();
	}

    public static ProductDTO toProductDTO(Product product) {
        final int id = product.getId();
        final String name = product.getName();
        final String model = product.getModel();
        final String description = product.getDescription();
        final int categoryId = product.getCategory().getId();
        final String categoryName = product.getCategory().getFullCategoryPath();

        return new ProductDTO(id, name, model, description, categoryId, categoryName);
    }

    public static List<ProductDTO> toProductDTOList(List<Product> products) {
        List<ProductDTO> dtos = new ArrayList<ProductDTO>();
        if (products != null) {
            for (Product product : products) {
                dtos.add(toProductDTO(product));
            }
        }
        return dtos;
    }

    public static Product toProductDomain(ProductDTO productDTO) {
        Product product = null;
        if(productDTO == null) return null;

        ProductCategory category = (ProductCategory) EntityLoadHolder.getUserDao().findById(productDTO.getProductCategoryId(), ProductCategory.class);
        if (productDTO.getId() > 0) {
            product = (Product) EntityLoadHolder.getUserDao().findById(productDTO.getId(), Product.class);
            product.setName(productDTO.getProductName().trim());
            product.setModel(productDTO.getProductModel().trim());
            product.setDescription(productDTO.getProductDescription());
            product.setCategory(category);
        } else {
            product = new Product(productDTO.getProductName().trim(), productDTO.getProductModel().trim(), productDTO.getProductDescription(), category);
        }
        return product;
    }
}
