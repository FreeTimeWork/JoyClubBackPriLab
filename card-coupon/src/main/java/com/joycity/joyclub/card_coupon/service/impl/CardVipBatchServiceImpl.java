package com.joycity.joyclub.card_coupon.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.joycity.joyclub.card_coupon.mapper.CardVipBatchMapper;
import com.joycity.joyclub.card_coupon.modal.BatchAndSum;
import com.joycity.joyclub.card_coupon.modal.generated.CardVipBatch;
import com.joycity.joyclub.card_coupon.service.CardVipBatchService;
import com.joycity.joyclub.client.mapper.ClientUserMapper;
import com.joycity.joyclub.commons.constant.RedisKeyConst;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.AbstractBatchInsertlUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by fangchen.chai on 2017/7/12.
 */
@Service
public class CardVipBatchServiceImpl implements CardVipBatchService {

    private Log logger = LogFactory.getLog(CardVipBatchServiceImpl.class);

    @Autowired
    private CardVipBatchMapper cardVipBatchMapper;
    @Autowired
    private ClientUserMapper clientUserMapper;
    @Autowired
    private RedisTemplate redisTemplate;


    public ResultData createCardVipBatch(List<List<String>> list){
        cardVipBatchMapper.clear();
        BoundHashOperations<String, String, String> cardVipBatchCache = redisTemplate.boundHashOps(RedisKeyConst.CARD_VIP_BATCH.getName());

        Set<String> tels = new HashSet<>();
        List<String> rows;
        for (int i = 0; i < list.size(); i++) {
            rows = list.get(i);
            if (rows != null && rows.size() > 0) {
                String telNum = rows.get(0);
                if (!StringUtils.isBlank(telNum)) {
                    tels.add(telNum);
                }
            }

        }

        String findBatch;
        Long count;
            do {
                String pix = String.valueOf(System.nanoTime());
                findBatch =  pix + RandomStringUtils.random(8, "1234567890");
               count = cardVipBatchMapper.countCardVipBatchByBatch(findBatch);
            } while (cardVipBatchCache.hasKey(findBatch) || count > 0);
            cardVipBatchCache.put(findBatch,findBatch);

        List<CardVipBatch> cardVipBatches = prepareCardVipBatch(tels,findBatch);
        logger.info("cardVipBatches = " + cardVipBatches + " tels = " + tels);
        if (cardVipBatches.isEmpty()) {
            throw new BusinessException(ResultCode.ERR_IMPORT_EXCEL,"手机号不存在");
        }
        int sum = 0;
        if (CollectionUtils.isNotEmpty(cardVipBatches)) {

            sum = new AbstractBatchInsertlUtils() {
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
                    return true;
                }

                @Override
                public String getUpdateSqlWhenDuplicate() {
                    return null;
                }

                @Override
                public String getValues(int index) {
                    CardVipBatch vipBatch = cardVipBatches.get(index);
                    StringBuilder builder = new StringBuilder();
                    builder.append("(")
                            .append("'" + vipBatch.getClientId() + "', ")
                            .append("'" + vipBatch.getBatch() + "' ")
                            .append(") ")
                    ;

                    return builder.toString();
                }
            }.batchInsert(cardVipBatches.size(),50000);
        }
        cardVipBatchCache.delete(findBatch);
        if (sum == 0) {
            throw new BusinessException(ResultCode.ERR_IMPORT_EXCEL,"数据已存在");
        }
        return new ResultData(new BatchAndSum(findBatch, sum));
    }

    private List<CardVipBatch> prepareCardVipBatch(Set<String> tels, String batch) {
        List<CardVipBatch> cardVipBatches = new ArrayList<>();

        for (String tel : tels) {
            Long clientId = clientUserMapper.getIdByTel(tel);
            //TODO:  cfc  如果没有查到clientId。到crm系统去查clientId
            if (clientId == null) {
                continue;
            }
            CardVipBatch cardVipBatch = new CardVipBatch();
            cardVipBatch.setClientId(clientId);
            cardVipBatch.setBatch(batch);
            cardVipBatches.add(cardVipBatch);
        }

        return cardVipBatches;
    }

}
