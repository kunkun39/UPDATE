package com.changhong.update.service;

import com.changhong.common.exception.CHDocumentOperationException;
import com.changhong.common.utils.IOUtils;
import com.changhong.update.domain.Document;
import com.changhong.update.domain.Product;
import com.changhong.update.domain.ProductUpdate;
import com.changhong.update.domain.ProductUpdateJSONHelper;
import com.changhong.update.repository.ProductDao;
import com.changhong.update.web.facade.dto.ProductUpdateHistoryDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.security.PrivateKey;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 下午3:12
 */
@Service("documentService")
public class DocumentServiceImpl implements DocumentService, InitializingBean {

    private static final Log logger = LogFactory.getLog(DocumentServiceImpl.class);

    //文件存储的基本路径
    @Value("${project.upload.file.path}")
    private String baseStorePath;

    //Web的路径
    @Value("${application.web.url}")
    private String webUrl;

    public void afterPropertiesSet() throws Exception {
        Assert.hasText(baseStorePath, "the basic store path not configure");
    }

    public void uploadData(ProductUpdate update, Document document) {
        //创建存储文件的基本路径
        String returnPath = DocumentPathResolver.generateUploadFileNamePath(update);
        File directory = new File(baseStorePath + File.separatorChar + returnPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        //上传有文件
        if (document != null) {
            String dataStoreFile = DocumentPathResolver.generateDataFileName(document, update);
            File dataFile = new File(directory, dataStoreFile);

            document.setActualFileName(dataStoreFile);
            document.setActualFilePath(returnPath);

            try {
                OutputStream dataOut = new FileOutputStream(dataFile.getAbsolutePath());
                FileCopyUtils.copy(document.getFile().getInputStream(), dataOut);

                logger.info("finish upload product update file for product " + dataStoreFile);
            } catch (Exception e) {
                logger.error(e);
                throw new CHDocumentOperationException("exception update file [" + dataStoreFile + "] failed for product " + dataStoreFile, e);
            }
        } else {
            //未上传文件,如果发现原来文件还是存在，需要考虑目录的变动
            document = update.getUpdateFile();
            if (document != null) {
                String dataStoreFile = DocumentPathResolver.generateDataFileName(document, update);
                File dataFile = new File(directory, dataStoreFile);

                String oldFileName = document.getActualFileName();
                String oldFilePath = document.getActualFilePath();
                document.setActualFileName(dataStoreFile);
                document.setActualFilePath(returnPath);

                try {
                    if (!oldFileName.equals(dataStoreFile) || !oldFilePath.equals(returnPath)) {
                        OutputStream dataOut = new FileOutputStream(dataFile.getAbsolutePath());
                        FileInputStream in = new FileInputStream(new File(baseStorePath + File.separatorChar + oldFilePath + File.separatorChar + oldFileName));
                        FileCopyUtils.copy(in, dataOut);
                        logger.info("finish upload product update file for product " + dataStoreFile);
                    }

                } catch (Exception e) {
                    logger.error(e);
                    throw new CHDocumentOperationException("exception update file [" + dataStoreFile + "] failed for product " + dataStoreFile, e);
                }
            }
        }

        //产生JSON文件
        String jsonStoreFile = DocumentPathResolver.generateJsonFileName(update);
        File jsonFile = new File(directory, jsonStoreFile);

        //上传JSON文件
        String jsonContent = ProductUpdateJSONHelper.generateJSONContent(update, webUrl);
        IOUtils.copyStringToFile(jsonContent, jsonFile);
    }

    public void uploadSNData(ProductUpdate update, MultipartFile snFile) {
        //创建存储文件的基本路径
        String returnPath = DocumentPathResolver.generateUploadFileNamePath(update);
        File directory = new File(baseStorePath + File.separatorChar + returnPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        //上传有文件
        if (snFile != null && snFile.getSize() > 0) {
            File dataFile = new File(directory, update.getUpdateVersionName() + "_" + "devices.txt");

            try {
                OutputStream dataOut = new FileOutputStream(dataFile.getAbsolutePath());
                FileCopyUtils.copy(snFile.getInputStream(), dataOut);

                logger.info("finish upload product update sn file for product " + update.getProgramName());
            } catch (Exception e) {
                logger.error(e);
                throw new CHDocumentOperationException("exception updatesn  file [" + update.getProgramName() + "]", e);
            }
        }
    }

    public void obtainDownloadData(int productUpdateId, Document document, OutputStream outputStream) {
    }

    public void delete(ProductUpdate update, Document document, boolean both) {
        String dataStoreFile = DocumentPathResolver.generateDataFileName(document, update);
        String jsonStoreFile = DocumentPathResolver.generateJsonFileName(update);
        String returnPath = DocumentPathResolver.generateUploadFileNamePath(update);

        File directory = new File(baseStorePath + File.separatorChar + returnPath);
        File file = new File(directory, dataStoreFile);
        file.delete();
        if (both) {
            File json = new File(directory, jsonStoreFile);
            json.delete();
        }
    }
}
