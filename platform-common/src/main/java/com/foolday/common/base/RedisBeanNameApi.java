package com.foolday.common.base;

public interface RedisBeanNameApi {
    String REDIS_TEMPLATE_S_S = "stringRedisTemplate";

    String REDIS_TEMPLATE_S_O = "stringObjectRedisTemplate";

    String REDIS_TEMPLATE_O_O = "objectObjectRedisTemplate";

    /**
     * redis的kv类型
     */
    enum RedisKvType {
        SS,
        SO,
        OO;
    }

    static String of(RedisKvType kvType) {
        switch (kvType) {
            case OO:
                return REDIS_TEMPLATE_O_O;
            case SO:
                return REDIS_TEMPLATE_S_O;
            case SS:
                return REDIS_TEMPLATE_S_S;
            default:
                return REDIS_TEMPLATE_O_O;
        }
    }
}
