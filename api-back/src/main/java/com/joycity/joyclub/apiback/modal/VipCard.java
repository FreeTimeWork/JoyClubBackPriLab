package com.joycity.joyclub.apiback.modal;

/**
 * Created by amosc on 2017/2/20.
 */
public class VipCard {
    private Long id;
    /**
     * 卡类型
     */
    private String cardType;
    /**
     * 门店
     */
    private String storeName;
    /**
     * 批次号
     */
    private String batch;
    /**
     * 卡状态
     */
    private String cardStatus;
    /**
     * 会员号码
     */
    private String cardNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

}
