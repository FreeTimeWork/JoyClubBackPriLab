package com.joycity.joyclub.commons.constant;

/**
 * Created by fangchen.chai on 2017/7/14.
 */
public enum QuartzPreKeyConst {
    BATCH_LAUNCH("batchLaunch");

    String name;

    QuartzPreKeyConst(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
