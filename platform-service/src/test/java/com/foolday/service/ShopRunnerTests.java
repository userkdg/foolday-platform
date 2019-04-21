package com.foolday.service;

import com.foolday.common.enums.ShopStatus;
import com.foolday.dao.shop.ShopEntity;
import com.foolday.dao.shop.ShopMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * service 层服务测试
 * 设计上是没有web层的配置文件，要是测试配置文件，要到web层的测试类，目前只允许web和service之间通过公共的javabean 或Java原生对象或者common包等基础包实体来进行数据传输
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"application.yml"}, classes = PlatformServiceApplication.class)
public class ShopRunnerTests {
    @Resource
    private ShopMapper shopMapper;

    @Test
    public void shopAdd(){
        ShopEntity shopEntity = new ShopEntity();
        shopEntity.setContact("xxx");
        shopEntity.setName("xxx");
        shopEntity.setStatus(ShopStatus.生效);
        shopEntity.setCreatetime(new Date());
        shopMapper.insert(shopEntity);
        System.out.println(shopEntity);
    }
}
