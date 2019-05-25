package com.foolday.service.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.common.base.BaseServiceUtils;
import com.foolday.common.enums.ShopStatus;
import com.foolday.common.enums.UserStatus;
import com.foolday.common.util.PlatformAssert;
import com.foolday.dao.shop.ShopEntity;
import com.foolday.dao.shop.ShopMapper;
import com.foolday.dao.system.admin.AdminEntity;
import com.foolday.dao.system.admin.AdminMapper;
import com.foolday.service.api.admin.ShopServiceApi;
import com.foolday.serviceweb.dto.admin.shop.ShopVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional
public class ShopService implements ShopServiceApi {
    @Resource
    private ShopMapper shopMapper;

    @Resource
    private AdminMapper adminMapper;

    @Override
    public boolean createShop(ShopVo shopVo) {
        ShopEntity shopEntity = new ShopEntity();
        boolean ret = false;

        BeanUtils.copyProperties(shopVo, shopEntity);
        shopEntity.setCreateTime(LocalDateTime.now());
        shopEntity.setUpdateTime(LocalDateTime.now());
        shopEntity.setStatus(ShopStatus.生效);
        int insertValue = shopMapper.insert(shopEntity);
        if (insertValue == 1) {
            log.info("店铺{}创建成功", shopVo.getName());
            ret = true;
        } else {
            log.error("店铺{}创建失败", shopVo.getName());
        }
        return ret;
    }

    @Override
    public List<ShopEntity> list() {
        LambdaQueryWrapper<ShopEntity> wrapper = Wrappers.lambdaQuery();
        List<ShopEntity> shopEntities = shopMapper.selectList(wrapper);
        return shopEntities;
    }

    @Override
    public boolean edit(ShopVo shopVo) {
        ShopEntity shopEntity = new ShopEntity();
        BeanUtils.copyProperties(shopVo, shopEntity);
        int i = shopMapper.updateById(shopEntity);
        return i > 0;
    }

    @Override
    public boolean delete(String... ids) {
        List<ShopEntity> shopEntities = BaseServiceUtils.checkAllByIds(shopMapper, ids);
        long count = shopEntities.stream().peek(entity -> {
            entity.setUpdateTime(LocalDateTime.now());
            entity.setStatus(ShopStatus.删除);
        }).map(entity -> {
            return shopMapper.updateById(entity);
        }).count();

        log.info("删除店铺：{}个, id为{}", count, ids);
        return count > 0;
    }

    @Override
    public boolean freeze(String ids) {
        List<ShopEntity> shopEntities = BaseServiceUtils.checkAllByIds(shopMapper, ids);
        long count = shopEntities.stream().peek(entity -> {
            entity.setUpdateTime(LocalDateTime.now());
            entity.setStatus(ShopStatus.冻结);
        }).map(entity -> {
            return shopMapper.updateById(entity);
        }).count();

        log.info("冻结店铺：{}个, id为{}", count, ids);
        return count > 0;
    }


    /**
     * 根据后台人员id获取对应的店铺信息
     *
     * @param adminId
     * @return
     */
    @Override
    public ShopEntity findByAdminId(String adminId) {
        AdminEntity adminEntity = BaseServiceUtils.checkOneById(adminMapper, adminId);
        PlatformAssert.isTrue(Objects.equals(UserStatus.有效, adminEntity.getStatus()), "用户账号" + adminEntity.getAccount() + "已无效");
        String shopId = adminEntity.getShopId();
        return BaseServiceUtils.checkOneById(shopMapper, shopId);
    }
}
