package com.joycity.joyclub.card_coupon.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.joycity.joyclub.card_coupon.mapper.CardThirdpartyCouponCodeMapper;
import com.joycity.joyclub.card_coupon.modal.BatchAndSum;
import com.joycity.joyclub.card_coupon.modal.generated.CardThirdpartyCouponCode;
import com.joycity.joyclub.card_coupon.service.ThirdpartyCouponCodeService;
import com.joycity.joyclub.card_coupon.util.RandomUtil;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.AbstractBatchInsertlUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fangchen.chai on 2017/7/13.
 */
@Service
public class ThirdpartyCouponCodeServiceImpl implements ThirdpartyCouponCodeService {

    @Autowired
    private CardThirdpartyCouponCodeMapper thirdpartyCouponCodeMapper;

    @Override
    public ResultData createThirdpartyCouponCode(Set<String> cardNos) {
        Long count = 0L;
        String batch = null;
        do {
            batch = RandomUtil.generateString(16);
            count = thirdpartyCouponCodeMapper.countByBatch(batch);
        } while (count > 0);
        List<CardThirdpartyCouponCode> thirdpartyCouponCodes = prepareThirdpartyCouponCode(cardNos,batch);

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
        return new ResultData(new BatchAndSum(batch, sum));
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
