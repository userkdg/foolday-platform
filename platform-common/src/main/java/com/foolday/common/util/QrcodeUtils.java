package com.foolday.common.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * qrcode工具类
 */
@UtilityClass
public class QrcodeUtils {

    private int weight = 300;
    private int height = 300;
    private String format = "png";

    /**
     * 创建二维码对象
     *
     * @param content
     * @return
     * @throws WriterException
     */
    public BitMatrix createBitMatrix(String content) throws WriterException {
        Map hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);
        return new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, weight, height, hints);
    }

    /**
     * 将二维码图片写到磁盘
     *
     * @param bitMatrix
     * @param path
     * @throws IOException
     */
    public void writeToPath(BitMatrix bitMatrix, Path path) throws IOException {
        MatrixToImageWriter.writeToPath(bitMatrix, format, path);
    }

}
