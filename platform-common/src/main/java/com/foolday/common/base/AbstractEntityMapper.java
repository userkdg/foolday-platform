package com.foolday.common.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface AbstractEntityMapper<Entity extends BaseEntity> extends BaseMapper<Entity> {

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

    default Entity poInsert(Entity entity) {
        entity.insert();
        return entity;
    }

    default Entity poInsertOrUpdate(Entity entity) {
        entity.insertOrUpdate();
        return entity;
    }

    default boolean poDeleteById(Entity entity) {
        return entity.deleteById();
    }

    default boolean poDeleteById(Entity entity, Serializable id) {
        return entity.deleteById(id);
    }

    @SuppressWarnings("unchecked")
    default boolean wrapperPoDelete(Entity entity, Wrapper wrappers) {
        return entity.delete(wrappers);
    }

    default boolean poUpdateById(Entity entity) {
        return updateById(entity) == 1;
    }

}
