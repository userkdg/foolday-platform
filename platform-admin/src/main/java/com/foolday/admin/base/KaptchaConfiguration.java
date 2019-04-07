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
 * <p>
 * CVE-2018-18531 More information
 * moderate severity
 * Vulnerable versions: <= 2.3.2
 * Patched version: No fix
 * text/impl/DefaultTextCreator.java, text/impl/ChineseTextProducer.java, and text/impl/FiveLetterFirstNameTextCreator.java in kaptcha 2.3.2 use the Random (rather than SecureRandom) function for generating CAPTCHA values, which makes it easier for remote attackers to bypass intended access restrictions via a brute-force approach.
 * 针对github 检查到的Random为线程不安全，调整为自定义的
 * properties.setProperty(KAPTCHA_TEXTPRODUCER_IMPL, "com.foolday.common.util.KaptchaTextCreator");
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
        properties.setProperty(KAPTCHA_TEXTPRODUCER_IMPL, "com.foolday.common.util.KaptchaTextCreator");
        properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_STRING, "abcde2345678gfynmnpwxz");
        properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "6");
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_NAMES, "宋体,楷体,微软雅黑");
        properties.setProperty(KAPTCHA_NOISE_COLOR, "red");
        properties.setProperty(KAPTCHA_IMAGE_WIDTH, "132");
        properties.setProperty(KAPTCHA_IMAGE_HEIGHT, "45");
        properties.setProperty(KAPTCHA_SESSION_KEY, "CAPTCHA_CODE_KEY");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
