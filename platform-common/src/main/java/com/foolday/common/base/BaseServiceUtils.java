package com.foolday.common.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.foolday.common.util.PlatformAssert;

/**
 * 不允许通过本接口来注入实例！！
 * 只为了给service层提取公共抽象
 * 如果不要求判断id的实体是否存在可以不实现
 * <p>
 */
public final class BaseServiceUtils {
    /**
     * 通用判断所给的id是否存在对应实体
     *
     * @param modelBaseMapper
     * @param id
     * @param <Model>
     * @return
     */
    public static <Model> Model checkOneById(BaseMapper<Model> modelBaseMapper, String id) {
        Model entity = modelBaseMapper.selectById(id);
        PlatformAssert.notNull(entity, "无法获取处理对象,信息已被删除,结束操作");
        return entity;
    }



}
