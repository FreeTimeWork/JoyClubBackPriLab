package com.joycity.joyclub.apiback.controller;

import com.joycity.joyclub.apiback.modal.base.ResultData;
import com.joycity.joyclub.apiback.exception.BusinessException;
import com.joycity.joyclub.apiback.modal.base.ResultError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by CallMeXYZ on 2017/3/20.
 */
@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(BusinessException.class)
    public ResultError exceptionHandler(BusinessException e) {
        return new ResultError(e.getCode(), e.getMessage());
    }
}
