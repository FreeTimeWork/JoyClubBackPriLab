package com.joycity.joyclub.card_coupon.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.joycity.joyclub.card_coupon.mapper.CardVipBatchMapper;
import com.joycity.joyclub.card_coupon.modal.BatchAndSum;
import com.joycity.joyclub.card_coupon.modal.generated.CardVipBatch;
import com.joycity.joyclub.card_coupon.service.CardVipBatchService;
import com.joycity.joyclub.card_coupon.util.RandomUtil;
import com.joycity.joyclub.client.mapper.ClientUserMapper;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.AbstractBatchInsertlUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * Created by fangchen.chai on 2017/7/12.
 */
@Service
public class CardVipBatchServiceImpl implements CardVipBatchService {

    @Autowired
    private CardVipBatchMapper cardVipBatchMapper;
    @Autowired
    private ClientUserMapper clientUserMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    public ResultData createCardVipBatch(List<List<String>> list){
        ValueOperations<String, String> cardVipBatchCache = redisTemplate.opsForValue();
        Set<String> vipNos = new HashSet<>();
        List<String> rows;
        for (int i = 0; i < list.size(); i++) {
            rows = list.get(i);
            if (rows != null && rows.size() > 0) {
                String cardNo = rows.get(0);
                if (!StringUtils.isBlank(cardNo)) {
                    vipNos.add(cardNo);
                }
            }

        }

        String findBatch;
        Long count;
        synchronized (this) {
            do {
                findBatch = RandomUtil.generateString(16);
                count = cardVipBatchMapper.countCardVipBatchByBatch(findBatch);
            } while (redisTemplate.hasKey(findBatch) || count > 0);
            cardVipBatchCache.set(findBatch,findBatch);
        }
        List<CardVipBatch> cardVipBatches = prepareCardVipBatch(vipNos,findBatch);

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
            }.batchInsert(cardVipBatches.size());
        }
        redisTemplate.delete(findBatch);
        return new ResultData(new BatchAndSum(findBatch, sum));
    }

    private List<CardVipBatch> prepareCardVipBatch(Set<String> tels, String batch) {
        List<CardVipBatch> cardVipBatches = new ArrayList<>();

        for (String tel : tels) {
            Long clientId = clientUserMapper.getIdByTel(tel);
            if (clientId != null) {
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
