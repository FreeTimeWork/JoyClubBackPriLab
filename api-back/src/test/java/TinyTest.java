import com.alibaba.fastjson.JSON;
import com.joycity.joyclub.commons.utils.MD5Util;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by CallMeXYZ on 2017/3/27.
 */
public class TinyTest {
   @Test
    public void testSql(){
       StringBuilder stringBuilder = new StringBuilder();
       for(int i=0;i<100000;i++)
       {
           stringBuilder.append("(1,1)");
           if(i!=100000-1)
           {
               stringBuilder.append(",");
           }
       }
       System.out.println(stringBuilder.toString());

   }

}
