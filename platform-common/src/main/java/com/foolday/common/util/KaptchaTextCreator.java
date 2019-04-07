package com.foolday.common.util;


import com.google.code.kaptcha.text.TextProducer;
import com.google.code.kaptcha.util.Configurable;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 针对
 * @link com.google.code.kaptcha.text.impl.DefaultTextCreator 的Random方法为非线程安全
 * 调整为ThreadLocalRandom
 */
public class KaptchaTextCreator extends Configurable implements TextProducer {
    public KaptchaTextCreator() {
    }

    public String getText() {

        int length = this.getConfig().getTextProducerCharLength();
        char[] chars = this.getConfig().getTextProducerCharString();
        ThreadLocalRandom rand = ThreadLocalRandom.current();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            text.append(chars[rand.nextInt(chars.length)]);
        }
        return text.toString();
    }
}
