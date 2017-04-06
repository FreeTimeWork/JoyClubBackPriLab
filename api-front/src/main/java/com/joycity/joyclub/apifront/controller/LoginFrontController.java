package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.modal.MsgAuthCode;
import com.joycity.joyclub.apifront.modal.client.Client;
import com.joycity.joyclub.apifront.service.KeChuanCrmService;
import com.joycity.joyclub.apifront.service.MsgAuthCodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.joycity.joyclub.apifront.constant.Global.URL_API_FRONT;

/**
 * Created by CallMeXYZ on 2017/4/6.
 */
@RestController
@RequestMapping(URL_API_FRONT)
public class LoginFrontController {

/*
    @Autowired
    KeChuanCrmService keChuanCrmService;
    @RequestMapping(value = "/login", method = { RequestMethod.POST})
    public ResponseInfo login(@RequestParam final String tel,
                              @RequestParam final String authCode,
                              @RequestParam final String openid,
                              @RequestParam final String payOpenId,
                              @RequestParam final String storeCode,
                              final HttpServletRequest request) {
        return new ResponseCallBack() {
            @Override
            public void execute(ResponseCriteria criteria, Object... obj) {
                Store store = storeService.getStoretByNum(storeCode);
                if(store == null) {
                    throw new BusinessException(-1, "门店不存在：" + storeCode);
                }

                WeChat wechat = weChatService.getWeiXin(openid, store.getWxTokenUrl());

                if(authCodeService.checkAuthCode(tel, authCode)) {
                    Client temp = keChuanCrmService.getMemberByTel(tel);
                    if(temp == null) {
                        VipCardNum cardNum = vipCardNumService.assignCardNum("74", store.getNum());//74是电子卡的代码
                        temp = new Client();
                        temp.setCardNo(cardNum.getCardNum());
                        temp.setTel(tel);
                        temp.setGroup13(store.getVipShare());
                        temp.setVipCardGrade(cardNum.getType());
                        temp.setCreditCardProject(store.getUseProject());
                        String vipCode = keChuanCrmService.createMember(temp.getCardNo(), tel, temp.getGroup13(), temp.getVipCardGrade(), temp.getCreditCardProject());
                        temp.setVipCode(vipCode);
                    }

                    Client user = userService.getUserByTel(tel);
                    if(user == null) {
                        user = new Client();
                        user.setTel(tel);
                    }
                    //同步科传会员信息
                    user.setVipPoint(temp.getVirPrice());//积分
                    user.setCardNo(temp.getCardNo());//卡面编号
                    user.setVipCode(temp.getVipCode());//
                    user.setSex(temp.getSex());//性别
                    user.setVipGrade(temp.getVipGrade());//会员等级
                    user.setHomeAddress(temp.getHomeAddress());//家庭住址
                    user.setBirthday(temp.getBirthday());//生日
                    user.setIdCard(temp.getIdCard());//身份证号
                    user.setRealName(temp.getRealName());//真实姓名
                    user.setGroup13(temp.getGroup13());
                    user.setCreditCardProject(temp.getCreditCardProject());

                    //同步会员微信信息
                    user.setPayOpenId(payOpenId);
                    user.setWxCity(wechat.getCity());
                    user.setWxCountry(wechat.getCountry());
                    user.setWxGender(wechat.getSex());
                    user.setWxHeadImgUrl(wechat.getHeadimgurl());
                    user.setWxLanguage(wechat.getLanguage());
                    user.setWxNickName(wechat.getNickname());
                    user.setWxProvince(wechat.getProvince());

                    user = userService.merge(user);

                    Wexin wexin = wexinService.getById(wechat.getOpenid());
                    if(wexin == null) {
                        wexin = new Wexin();
                        wexin.setWxOpenId(wechat.getOpenid());
                        wexin.setUser(user);
                        wexin.setStore(store);
                        wexinService.merge(wexin);
                    }

                    user.setFranchisee(null);
                    user.setVipLevel(null);
                    user.setMerchant(null);
                    user.setStore(null);
                    request.getSession().setAttribute("user", user);
                    criteria.addMapResult("user", user).addMapResult("permissions", null);
                }
            }
        }.sendRequest();
    }


    @RequestMapping(value = "/login/refresh", method = { RequestMethod.POST})
    public ResponseInfo autoLogin(@RequestParam final String openid, final HttpServletRequest request) {
        return new ResponseCallBack() {
            @Override
            public void execute(ResponseCriteria criteria, Object... obj) {
                Wexin wexin = wexinService.getById(openid);
                if(wexin == null) {
                    throw new BusinessException(201, "需要使用手机号加验证码登录");
                }
                User user = userService.getUserById(wexin.getUser().getId());
                if(StringUtils.isBlank(user.getPayOpenId())) {
                    throw new BusinessException(201, "需要使用手机号加验证码登录");
                }
                if(StringUtils.isBlank(user.getTel())) {
                    throw new BusinessException(201, "需要使用手机号加验证码登录");
                }
                if(user.getStore() == null) {
                    throw new BusinessException(201, "需要使用手机号加验证码登录");
                }
                User temp = crmTechService.getMemberByTel(user.getTel());
                if(temp == null) {
                    throw new BusinessException(201, "需要使用手机号加验证码登录");
                }
                //同步科传会员信息
                user.setVirPrice(temp.getVirPrice());//积分
                user.setCardNo(temp.getCardNo());//卡面编号
                user.setVipCode(temp.getVipCode());//
                user.setSex(temp.getSex());//性别
                user.setVipGrade(temp.getVipGrade());//会员等级
                user.setHomeAddress(temp.getHomeAddress());//家庭住址
                user.setBirthday(temp.getBirthday());//生日
                user.setIdCard(temp.getIdCard());//身份证号
                user.setRealName(temp.getRealName());//真实姓名
                user.setGroup13(temp.getGroup13());
                user.setCreditCardProject(temp.getCreditCardProject());
                user = userService.merge(user);

                user.setFranchisee(null);
                user.setVipLevel(null);
                user.setMerchant(null);
                user.setStore(null);
                request.getSession().setAttribute("user", user);
                user.setVipLevel(null);
                criteria.addMapResult("user", user).addMapResult("permissions", null);
            }
        }.sendRequest();
    }
*/
}
