package com.joycity.joyclub.coupon.utils;

import java.util.List;

/**
 * Created by CallMeXYZ on 2017/6/23.
 * 券号插入数据库抽象类
 */
public abstract class CodeInsertHelper {
    /**
     * 给定一批券号，实现插入
     *
     * @param codes
     * @return 实际插入的数量
     */
    abstract int insertCodes(List<Long> codes);
}
