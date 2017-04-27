package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.apiback.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.joycity.joyclub.commons.constant.Global.URL_API_BACK;

/**
 * Created by CallMeXYZ on 2017/3/20.
 */
@RestController
@RequestMapping(URL_API_BACK)
public class FileController {
    @Autowired
    private FileService fileService;
// TODO: 2017/3/28 增加文件类型校验
    /**
     * 目前只考虑图片上传
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResultData upload(@RequestParam MultipartFile file) {
        return fileService.uploadImage(file);
    }

}
