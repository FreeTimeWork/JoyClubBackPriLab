package com.joycity.joyclub.card_coupon.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.joycity.joyclub.card_coupon.mapper.CardThirdpartyCouponCodeMapper;
import com.joycity.joyclub.card_coupon.modal.BatchAndSum;
import com.joycity.joyclub.card_coupon.modal.generated.CardThirdpartyCouponCode;
import com.joycity.joyclub.card_coupon.service.ThirdpartyCouponCodeService;
import com.joycity.joyclub.commons.constant.RedisKeyConst;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.AbstractBatchInsertlUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by fangchen.chai on 2017/7/13.
 */
@Service
public class ThirdpartyCouponCodeServiceImpl implements ThirdpartyCouponCodeService {

    @Autowired
    private CardThirdpartyCouponCodeMapper thirdpartyCouponCodeMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    private final BoundHashOperations<String, String, String> thirdpartyCouponBatchCache;

    @Autowired
    public ThirdpartyCouponCodeServiceImpl(RedisTemplate redisTemplate) {
        thirdpartyCouponBatchCache = redisTemplate.boundHashOps(RedisKeyConst.THIRDPARTY_COUPON_BATCH);
    }
    @Override
    public ResultData createThirdpartyCouponCode(List<List<String>> list) {

        Set<String> cardNos = new HashSet<>();
        List<String> rows;
        for (int i = 0; i < list.size(); i++) {
            rows = list.get(i);
            if (rows != null && rows.size() > 0) {
                String cardNo = rows.get(0);
                if (!StringUtils.isBlank(cardNo)) {
                    cardNos.add(cardNo);
                }
            }

        }

        Long count;
        String findBatch;
        do {
            String pix = String.valueOf(System.nanoTime());
            findBatch =  pix + RandomStringUtils.random(8, "1234567890");
            count = thirdpartyCouponCodeMapper.countByBatch(findBatch);
        } while (redisTemplate.hasKey(findBatch) || count > 0);
        thirdpartyCouponBatchCache.put(findBatch,findBatch);
        List<CardThirdpartyCouponCode> thirdpartyCouponCodes = prepareThirdpartyCouponCode(cardNos,findBatch);

        int sum = 0;
        if (CollectionUtils.isNotEmpty(thirdpartyCouponCodes)) {
            sum = new AbstractBatchInsertlUtils() {
                @Override
                public int executeInsert(String longInsertSql) {
                    return thirdpartyCouponCodeMapper.batchInsertCardThirdpartyCouponCode(longInsertSql);
                }

                @Override
                public String getTableName() {
                    return "card_thirdparty_coupon_code";
                }

                @Override
                public String getValuesNames() {
                    return "(code, batch)";
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
                    CardThirdpartyCouponCode thirdpartyCouponCode = thirdpartyCouponCodes.get(index);
                    StringBuilder builder = new StringBuilder();
                    builder.append("(")
                            .append("'" + thirdpartyCouponCode.getCode() + "', ")
                            .append("'" + thirdpartyCouponCode.getBatch() + "' ")
                            .append(") ")
                    ;

                    return builder.toString();
                }
            }.batchInsert(thirdpartyCouponCodes.size());

        }
        redisTemplate.delete(findBatch);
        return new ResultData(new BatchAndSum(findBatch, sum));
    }

    private List<CardThirdpartyCouponCode> prepareThirdpartyCouponCode(Set<String> cardNos, String batch) {

        List<CardThirdpartyCouponCode> thirdpartyCouponCodes = new ArrayList<>();

        for (String str : cardNos) {
            CardThirdpartyCouponCode thirdpartyCouponCode = new CardThirdpartyCouponCode();
            thirdpartyCouponCode.setBatch(batch);
            thirdpartyCouponCode.setCode(str);
            thirdpartyCouponCodes.add(thirdpartyCouponCode);
        }
        return thirdpartyCouponCodes;
    }

}
