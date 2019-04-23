package com.foolday.service.api.admin;

import com.foolday.dao.specification.GoodsSpecEntity;
import com.foolday.serviceweb.dto.admin.specification.GoodsSpecVo;
import lombok.NonNull;

import java.util.List;

public interface GoodsSpecServiceApi {
    GoodsSpecEntity add(GoodsSpecVo goodsSpecVo);

    void edit(GoodsSpecVo goodsSpecVo, String goodsSpecId);

    void delete(String goodsSpecId);

    List<GoodsSpecEntity> findByGoodsId(@NonNull String goodsId);
}
