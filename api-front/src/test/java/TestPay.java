import org.junit.Test;
import org.springframework.web.client.RestTemplate;

/**
 * Created by CallMeXYZ on 2017/4/1.
 */

public class TestPay {
    private RestTemplate restTemplate = new RestTemplate();
    private final String URL_OPENID = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    private final String URL_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    @Test
    public void test() {

       Integer a = 12324;
        assert  a ==12324;
    }


}
