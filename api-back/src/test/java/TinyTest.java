import com.joycity.joyclub.apiback.util.MD5Util;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by CallMeXYZ on 2017/3/27.
 */
public class TinyTest {
    @Test
    public void testMD5() {
        System.out.println(MD5Util.MD5("1"));
        System.out.println(MD5Util.MD5(MD5Util.MD5("1"), "joyclub$%^@"));
        System.out.println(MD5Util.MD5("21218cca77804d2ba1922c33e0151105", "joyclub$%^@"));
        assert MD5Util.MD5("21218cca77804d2ba1922c33e0151105", "joyclub$%^@").equals("6d78570770f5cf00800e36151fb561b4");
    }

    @Test
    public void testNio() throws IOException {
        Path path = Paths.get("/joyclub/image/test.image");
        if (!Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
    }

    @Test
    public void testGetType() {
        System.out.println(getType("test.img"));

    }

    private String getType(String s) {
        int index = s.lastIndexOf(".");
        String type = s.substring(index + 1, s.length());
        return type;
    }

    @Test
    public void testIntegerEqual() {
        Integer int1 = 1;
        Integer int2 = new Integer(1);
        assert int1.equals(int2);
        assert int1.equals(1);

    }

    @Test
    public void testTertary() {
        System.out.println(true ? 1 : 2 + '3');

    }

    @Test
    public void testArrayArgs() {
        assert argsStr("1", "2", "3").equals("123");
    }

    private String argsStr(String... test) {
        StringBuilder sb = new StringBuilder();
        for (String s : test) {
            sb.append(s);
        }
        return sb.toString();
    }
}
