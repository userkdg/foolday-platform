package com.foolday.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * service 层服务测试
 * 设计上是没有web层的配置文件，要是测试配置文件，要到web层的测试类，目前只允许web和service之间通过公共的javabean 或Java原生对象或者common包等基础包实体来进行数据传输
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"application.yml"}, classes = PlatformServiceApplication.class)
public class RedisRunnerTests {

    @Resource(name = "redisTemplateString")
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testRedis() {
        redisTemplate.opsForValue().set("aa:bb:c:dd", "aaaa");
        System.out.println(redisTemplate.opsForValue().get("aa:bb:c:dd"));
        redisTemplate.opsForHash().put("map:bb:c:dd", "key1", "value1");
        String key1Value = (String)redisTemplate.opsForHash().get("map:bb:c:dd", "key1");
        System.out.println(key1Value);

        redisTemplate.opsForSet().add("set:bb:xx:你好", "value");
        String pop = redisTemplate.opsForSet().pop("set:bb:xx:你好");
        System.out.println(pop);
        redisTemplate.opsForList().rightPush("list:bb:xx:", "v");
        redisTemplate.opsForZSet().add("zset:xxx:bb", "value", 3.0D);
//        redisTemplate.opsForGeo().add("geo:xx:bb", new Point(12D, 324D), "model");

    }

}
