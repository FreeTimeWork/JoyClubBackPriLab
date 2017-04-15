package com.joycity.joyclub.apifront.util;

import com.joycity.joyclub.apifront.exception.BusinessException;

import static com.joycity.joyclub.apifront.constant.ResultCode.DATA_NOT_EXIST;

/**
 * Created by CallMeXYZ on 2017/4/13.
 */
public class ThrowBusinessExceptionUtil {
    public static void checkNull(Object o, String msg) {
        if (o == null) {
            throw new BusinessException(DATA_NOT_EXIST, msg);
        }
    }
}
