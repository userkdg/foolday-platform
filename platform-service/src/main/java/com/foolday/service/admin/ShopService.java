package com.foolday.service.admin;

import com.foolday.cloud.serviceweb.dto.admin.shop.ShopVo;
import com.foolday.dao.shop.ShopEntity;
import com.foolday.dao.shop.ShopMapper;
import com.foolday.service.api.admin.ShopServiceApi;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;

@Service
@Transactional
public class ShopService implements ShopServiceApi {

    private static Logger logger = LoggerFactory.getLogger(ShopService.class);

    @Resource
    private ShopMapper shopMapper;

    @Override
    public boolean createShop(ShopVo shopVo){
        ShopEntity shopEntity = new ShopEntity();
        boolean ret = false;
        try {
            BeanUtils.copyProperties(shopEntity,shopVo);
            int insertValue = shopMapper.insert(shopEntity);
            if(insertValue == 1){
                logger.info("店铺{}创建成功", shopVo.getName());
                ret = true;
            } else {
                logger.error("店铺{}创建失败",shopVo.getName());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return ret;
    }
}
