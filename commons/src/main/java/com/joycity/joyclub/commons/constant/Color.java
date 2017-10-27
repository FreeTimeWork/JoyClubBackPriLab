package com.joycity.joyclub.commons.constant;

import com.i360r.framework.i18n.EnumMessageTranslator;
import com.i360r.framework.mybatis.typehandler.IdInterface;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum Color implements IdInterface {
    RED(1, "RED", "Red"), 
    WHITE(2, "WHITE", "White"), 
    YELLOW(3, "YELLOW", "Yellow"), 
    BLACK(4, "BLACK", "Black"), 
    BLUE(5, "BLUE", "Blue"),
    ORANGE(6, "ORANGE", "Orange"),
    GREEN(7, "GREEN", "Green"),
    PURPLE(8, "PURPLE", "Purple"),
    PINK(9, "PINK", "Pink"),
    GRAY(10, "GRAY", "Gray"),
    BROWN(11, "BROWN", "Brown");

    private static final Map<String, Color> code2Color;
    private static Map<String, Color> name2Color;

    private int id;
    private String code;
    private String description;

    static {
        code2Color = new HashMap<String, Color>();

        for (Color color : Color.values()) {
            code2Color.put(color.getCode(), color);
        }
    }

    public static Color fromCode(String code) {
        return code2Color.get(code);
    }
    
    public static Color fromName(String name) {
        if(name2Color == null){  
            name2Color = new ConcurrentHashMap<String, Color>();
            for (Color color : Color.values()) {            
                name2Color.put(color.getName(), color);
            }
        }       
        return name2Color.get(name);
    }


    Color(int id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

    @Override
	public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return EnumMessageTranslator.getName(getClass().getSimpleName() + "." + code);
    }

}
