package com.foolday.service.admin;

import com.foolday.common.enums.GroupBuyTaskStatus;
import com.foolday.dao.groupbuy.GroupBuyEntity;
import com.foolday.dao.groupbuytask.GroupBuyTaskEntity;
import com.foolday.dao.groupbuytask.GroupBuyTaskMapper;
import com.foolday.service.api.admin.GroupBuyTaskServiceApi;
import com.foolday.serviceweb.dto.admin.base.LoginUserHolder;
import com.foolday.serviceweb.dto.admin.groupbuy.GroupBuyVo;
import com.foolday.serviceweb.dto.admin.groupbuytask.GroupBuyTaskVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 *
 */
@Slf4j
@Service
@Transactional
public class GroupBuyTaskService implements GroupBuyTaskServiceApi {
    @Resource
    private GroupBuyTaskMapper groupBuyTaskMapper;

    @Override
    public boolean add(GroupBuyTaskVo vo) {
        GroupBuyTaskEntity entity = new GroupBuyTaskEntity();
        BeanUtils.copyProperties(vo, entity);
        entity.setCreateTime(LocalDateTime.now());
        entity.setStatus(GroupBuyTaskStatus.有效);
        entity.setUserId(LoginUserHolder.get().getUserId());
        int ret = groupBuyTaskMapper.insert(entity);
        return ret > 0;
    }

    @Override
    public boolean edit(GroupBuyTaskVo vo) {
        GroupBuyTaskEntity entity = new GroupBuyTaskEntity();
        BeanUtils.copyProperties(vo, entity);
        int i = groupBuyTaskMapper.updateById(entity);
        return i > 0;
    }

}
