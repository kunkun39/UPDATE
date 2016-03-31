package com.changhong.update.repository;

import com.changhong.common.repository.EntityObjectDao;
import com.changhong.update.domain.Product;
import com.changhong.update.domain.ProductCategory;
import com.changhong.update.domain.ProductUpdate;
import com.changhong.update.web.facade.dto.ProductDTO;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 下午2:33
 */
public interface ProductDao extends EntityObjectDao {

    /*************************目录******************************/

    ProductCategory loadProductCategoryById(int id);

    List<ProductCategory> loadCategoryByParentId(int categoryId);

    /*************************产品******************************/

    List<Product> loadProducts(String name, int categoryId, int startPosition, int pageSize);

    int loadProductSize(String name, int categoryId);

    List<Product> obtainAllUserProducts(int userId);

    List<Product> loadMyProducts(String name, int currentUserId, int startPosition, int pageSize);

    int loadMyProductSize(String name, int currentUserId);

    boolean loadProductModelDuplicate(int productId, String model);

    boolean loadProductContainsUpdates(int productId);

    /*************************升级******************************/

    List<ProductUpdate> loadProductUpdateByProductId(int productId);

    int loadProductUpdateSize(int productId);

    List<ProductUpdate> loadUpdateHisoryByProduct(int productId, String updateWayFilter, String versionFilter, int startPosition, int pageSize);

    int loadUpdateHisoryByProductSize(int productId, String updateWayFilter, String versionFilter);
}
