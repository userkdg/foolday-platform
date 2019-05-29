package com.foolday.service.admin;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.common.enums.UserStatus;
import com.foolday.common.util.PlatformAssert;
import com.foolday.dao.shop.ShopEntity;
import com.foolday.dao.system.admin.AdminEntity;
import com.foolday.dao.system.admin.AdminMapper;
import com.foolday.service.api.admin.LoginServiceApi;
import com.foolday.service.api.admin.ShopServiceApi;
import com.foolday.serviceweb.dto.admin.base.LoginUser;
import com.foolday.serviceweb.dto.admin.login.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional
public class LoginService implements LoginServiceApi {
    @Resource
    AdminMapper adminMapper;

    @Resource
    private ShopServiceApi shopServiceApi;

    /**
     * 检查账号密码是否存在
     *
     * @param loginVo
     * @return
     */
    @Override
    public boolean checkLoginAccount(LoginVo loginVo) {
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setAccount(loginVo.getAccount());
        adminEntity.setPassword(LoginServiceApi.md5DigestAsHex(loginVo.getPassword()));
        return adminMapper.selectCount(Wrappers.query(adminEntity)) >= 1;
    }

    /**
     * 店铺获取后台人员信息
     *
     * @param shopId
     * @return
     */
    @Override
    public List<AdminEntity> findByShopId(String shopId) {
        if (StringUtils.isBlank(shopId)) log.warn("店铺id为空获取所有后台用户信息");
        return adminMapper.selectList(Wrappers.lambdaQuery(new AdminEntity()).eq(AdminEntity::getShopId, shopId));
    }

    /**
     * 针对验证通过的用户创建loginUser提供给jwt
     *
     * @param loginVo
     * @return
     */
    @Override
    public LoginUser getLoginUser(LoginVo loginVo) {
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setAccount(loginVo.getAccount());
        adminEntity.setPassword(LoginServiceApi.md5DigestAsHex(loginVo.getPassword()));
        AdminEntity admin = adminMapper.selectOne(Wrappers.lambdaQuery(adminEntity));
        PlatformAssert.isFalse(Objects.isNull(admin), "账号信息不存在");
        ShopEntity shopEntity = shopServiceApi.findByAdminId(admin.getId());
        LoginUser loginUser = new LoginUser(admin.getId(), admin.getAccount(), shopEntity.getId(), UserStatus.有效.equals(admin.getStatus()));
        return loginUser;
    }


}
