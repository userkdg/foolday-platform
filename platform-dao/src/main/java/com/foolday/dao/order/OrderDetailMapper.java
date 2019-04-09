package com.foolday.dao.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * 可加@Repository（加了就把实例给spring容器管理） 或者不加 都可以
 */
@Repository
public interface OrderDetailMapper extends BaseMapper<OrderDetailEntity> {

}
