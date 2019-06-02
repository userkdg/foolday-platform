package com.foolday.service.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.common.base.BaseServiceUtils;
import com.foolday.common.enums.ShopStatus;
import com.foolday.common.enums.UserStatus;
import com.foolday.common.util.MathUtils;
import com.foolday.common.util.PlatformAssert;
import com.foolday.core.init.ContextLoader;
import com.foolday.dao.shop.ShopEntity;
import com.foolday.dao.shop.ShopMapper;
import com.foolday.dao.system.admin.AdminEntity;
import com.foolday.dao.system.admin.AdminMapper;
import com.foolday.service.api.admin.ShopServiceApi;
import com.foolday.serviceweb.dto.admin.shop.ShopVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

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
     * latitude	number	纬度，范围为 -90~90，负数表示南纬
     * longitude	number	经度，范围为 -180~180，负数表示西经
     *
     * @param latitude
     * @param longitude
     * @return
     */
    @Override
    public Optional<String> findByLatitudeAndLonitude(float latitude, float longitude) {
        if (latitude < -90 || latitude > 90)
            return Optional.empty();
        if (longitude < -180 || longitude > 180)
            return Optional.empty();
        Set<ShopEntity> shopsCache = ContextLoader.getShopsCache();
        return shopsCache.stream().filter(shopEntity -> {
            Float lat = shopEntity.getLat();
            Float lnt = shopEntity.getLnt();
            return (lat != null && lat >= -90 && lat <= 90) && (lnt != null && lnt >= -180 && lnt <= 180);
        }).map(shopEntity -> {
            Float lat = shopEntity.getLat();
            Float lnt = shopEntity.getLnt();
            double dist = MathUtils.calcDistByLatitudeAndLonitude(latitude, longitude, lat, lnt);
            return new DistShopDto(shopEntity.getId(), latitude, longitude, dist);
        }).min(Comparator.comparing(DistShopDto::getDist)).map(DistShopDto::getShopId);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class DistShopDto {
        private String shopId;

        private float latitude;

        private float longitude;

        private double dist;
    }

    @Override
    public Optional<ShopEntity> getDefaultShop() {
        if (ContextLoader.getShopsCache().isEmpty())
            return Optional.empty();
        return Optional.ofNullable(ContextLoader.getShopsCache().iterator().next());
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
