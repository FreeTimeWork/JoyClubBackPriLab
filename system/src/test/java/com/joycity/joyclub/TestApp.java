package com.joycity.joyclub;

import com.alibaba.fastjson.JSON;
import com.joycity.joyclub.client.modal.Client;
import com.joycity.joyclub.client.service.KeChuanCrmService;
import com.joycity.joyclub.commons.constant.Global;
import com.joycity.joyclub.system.App;
import com.joycity.joyclub.test.mapper.TestMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by CallMeXYZ on 2017/6/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ActiveProfiles(Global.PROFILE_DEVELOPMENT)
public class TestApp {
    @Autowired
    TestMapper testMapper;
    @Autowired
    KeChuanCrmService keChuanCrmService;

    @Test
    public void test() {
        List<Client> list = new ArrayList<>();
        String prefix = "0500";
        int size = 400000;
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            String code = String.format("%06d", i);
            Client client = keChuanCrmService.getMemberByTel(prefix + code);
            if (client != null) {
                list.add(client);
            }
        }
        System.out.println("访问" + size + "次,获得" + list.size() + "条数据，耗时" + (System.currentTimeMillis() - start));
        String fileName = "VIPDATA_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".txt";
        Path savePath = Paths.get("/temp", fileName);
        // 确保父目录存在
        try {
            if (Files.notExists(savePath.getParent())) {
                Files.createDirectories(savePath.getParent());
            }
            // 确保文件不存在
            if (Files.exists(savePath)) {
                Files.delete(savePath);
            }
            // 创建并写入 写入的文件时一行 在读取的时候readLine即可
            BufferedWriter writer = Files.newBufferedWriter(savePath);
            writer.write(JSON.toJSONString(list));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}