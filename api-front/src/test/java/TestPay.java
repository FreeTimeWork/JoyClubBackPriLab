import com.alibaba.fastjson.JSON;
import com.joycity.joyclub.apifront.exception.BusinessException;
import com.joycity.joyclub.apifront.modal.wechat.WechatUserInfo;
import com.joycity.joyclub.apifront.util.WechatXmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

import static com.joycity.joyclub.apifront.constant.ResultCode.WECHAT_ERROR;

/**
 * Created by CallMeXYZ on 2017/4/1.
 */

public class TestPay {
    private RestTemplate restTemplate = new RestTemplate();
    private final String URL_OPENID = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    private final String URL_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    @Test
    public void testWexinOpenId() {
       RestTemplate rest = new RestTemplate();
        WechatUserInfo info = rest.getForObject("https://api.weixin.qq.com/cgi-bin/user/info?access_token=xNo9Dw3tYUtZKXmWD-97Saz9k9OE8Kv2fNyJIXfkMWjGukFiRre_SipFjP-vAjUL5Pd7MKVHlluAiBw1dBN_rPBthcKR3DwDj-cKKXQo8QU8F65lz7mZVEhrGTwPSN3MWHTeAHAQBN&openid=oBDYawi1UjKoNXeDDeunHbq123Un3As&lang=zh_CN",WechatUserInfo.class);
        System.out.println(JSON.toJSONString(info));
        
    }

}
