import com.joycity.joyclub.apifront.util.WechatXmlUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * Created by CallMeXYZ on 2017/4/1.
 */

public class TestPay {

    @Test
    public void testXmlToString() {
        String xmlStr = "<xml><nonce_str><![CDATA[IITRi8Iabbblz1Jc]]></nonce_str><openid><![CDATA[oUpF8uMuAJO_M2pxb1Q9zNjWeS6o]]></openid><appid><![CDATA[wx2421b1c4370ec43b]]></appid><sign><![CDATA[7921E432F65EB8ED0CE9755F0E86D72F]]></sign><trade_type><![CDATA[JSAPI]]></trade_type><return_msg><![CDATA[OK]]></return_msg><result_code><![CDATA[SUCCESS]]></result_code><mch_id><![CDATA[10000100]]></mch_id><return_code><![CDATA[SUCCESS]]></return_code><prepay_id><![CDATA[wx201411101639507cbf6ffd8b0779950874]]></prepay_id></xml>";
        Map a = WechatXmlUtil.xmlToMap(xmlStr);
        assert WechatXmlUtil.mapToXml(a).equals(xmlStr);

    }


}
