package com.foolday.service.admin;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.foolday.cloud.serviceweb.dto.admin.login.LoginVo;
import com.foolday.dao.admin.AdminEntity;
import com.foolday.dao.admin.AdminMapper;
import com.foolday.service.api.admin.LoginServiceApi;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class LoginService implements LoginServiceApi {
    @Resource
    AdminMapper adminMapper;

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
}
