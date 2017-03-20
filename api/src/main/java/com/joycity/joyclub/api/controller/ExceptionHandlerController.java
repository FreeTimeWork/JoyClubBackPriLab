package com.joycity.joyclub.api.controller;

import com.joycity.joyclub.api.entity.base.ResultData;
import com.joycity.joyclub.api.exception.BusinessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by CallMeXYZ on 2017/3/20.
 */
@RestControllerAdvice
public class ExceptionHandlerController{
    @ExceptionHandler(BusinessException.class)
    public ResultData exceptionHandler(BusinessException e){
        return new ResultData(e.getCode(),e.getMessage());
    }
}
