package com.changhong.update.service;

import com.changhong.update.domain.Document;
import com.changhong.update.domain.ProductUpdate;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 下午3:12
 */
public interface DocumentService {

    public void uploadData(ProductUpdate update, Document document);

    public void uploadSNData(ProductUpdate update, MultipartFile snFile);

    public void obtainDownloadData(int productUpdateId, Document document, OutputStream outputStream);

    public void delete(ProductUpdate update, Document document, boolean both);
}
