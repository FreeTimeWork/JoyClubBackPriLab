package com.joycity.joyclub.client.service;



import com.joycity.joyclub.client.modal.Client;

import java.util.List;
import java.util.Map;

public interface KeChuanCrmService {


    void getBaseInfo();

    /**
     * 创建电子卡会员
     * @param cardNo 卡面编号
     * @param tel	手机号
     * @param group13	crm的分摊积分标记(参见门店表)
     * @param vipgrade	会员等级（对应数据字典vipCardType）
     * @param creditCardProject	发卡项目
     * @return vipcode crm对会员的唯一标识
     */
    String createMember(String cardNo, String tel, String group13, String vipgrade, String creditCardProject);

    String createMember(Client user);

    /**
     * 更新会员
     * @param user
     */
    void updateMember(Client user);

    /**
     * 根据手机号获取会员
     * @return
     */
    Client getMemberByTel(String tel);

    Client getMemberByVipCode(String code);

    /**
     * 更新会员卡号
     * @param vipCode
     * @param newCardNo
     * @return
     */
    void updateCardNo(String vipCode, String newCardNo);

    /**
     * 更新会员等级
     * @param vipCode
     * @param level
     * @return
     */
    void updateLevel(String vipCode, String level);

    /**
     * 增减积分
     * @param vipCode
     * @param changeValue
     */
    Integer changePoint(String vipCode, Double changeValue);

    /**
     * 查询积分流水
     * @param vipCode
     * @return
     */
    List<Map<String, Object>> getScoreRecord(String vipCode);

    /**
     * 查询消费流水
     * @param vipCode
     * @return
     */
    List<Map<String, Object>> getSaleRecord(String vipCode);

}
