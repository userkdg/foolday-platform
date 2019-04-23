package com.foolday.service.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "platform.qrcode")
public class QrcodeConfigProperty {
    private String storePath;
}
