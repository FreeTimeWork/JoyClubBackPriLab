import com.alibaba.fastjson.JSON;
import com.joycity.joyclub.api.mapper.account.ManagerMapper;
import config.MybatisConfig;
import config.RootConfig;
import config.ServletConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TestTransaction;

import javax.servlet.Servlet;

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
        System.out.println(JSON.toJSON(managerMapper.getList(null, null)));
        System.out.println(JSON.toJSON(managerMapper.getList(1, null)));
        long testNum = managerMapper.getUserNumByAccount("test");
        long TestNum = managerMapper.getUserNumByAccount("Test");
        Assert.assertEquals(testNum,1);
        Assert.assertEquals(TestNum,0);
    }
}
