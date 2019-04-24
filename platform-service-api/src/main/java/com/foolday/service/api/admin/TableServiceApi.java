package com.foolday.service.api.admin;

import com.foolday.dao.table.TableEntity;
import com.foolday.serviceweb.dto.admin.table.TableVo;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TableServiceApi {

    boolean add(TableVo tableVo);

    List<TableEntity> list();

    boolean edit(TableVo tableVo);

    boolean delete(String ids);

    boolean bindQrcode(TableVo tableVo, String id);

}
