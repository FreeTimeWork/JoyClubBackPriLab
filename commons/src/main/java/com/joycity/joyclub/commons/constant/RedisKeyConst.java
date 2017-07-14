package com.joycity.joyclub.commons.constant;

/**
 * Created by fangchen.chai on 2017/7/14.
 */
public enum RedisKeyConst {
    CLIENT_TOKEN("clientToken"),
    CLIENT_TOKEN_EXPIRE("clientTokenExpire");
    String name;

    RedisKeyConst(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
