package com.changhong.update.service;

import com.changhong.common.utils.SecurityUtils;
import com.changhong.common.web.application.ApplicationEventPublisher;
import com.changhong.system.domain.SystemActionLog;
import com.changhong.system.domain.User;
import com.changhong.system.web.event.CategoryActionEvent;
import com.changhong.system.web.event.ProductActionEvent;
import com.changhong.system.web.event.ProductUpdateActionEvent;
import com.changhong.update.domain.Product;
import com.changhong.update.domain.ProductCategory;
import com.changhong.update.domain.ProductUpdate;
import com.changhong.update.domain.UpdateFile;
import com.changhong.update.repository.ProductDao;
import com.changhong.update.web.facade.assember.DocumentWebAssember;
import com.changhong.update.web.facade.assember.ProductUpdateWebAssember;
import com.changhong.update.web.facade.assember.ProductWebAssember;
import com.changhong.update.web.facade.dto.CategoryDTO;
import com.changhong.update.web.facade.dto.ProductDTO;
import com.changhong.update.web.facade.dto.ProductUpdateHistoryDTO;
import com.changhong.yupan.repository.UpdateDao;
import com.changhong.yupan.service.DeviceUpdateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 下午2:30
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private UpdateDao updateDao;

    @Autowired
    private ProductDao productDao;

    @Value("${project.upload.file.path}")
    private String baseStorePath;

    /*************************目录******************************/

    public void saveOrUpdate(int id, String name, int parentId) {
        ProductCategory category = ProductWebAssember.toCategoryDomain(id, name, parentId);
        productDao.persist(category);

        //记录目录添加日志
        if(id <= 0) {
            User user = SecurityUtils.currentUser();
            ApplicationEventPublisher.publish(new CategoryActionEvent(SystemActionLog.ActionType.ADD, user.getName(), user.getUsername(), name));
        }
    }

    public CategoryDTO obtainCategoryById(int id) {
        ProductCategory category = productDao.loadProductCategoryById(id);
        return ProductWebAssember.toCategoryDTO(category);
    }

    public boolean deleteCategory(int categoryId) {
        boolean canDelete = false;
        ProductCategory category = productDao.loadProductCategoryById(categoryId);

        if(category.getChildren() != null && !category.getChildren().isEmpty()) {
            return canDelete;
        }
        if(category.getProducts() != null && !category.getProducts().isEmpty()) {
            return canDelete;
        }

        canDelete = true;
        productDao.delete(category);

        //记录目录删除日志
        User user = SecurityUtils.currentUser();
        ApplicationEventPublisher.publish(new CategoryActionEvent(SystemActionLog.ActionType.DELETE, user.getName(), user.getUsername(), category.getName()));

        return canDelete;
    }

    public String obtainCategoryByParentId(int parentId) {
        List<ProductCategory> categories = productDao.loadCategoryByParentId(parentId);
        return ProductWebAssember.toJson(categories, parentId);
    }

    /*************************产品******************************/

    public void deleteProduct(int productId) {
        List<ProductUpdate> updates = productDao.loadProductUpdateByProductId(productId);
        if (updates != null) {
            for (ProductUpdate update : updates) {
                UpdateFile file = update.getUpdateFile();
                if (file != null) {
                    documentService.delete(update, file, true);
                }
                productDao.delete(update);
            }
        }

        Product product = (Product) productDao.findById(productId, Product.class);
        productDao.delete(product);

        //记录删除产品日志
        User user = SecurityUtils.currentUser();
        ApplicationEventPublisher.publish(new ProductActionEvent(SystemActionLog.ActionType.DELETE, user.getName(), user.getUsername(), product.getName()));
    }

    public List<ProductDTO> obtainProducts(String name, int categoryId, int startPosition, int pageSize) {
        List<Product> products = productDao.loadProducts(name, categoryId, startPosition, pageSize);
        return ProductWebAssember.toProductDTOList(products);
    }

    public int obtainProductSize(String name, int categoryId) {
        return productDao.loadProductSize(name, categoryId);
    }

    public int obtainProductModelChecking(int productId, String model) {
        boolean duplicate = productDao.loadProductModelDuplicate(productId, model);
        if (duplicate) {
            return 1;
        } else {
            if (productId > 0) {
                Product product = (Product) productDao.findById(productId, Product.class);
                if (!product.getModel().equals(model)) {
                    int updateSize = productDao.loadProductUpdateSize(productId);
                    if (updateSize > 0) {
                        return 2;
                    }
                }
            }
        }
        return 0;
    }

    public ProductDTO obtainProductById(int productId) {
        Product product = (Product) productDao.findById(productId, Product.class);
        return ProductWebAssember.toProductDTO(product);
    }

    public void changeProductDetails(ProductDTO productDTO) {
        Product product = ProductWebAssember.toProductDomain(productDTO);
        productDao.persist(product);

        //记录添加产品日志
        if(productDTO.getId() <= 0) {
            User user = SecurityUtils.currentUser();
            ApplicationEventPublisher.publish(new ProductActionEvent(SystemActionLog.ActionType.ADD, user.getName(), user.getUsername(), productDTO.getProductName()));
        }
    }

    public List<Product> obtainAllUserProducts(int userId) {
        return productDao.obtainAllUserProducts(userId);
    }

    public List<ProductDTO> obtainMyProducts(String name, int currentUserId, int startPosition, int pageSize) {
        List<Product> products = productDao.loadMyProducts(name, currentUserId, startPosition, pageSize);
        return ProductWebAssember.toProductDTOList(products);
    }

    public int obtainMyProductSize(String name, int currentUserId) {
        return productDao.loadMyProductSize(name, currentUserId);
    }

    public boolean obtainProductContainsUpdates(int productId) {
        return productDao.loadProductContainsUpdates(productId);
    }

    /*************************升级******************************/

    public ProductUpdateHistoryDTO obtainProductUpdateHistoryById(int productUpdateId) {
        ProductUpdate productUpdate = (ProductUpdate) productDao.findById(productUpdateId, ProductUpdate.class);
        return ProductUpdateWebAssember.toProductUpdateHistoryDTO(productUpdate);
    }

    public List<ProductUpdateHistoryDTO> obtainUpdateHisoryByProduct(int productId, String updateWayFilter, String versionFilter, int startPosition, int pageSize) {
        List<ProductUpdate> updates = productDao.loadUpdateHisoryByProduct(productId, updateWayFilter, versionFilter, startPosition, pageSize);
        return ProductUpdateWebAssember.toProductUpdateHistoryDTOList(updates);
    }

    public int obtainUpdateHisoryByProductSize(int productId, String updateWayFilter, String versionFilter) {
        return productDao.loadUpdateHisoryByProductSize(productId, updateWayFilter, versionFilter);
    }

    public void changeProductUpdateHistoryDetails(ProductUpdateHistoryDTO updateDTO) throws IOException {
        MultipartFile file = updateDTO.getFile();
        MultipartFile snFile = updateDTO.getSnFile();
        UpdateFile updateFile = null;

        if(file != null && file.getSize() > 0) {
            updateFile = DocumentWebAssember.toUpdateFileDomain(file, "");
        }
        ProductUpdate update = ProductUpdateWebAssember.toProductUpdateDomain(updateDTO, updateFile);
        documentService.uploadData(update, updateFile);
        documentService.uploadSNData(update, snFile);
        productDao.persist(update);

        //清楚缓存
        updateDao.cleanCache();

        //记录添加产品日志
        if(updateDTO.getId() <= 0) {
            User user = SecurityUtils.currentUser();
            ApplicationEventPublisher.publish(new ProductUpdateActionEvent(SystemActionLog.ActionType.ADD,
                    user.getName(), user.getUsername(), update.getProduct().getName(), update.getUpdateWay()));
        }
    }

    public UpdateFile obtainProductUpdateFile(int productUpdateFileId) {
        return (UpdateFile) productDao.findById(productUpdateFileId, UpdateFile.class);
    }

    public void deleteProductUpdate(int productUpdateId) {
        ProductUpdate update = (ProductUpdate) productDao.findById(productUpdateId, ProductUpdate.class);
        UpdateFile file = update.getUpdateFile();
        if (file != null) {
            documentService.delete(update, file, true);
        }
        productDao.delete(update);

        //清楚缓存
        updateDao.cleanCache();

         //记录删除产品日志
        User user = SecurityUtils.currentUser();
        ApplicationEventPublisher.publish(new ProductUpdateActionEvent(SystemActionLog.ActionType.DELETE,
                user.getName(), user.getUsername(), update.getProduct().getName(), update.getUpdateWay()));
    }

    public void deleteProductUploadFile(int productUpdateId, int uploadFileId) {
        ProductUpdate update = (ProductUpdate) productDao.findById(productUpdateId, ProductUpdate.class);
        update.setUpdateFile(null);
        UpdateFile file = (UpdateFile) productDao.findById(uploadFileId, UpdateFile.class);
        productDao.delete(file);
        documentService.delete(update, file, false);
    }

    public String obtainSNListFilepath(int updateId) {
        ProductUpdate update = (ProductUpdate) productDao.findById(updateId, ProductUpdate.class);
        String returnPath = DocumentPathResolver.generateUploadFileNamePath(update);
        File directory = new File(baseStorePath + File.separatorChar + returnPath);

        return directory.getAbsolutePath() + File.separatorChar + "devices.txt";
    }
}
