package com.foolday.service.api.admin;

import com.foolday.common.base.BaseServiceApi;
import com.foolday.common.base.BeanFactory;
import com.foolday.dao.comment.CommentEntity;
import com.foolday.serviceweb.dto.admin.base.LoginUser;
import com.foolday.serviceweb.dto.admin.comment.CommentVo;

import java.util.List;

public interface AdminCommentServiceApi extends BaseServiceApi<CommentEntity> {

    List<CommentEntity> findById(String commentId);

    List<CommentEntity> findByOrderId(String orderId);

    /**
     * 实例化实体类的工厂
     *
     * @return
     */
    @Override
    default BeanFactory<CommentEntity> beanFactory() {
        return CommentEntity::new;
    }

    @SuppressWarnings("unchecked")
    List<CommentEntity> list(LoginUser loginUser);

    void replay(String commentId, CommentVo commentVo, LoginUser loginUser);
}
