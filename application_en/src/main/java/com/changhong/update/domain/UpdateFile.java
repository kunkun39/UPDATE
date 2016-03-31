package com.changhong.update.domain;

import org.springframework.web.multipart.MultipartFile;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 下午3:06
 */
public class UpdateFile extends Document {

    public UpdateFile() {
    }

    public UpdateFile(MultipartFile file, String actualFilePath) {
        super(file, actualFilePath);
    }
}
