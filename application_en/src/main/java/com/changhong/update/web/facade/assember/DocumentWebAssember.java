package com.changhong.update.web.facade.assember;

import com.changhong.update.domain.UpdateFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * User: Jack Wang
 * Date: 14-4-11
 * Time: 上午10:47
 */
public class DocumentWebAssember {

    public static UpdateFile toUpdateFileDomain(MultipartFile file, String actualFilePath) {
        return new UpdateFile(file, actualFilePath);
    }
}
