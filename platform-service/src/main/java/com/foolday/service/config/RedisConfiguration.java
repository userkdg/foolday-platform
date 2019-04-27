package com.foolday.service.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.foolday.common.base.RedisBeanNameApi;
import com.foolday.common.enums.ChannelType;
import com.foolday.service.common.CommentMessageCustomer;
import com.foolday.service.common.OrderMessageCustomer;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

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

    @Bean
    public ChannelTopic orderChannelTopic() {
        ChannelTopic channelTopic = new ChannelTopic(ChannelType.订单类.getValue());
        return channelTopic;
    }


    @Bean
    public ChannelTopic commentChannelTopic() {
        ChannelTopic channelTopic = new ChannelTopic(ChannelType.评论类.getValue());
        return channelTopic;
    }

    @Autowired
    private OrderMessageCustomer orderMessageCustomer;

    @Autowired
    private CommentMessageCustomer commentMessageCustomer;

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        Map<MessageListener, Collection<? extends Topic>> listeners = Maps.newHashMap();
        listeners.put(orderMessageCustomer, Collections.singletonList(orderChannelTopic()));
        listeners.put(commentMessageCustomer, Collections.singletonList(commentChannelTopic()));
        container.setMessageListeners(listeners);
        return container;
    }

    @Bean(REDIS_TEMPLATE_S_S)
    public RedisTemplate<String, String> redisTemplateString(RedisConnectionFactory redisConnectionFactory) {
        return redisTemplateKeyString(redisConnectionFactory);
    }

    private <T> RedisTemplate<String, T> redisTemplateKeyString(RedisConnectionFactory redisConnectionFactory) {
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

    @Bean(REDIS_TEMPLATE_S_O)
    public RedisTemplate<String, Object> redisTemplateStringObject(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
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
