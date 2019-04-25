package com.foolday.common.util;


import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public final class KeyUtils {
    private KeyUtils() {
    }

    /**
     * 生成每天的唯一订单号
     *
     * @param redisTemplate
     * @return
     */
    public static String generateOrderNo(RedisTemplate<String, String> redisTemplate) {
        return generateOrderNo(redisTemplate, LocalDateTime.now());
    }


    /**
     * 生成每天的唯一订单号
     * <p>
     * 20190425000000001
     * 20190425000000002
     * 20190425000000003
     * 20190425000000004
     * 20190425000000005
     * 20190425000000006
     *
     * @param redisTemplate
     * @return
     */
    public static String generateOrderNo(RedisTemplate<String, String> redisTemplate, LocalDateTime dateTime) {
        PlatformAssert.notNull(redisTemplate, "redis 客户端不可为空");
        String yyyyMMdd = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        if (Boolean.TRUE.equals(redisTemplate.hasKey(yyyyMMdd))) {
            redisTemplate.opsForValue().increment(yyyyMMdd, 1L);
            return redisTemplate.opsForValue().get(yyyyMMdd);
        } else {
            String dayFirstNo = (yyyyMMdd + "00000000" + 1);
            redisTemplate.opsForValue().set(yyyyMMdd, dayFirstNo, 31L, TimeUnit.DAYS);
            return dayFirstNo;
        }
    }


}
