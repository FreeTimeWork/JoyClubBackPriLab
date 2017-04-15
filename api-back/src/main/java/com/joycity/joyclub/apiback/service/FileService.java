package com.joycity.joyclub.apiback.service;

import com.joycity.joyclub.commons.modal.base.ResultData;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by CallMeXYZ on  2017/3/28.
 */
public interface FileService {
    ResultData uploadImage(MultipartFile file);
}
