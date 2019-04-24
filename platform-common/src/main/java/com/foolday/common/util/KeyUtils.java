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
     * 201904250133200001
     * 201904250133200002
     * 201904250133200003
     * 201904250133200004
     * 201904250133200005
     *
     * @param redisTemplate
     * @return
     */
    public static String generateOrderNo(RedisTemplate<String, String> redisTemplate, LocalDateTime dateTime) {
        PlatformAssert.notNull(redisTemplate, "redis 客户端不可为空");
        String yyyyMMddHHmmss = dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        if (Boolean.TRUE.equals(redisTemplate.hasKey(yyyyMMddHHmmss))) {
            redisTemplate.opsForValue().increment(yyyyMMddHHmmss, 1L);
            return redisTemplate.opsForValue().get(yyyyMMddHHmmss);
        } else {
            String dayFirstNo = (yyyyMMddHHmmss + "000" + 1);
            redisTemplate.opsForValue().set(yyyyMMddHHmmss, dayFirstNo, 31L, TimeUnit.DAYS);
            return dayFirstNo;
        }
    }


}
