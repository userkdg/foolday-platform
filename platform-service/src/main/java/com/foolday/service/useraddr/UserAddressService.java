package com.foolday.service.useraddr;

import com.foolday.common.base.BeanFactory;
import com.foolday.common.base.annotation.PlatformService;
import com.foolday.dao.useraddr.UserAddressEntity;
import com.foolday.service.api.useraddr.UserAddressServiceApi;
import lombok.extern.slf4j.Slf4j;

/**
 * 记录用户使用过的地址
 * 下次选择提供
 *
 * @author userkdg
 */
@Slf4j
@PlatformService
public class UserAddressService implements UserAddressServiceApi {

    @Override
    public BeanFactory<UserAddressEntity> beanFactory() {
        return UserAddressEntity::new;
    }
}
