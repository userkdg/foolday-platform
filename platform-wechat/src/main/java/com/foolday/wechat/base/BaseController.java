package com.foolday.wechat.base;

import com.foolday.common.base.BaseEntity;
import com.foolday.common.base.BaseServiceApi;

/**
 * 基础增删改查
 *
 * @author Administrator
 */
public interface BaseController<Entity extends BaseEntity> {
    /**
     * 获取实现类
     *
     * @return 抽象的service api
     */
    BaseServiceApi<Entity> getService();
 
}
