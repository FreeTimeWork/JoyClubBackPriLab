package com.joycity.joyclub.apifront.controller;


import com.joycity.joyclub.apifront.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.ResultError;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by CallMeXYZ on 2017/3/20.
 */
@RestControllerAdvice
public class ExceptionHandlerFrontController {
    private Log logger = LogFactory.getLog(ExceptionHandlerFrontController.class);
    @ExceptionHandler(BusinessException.class)
    public ResultError businessExceptionHandler(BusinessException e) {
        return new ResultError(e.getCode(), e.getMessage());
    }

}
