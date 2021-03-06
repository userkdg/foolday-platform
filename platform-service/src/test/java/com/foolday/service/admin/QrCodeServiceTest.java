package com.foolday.service.admin;

import com.foolday.dao.qrcode.QrcodeMapper;
import com.foolday.service.api.admin.QrCodeServiceApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class QrCodeServiceTest {

    @Resource
    private QrCodeServiceApi qrCodeServiceApi;

    @Test
    public void createQrcodeImg() {
        String url = "http://www.baidu.com";
        qrCodeServiceApi.createQrcodeImg(url);
    }

    @Test
    public void batchCreateQrcodeImg() {
        List<String> list = Arrays.asList("http://www.alipay.com", "http://www.google.com");
        qrCodeServiceApi.batchCreateQrcodeImg(list);
    }
}