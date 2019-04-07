package com.foolday.admin.controller;

import com.foolday.cloud.serviceweb.dto.admin.login.LoginVo;
import com.foolday.common.dto.FantResult;
import com.foolday.service.api.admin.LoginServiceApi;
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

@Api("登陆控制器")
@RequestMapping("/admin")
@RestController
public class LoginController {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource
    private LoginServiceApi loginServiceApi;

    /**
     *
     */
    @ApiOperation("登录")
    @ApiResponses(@ApiResponse(code = 200, message = "正常返回", response = FantResult.class))
    @PostMapping
    public FantResult<String> login(@RequestBody LoginVo loginVo, HttpServletRequest request) {
        System.out.println(loginVo);
        boolean checkLoginAccount = loginServiceApi.checkLoginAccount(loginVo);
        final String captcha = loginVo.getCaptcha();
        return checkLoginAccount && Objects.equals(request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY), captcha) ?
                FantResult.ok("登录成功") :
                FantResult.fail("账号或密码错误，登录失败");
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
