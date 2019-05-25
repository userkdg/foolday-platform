package com.foolday.service.api.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foolday.dao.system.admin.AdminEntity;
import com.foolday.serviceweb.dto.admin.base.LoginUser;
import com.foolday.serviceweb.dto.admin.login.LoginVo;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import static com.foolday.common.constant.WebConstant.PLATFORM_PASSWORD_HEX_CHAR;

public interface LoginServiceApi {
    /**
     * 验证账号密码
     *
     * @param loginVo
     * @return
     */
    boolean checkLoginAccount(LoginVo loginVo);

    /**
     * 统一密码加密（原则上不可逆）
     *
     * @param password
     * @return
     */
    static String md5DigestAsHex(String password) {
        if (StringUtils.isEmpty(password))
            return null;
        return DigestUtils.appendMd5DigestAsHex(password.getBytes(StandardCharsets.UTF_8), new StringBuilder(PLATFORM_PASSWORD_HEX_CHAR)).toString();
    }

    /**
     * 跟前端返回的token信息解析为loginUser,并放到threadLocal 中管理
     *
     * @param authToken
     * @return
     * @throws IOException
     */
    static LoginUser tokenBase64Decoder(String authToken) throws IOException {
        return new ObjectMapper().readValue(Base64.getDecoder().decode(authToken), LoginUser.class);
    }


    List<AdminEntity> findByShopId(String shopId);

    /**
     * 用户信息创建token
     *
     * @param loginVo
     * @return
     */
    LoginUser getLoginUser(LoginVo loginVo);
}
