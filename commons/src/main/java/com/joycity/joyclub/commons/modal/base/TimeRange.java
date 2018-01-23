package com.joycity.joyclub.commons.modal.base;


import com.joycity.joyclub.commons.utils.DateTimeUtil;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @auther fangchen.chai ON 2017/11/3
 */
public class TimeRange {
    private Date from;
    private Date to;

    public TimeRange() {
    }

    public TimeRange(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    /**
     * @param beginDate 开始日期，系统只解析日期
     * @param endDate   结束日期，系统只解析日期，且会增加1天
     * @return
     * @throws ParseException
     * @Description: 创建时间段对象
     */
    public static TimeRange toTimeRange(String beginDate, String endDate) throws ParseException {
        if (!StringUtils.isEmpty(beginDate) && !StringUtils.isEmpty(endDate)) {
            TimeRange range = new TimeRange();
            range.setFrom(DateTimeUtil.parseYYYYMMDD(beginDate));
            range.setTo(new Date(DateTimeUtil.parseYYYYMMDD(endDate).getTime() + TimeUnit.DAYS.toMillis(1)));
            return range;
        }
        return null;
    }
}
