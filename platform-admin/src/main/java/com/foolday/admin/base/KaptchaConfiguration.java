package com.foolday.admin.base;


import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import static com.google.code.kaptcha.Constants.*;

/**
 * 配置获取验证码
 */
@Configuration
public class KaptchaConfiguration {
    /**
     * @return
     */
    @Bean(name = "captchaProducer")
    public DefaultKaptcha defaultKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty(Constants.KAPTCHA_BORDER, "yes");
        properties.setProperty(KAPTCHA_BORDER_COLOR, "105,179,90");
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_COLOR, "blue");
//        properties.setProperty(KAPTCHA_TEXTPRODUCER_IMPL, "com.google.code.kaptcha.text.impl.ChineseTextProducer");
        properties.setProperty(KAPTCHA_TEXTPRODUCER_IMPL, "com.google.code.kaptcha.text.impl.DefaultTextCreator");
        properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_STRING, "abcde2345678gfynmnpwxz");
        properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "6");
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_NAMES, "宋体,楷体,微软雅黑");
        properties.setProperty(KAPTCHA_NOISE_COLOR, "red");
        properties.setProperty(KAPTCHA_IMAGE_WIDTH, "125");
        properties.setProperty(KAPTCHA_IMAGE_HEIGHT, "45");
        properties.setProperty(KAPTCHA_SESSION_KEY, "CAPTCHA_CODE_KEY");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
