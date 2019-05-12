package com.foolday.service.wechat;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.common.base.BaseServiceUtils;
import com.foolday.dao.advice.UserAdviceEntity;
import com.foolday.service.api.wechat.WxUserAdviceServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class WxUserAdviceService implements WxUserAdviceServiceApi {

    /**
     * 意见反馈
     *
     * @param userId
     * @param content
     * @param shopId
     * @param imageIds
     * @return
     */
    @Override
    public UserAdviceEntity add(String userId, String content, String shopId, String[] imageIds) {
        UserAdviceEntity advice = new UserAdviceEntity();
        advice.setContent(content);
        advice.setImgIds(String.join(",", imageIds));
        advice.setShopId(shopId);
        advice.setUserId(userId);
        advice.insert();
        return advice;
    }

    /**
     * 获取意见列表
     *
     * @param userId
     * @param shopId
     * @return
     */
    @Override
    public List<UserAdviceEntity> listByUserIdAndShopId(String userId, String shopId) {
        UserAdviceEntity adviceEntity = new UserAdviceEntity();
        adviceEntity.setUserId(userId);
        adviceEntity.setShopId(shopId);
        LambdaQueryWrapper<UserAdviceEntity> wrapper = Wrappers.lambdaQuery(adviceEntity)
                .orderByDesc(UserAdviceEntity::getCreateTime, UserAdviceEntity::getUpdateTime);
        List<UserAdviceEntity> list = adviceEntity.selectList(wrapper);
        return list;
    }

    @Override
    public UserAdviceEntity get(String id) {
        return BaseServiceUtils.checkOneByModelId(UserAdviceEntity.class, id, "获取意见信息失败");
    }

}
