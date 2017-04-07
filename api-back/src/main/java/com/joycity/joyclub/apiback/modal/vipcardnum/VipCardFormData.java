package com.joycity.joyclub.apiback.modal.vipcardnum;

import com.joycity.joyclub.apiback.modal.base.IdName;

import java.util.List;

/**
 * 会员卡管理里select需要的数据
 * Created by CallMeXYZ on 2017/4/7.
 */
public class VipCardFormData {
    private List<IdName> projects;
    /**
     * 所有的批次号
     * 批次号和项目不是联动的
     */
    private List<String> batches;

    public List<IdName> getProjects() {
        return projects;
    }

    public void setProjects(List<IdName> projects) {
        this.projects = projects;
    }

    public List<String> getBatches() {
        return batches;
    }

    public void setBatches(List<String> batches) {
        this.batches = batches;
    }
}
