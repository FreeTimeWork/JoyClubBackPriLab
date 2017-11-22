package com.joycity.joyclub.recharge.modal.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @auther fangchen.chai ON 2017/11/21
 */
public class SpecList {

    private String spec;
    private String scope;
    private String officialPrice;
    private String actualPrice;
    private String appendCount;
    private String validMonth;
    private String effective;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getOfficialPrice() {
        return officialPrice;
    }

    public void setOfficialPrice(String officialPrice) {
        this.officialPrice = officialPrice;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getAppendCount() {
        return appendCount;
    }

    public void setAppendCount(String appendCount) {
        this.appendCount = appendCount;
    }

    public String getValidMonth() {
        return validMonth;
    }

    public void setValidMonth(String validMonth) {
        this.validMonth = validMonth;
    }

    public String getEffective() {
        return effective;
    }

    public void setEffective(String effective) {
        this.effective = effective;
    }
}
