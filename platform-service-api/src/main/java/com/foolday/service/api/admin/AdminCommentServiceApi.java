package com.foolday.service.api.admin;

import com.foolday.dao.comment.CommentEntity;
import com.foolday.serviceweb.dto.admin.comment.CommentVo;

import java.util.List;

public interface AdminCommentServiceApi {

    List<CommentEntity> findById(String commentId);

    List<CommentEntity> findByOrderId(String orderId);

    List<CommentEntity> list();

    void replay(String commentId, CommentVo commentVo);
}
