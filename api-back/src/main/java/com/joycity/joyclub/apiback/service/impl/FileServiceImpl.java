package com.joycity.joyclub.apiback.service.impl;

import com.joycity.joyclub.apiback.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.apiback.modal.upload.FileUploadResult;
import com.joycity.joyclub.apiback.service.FileService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static com.joycity.joyclub.apiback.constant.ResultCode.UPLOAD_ERROR;

/**
 * Created by CallMeXYZ on 2017/3/28.
 */
@Service
public class FileServiceImpl implements FileService {
    private final Log logger = LogFactory.getLog(FileServiceImpl.class);
    @Value("${image.upload.api-back.path}")
    private String IMAGE_UPLOAD_PATH;
    @Value("${image.upload.api-back.result-base-url}")
    private String IMAGE_UPLOAD_RESULT_BASE_URL;
    private static final char FILE_SEPERATOR = '.';

    @Override
    public ResultData uploadImage(MultipartFile file) {
        try {
            if (file == null && file.isEmpty()) {
                throw new BusinessException(UPLOAD_ERROR, "上传文件为空");
            }
            String fileType = getFileType(file.getOriginalFilename());
            String fileName = UUID.randomUUID().toString().replace("-", "") +FILE_SEPERATOR+ fileType;
            Path savePath = Paths.get(IMAGE_UPLOAD_PATH, fileName);
            if (Files.notExists(savePath.getParent())) {
                Files.createDirectories(savePath.getParent());
            }
            /*if (Files.notExists(savePath)) {
                Files.createFile(savePath);
            }*/
            Files.copy(file.getInputStream(), savePath);
            FileUploadResult result = new FileUploadResult();
            result.setUrl(IMAGE_UPLOAD_RESULT_BASE_URL + fileName);
            result.setFileType(fileType);
            return new ResultData(result);

        } catch (IOException e) {
            logger.error("upload image error", e);
            throw new BusinessException(UPLOAD_ERROR, "上传出错");
        }
    }

    private String getFileType(String s) {
        int index = s.lastIndexOf(FILE_SEPERATOR);
        String type = s.substring(index + 1, s.length());
        return type;
    }
}
