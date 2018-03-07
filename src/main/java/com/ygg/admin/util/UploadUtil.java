package com.ygg.admin.util;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ggj.platform.jupiter.client.ClusterClientIF;
import com.ggj.platform.jupiter.factory.FsFactory;

/**
 * Created by Administrator on 2017/4/28.
 */
public class UploadUtil
{

    private static final Logger logger = LoggerFactory.getLogger(UploadUtil.class);

    private static ClusterClientIF clusterClient = FsFactory.createClusterFsClient("config/jupiter.properties");

    public static ClusterClientIF getClusterClient() {
        return clusterClient;
    }

    /**
     * 获取图片具体信息
     */
    public static BufferedImage getBufferedImage(String imageUrl){
        BufferedImage bufferedImage = null;
        try
        {
            bufferedImage = ImageIO.read(new URL(imageUrl));
        }
        catch (Exception e)
        {
            logger.warn("----------获取图片BufferedImage出错：" + imageUrl);
        }
        return bufferedImage;
    }
}
