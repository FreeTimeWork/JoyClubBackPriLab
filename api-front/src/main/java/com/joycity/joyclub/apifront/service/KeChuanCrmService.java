package com.joycity.joyclub.apifront.service;



import com.joycity.joyclub.apifront.modal.client.Client;

import java.util.List;
import java.util.Map;

public interface KeChuanCrmService {



    public void getBaseInfo();

    /**
     * 创建电子卡会员
     * @param cardNo 卡面编号
     * @param tel	手机号
     * @param group13	crm的分摊积分标记(参见门店表)
     * @param vipgrade	会员等级（对应数据字典vipCardType）
     * @param creditCardProject	发卡项目
     * @return vipcode crm对会员的唯一标识
     */
    public String createMember(String cardNo, String tel, String group13, String vipgrade, String creditCardProject);

    /**
     * 更新会员
     * @param user
     */
    public void updateMember(Client user);

    /**
     * 根据手机号获取会员
     * 会员积分为：user.getVirPrice()
     * @return
     */
    public Client getMemberByTel(String tel);

    /**
     * 更新会员卡号
     * @param vipCode
     * @param newCardNo
     * @return
     */
    public void updateCardNo(String vipCode, String newCardNo);

    /**
     * 更新会员等级
     * @param vipCode
     * @param level
     * @return
     */
    public void updateLevel(String vipCode, String level);

    /**
     * 增减积分
     * @param vipCardNum
     * @param score
     */
    public Integer changeScore(String vipCardNum, Double score);

    /**
     * 查询积分流水
     * @param vipCode
     * @return
     */
    public List<Map<String, Object>> getSocreRecord(String vipCode);

}
