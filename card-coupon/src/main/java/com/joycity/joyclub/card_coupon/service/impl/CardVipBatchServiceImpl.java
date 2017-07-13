package com.joycity.joyclub.card_coupon.service.impl;

import java.util.List;

import com.joycity.joyclub.card_coupon.mapper.CardVipBatchMapper;
import com.joycity.joyclub.card_coupon.modal.generated.CardVipBatch;
import com.joycity.joyclub.card_coupon.service.CardVipBatchService;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.AbstractBatchInsertlUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by fangchen.chai on 2017/7/12.
 */
public class CardVipBatchServiceImpl implements CardVipBatchService {

    @Autowired
    private CardVipBatchMapper cardVipBatchMapper;

    public ResultData batchInsertCardVipBatch(List<CardVipBatch> cardVipBatches){

        return new ResultData(new AbstractBatchInsertlUtils() {

            @Override
            public int executeInsert(String longInsertSql) {
                return cardVipBatchMapper.batchInsertCardVipBatch(longInsertSql);
            }

            @Override
            public String getTableName() {
                return "card_vip_batch";
            }

            @Override
            public String getValuesNames() {
                return "(client_id, batch)";
            }

            @Override
            public Boolean ifIgnoreDuplicate() {
                return false;
            }

            @Override
            public String getUpdateSqlWhenDuplicate() {
                return "client_id = VALUES (client_id)";
            }

            @Override
            public String getValues(int index) {
                CardVipBatch vipBatch = cardVipBatches.get(index);
                StringBuilder builder = new StringBuilder();
                builder.append("(")
                        .append("'"+vipBatch.getClientId()+"', ")
                        .append("'"+vipBatch.getBatch()+"' ")
                        .append(") ")
                ;

                return builder.toString();
            }
        }.batchInsert(cardVipBatches.size()));
    }

}
