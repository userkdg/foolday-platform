package com.foolday.admin.controller;

import com.foolday.common.constant.WebConstant;
import com.foolday.common.dto.FantResult;
import com.foolday.service.api.admin.LoginServiceApi;
import com.foolday.service.common.JwtUtils;
import com.foolday.serviceweb.dto.admin.base.LoginUser;
import com.foolday.serviceweb.dto.admin.login.LoginVo;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

@Api(value = "登陆控制器",tags = "登陆控制器")
@RequestMapping("/admin")
@RestController
public class LoginController {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource
    private LoginServiceApi loginServiceApi;

//    @Resource(name = RedisBeanNameApi.REDIS_TEMPLATE_S_S)
//    private RedisTemplate<String, String> redisTemplate;

    /**
     * simple login
     */
    @ApiOperation("登录")
    @ApiResponses(@ApiResponse(code = 200, message = "正常返回", response = FantResult.class))
    @PostMapping
    public FantResult<String> login(@RequestBody LoginVo loginVo, HttpServletRequest request) throws IOException {
        // 必须有获取验证码到session中
        if (request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY) == null || !Objects.equals(request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY), loginVo.getCaptcha())) // 验证码有效性
            return FantResult.fail("验证码输入错误");
        // 判断是否存在
        if (loginServiceApi.checkLoginAccount(loginVo)) {
//            AdminBaseDataUtils.setShopId2Redis(redisTemplate, LoginUserHolder.get().getUserId(), LoginUserHolder.get().getShopId());
            final LoginUser loginUser = loginServiceApi.getLoginUser(loginVo);
            final String jwtoken = JwtUtils.createJwt(loginUser.getUserId(), loginUser);
            return FantResult.ok("登录成功").addMoreData(WebConstant.AUTH_AUTHED_TOKEN, jwtoken);
        }
        return FantResult.fail("账号或密码错误，登录失败");
    }

    @ApiOperation("获取验证码")
    @ApiResponses(@ApiResponse(code = 200, message = "正常返回", response = Void.class))
    @GetMapping("/kaptcha")
    public void kaptcha(HttpServletResponse response, HttpServletRequest request) {
        response.setDateHeader("Expires", 0L);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        // create the text for the image
        String capText = captchaProducer.createText();
        //将验证码存到session
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
//        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        BufferedImage bi = captchaProducer.createImage(capText);
        try (ServletOutputStream out = response.getOutputStream()) {
            // write the data out
            ImageIO.write(bi, "jpg", out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
