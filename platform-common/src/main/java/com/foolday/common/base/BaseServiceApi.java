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
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * service工具，基于entity的实现的封装
 * 集成本类的好处时针对实现类中需要注入其他实体类的mapper+service的情况进行了优化，后续只要注入其他实体对应的serviceApi（实现了本类）即可，
 * 无需在注入Mapper
 *
 * @param <Entity> 基于mybatis-plus的实现的实体类进行crud
 */
public interface BaseServiceApi<Entity extends BaseEntity> {
    default LambdaQueryWrapper<Entity> lqWrapper() {
        return Wrappers.lambdaQuery(getEntityBean());
    }

    /**
     * 统一通过工厂生成实体类对象用于Wrapper工具使用
     *
     * @return
     */
    default Wrapper<Entity> qWrapper() {
        return Wrappers.query(getEntityBean());
    }

    default LambdaUpdateWrapper<Entity> luWrapper() {
        return Wrappers.lambdaUpdate(getEntityBean());
    }

    default Wrapper<Entity> uWrapper() {
        return Wrappers.update(getEntityBean());
    }

    default QueryWrapper<Entity> emWrapper() {
        return Wrappers.emptyWrapper();
    }


    // =======================================insert or update ============================================//

    default Entity insert(Entity entity) {
        entity.insert();
        return entity;
    }

    /**
     * default Entity upsert(Entity entity) {
     * entity.insertOrUpdate();
     * return entity;
     * }
     *
     * @param entity
     * @return
     */
    default Entity upsert(Entity entity) {
        entity.insertOrUpdate();
        return entity;
    }

    default Entity insertOrUpdate(Entity entity) {
        entity.insertOrUpdate();
        return entity;
    }

    // =======================================update ============================================//

    @SuppressWarnings("unchecked")
    default boolean update(Wrapper<Entity> wrapper) {
        return getEntityBean().update(wrapper);
    }

    default boolean updateById(Entity entity) {
        assertEntityPKey(entity, "update entity by id but id is null");
        return entity.updateById();
    }

    // =======================================delete ============================================//

    default boolean deleteById(Serializable id) {
        return getEntityBean().deleteById(id);
    }

    @SuppressWarnings("unchecked")
    default boolean delete(Wrapper<Entity> wrappers) {
        return getEntityBean().delete(wrappers);
    }

    // =======================================select ============================================//

    @SuppressWarnings("unchecked")
    default List<Entity> selectAll() {
        return getEntityBean().selectAll();
    }

    @SuppressWarnings("unchecked")
    default List<Entity> selectList(Wrapper<Entity> wrapper) {
        return getEntityBean().selectList(wrapper);
    }

    default Optional<Entity> selectOne(Wrapper<Entity> wrapper) {
        @SuppressWarnings("unchecked") Model model = getEntityBean().selectOne(wrapper);
        return Optional.ofNullable(model2Entity(model));
    }

    default Optional<Entity> selectById(Serializable id) {
        Entity entity = getEntityBean();
        return Optional.ofNullable(model2Entity(entity.selectById(id)));
    }

    /**
     * pass 请使用selectById(Serializable id)
     *
     * @param entity
     * @return
     */
    @Deprecated
    default Optional<Entity> selectById(Entity entity) {
        Model model = entity.selectById();
        assertEntityPKey(entity, "select entity by id but id is null");
        return Optional.ofNullable(model2Entity(model));
    }

    // =======================================bean utils ============================================//

    @SuppressWarnings("unchecked")
    default Entity model2Entity(Model<?> model) {
        try {
            return (Entity) model;
        } catch (Exception e) {
            e.printStackTrace();
            throw new PlatformException("查询的实体类无法强转Model to BaseEntity");
        }
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

//    ======================= clone 一个原有的实体类=====================================

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

    //    ======================= VO（必须实现Serializable由于vo进行前后端交互需要网络传输) 转 实体类=====================================

    /**
     * 用于vo 转entiy
     *
     * @param vo
     * @param <Vo>
     * @return
     */
    default <Vo extends Serializable> Entity of(Vo vo) {
        return vo2entity(vo);
    }


    /**
     * 用于vo 转entiy
     *
     * @param vo
     * @param <Vo>
     * @return
     */
    default <Vo extends Serializable> Entity entityForm(Vo vo) {
        return vo2entity(vo);
    }


    /**
     * 用于vo 转entiy
     *
     * @param vo
     * @param <Vo>
     * @return
     */
    default <Vo extends Serializable> Entity vo2entity(Vo vo) {
        Entity entity = getEntityBean();
        BeanUtils.copyProperties(vo, entity);
        return entity;
    }

    //    ============================== 实例化对象或构建工厂========================

    /**
     * 实例化
     *
     * @return
     */
    default Entity getEntityBean() {
        return beanFactory().newInstance();
    }

    BeanFactory<Entity> beanFactory();

//    =================================校验数据有效性========================================

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

    /**
     * 判断id是否已存在数据，并返回实体数据，否则报错反馈前端
     *
     * @param id
     * @param errmsg
     * @return
     * @see BaseServiceUtils 类似，区别时一个基于mapper 一个基于entiy
     */
    default Entity checkOneById(Serializable id, String errmsg) {
        final String finalErrmsg = StringUtils.isEmpty(errmsg) ? "获取相应数据失败" : errmsg;
        Entity model = selectById(id).orElseThrow(()->new PlatformException(finalErrmsg));
        Entity entity = model2Entity(model);
        PlatformAssert.isTrue(entity != null,  errmsg);
        return entity;
    }
}
