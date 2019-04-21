package com.foolday.service;

import com.foolday.service.api.admin.GoodsCouponServiceApi;
import com.foolday.service.api.admin.GoodsServiceApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * service 层服务测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"application.yml"}, classes = PlatformServiceApplication.class)
public class GoodsCouponRunnerTests {
    @Resource
    private GoodsCouponServiceApi adminGoodsCouponServiceApi;

    @Resource
    private GoodsServiceApi goodsServiceApi;


    @Test
    public void test() {

    }
}
