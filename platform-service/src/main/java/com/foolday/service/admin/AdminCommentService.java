package com.foolday.service.admin;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.common.base.BaseServiceUtils;
import com.foolday.common.enums.CommentType;
import com.foolday.dao.comment.CommentEntity;
import com.foolday.dao.comment.CommentMapper;
import com.foolday.service.api.admin.AdminCommentServiceApi;
import com.foolday.serviceweb.dto.admin.base.LoginUser;
import com.foolday.serviceweb.dto.admin.comment.CommentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 后台管理的评论
 */
@Slf4j
@Service
@Transactional
public class AdminCommentService implements AdminCommentServiceApi {

    @Resource
    private CommentMapper commentMapper;

    /**
     * @param commentId
     * @return
     */
    @Override
    public List<CommentEntity> findById(String commentId) {
        CommentEntity comment = BaseServiceUtils.checkOneById(commentMapper, commentId);
        String orderId = comment.getOrderId();
        return findByOrderId(orderId);
    }

    /**
     * 通过订单id获取评论列表
     *
     * @param orderId
     * @return
     */
    @Override
    public List<CommentEntity> findByOrderId(String orderId) {
        CommentEntity entity = new CommentEntity();
        entity.setOrderId(orderId);
        return commentMapper.selectList(Wrappers.lambdaQuery(entity)
                .orderByAsc(CommentEntity::getCreateTime));
    }

    /**
     * 获取评论列表
     *
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<CommentEntity> list(LoginUser loginUser) {
        CommentEntity comment = new CommentEntity();
        comment.setShopId(loginUser.getShopId());
        return commentMapper.selectList(Wrappers.lambdaQuery(comment)
                .orderByDesc(CommentEntity::getUpdateTime, CommentEntity::getCreateTime));
    }

    /**
     * 店铺人员对用户评论进行回复
     *
     * @param commentId
     * @param commentVo
     */
    @Override
    public void replay(String commentId, CommentVo commentVo, LoginUser loginUser) {
        CommentEntity entity = BaseServiceUtils.checkOneById(commentMapper, commentId);
        CommentEntity commentEntity = new CommentEntity();
        BeanUtils.copyProperties(commentVo, commentEntity);
        commentEntity.setOrderId(entity.getOrderId());
        commentEntity.setCreateTime(LocalDateTime.now());
        commentEntity.setCommentType(CommentType.店长);
        commentEntity.setCreateTime(LocalDateTime.now());
        commentEntity.setAdminId(loginUser.getUserId());
        commentEntity.setAdminName(loginUser.getUserName());
        commentEntity.setShopId(loginUser.getShopId());
        commentMapper.insert(commentEntity);
        log.info("人员{}对用户{}的评论{}进行回复，内容为【{}】", loginUser.getUserName(), entity.getUserId(), commentId, commentVo.getDescription());
    }


}
