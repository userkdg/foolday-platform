package com.foolday.common.util;

import com.foolday.common.exception.PlatformException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

/**
 * 图片工具
 */
public class ImageUtils {

    private static final Logger logger = LoggerFactory.getLogger(ImageUtils.class);

    /**
     * 判断文件是否图片文件，只能匹配bmp/gif/jpg/png文件
     *
     * @param file
     * @return
     */
    public static boolean isImage(File file) {
        try {
            Image image = ImageIO.read(file);
            return image != null;
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * 获取图片尺寸信息
     *
     * @param filePath a {@link String} object.
     * @return [width, height]
     */
    public static int[] getSizeInfo(String filePath) {
        File file = new File(filePath);
        return getSizeInfo(file);
    }


    /**
     * 获取图片尺寸信息
     *
     * @param file a {@link File} object.
     * @return [width, height]
     */
    public static int[] getSizeInfo(File file) {
        BufferedInputStream input = null;
        try {
            input = new BufferedInputStream(new FileInputStream(file));
            return getSizeInfo(input);
        } catch (FileNotFoundException e) {
            logger.error("文件不存在", e);
            throw new PlatformException("文件不存在");
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    /**
     * 获取图片尺寸
     *
     * @param input a {@link InputStream} object.
     * @return [width, height]
     */
    public static int[] getSizeInfo(InputStream input) {
        try {
            BufferedImage img = ImageIO.read(input);
            int w = img.getWidth(null);
            int h = img.getHeight(null);
            return new int[]{w, h};
        } catch (IOException e) {
            logger.error("图片文件损坏，无法读取图片信息", e);
            throw new PlatformException("图片文件损坏，无法读取图片信息");
        }
    }

    /**
     * 重调图片尺寸
     *
     * @param srcFilePath 原图路径
     * @param destFile    目标文件
     * @param width       新的宽度，小于1则忽略，按原图比例缩放
     * @param height      新的高度，小于1则忽略，按原图比例缩放
     */
    public static void resize(String srcFilePath, String destFile, int width, int height) throws Exception {
        resize(srcFilePath, destFile, width, height, -1, -1);
    }

    /**
     * 重调图片尺寸
     *
     * @param input  a {@link InputStream} object.
     * @param output a {@link OutputStream} object.
     * @param width  a int.
     * @param height a int.
     */
    public static void resize(InputStream input, OutputStream output, int width, int height) throws Exception {
        resize(input, output, width, height, -1, -1);
    }

    /**
     * 重调图片尺寸
     *
     * @param input     a {@link InputStream} object.
     * @param output    a {@link OutputStream} object.
     * @param width     a int.
     * @param height    a int.
     * @param maxWidth  a int.
     * @param maxHeight a int.
     */
    public static void resize(InputStream input, OutputStream output, int width, int height, int maxWidth,
                              int maxHeight) {

        if (width < 1 && height < 1 && maxWidth < 1 && maxHeight < 1) {
            try {
                IOUtils.copy(input, output);
            } catch (IOException e) {
                logger.error("无法生成缩略图", e);
                throw new PlatformException("无法生成缩略图");
            }
        }
        try {
            BufferedImage img = ImageIO.read(input);
            boolean hasNotAlpha = !img.getColorModel().hasAlpha();
            double w = img.getWidth(null);
            double h = img.getHeight(null);
            int toWidth;
            int toHeight;
            double rate = w / h;

            if (width > 0 && height > 0) {
                rate = ((double) width) / ((double) height);
                toWidth = width;
                toHeight = height;
            } else if (width > 0) {
                toWidth = width;
                toHeight = (int) (toWidth / rate);
            } else if (height > 0) {
                toHeight = height;
                toWidth = (int) (toHeight * rate);
            } else {
                toWidth = ((Number) w).intValue();
                toHeight = ((Number) h).intValue();
            }

            if (maxWidth > 0 && toWidth > maxWidth) {
                toWidth = maxWidth;
                toHeight = (int) (toWidth / rate);
            }
            if (maxHeight > 0 && toHeight > maxHeight) {
                toHeight = maxHeight;
                toWidth = (int) (toHeight * rate);
            }

            BufferedImage tag = new BufferedImage(toWidth, toHeight,
                    hasNotAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB);

            tag.getGraphics().drawImage(img.getScaledInstance(toWidth, toHeight, Image.SCALE_SMOOTH), 0, 0, null);
            ImageIO.write(tag, hasNotAlpha ? "jpg" : "png", output);
        } catch (IOException e) {
            logger.error("无法生成缩略图", e);
            throw new PlatformException("无法生成缩略图");
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
        }

    }

    /**
     * 重调图片尺寸
     *
     * @param srcFile   原图路径
     * @param destFile  目标文件
     * @param width     新的宽度，小于1则忽略，按原图比例缩放
     * @param height    新的高度，小于1则忽略，按原图比例缩放
     * @param maxWidth  最大宽度，限制目标图片宽度，小于1则忽略此设置
     * @param maxHeight 最大高度，限制目标图片高度，小于1则忽略此设置
     */
    public static void resize(String srcFile, String destFile, int width, int height, int maxWidth, int maxHeight) {
        resize(new File(srcFile), new File(destFile), width, height, maxWidth, maxHeight);
    }

    /**
     * 重调图片尺寸
     *
     * @param srcFile  原图路径
     * @param destFile 目标文件
     * @param width    新的宽度，小于1则忽略，按原图比例缩放
     * @param height   新的高度，小于1则忽略，按原图比例缩放
     */
    public static void resize(File srcFile, File destFile, int width, int height) {
        resize(srcFile, destFile, width, height, -1, -1);
    }

    /**
     * 重调图片尺寸
     *
     * @param srcFile   原图路径
     * @param destFile  目标文件
     * @param width     新的宽度，小于1则忽略，按原图比例缩放
     * @param height    新的高度，小于1则忽略，按原图比例缩放
     * @param maxWidth  最大宽度，限制目标图片宽度，小于1则忽略此设置
     * @param maxHeight 最大高度，限制目标图片高度，小于1则忽略此设置
     */
    public static void resize(File srcFile, File destFile, int width, int height, int maxWidth, int maxHeight) {
        if (destFile.exists()) {
            PlatformAssert.isTrue(destFile.delete(), "文件系统异常,请稍后重试");
        } else if (!destFile.getParentFile().exists()) {
            PlatformAssert.isTrue(destFile.getParentFile().mkdirs(), "文件系统异常,请稍后重试");
        }
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new BufferedInputStream(new FileInputStream(srcFile));
            output = new FileOutputStream(destFile);
            resize(input, output, width, height, maxWidth, maxHeight);
        } catch (FileNotFoundException e) {
            logger.error("找不到原图", e);
            throw new PlatformException("找不到原图");
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
        }
    }

    /**
     * 压缩图片,PNG图片按JPG处理
     *
     * @param input   a {@link InputStream} object.
     * @param output  a {@link OutputStream} object.
     * @param quality 图片质量0-1之间
     */
    public static void optimize(InputStream input, OutputStream output, float quality) {
        BufferedImage image;
        ImageOutputStream ios = null;
        ImageWriter writer = null;
        try {
            image = ImageIO.read(input);
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");

            if (!writers.hasNext())
                throw new IllegalStateException("No writers found");

            writer = (ImageWriter) writers.next();
            ios = ImageIO.createImageOutputStream(output);

            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();

            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);

            writer.write(null, new IIOImage(image, null, null), param);
        } catch (IOException e) {
            logger.error("无法生成缩略图", e);
            throw new PlatformException("无法生成缩略图");
        } finally {
            if (ios != null) {
                try {
                    ios.close();
                } catch (IOException e) {
                    logger.error("无法生成缩略图", e);
                    throw new PlatformException("无法生成缩略图");
                }
            }
            if (writer != null) {
                writer.dispose();
            }
        }
    }

    /**
     * 压缩图片
     *
     * @param source  a {@link String} object.
     * @param target  a {@link String} object.
     * @param quality a float.
     */
    public static void optimize(String source, String target, float quality) {
        File fromFile = new File(source);
        File toFile = new File(target);
        optimize(fromFile, toFile, quality);
    }

    /**
     * 压缩图片
     *
     * @param source  a {@link File} object.
     * @param target  a {@link File} object.
     * @param quality 图片质量0-1之间
     */
    public static void optimize(File source, File target, float quality) {
        if (target.exists()) {
            PlatformAssert.isTrue(target.delete(), "文件系统异常,请稍后重试");
        } else if (!target.getParentFile().exists()) {
            PlatformAssert.isTrue(target.getParentFile().mkdirs(), "文件系统异常,请稍后重试");
        }
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new BufferedInputStream(new FileInputStream(source));
            os = new BufferedOutputStream(new FileOutputStream(target));
            optimize(is, os, quality);
        } catch (FileNotFoundException e) {
            logger.error("找不到原图", e);
            throw new PlatformException("找不到原图");
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(os);
        }
    }

}
