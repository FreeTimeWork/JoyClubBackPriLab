package com.joycity.joyclub.recharge.constants;

import java.util.HashMap;
import java.util.Map;

public enum TelOperator {
    CM("1","中国移动"),
    CU("2","中国联通"),
    CT("3","中国电信"),

    ;

    private String code;
    private String name;
    public static Map<String,TelOperator> mapNameKey;

    static {
        mapNameKey = new HashMap<>();
        for (TelOperator tel : TelOperator.values()) {
            mapNameKey.put(tel.getName(), tel);
        }
    }
    TelOperator() {
    }

    TelOperator(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
