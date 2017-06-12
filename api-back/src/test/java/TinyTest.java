import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.text.DecimalFormat;

/**
 * Created by CallMeXYZ on 2017/3/27.
 */
public class TinyTest {
    @Test
    public void test() {
        System.out.println("%.2f");

        System.out.println(getTwoStr(0.001f));
        System.out.println(getTwoStr(0.01f));
        System.out.println(getTwoStr(1.01f));
        System.out.println("decimal format");
        System.out.println(getTwoStr2(123123.001f));
        System.out.println(getTwoStr2(123123.01f));
        System.out.println(getTwoStr2(123123123.01f));


    }

    public String getTwoStr(Float a) {
        return String.format("%.2f", Math.ceil(a * 100) / 100);
    }

    public String getTwoStr2(Float a) {
        return new DecimalFormat("#.00").format(a);
    }

    @Test
    public void testSbuStr() {
        String s = "1";
        System.out.println(s.substring(0, 2));

    }




}
