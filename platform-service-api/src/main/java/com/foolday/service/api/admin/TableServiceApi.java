package com.foolday.service.api.admin;

import com.foolday.dao.table.TableEntity;
import com.foolday.serviceweb.dto.admin.base.LoginUser;
import com.foolday.serviceweb.dto.admin.table.TableVo;

import java.util.List;

public interface TableServiceApi {

    boolean add(TableVo tableVo, LoginUser loginUser);

    List<TableEntity> list();

    boolean edit(TableVo tableVo);

    boolean delete(String ids);

    boolean bindQrcode(TableVo tableVo, String id);

}
