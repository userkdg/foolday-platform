package com.foolday.service.admin;

import com.foolday.common.base.BaseServiceUtils;
import com.foolday.common.enums.ShopStatus;
import com.foolday.common.enums.UserStatus;
import com.foolday.common.util.PlatformAssert;
import com.foolday.dao.admin.AdminEntity;
import com.foolday.dao.admin.AdminMapper;
import com.foolday.dao.shop.ShopEntity;
import com.foolday.dao.shop.ShopMapper;
import com.foolday.service.api.admin.ShopServiceApi;
import com.foolday.serviceweb.dto.admin.shop.ShopVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
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
    public boolean createShop(ShopVo shopVo){
        ShopEntity shopEntity = new ShopEntity();
        boolean ret = false;

        BeanUtils.copyProperties(shopVo,shopEntity);
        shopEntity.setCreatetime(new Date());
        shopEntity.setUpdatetime(new Date());
        shopEntity.setStatus(ShopStatus.生效);
        int insertValue = shopMapper.insert(shopEntity);
        if(insertValue == 1){
            log.info("店铺{}创建成功", shopVo.getName());
            ret = true;
        } else {
            log.error("店铺{}创建失败",shopVo.getName());
        }
        return ret;
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
