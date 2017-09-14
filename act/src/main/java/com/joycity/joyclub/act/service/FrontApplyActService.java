package com.joycity.joyclub.act.service;

import com.joycity.joyclub.act.modal.generated.FrontApplyAct;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

import javax.xml.transform.Result;

/**
 * Created by fangchen.chai on 2017/9/14
 */
public interface FrontApplyActService {

    ResultData createApplyAct(FrontApplyAct applyAct);

    ResultData getListApplyAct(Byte reviewStatus, PageUtil pageUtil);

    ResultData permitApplyAct(Long id);

    ResultData rejectApplyAct(Long id, String reviewInfo);
}
