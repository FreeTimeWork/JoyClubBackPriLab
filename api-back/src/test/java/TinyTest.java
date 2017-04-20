import org.junit.Test;

/**
 * Created by CallMeXYZ on 2017/3/27.
 */
public class TinyTest {
@Test
public void test(){
    Integer a = 2;
    System.out.println(a);
    testInt(a);
 System.out.println(a);   
}
    public void testInt(Integer a){
        a++;
    }
}
