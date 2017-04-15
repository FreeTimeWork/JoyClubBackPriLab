package com.joycity.joyclub.apifront.service.impl;

import com.joycity.joyclub.apifront.exception.BusinessException;
import com.joycity.joyclub.apifront.mapper.manual.ClientUserMapper;
import com.joycity.joyclub.apifront.mapper.manual.OpenIdMapper;
import com.joycity.joyclub.apifront.mapper.manual.ProjectMapper;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.apifront.modal.client.Client;
import com.joycity.joyclub.apifront.modal.project.SysProject;
import com.joycity.joyclub.apifront.modal.wechat.WechatUserInfo;
import com.joycity.joyclub.apifront.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.joycity.joyclub.apifront.constant.ResultCode.DATA_NOT_EXIST;
import static com.joycity.joyclub.commons.constants.ProjectVipCard.VIP_CARD_DIGITAL;

/**
 * Created by CallMeXYZ on 2017/4/10.
 */
@Service
public class LoginFrontServiceImpl implements LoginFrontService {
    @Autowired
    WechatService wechatService;
    @Autowired
    MsgAuthCodeService authCodeService;
    @Autowired
    KeChuanCrmService keChuanCrmService;
    @Autowired
    OpenIdService openIdService;
    @Autowired
    ProjectMapper projectMapper;
    @Autowired
    ClientUserMapper clientMapper;
    @Autowired
    OpenIdMapper openIdMapper;
    @Autowired
    VipCardNumFrontService vipCardNumService;

    @Override
    public ResultData wechatLogin(String openId, Long projectId, String phone, String authCode) {

        SysProject project = projectMapper.selectByPrimaryKey(projectId);
        if (project == null) {
            throw new BusinessException(DATA_NOT_EXIST, "请提供正确的项目编号");
        }
        //验证验证码正确性，不正确时authCodeService自动抛出异常
        authCodeService.checkAuthCode(phone, authCode);
        //先从科传处查看用户注册情况
        Client kechuanClient = keChuanCrmService.getMemberByTel(phone);
        //如果科传处没有该号码用户，立刻新建
        if (kechuanClient == null) {
            Long cardNumAvailable = vipCardNumService.useDigitalVipCard(projectId);

            kechuanClient = new Client();
            kechuanClient.setCardNo(cardNumAvailable+"");
            kechuanClient.setTel(phone);
            kechuanClient.setGroup13(project.getVipShare());
            kechuanClient.setVipCardGrade(VIP_CARD_DIGITAL);
            //发卡项目
            kechuanClient.setCreditCardProject(project.getCardResourceProject());
            //在科传处创建会员
            String vipCode = keChuanCrmService.createMember(kechuanClient);
            kechuanClient.setVipCode(vipCode);
        }

        Client user = new Client();
        //同步科传会员信息
        updateClientFromKeChuan(user,kechuanClient);
        WechatUserInfo wechatInfo = wechatService.getUserInfo(openId, project.getWechatTokenAddress());

        //同步会员微信信息
        updateClientWechatInfo(user,wechatInfo);
        //查看本地有没有该号码用户
        Long userId = clientMapper.getIdByTel(phone);
        user.setTel(phone);
        if (userId == null) {
            //插入成功后，user已经有id
            clientMapper.insertSelective(user);
        } else {
            user.setId(userId);
            clientMapper.updateByPrimaryKeySelective(user);
        }
        //这一步不放在userId==null里，因为后续的话有多种登陆，同步和创建将会拆成新方法，
        //可能用户在其他登陆中先创建了会员，然后再进行微信登陆
        //所以判断openid有没有存储，必须一直判断，而不是只放在userId==null时
        if (!openIdService.checkOpenIdExist(openId)) {
            openIdMapper.saveOpenId(projectId, user.getId(), openId);
        }

        return new ResultData(user);

    }
    private void updateClientFromKeChuan (Client user,Client kechuanClient) {
        user.setVipPoint(kechuanClient.getVipPoint());//积分
        user.setCardNo(kechuanClient.getCardNo());//卡面编号
        user.setVipCode(kechuanClient.getVipCode());//
        user.setSex(getStrBeforeCheck(kechuanClient.getSex()));//性别
        user.setVipGrade(kechuanClient.getVipGrade());//会员等级
        user.setVipCardGrade(kechuanClient.getVipCardGrade());//会员等级
        user.setHomeAddress(getStrBeforeCheck(kechuanClient.getHomeAddress()));//家庭住址
        user.setBirthday(kechuanClient.getBirthday());//生日
        user.setIdCard(getStrBeforeCheck(kechuanClient.getIdCard()));//身份证号
        user.setRealName(getStrBeforeCheck(kechuanClient.getRealName()));//真实姓名
        user.setGroup13(kechuanClient.getGroup13());
        user.setCreditCardProject(kechuanClient.getCreditCardProject());
    }
    private void updateClientWechatInfo(Client client,WechatUserInfo info){
        client.setWxCity(getStrBeforeCheck(info.getCity()));
        client.setWxCountry(getStrBeforeCheck(info.getCountry()));
        client.setWxGender(getStrBeforeCheck(info.getSex()));
        client.setWxHeadImgUrl(getStrBeforeCheck(info.getHeadimgurl()));
        client.setWxLanguage(getStrBeforeCheck(info.getLanguage()));
        client.setWxNickName(getStrBeforeCheck(info.getNickname()));
        client.setWxProvince(getStrBeforeCheck(info.getProvince()));
    }

    /**
     * 返回有值的str
     * @param s
     * @return
     */
    private String getStrBeforeCheck(String s){
       return StringUtils.isEmpty(s)?null:s;
    }
}
