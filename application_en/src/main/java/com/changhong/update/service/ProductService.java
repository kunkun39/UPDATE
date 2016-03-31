package com.changhong.update.service;

import com.changhong.update.domain.Product;
import com.changhong.update.domain.UpdateFile;
import com.changhong.update.web.facade.dto.CategoryDTO;
import com.changhong.update.web.facade.dto.ProductDTO;
import com.changhong.update.web.facade.dto.ProductUpdateHistoryDTO;
import com.sun.xml.internal.fastinfoset.algorithm.BooleanEncodingAlgorithm;

import java.io.IOException;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 下午2:30
 */
public interface ProductService {

    /*************************目录******************************/

    void saveOrUpdate(int id, String name, int parentId);

    CategoryDTO obtainCategoryById(int id);

    String obtainCategoryByParentId(int parentId);

    boolean deleteCategory(int categoryId);

    /*************************产品******************************/

    void deleteProduct(int productId);

    List<ProductDTO> obtainProducts(String name, int categoryId, int startPosition, int pageSize);

    int obtainProductSize(String name, int categoryId);

    int obtainProductModelChecking(int productId, String model);

    ProductDTO obtainProductById(int productId);

    void changeProductDetails(ProductDTO productDTO);

    List<Product> obtainAllUserProducts(int userId);

    List<ProductDTO> obtainMyProducts(String name, int currentUserId, int startPosition, int pageSize);

    int obtainMyProductSize(String name, int currentUserId);

    boolean obtainProductContainsUpdates(int productId);

    /*************************升级******************************/

    ProductUpdateHistoryDTO obtainProductUpdateHistoryById(int productUpdateId);

    List<ProductUpdateHistoryDTO> obtainUpdateHisoryByProduct(int productId, String updateWayFilter, String versionFilter, int startPosition, int pageSize);

    int obtainUpdateHisoryByProductSize(int productId, String updateWayFilter, String versionFilter);

    void changeProductUpdateHistoryDetails(ProductUpdateHistoryDTO updateDTO) throws IOException;

    UpdateFile obtainProductUpdateFile(int productUpdateFileId);

    void deleteProductUpdate(int productUpdateId);

    void deleteProductUploadFile(int productUpdateId, int uploadFileId);

    String obtainSNListFilepath(int updateId);
}
