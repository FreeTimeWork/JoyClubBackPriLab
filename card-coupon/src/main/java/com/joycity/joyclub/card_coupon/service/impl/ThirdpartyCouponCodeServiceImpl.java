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
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.commons.exception.BusinessException;
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

    @Override
    public ResultData createThirdpartyCouponCode(List<List<String>> list, Long thirdpartyShopId) {
        thirdpartyCouponCodeMapper.clear();
        BoundHashOperations<String, String, String> thirdpartyCouponBatchCache = redisTemplate.boundHashOps(RedisKeyConst.THIRD_PARTY_COUPON_BATCH.getName());

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
            count = thirdpartyCouponCodeMapper.countIncludeDeleteByBatch(findBatch);
        } while (thirdpartyCouponBatchCache.hasKey(findBatch) || count > 0);
        thirdpartyCouponBatchCache.put(findBatch,findBatch);
        List<CardThirdpartyCouponCode> thirdpartyCouponCodes = prepareThirdpartyCouponCode(cardNos, findBatch, thirdpartyShopId);

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
                    return "(thirdparty_shop_id, code, batch )";
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
                            .append("'" + thirdpartyCouponCode.getThirdpartyShopId() + "', ")
                            .append("'" + thirdpartyCouponCode.getCode() + "', ")
                            .append("'" + thirdpartyCouponCode.getBatch() + "' ")
                            .append(") ")
                    ;

                    return builder.toString();
                }
            }.batchInsert(thirdpartyCouponCodes.size());

        }
        thirdpartyCouponBatchCache.delete(findBatch);
        if (sum == 0) {
            throw new BusinessException(ResultCode.ERR_IMPORT_EXCEL,"数据已存在");
        }
        return new ResultData(new BatchAndSum(findBatch, sum));
    }

    private List<CardThirdpartyCouponCode> prepareThirdpartyCouponCode(Set<String> cardNos, String batch, Long thirdpartyShopId) {

        List<CardThirdpartyCouponCode> thirdpartyCouponCodes = new ArrayList<>();

        for (String str : cardNos) {
            CardThirdpartyCouponCode thirdpartyCouponCode = new CardThirdpartyCouponCode();
            thirdpartyCouponCode.setThirdpartyShopId(thirdpartyShopId);
            thirdpartyCouponCode.setBatch(batch);
            thirdpartyCouponCode.setCode(str);
            thirdpartyCouponCodes.add(thirdpartyCouponCode);
        }
        return thirdpartyCouponCodes;
    }

}
