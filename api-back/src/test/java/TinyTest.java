import com.joycity.joyclub.apiback.util.MD5Util;
import org.junit.Test;

/**
 * Created by CallMeXYZ on 2017/3/27.
 */
public class TinyTest {
    @Test
    public void testMD5(){
        System.out.println(MD5Util.MD5(MD5Util.MD5("888888"),"joyclub$%^@"));
        
    }
}
