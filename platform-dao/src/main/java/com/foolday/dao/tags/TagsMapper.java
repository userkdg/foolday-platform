package com.foolday.dao.tags;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * 可加@Repository（加了就把实例给spring容器管理） 或者不加 都可以
 */
@Deprecated
@Repository
public interface TagsMapper extends BaseMapper<TagsEntity> {

}
