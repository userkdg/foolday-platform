package com.foolday.service.carouse;

import com.foolday.common.base.BeanFactory;
import com.foolday.common.base.PlatformService;
import com.foolday.dao.carouse.CarouseEntity;
import com.foolday.service.api.carouse.CarouseServiceApi;
import lombok.extern.slf4j.Slf4j;


/**
 * 业务轮播
 * @author Administrator
 */
@Slf4j
@PlatformService
public class CarouseServiceImpl implements CarouseServiceApi {


    @Override
    public BeanFactory<CarouseEntity> beanFactory() {
        return CarouseEntity::new;
    }
}
