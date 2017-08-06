package com.joycity.joyclub.apifront.service.impl;

import com.alibaba.fastjson.JSON;
import com.joycity.joyclub.apifront.modal.point.qrcode.PointHintFromPosResult;
import com.joycity.joyclub.apifront.service.PointQRCodeService;
import com.joycity.joyclub.client.mapper.ClientUserMapper;
import com.joycity.joyclub.commons.exception.BusinessException;
import com.joycity.joyclub.commons.modal.base.ResultData;
import com.joycity.joyclub.commons.utils.EncryptionUtil;
import com.joycity.joyclub.commons.utils.ThrowBusinessExceptionUtil;
import com.joycity.joyclub.mallcoo.modal.result.data.UserAdvancedInfo;
import com.joycity.joyclub.mallcoo.service.MallCooService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.joycity.joyclub.commons.constant.ResultCode.USE_OTHER_API_ERROR;

/**
 * Created by Administrator on 2017/8/6.
 */
@Service
public class PointQRCodeServiceImpl implements PointQRCodeService {
    private final Log logger = LogFactory.getLog(PointQRCodeServiceImpl.class);
    @Value("${point.qrcode.key}")
    private String QR_CODE_KEY;
    @Value("${point.qrcode.pos.api.url}")
    private String POST_API_URL;
    @Value("${point.qrcode.pos.api.data}")
    private String POST_API_DATA;
    @Autowired
    MallCooService mallcooService;
    @Autowired
    ClientUserMapper clientUserMapper;
    @Autowired
    RestTemplate restTemplate;
    private final SimpleDateFormat dateFormatForPos = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public ResultData getQRCode(Long clientId) {
        String vipCode = clientUserMapper.getVipCodeById(clientId);
        ThrowBusinessExceptionUtil.checkNull(vipCode, "会员不存在");
        return getQRCodeData(vipCode);
    }

    @Override
    public ResultData getQRCodeForMallcoo(Long projectId,String ticket) {
       UserAdvancedInfo info =  mallcooService.getUserAdvancedInfo(projectId,ticket);
        return getQRCodeData(info.getThirdPartyCardID());
    }

    private ResultData getQRCodeData(String vipCode) {
        String qrCode = EncryptionUtil.aesEncrypt(vipCode + System.currentTimeMillis(), QR_CODE_KEY);
        String hint = getPointHintFromPosOrThrow(vipCode);
        Map<String, String> result = new HashMap<>();
        result.put("qrCode", qrCode);
        result.put("hint", hint);
        return new ResultData(result);
    }

    private String getPointHintFromPosOrThrow(String vipCode) {
        String apiData = POST_API_DATA.replace("VIPCODE", vipCode).replace("DATE", dateFormatForPos.format(new Date()));
//        URI uri = URI.create(POST_API_URL).getQuery();
        PointHintFromPosResult result = restTemplate.getForObject(POST_API_URL, PointHintFromPosResult.class, apiData);
        if (result == null || result.getCode() != 0 || result.getData() == null || result.getData().getDataList() == null || result.getData().getDataList().getScogiftinfo() == null) {
            logger.error("请求POS的积分抵现接口失败，返回结果为 " + JSON.toJSONString(result));
            throw new BusinessException(USE_OTHER_API_ERROR, "POS接口调用失败");
        }
        return result.getData().getDataList().getScogiftinfo();

    }
}
