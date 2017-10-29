package com.joycity.joyclub.commons.constant;



import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum Color {
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


    Color(int id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

	public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }


}
