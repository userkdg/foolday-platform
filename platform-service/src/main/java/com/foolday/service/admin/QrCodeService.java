package com.foolday.service.admin;

import com.foolday.common.util.QrcodeUtils;
import com.foolday.common.util.UuidUtils;
import com.foolday.dao.qrcode.QrcodeEntity;
import com.foolday.dao.qrcode.QrcodeMapper;
import com.foolday.service.api.admin.QrCodeServiceApi;
import com.foolday.service.common.QrcodeConfigProperty;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class QrCodeService implements QrCodeServiceApi {

    @Resource
    private QrcodeMapper qrcodeMapper;

    @Resource
    private QrcodeConfigProperty qrcodeConfigProperty;

    /**
     * 生成二维码
     *
     * @param content 内容
     * @return id 二维码id
     */
    @Override
    public String createQrcodeImg(String content) {
        String id = "";
        // 生成二维码
        try {
            String imgName = "/"+UuidUtils.uuid32()+".png";
            Path path = new File(qrcodeConfigProperty.getStorePath()+imgName).toPath();
            BitMatrix matrix = QrcodeUtils.createBitMatrix(content);
            QrcodeUtils.writeToPath(matrix, path);

            // 写表
            id = UuidUtils.uuid32();
            QrcodeEntity qrcodeEntity = new QrcodeEntity();
            qrcodeEntity.setId(id);
            qrcodeEntity.setContent(content);
            qrcodeEntity.setPath(path.toString());
            qrcodeEntity.insert();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public List<String> batchCreateQrcodeImg(List<String> contents) {
        List<String> ids = new ArrayList<>();
        if(contents!=null){
            ids = contents.stream().map(content -> this.createQrcodeImg(content)).collect(Collectors.toList());
        }
        return ids;
    }
}
