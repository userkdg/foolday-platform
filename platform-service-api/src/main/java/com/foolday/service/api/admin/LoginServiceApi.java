package com.foolday.service.api.admin;

import com.foolday.cloud.serviceweb.dto.admin.login.LoginVo;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

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
}
