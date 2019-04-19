package com.foolday.core.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.foolday.common.base.RedisBeanNameApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 由于spring boot 默认的 redisTemplate写入到redis中是bytes[]显示，定制序列化来调整显示
 * <p>
 * redisTemplate.setKeySerializer(new StringRedisSerializer());
 * redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
 * 目前定义redis的map结构中key1为string 序列化，而key1中的map(key,value)都是objectMapper对象
 * <p>
 * redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
 * redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
 */
@Configuration
public class RedisConfiguration implements RedisBeanNameApi {

    @Bean(REDIS_TEMPLATE_S_S)
    public RedisTemplate<String, String> redisTemplateString(RedisConnectionFactory redisConnectionFactory) {
        return redisTemplateKeyString(redisConnectionFactory);
    }

    @Bean(REDIS_TEMPLATE_S_O)
    public <T> RedisTemplate<String, T> redisTemplateKeyString(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        /*
        普通结构：list set zset string
         */
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        /*
        目前定义redis的map结构中key1为string 序列化，而key1中的map(key,value)都是objectMapper对象
         */
        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 配置其他类型的redisTemplate
     ***/
    @Bean(REDIS_TEMPLATE_O_O)
    public RedisTemplate<Object, Object> redisTemplateKeyObject(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
