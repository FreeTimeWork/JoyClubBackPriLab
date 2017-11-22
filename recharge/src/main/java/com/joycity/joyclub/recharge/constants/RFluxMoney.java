package com.joycity.joyclub.recharge.constants;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 流量对应的钱
 */
public enum RFluxMoney {
    F_100m(1,100,new BigDecimal(10)),
    F_200m(1,200,new BigDecimal(15)),
    F_300m(1,300,new BigDecimal(20)),
    F_500m(1,500,new BigDecimal(30)),
    F_1G(1,1024,new BigDecimal(50)),

    ;

    private Integer id;
    private Integer flux;
    private BigDecimal money;

    public static Map<Integer, RFluxMoney> map;
    static {
        map.put(F_100m.getFlux(), F_100m);
        map.put(F_200m.getFlux(), F_200m);
        map.put(F_300m.getFlux(), F_300m);
        map.put(F_500m.getFlux(), F_500m);
        map.put(F_1G.getFlux(), F_1G);
    }
    RFluxMoney() {
        
    }

    public static RFluxMoney getMoneyByFlux(Integer flux) {
        return map.get(flux);
    }

    public static Map<Integer, RFluxMoney> getMap() {
        return map;
    }

    public static void setMap(Map<Integer, RFluxMoney> map) {
        RFluxMoney.map = map;
    }

    RFluxMoney(Integer id, Integer flux, BigDecimal money) {
        this.id = id;
        this.flux = flux;
        this.money = money;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFlux() {
        return flux;
    }

    public void setFlux(Integer flux) {
        this.flux = flux;
    }

}
