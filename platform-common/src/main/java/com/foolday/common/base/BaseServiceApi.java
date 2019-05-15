package com.foolday.common.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.foolday.common.exception.PlatformException;
import com.foolday.common.util.PlatformAssert;
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

    default QueryWrapper<Entity> eqWrapper() {
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
        assertEntityPKey(entity, "delete entity by id but id is null");
        return entity.deleteById();
    }

    @Deprecated
    default boolean deleteById(Entity entity, Serializable id) {
        return entity.deleteById(id);
    }

    @SuppressWarnings("unchecked")
    default boolean delete(Wrapper<Entity> wrappers) {
        return wrappers.getEntity().delete(wrappers);
    }

    @SuppressWarnings("unchecked")
    default boolean update(Wrapper<Entity> wrapper) {
        return wrapper.getEntity().update(wrapper);
    }

    default boolean updateById(Entity entity) {
        assertEntityPKey(entity, "update entity by id but id is null");
        return entity.updateById();
    }


    default boolean updateById(Wrapper<Entity> wrapper) {
        Entity entity = wrapper.getEntity();
        assertEntityPKey(entity, "update entity by id but id is null");
        return entity.updateById();
    }

    @SuppressWarnings("unchecked")
    default List<Entity> selectAll(Entity entity) {
        return entity.selectAll();
    }

    @SuppressWarnings("unchecked")
    default List<Entity> selectAll(Class<Entity> entityCls) {
        return newInstance(entityCls).selectAll();
    }

    @SuppressWarnings("unchecked")
    default List<Entity> selectList(Wrapper<Entity> wrapper) {
        return wrapper.getEntity().selectList(wrapper);
    }

    default Entity selectOne(Wrapper<Entity> wrapper) {
        @SuppressWarnings("unchecked") Model model = wrapper.getEntity().selectOne(wrapper);
        return model2Entity(model);
    }

    default Entity selectById(Class<Entity> entityCls, Serializable id) {
        Entity entity = newInstance(entityCls);
        assertEntityPKey(entity, "select entity by id but id is null");
        return model2Entity(entity.selectById(id));
    }

    default Entity selectById(Entity entity) {
        Model model = entity.selectById();
        assertEntityPKey(entity, "select entity by id but id is null");
        return model2Entity(model);
    }

    @SuppressWarnings("unchecked")
    default Entity model2Entity(Model<?> model) {
        try {
            return (Entity) model;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PlatformException("查询的实体类无法强转Model to BaseEntity");
        }
    }

    default Entity selectById(Wrapper<Entity> wrapper) {
        Entity entity = wrapper.getEntity();
        assertEntityPKey(entity, "select entity by id but id is null");
        Model model = entity.selectById();
        return model2Entity(model);
    }


    default boolean deleteById(Class<Entity> entityCls, Serializable id) {
        Entity entity = newInstance(entityCls);
        assertEntityPKey(entity, "delete entity by id but id is null");
        return entity.deleteById(id);
    }

    default boolean deleteById(Wrapper<Entity> wrapper) {
        Entity entity = wrapper.getEntity();
        assertEntityPKey(entity, "delete entity by id but id is null");
        return entity.deleteById();
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


    /**
     * byId 的实体dao操作进行控制id不为空
     *
     * @param entity
     * @param errmsg
     */
    default void assertEntityPKey(Entity entity, String errmsg) {
        PlatformAssert.notNull(entity, "entity must not null");
        String id = entity.getId();
        PlatformAssert.notNull(id, errmsg);
    }
}
