package com.joycity.joyclub.apifront.controller;

import com.joycity.joyclub.apifront.modal.vo.WechatNotifyVO;
import com.joycity.joyclub.client.mapper.ClientUserMapper;
import com.joycity.joyclub.client.modal.Client;
import com.joycity.joyclub.client_token.service.ClientTokenService;
import com.joycity.joyclub.commons.constant.Global;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.we_chat.modal.AccessTokenAndOpenId;
import com.joycity.joyclub.we_chat.modal.WechatUserInfo;
import com.joycity.joyclub.we_chat.service.WechatService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.joycity.joyclub.commons.constant.Global.PLATFORM_ID_REQUEST_PARAM;
import static com.joycity.joyclub.commons.constant.Global.URL_API_FRONT;

/**
 * Created by CallMeXYZ on 2017/4/14.
 */
@RestController
@RequestMapping(URL_API_FRONT)
public class WechatController {
    Log logger = LogFactory.getLog(WechatController.class);
    @Autowired
    WechatService wechatService;
    @Autowired
    ClientTokenService clientTokenService;
    @Autowired
    ClientUserMapper clientUserMapper;

 /*   //todo token写死了
    @RequestMapping("/wechat/userinfo/{openId}")
    public ResultData getUserInfoForProject(@PathVariable String openId) {
        return new ResultData(wechatService.getUserInfo(openId, "http://bjmall.stcl365.com:20000/wechat/public/getToken"));
    }*/

    /**
     * 用于在微信端登陆获取openId
     * 会返回openId accessToken等，accessToken在登陆时用于获取用户信息，accessToken2小时会过期，目前认为登录页不会待2小时
     *
     * @param code
     * @param projectId
     * @return
     */
    @RequestMapping("/wechat/openid")
    public ResultData getAuthCode(@RequestParam String code, @RequestParam(defaultValue = PLATFORM_ID_REQUEST_PARAM) Long projectId) {
        logger.info("/wechat/openid = " + code);
        return new ResultData(wechatService.getAccessTokenAndOpenId(code, projectId));
    }

    /**
     * 记录用户进入微信网页
     * 所有微信公众号的入口都应该使用微信跳转回调，然后调用这个接口记录微信信息
     */
    @PostMapping("/wechat/notify")
    public ResultData notifyWechat(@CookieValue(Global.COOKIE_TOKEN) String token, @Valid @RequestBody WechatNotifyVO vo) {
        logger.info("/wechat/notify = " + vo);
        Long clientId = clientTokenService.getIdOrThrow(token);
        AccessTokenAndOpenId accessTokenAndOpenId = wechatService.saveOrUpdateProjectOpenIdByCode(vo.getProjectId(), clientId, vo.getCode());
        WechatUserInfo userInfo = wechatService.getUserInfo(accessTokenAndOpenId.getOpenid(), accessTokenAndOpenId.getAccess_token());
        Client client = new Client();
        client.setId(clientId);
        client.setWxCity(userInfo.getCity());
        client.setWxCountry(userInfo.getCountry());
        client.setWxProvince(userInfo.getProvince());
        client.setWxGender(userInfo.getSex());
        client.setWxHeadImgUrl(userInfo.getHeadimgurl());
        client.setWxLanguage(userInfo.getLanguage());
        client.setWxNickName(userInfo.getNickname());
        // 注意selective
        clientUserMapper.updateByPrimaryKeySelective(client);
        logger.info("/wechat/notify end");
        return new ResultData();
    }



}
