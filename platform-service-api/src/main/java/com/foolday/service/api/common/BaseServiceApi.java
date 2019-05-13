package com.foolday.service.api.common;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.common.base.BaseEntity;
import com.foolday.common.exception.PlatformException;
import io.netty.util.internal.UnstableApi;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

/**
 * service工具，基于entity的实现的封装
 *
 * @param <Entity> 基于mybatis-plus的实现的实体类进行crud
 * @see com.foolday.service.api.wechat.WxArticleServiceApi 的用法
 */
public interface BaseServiceApi<Entity extends BaseEntity> {
    default LambdaQueryWrapper<Entity> lqWrapper(Entity entity) {
        return Wrappers.lambdaQuery(entity);
    }

    default Wrapper<Entity> qWrapper(Entity entity) {
        return Wrappers.query(entity);
    }

    default LambdaUpdateWrapper<Entity> luWrapper(Entity entity) {
        return Wrappers.lambdaUpdate(entity);
    }

    default Wrapper<Entity> uWrapper(Entity entity) {
        return Wrappers.update(entity);
    }

    default QueryWrapper<Entity> lqWrapper() {
        return Wrappers.emptyWrapper();
    }

    default Entity insert(Entity entity) {
        entity.insert();
        return entity;
    }

    default Entity insertOrUpdate(Entity entity) {
        entity.insertOrUpdate();
        return entity;
    }

    default boolean deleteById(Entity entity) {
        return entity.deleteById();
    }

    default boolean deleteById(Entity entity, Serializable id) {
        return entity.deleteById(id);
    }

    @SuppressWarnings("unchecked")
    default boolean delete(Wrapper<Entity> wrappers) {
        return wrappers.getEntity().delete(wrappers);
    }

    default boolean updateById(Entity entity) {
        return entity.updateById();
    }

    default boolean updateById(Wrapper<Entity> wrapper) {
        return wrapper.getEntity().updateById();
    }

    @SuppressWarnings("unchecked")
    default List<Entity> selectAll(Entity entity) {
        return entity.selectAll();
    }

    @SuppressWarnings("unchecked")
    default List<Entity> selectList(Wrapper<Entity> wrapper) {
        return wrapper.getEntity().selectList(wrapper);
    }

    default boolean deleteById(Wrapper<Entity> wrapper) {
        return wrapper.getEntity().deleteById();
    }

    @SuppressWarnings("unchecked")
    default boolean update(Wrapper<Entity> wrapper) {
        return wrapper.getEntity().update(wrapper);
    }


    /**
     * 稳定的实例化对象
     *
     * @param entityCls
     * @return
     */
    default Entity newInstance(Class<Entity> entityCls) {
        try {
            return entityCls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new PlatformException("实例化对象失败 e=>" + e);
        }
    }

    /**
     * 尝试创建实体类，不稳定
     *
     * @param entityCls
     * @return
     */
    @UnstableApi
    default Entity newUnStableInstance(Class<?> entityCls) {
        try {
            @SuppressWarnings("unchecked") Entity entity = (Entity) entityCls.newInstance();
            return entity;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new PlatformException("实例化对象失败 e=>" + e);
        }
    }

    /**
     * 克隆一个对象 用于Vo -> Entity中工具类
     * <p>
     * UserVo => UserEntity
     *
     * @param entity
     * @return
     */
    default Entity clone(Entity entity) {
        Entity newEntity = newUnStableInstance(entity.getClass());
        BeanUtils.copyProperties(entity, newEntity);
        return newEntity;
    }

}
