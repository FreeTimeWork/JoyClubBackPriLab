package com.joycity.joyclub.commons.utils;

/**
 * @auther fangchen.chai ON 2017/10/27
 */
public class StringUtility {

    public static String getConcatString(String split, Object... objects) {
        if (objects == null) {
            return "null";
        } else {
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < objects.length; ++i) {
                if (i == 0) {
                    sb.append(objects[i]);
                } else {
                    sb.append(split).append(objects[i]);
                }
            }

            return sb.toString();
        }
    }
}
