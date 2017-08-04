package com.joycity.joyclub;

import com.joycity.joyclub.system.App;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by CallMeXYZ on 2017/6/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ActiveProfiles(value = "development")
public class TestApp {

}