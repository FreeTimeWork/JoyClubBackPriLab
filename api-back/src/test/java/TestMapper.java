import com.alibaba.fastjson.JSON;
import com.joycity.joyclub.apiback.mapper.account.ManagerMapper;
import config.MybatisConfig;
import config.RootConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by CallMeXYZ on 2017/2/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MybatisConfig.class, RootConfig.class})
public class TestMapper {
    @Autowired
    ManagerMapper managerMapper;
    @Test
    public void testManagerMapper() {
        long testNum = managerMapper.getUserNumByAccount("test");
        long TestNum = managerMapper.getUserNumByAccount("Test");
        Assert.assertEquals(testNum,1);
        Assert.assertEquals(TestNum,0);
    }
    @Test
    public void testSessionName() {


    }
}
