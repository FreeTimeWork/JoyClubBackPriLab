package com.joycity.joyclub.apiback.modal.base;

/**
 * 只有id,name属性的简单类
 * Created by CallMeXYZ on 2017/3/29.
 */
public class IdName {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
