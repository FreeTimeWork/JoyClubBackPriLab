package com.joycity.joyclub.commons.utils;

/**
 * Created by CallMeXYZ on 2017/5/22.
 */
public class SqlUtil {
    public static String getLikeStr (String value){
        if(value==null) {
            return value;
        }
        return "%"+value+"%";
    }
}
