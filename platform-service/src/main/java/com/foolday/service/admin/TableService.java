package com.foolday.service.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.common.base.BaseServiceUtils;
import com.foolday.common.enums.TableStatus;
import com.foolday.dao.table.TableEntity;
import com.foolday.dao.table.TableMapper;
import com.foolday.service.api.admin.TableServiceApi;
import com.foolday.serviceweb.dto.admin.base.LoginUser;
import com.foolday.serviceweb.dto.admin.table.TableVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
@Transactional
public class TableService implements TableServiceApi {

    @Resource
    private TableMapper tableMapper;

    @Override
    public boolean add(TableVo tableVo, LoginUser loginUser) {
        TableEntity tableEntity = new TableEntity();
        BeanUtils.copyProperties(tableVo, tableEntity);
        tableEntity.setShopId(loginUser.getShopId());
        tableEntity.setStatus(TableStatus.生效);
        int insert = tableMapper.insert(tableEntity);
        return insert > 0;
    }

    @Override
    public List<TableEntity> list() {
        QueryWrapper<TableEntity> wrapper = Wrappers.query(new TableEntity());
        return tableMapper.selectList(wrapper);
    }

    @Override
    public boolean edit(TableVo tableVo) {
        TableEntity tableEntity = new TableEntity();
        BeanUtils.copyProperties(tableVo, tableEntity);
        int update = tableMapper.updateById(tableEntity);
        return update > 0;
    }

    @Override
    public boolean delete(String ids) {
        List<TableEntity> shopEntities = BaseServiceUtils.checkAllByIds(tableMapper, ids);
        long count = shopEntities.stream().peek(entity -> {
            tableMapper.deleteById(entity);
        }).count();

        log.info("删除桌位：{}个, id为{}", count, ids);
        return count > 0;
    }

    /**
     * 绑定二维码
     * @param tableVo 桌位
     * @param id 二维码id
     * @return
     */
    @Override
    public boolean bindQrcode(TableVo tableVo, String id) {
        TableEntity tableEntity = new TableEntity();
        BeanUtils.copyProperties(tableVo, tableEntity);
        tableEntity.setQrcodeId(id);
        boolean ret = tableEntity.updateById();
        return ret;
    }

}
