package com.changhong.update.web.dwr;

import com.changhong.update.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: Jack Wang
 * Date: 14-4-11
 * Time: 下午2:03
 */
@Service("productDWRHandler")
public class ProductDWRHandler {

     @Autowired
    private ProductService productService;

    public void removeAssetAttachedFile(int productUpdateId, int uploadFileId) {
        productService.deleteProductUploadFile(productUpdateId, uploadFileId);
    }

    public boolean isProductContainUpdates(int productId) {
        return productService.obtainProductContainsUpdates(productId);
    }
}
