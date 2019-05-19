package com.foolday.service.banner;

import com.foolday.common.base.BeanFactory;
import com.foolday.common.base.PlatformService;
import com.foolday.dao.banner.BannerEntity;
import com.foolday.service.api.banner.BannerServiceApi;
import lombok.extern.slf4j.Slf4j;

/**
 * banner service
 * @author userkdg
 */
@Slf4j
@PlatformService
public class BannerServiceImpl implements BannerServiceApi {


    @Override
    public BeanFactory<BannerEntity> beanFactory() {
        return BannerEntity::new;
    }
}
