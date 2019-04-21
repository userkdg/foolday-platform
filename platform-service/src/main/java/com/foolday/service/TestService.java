package com.foolday.service;

import com.foolday.common.enums.GoodsStatus;
import com.foolday.dao.goods.GoodsEntity;
import com.foolday.dao.goods.GoodsMapper;
import com.foolday.service.api.TestServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.time.LocalDateTime;

import static com.foolday.common.util.UuidUtils.uuid32;

@Service
public class TestService implements TestServiceApi {
//    private static final String mysql_config_name = "platform-service.yml";

    @Autowired
    DataSource dataSource;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public void test() {

        GoodsEntity goodsEntity = new GoodsEntity();
        goodsEntity.setName("雪碧");
        goodsEntity.setShopId(uuid32());
        goodsEntity.setStatus(GoodsStatus.上架);
        goodsEntity.setDescription("雪的口感");
        goodsEntity.setPrice(5.0F);
        goodsEntity.setDiscntPrice(4.0F);
        goodsEntity.setKccnt(100);
        goodsEntity.setImgId(uuid32());
        goodsEntity.setCreateTime(LocalDateTime.now());
        goodsMapper.insert(goodsEntity);
        GoodsEntity goodsEntity1 = goodsMapper.selectById(goodsEntity.getId());

        System.out.println(goodsEntity);
        goodsEntity.setStatus(GoodsStatus.下架);
        goodsMapper.updateById(goodsEntity);
    }
}
