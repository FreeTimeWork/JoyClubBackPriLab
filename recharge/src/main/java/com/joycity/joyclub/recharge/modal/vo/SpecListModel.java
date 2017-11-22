package com.joycity.joyclub.recharge.modal.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * @auther fangchen.chai ON 2017/11/21
 */
public class SpecListModel {

    private String mo;
    private String province;

    private List<SpecList> specList;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getMo() {
        return mo;
    }

    public void setMo(String mo) {
        this.mo = mo;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public List<SpecList> getSpecList() {
        return specList;
    }

    public void setSpecList(List<SpecList> specList) {
        this.specList = specList;
    }
}
