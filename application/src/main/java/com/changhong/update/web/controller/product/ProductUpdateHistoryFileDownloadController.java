package com.changhong.update.web.controller.product;

import com.changhong.update.domain.UpdateFile;
import com.changhong.update.service.DocumentService;
import com.changhong.update.service.ProductService;
import com.changhong.update.web.facade.dto.ProductUpdateHistoryDTO;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Jack Wang
 * Date: 14-4-11
 * Time: 下午2:24
 */
public class ProductUpdateHistoryFileDownloadController extends AbstractController {

    private ProductService productService;

    private DocumentService documentService;

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int productUpdateId = ServletRequestUtils.getIntParameter(request, "productUpdateId", -1);
        int updateUploadFileId = ServletRequestUtils.getIntParameter(request, "updateUploadFileId", -1);

        UpdateFile updateFile = productService.obtainProductUpdateFile(updateUploadFileId);

        String filename = new String(updateFile.getActualFileName().replaceAll(" ", "").getBytes("GBK"), "ISO_8859_1");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        ServletOutputStream out = response.getOutputStream();
        documentService.obtainDownloadData(productUpdateId, updateFile, out);
        return null;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }
}
