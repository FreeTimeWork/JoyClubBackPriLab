package com.joycity.joyclub.commons.utils;

/**
 * Created by CallMeXYZ on 2017/4/10.
 */
public class Check {
    /**
     * 判断一系列参数是否有null
     * @param values
     * @return
     */
    public static boolean checkNull(Object... values) {
        boolean result = false;
        for (Object value : values) {
            if (value == null) {
                result = true;
                break;
            }
        }
        return result;
    }
}
