package com.joycity.joyclub.apifront.service.impl;

import com.joycity.joyclub.apifront.mapper.manual.ClientLoginLogMapper;
import com.joycity.joyclub.apifront.mapper.manual.ProjectMapper;
import com.joycity.joyclub.apifront.modal.login.LoginMethodParam;
import com.joycity.joyclub.apifront.modal.project.SysProject;
import com.joycity.joyclub.apifront.service.*;
import com.joycity.joyclub.client.mapper.ClientUserMapper;
import com.joycity.joyclub.client.modal.Client;
import com.joycity.joyclub.commons.constant.ResultCode;
import com.joycity.joyclub.mallcoo.modal.result.data.UserAdvancedInfo;
import com.joycity.joyclub.mallcoo.service.MallCooService;
import com.joycity.joyclub.we_chat.modal.WechatUserInfo;
import com.joycity.joyclub.client.service.KeChuanCrmService;
import com.joycity.joyclub.client_token.service.ClientTokenService;
import com.joycity.joyclub.commons.constant.Global;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.we_chat.service.WechatService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static com.joycity.joyclub.commons.constant.Global.PLATFORM_ID;
import static com.joycity.joyclub.commons.constant.ProjectVipCard.VIP_CARD_DIGITAL;
import static com.joycity.joyclub.commons.constant.ResultCode.DATA_NOT_EXIST;

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
    ProjectMapper projectMapper;
    @Autowired
    ClientUserMapper clientMapper;
    @Autowired
    VipCardNumFrontService vipCardNumService;
    @Autowired
    ClientLoginLogMapper clientLoginLogMapper;
    @Autowired
    ClientTokenService clientTokenService;
    @Autowired
    private MallCooService mallCooService;

    private Log logger = LogFactory.getLog(LoginFrontServiceImpl.class);

    /**
     * @param openId
     * @param projectId
     * @param phone
     * @param authCode
     * @return
     */
    @Override
    public ResultData wechatLogin(HttpServletResponse response, Long projectId, String phone, String authCode, String openId, String accessToken) {
        LoginMethodParam param = LoginMethodParam
                .LoginMethodParamBuilder
                .create()
                .setCardProjectId(projectId)
                .setPhone(phone)
                .setAuthCode(authCode)
                .setWechat(projectId, openId, accessToken)
                .build();
        Long userId = clientLogin(param, response);
        clientLoginLogMapper.addLog(userId, projectId, null);
        return new ResultData();

    }

    @Override
    public ResultData wapLogin(HttpServletResponse response, Long projectId, String phone, String authCode) {
        LoginMethodParam param = LoginMethodParam
                .LoginMethodParamBuilder
                .create()
                .setCardProjectId(projectId)
                .setPhone(phone)
                .setAuthCode(authCode)
                .build();
        Long userId = clientLogin(param, response);
        clientLoginLogMapper.addLog(userId, projectId, null);
        return new ResultData();

    }
    @Override
    public ResultData wapAutoLogin(HttpServletResponse response, Long projectId, String phone, String from) {
        LoginMethodParam param = LoginMethodParam
                .LoginMethodParamBuilder
                .create()
                .setCardProjectId(projectId)
                .setPhone(phone).build();
        Long userId = clientLogin(param, response);
        clientLoginLogMapper.addLog(userId, projectId, from);
        return new ResultData();
    }

    @Override
    public ResultData wechatAutoLogin(HttpServletResponse response, Long projectId, String phone, String openId, String accessToken, String from) {
        LoginMethodParam param = LoginMethodParam
                .LoginMethodParamBuilder
                .create()
                .setCardProjectId(projectId)
                .setPhone(phone)
                .setWechat(projectId, openId, accessToken)
                .build();
        Long userId = clientLogin(param, response);
        clientLoginLogMapper.addLog(userId, projectId, from);
        return new ResultData();
    }

    @Override
    public ResultData subProjectWapAutoLogin(HttpServletResponse response, Long subProjectId, String phone, String from) {
        LoginMethodParam param = LoginMethodParam
                .LoginMethodParamBuilder
                .create()
                .setCardProjectId(subProjectId)
                .setPhone(phone).build();
        Long userId = clientLogin(param, response);
        clientLoginLogMapper.addLogWithSubProject(userId, PLATFORM_ID, subProjectId, from);
        return new ResultData();
    }

    @Override
    public ResultData subProjectWechatAutoLogin(HttpServletResponse response, Long subProjectId, String phone, String openId, String accessToken, String from) {
        LoginMethodParam param = LoginMethodParam
                .LoginMethodParamBuilder
                .create()
                .setCardProjectId(subProjectId)
                .setPhone(phone)
                .setWechat(PLATFORM_ID, openId, accessToken)
                .build();
        Long userId = clientLogin(param, response);
        clientLoginLogMapper.addLogWithSubProject(userId, PLATFORM_ID, subProjectId, from);
        return new ResultData();
    }

    @Override
    public ResultData logout(String token) {
        clientTokenService.removeToken(token);
        return new ResultData();
    }



    private void updateClientFromKeChuan(Client user, Client kechuanClient) {
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


    private void updateClientWechatInfo(Client client, WechatUserInfo info) {
        client.setWxCity(getStrBeforeCheck(info.getCity()));
        client.setWxCountry(getStrBeforeCheck(info.getCountry()));
        client.setWxGender(getStrBeforeCheck(info.getSex()));
        client.setWxHeadImgUrl(getStrBeforeCheck(info.getHeadimgurl()));
        client.setWxLanguage(getStrBeforeCheck(info.getLanguage()));
        client.setWxNickName(getStrBeforeCheck(info.getNickname()));
        client.setWxProvince(getStrBeforeCheck(info.getProvince()));
    }

    @Override
    public Client mallcooSysn(Long projectId,String ticket) {
        UserAdvancedInfo info = mallCooService.getUserAdvancedInfoByTicket(projectId, ticket);
        logger.info("UserAdvancedInfo = "+info);
        String vipCode = info.getThirdPartyCardID();
        if (vipCode == null) {
            throw new BusinessException(ResultCode.REQUEST_PARAMS_ERROR, "获取猫酷会员信息失败！");
        }
        Long clientId = clientMapper.getIdByVipCode(vipCode);
        logger.info("clientId-1 = "+clientId);
        if (clientId == null) {
            LoginMethodParam param = LoginMethodParam
                    .LoginMethodParamBuilder
                    .create()
                    .setCardProjectId(projectId)
                    .setPhone(info.getMobile()).build();
            clientId = clientLogin(param, null);
        }
        logger.info("clientId-2 = "+clientId);
        return clientMapper.selectByPrimaryKey(clientId);
    }
// TODO: 2017/5/11 参数对象化
    /**
     * 用户登陆逻辑
     * 商场项目使用平台的悦客会，登录时发自己项目的卡，但是记录平台的openId
     * 正常的项目走自己的悦客会，cardProjectId和openIdProjectId相等
     * <p>
     * cardProjectId   是发卡的项目id,商场项目使用自己的projectId
     * openIdProjectId 微信对应的项目id，商场项目的悦客会使用平台的，所以微信项目id使用平台id
     * phone
     * authCode        手机验证码 可以为null，如果提供，则在登录逻辑前先进行验证
     * openId          可以为null，如果null,则不进行微信相关业务
     *
     * @return Client 只包含 id tel
     */
    @Override
    public Long clientLogin(LoginMethodParam params, HttpServletResponse response) {
        logger.info("into clientLogin--------------------");
        SysProject project = projectMapper.selectByPrimaryKey(params.getCardProjectId());
        if (project == null) {
            throw new BusinessException(DATA_NOT_EXIST, "请提供正确的项目编号");
        }
        //验证验证码正确性，不正确时authCodeService自动抛出异常
        if (params.getAuthCode() != null) {
            authCodeService.checkAuthCode(params.getPhone(), params.getAuthCode());
        }
        //先从科传处查看用户注册情况
        Client kechuanClient = keChuanCrmService.getMemberByTel(params.getPhone());
        logger.info("科传会员数据："+kechuanClient);
        //如果科传处没有该号码用户，立刻新建
        if (kechuanClient == null) {
            // TODO: 2017/4/18 这里是先将卡设为使用，如果创建会员失败，再设置为不可用 。而不是先找到可用的号码，创建成功后再设置为已用。
            //主要考虑是创建会员操作耗时，可能出现并发，导致一号多用。
            //如果后续进行锁记录，则这个蠢方法可以废弃。
            Long cardNumAvailable = vipCardNumService.useDigitalVipCard(params.getCardProjectId());

            kechuanClient = new Client();
            kechuanClient.setCardNo(cardNumAvailable + "");
            kechuanClient.setTel(params.getPhone());
            kechuanClient.setGroup13(project.getVipShare());
            kechuanClient.setVipCardGrade(VIP_CARD_DIGITAL);
            //发卡项目
            kechuanClient.setCreditCardProject(project.getCardResourceProject());
            //在科传处创建会员
            String vipCode = null;
            try {
                vipCode = keChuanCrmService.createMember(kechuanClient);
            } catch (BusinessException e) {
                //如果创建失败，则捕获异常，恢复卡号为未使用
                vipCardNumService.cancelUseVipCard(cardNumAvailable);
                //一定要抛出异常，终止后续操作
                throw e;
            }
            logger.info("使用电子卡" + cardNumAvailable);
            kechuanClient.setVipCode(vipCode);
        }

        Client user = new Client();
        //同步科传会员信息
        updateClientFromKeChuan(user, kechuanClient);
        if (params.getOpenIdProjectId() != null) {
            WechatUserInfo wechatInfo = wechatService.getUserInfo(params.getOpenId(), params.getAccessToken());

            //同步会员微信信息
            updateClientWechatInfo(user, wechatInfo);
        }
        //查看本地有没有该号码用户
        Long userId = clientMapper.getIdByTel(params.getPhone());
        user.setTel(params.getPhone());
        //必须是insertSelective 和updateSelective 防止被null覆盖
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
        if (params.getOpenIdProjectId() != null) {
            wechatService.saveOrUpdateProjectOpenId(params.getOpenIdProjectId(), user.getId(), params.getOpenId());
        }
        if (response == null) {
            addTokenCookie(response, user.getId());
        }
        logger.info("同步科传后的user = "+user);
        return user.getId();
    }

    private void addTokenCookie(HttpServletResponse response, Long clientId) {
        Cookie cookie = new Cookie(Global.COOKIE_TOKEN, clientTokenService.setToken(clientId));
        cookie.setMaxAge(3600 * 24 * 30);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 返回有值的str
     * 等下在插入的时候使用insertSelective
     *
     * @param s
     * @return
     */
    private String getStrBeforeCheck(String s) {
        return StringUtils.isEmpty(s) ? null : s;
    }


}
