package com.joycity.joyclub.apifront.modal.base;

/**
 * 基础对象，包含id,name,portrait
 * Created by CallMeXYZ on 2017/4/5.
 */
public class IdNamePortrait {
    private Long id;
    private String name;
    private String portrait;

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

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
