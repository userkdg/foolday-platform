package com.foolday.common.base;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.foolday.common.dto.FantPage;

/**
 * service工具，基于entity的实现的封装
 * 集成本类的好处时针对实现类中需要注入其他实体类的mapper+service的情况进行了优化，后续只要注入其他实体对应的serviceApi（实现了本类）即可，
 * 需在注入Mapper
 * 增加BaseServiceApi的mapper API,由于实体操作无法实现分页等功能而引进mapper实现
 *
 * @param <Entity> 基于mybatis-plus的实现的实体类进行crud
 */
public interface BaseMapperServiceApi<Entity extends BaseEntity> {

    BaseMapper<Entity> getMapper();

    /*分页现实*/
    default FantPage<Entity> page2FantPage(IPage<Entity> selectPage) {
        return FantPage.Builder.ofPage(selectPage);
    }

    default FantPage<Entity> selectPage(IPage page, LambdaQueryWrapper queryWrapper) {
        @SuppressWarnings("unchecked")
        IPage<Entity> selectPage = getMapper().selectPage(page, queryWrapper);
        return FantPage.Builder.ofPage(selectPage);
    }

}
