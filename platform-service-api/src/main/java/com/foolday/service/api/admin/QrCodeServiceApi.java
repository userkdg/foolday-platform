package com.foolday.service.api.admin;

import java.util.List;

public interface QrCodeServiceApi {
    String createQrcodeImg(String content);

    List<String> batchCreateQrcodeImg(List<String> contents);
}
