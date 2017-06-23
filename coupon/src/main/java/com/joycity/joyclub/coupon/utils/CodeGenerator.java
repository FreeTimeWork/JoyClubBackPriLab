package com.joycity.joyclub.coupon.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by CallMeXYZ on 2017/6/23.
 */
public class CodeGenerator {
    /**
     * 最大券号
     */
    private static final long MAX_CODE = 999999999999L;
    /**
     * 最大重试制券次数
     */
    private static final int MAX_RETRY = 10;

    /**
     * 生成指定数量的券号
     *
     * @param num
     * @return
     */
    public int makeCodes(int num, CodeInsertHelper insertHelper) {
        // 制号逻辑是，生成一批卡号，然后尝试插入数据库。数据库以卡号做唯一约束。
        // 接下来再制作插入失败数量的卡号，此操作最多重复10次。
        //注意 可能在卡券号存在很多时，制卡越来越难。
        //记录插入数据库的卡券数量
        int numMade = 0;
        //记录需要制作的卡券数量
        int numNeedToMake = num;
        for (int i = 0; i < MAX_RETRY; i++) {
            List<Long> codesMadePerLoop = startMakeCode(numNeedToMake);
            int insertNum = insertHelper.insertCodes(codesMadePerLoop);
            numMade += insertNum;
            if (insertNum == numNeedToMake) {
                break;
            } else {
                numNeedToMake = num - insertNum;
            }
        }
        return numMade;
    }


    private List<Long> startMakeCode(int num) {
        List<Long> codes = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            codes.add(ThreadLocalRandom.current().nextLong(0, MAX_CODE));
        }
        return codes;
    }
}
