package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.constant.ResultCode;
import com.joycity.joyclub.apiback.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.ResultError;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * Created by CallMeXYZ on 2017/3/20.
 */
@RestControllerAdvice
public class ExceptionHandlerController {
    private Log logger = LogFactory.getLog(ExceptionHandlerController.class);
    @ExceptionHandler(BusinessException.class)
    public ResultError businessExceptionHandler(BusinessException e) {
        return new ResultError(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResultError maxUploadSizeExceededExceptionHandler(MaxUploadSizeExceededException e) {
        return new ResultError(ResultCode.UPLOAD_ERROR,"上传文件过大，最大为10M");
    }
}
