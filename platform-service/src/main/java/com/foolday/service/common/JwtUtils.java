package com.foolday.service.common;

import com.foolday.common.constant.WebConstant;
import com.foolday.serviceweb.dto.admin.base.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;


/**
 * jwt 的
 */
public class JwtUtils {
    private static final long TOKEN_EXPIRED_TIME = 30 * 24 * 60 * 60;

    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(WebConstant.Admin_JWT_SECERT);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static String createJwt(String adminId,  LoginUser loginUser) throws IOException {
        return createJwt(adminId, loginUser, TOKEN_EXPIRED_TIME);
    }

    /**
     * 创建jwt
     *
     * @param adminId   后台人员id
     * @param loginUser 后台账号信息
     * @param ttlMillis 过期时间
     * @return
     * @throws IOException
     */
    public static String createJwt(String adminId, LoginUser loginUser, long ttlMillis) throws IOException {
        //jjwt已经发封装好了所有的请求头，这里是指定签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成jwt的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        //获取服务器端的密钥，这个不能贡献出去，贡献出去后就可以随意伪造了
        SecretKey secretKey = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(adminId)
                .claim(WebConstant.AUTH_AUTHED_TOKEN, loginUser)
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, secretKey);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate);
        }
        return builder.compact();
    }


    /**
     * 解析
     *
     * @param jwt
     * @return
     */
    public static Claims parseJWT(String jwt) {

        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(WebConstant.Admin_JWT_SECERT))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }
}
