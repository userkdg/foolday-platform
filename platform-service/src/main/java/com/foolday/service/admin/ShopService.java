package com.foolday.service.admin;

import com.foolday.serviceweb.dto.admin.shop.ShopVo;
import com.foolday.common.enums.ShopStatus;
import com.foolday.dao.shop.ShopEntity;
import com.foolday.dao.shop.ShopMapper;
import com.foolday.service.api.admin.ShopServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

@Slf4j
@Service
@Transactional
public class ShopService implements ShopServiceApi {
    @Resource
    private ShopMapper shopMapper;

    @Override
    public boolean createShop(ShopVo shopVo){
        ShopEntity shopEntity = new ShopEntity();
        boolean ret = false;

        try {
            BeanUtils.copyProperties(shopEntity,shopVo);
            shopEntity.setCreatetime(new Date());
            shopEntity.setUpdatetime(new Date());
            shopEntity.setStatus(ShopStatus.生效);
            int insertValue = shopMapper.insert(shopEntity);
            if(insertValue == 1){
                log.info("店铺{}创建成功", shopVo.getName());
                ret = true;
            } else {
                log.error("店铺{}创建失败",shopVo.getName());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
