package com.foolday.service.admin;

import com.foolday.cloud.serviceweb.dto.admin.goods.GoodsVo;
import com.foolday.dao.goods.GoodsEntity;
import com.foolday.dao.goods.GoodsMapper;
import com.foolday.service.api.admin.GoodsServiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 商品具体业务
 */
@Service
@Transactional
public class GoodsService implements GoodsServiceApi {
    private static final Logger logger = LoggerFactory.getLogger(GoodsService.class);

    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public GoodsEntity newGoods(GoodsVo goodsVo){
        GoodsEntity goodsEntity = new GoodsEntity();
        BeanUtils.copyProperties(goodsVo, goodsEntity);
        goodsMapper.insert(goodsEntity);
        return goodsEntity;
    }

}
