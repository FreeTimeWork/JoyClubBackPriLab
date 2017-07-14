package com.joycity.joyclub.card_coupon.service;

import java.util.List;

import com.joycity.joyclub.commons.modal.base.ResultData;

/**
 * Created by fangchen.chai on 2017/7/12.
 */
public interface CardVipBatchService {

    ResultData createCardVipBatch(List<List<String>> list);


}
