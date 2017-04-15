package com.joycity.joyclub.apifront.modal.product;

/**
 * Created by CallMeXYZ on 2017/4/14.
 */
public class ProductWithPointRate extends Product{
    private Float pointRate;
    private Float basePointRate;

    public Float getPointRate() {
        return pointRate;
    }

    public void setPointRate(Float pointRate) {
        this.pointRate = pointRate;
    }

    public Float getBasePointRate() {
        return basePointRate;
    }

    public void setBasePointRate(Float basePointRate) {
        this.basePointRate = basePointRate;
    }
}
