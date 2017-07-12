package com.joycity.joyclub.card_coupon.service;

import java.util.List;

import com.joycity.joyclub.card_coupon.modal.generated.CardVipBatch;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.PageUtil;

/**
 * Created by fangchen.chai on 2017/7/12.
 */
public interface CardVipBatchService {

    ResultData batchInsertCardVipBatch(List<CardVipBatch> cardVipBatches);

}
