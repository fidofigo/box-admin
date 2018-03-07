package com.ygg.admin.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

/**
 * 二维码生成
 * Created by LiuGJ on 2017/10/12.
 */
public class QRCodeUtil
{
    private static Logger logger = LoggerFactory.getLogger(QRCodeUtil.class);

    private static final int WIDTH = 300;

    private static final int HEIGHT = 300;

    private static final String CODE_FORMAT = "UTF-8";

    private static final String IMAGE_FORMAT = "png";

    private static Random random = new Random();

    /**
     * 获取二维码图片地址
     * @param text 二维码内容
     * @return 二维码图片地址
     */
    public static String getQRCodeUrl(String text)
    {
        Map<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, CODE_FORMAT);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 1);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] imageByteArray;
        String fileName = Long.toHexString(Long.valueOf(random.nextInt(10) + "" + System.currentTimeMillis() + "" + random.nextInt(10)));
        String filePath = "QRCode/" + fileName + "." + IMAGE_FORMAT;
        try
        {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);
            MatrixToImageWriter.writeToStream(bitMatrix, IMAGE_FORMAT, outputStream);
            imageByteArray = outputStream.toByteArray();
        }
        catch (WriterException | IOException e)
        {
            logger.error("二维码生成出错", e);
            return "";
        }
        finally
        {
            try
            {
                outputStream.close();
            }
            catch (IOException e)
            {
                logger.error("二维码图片输出流关闭异常", e);
            }
        }

        return UploadUtil.getClusterClient().upload(imageByteArray, filePath);
    }
}
