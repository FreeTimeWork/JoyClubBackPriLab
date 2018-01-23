package com.joycity.joyclub.recharge.modal.filter;

import com.joycity.joyclub.commons.modal.base.TimeRange;

/**
 * @auther fangchen.chai ON 2018/1/23
 */
public class XiangfuRechargeDetailFilter {
    private String type;
    private Long clientId;
    private TimeRange timeRange;
    private Boolean valid;

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public TimeRange getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(TimeRange timeRange) {
        this.timeRange = timeRange;
    }
}
