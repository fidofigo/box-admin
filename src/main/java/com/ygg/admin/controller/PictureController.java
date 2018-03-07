package com.ygg.admin.controller;

import java.awt.image.BufferedImage;
import java.util.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.ggj.platform.jupiter.exception.FileClientException;
import com.google.common.base.Splitter;
import com.ygg.admin.annotation.ControllerLog;
import com.ygg.admin.annotation.PermissionDesc;
import com.ygg.admin.util.UploadUtil;

@Controller
@RequestMapping("/pic")
public class PictureController
{
    private Logger logger = Logger.getLogger(PictureController.class);
    
    private Random random = new Random();
    
    /**
     * 
     * @param file
     * @param request
     * @param limitSize：是否需要验证文件大小，1是，0否
     * @param width：图片宽(px)
     * @param height：图片高(px)
     * @return
     */
    @RequestMapping(value = "/fileUpLoad", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ControllerLog(description = "图片管理-上传图片")
    @PermissionDesc("上传图片")
    public String fileUpLoad(@RequestParam("picFile") MultipartFile file, HttpServletRequest request, //
        @RequestParam(value = "limitSize", required = false, defaultValue = "1") int limitSize, @RequestParam(value = "needWidth", required = false, defaultValue = "0") int width, // 限制宽度
        @RequestParam(value = "needHeight", required = false, defaultValue = "0") int height, // 限制高度
        @RequestParam(value = "heightList", required = false, defaultValue = "") String heightList, // 限制宽度2
        @RequestParam(value = "widthList", required = false, defaultValue = "") String widthList, // 限制高度2
        @RequestParam(value = "getSize", required = false, defaultValue = "0") int getSize, // 是否返回图片的宽高 0不返回，1返回
        @RequestParam(value = "gift", required = false, defaultValue = "0") int showHeightAndWidth, // 是否来源于新手礼包或者自定义导航的图片上传
                                                                                                    // 0不是，1是
        @RequestParam(value = "imageType", required = false, defaultValue = "0") int imageType // 是否校验图片类型 0不校验，1校验
    )
    {
        Map<String, Object> result = new HashMap<>();
        String prompt = "";
        try
        {
            if (file.isEmpty())
            {
                result.put("status", 0);
                result.put("msg", "请选择文件后上传");
                return JSON.toJSONString(result);
            }
            if (file.getSize() > 409600 && limitSize == 1)
            {
                result.put("status", 0);
                result.put("msg", "图片不得大于400kb");
                return JSON.toJSONString(result);
            }
            else if (file.getSize() >= 153600 && limitSize == 1)
            {
                prompt = "提示：图片大小超过150kb！";
            }
            List<Integer> heights = new ArrayList<>();
            if (StringUtils.isNotEmpty(heightList))
            {
                List<String> heightStrings = Splitter.on(",").splitToList(heightList);
                for (String s : heightStrings)
                {
                    heights.add(Integer.valueOf(s));
                }
            }
            List<Integer> widths = new ArrayList<>();
            if (StringUtils.isNotEmpty(widthList))
            {
                List<String> widthStrings = Splitter.on(",").splitToList(widthList);
                for (String s : widthStrings)
                {
                    widths.add(Integer.valueOf(s));
                }
            }
            int imageWidth = 0, imageHeight = 0;
            if (getSize == 1)
            {
                BufferedImage img = ImageIO.read(file.getInputStream());
                imageHeight = img.getHeight();
                imageWidth = img.getWidth();
            }
            // 限制图片宽高
            if (width > 0)
            {
                BufferedImage img = ImageIO.read(file.getInputStream());
                int currWidth = img.getWidth();
                if (currWidth != width)
                {
                    result.put("status", 0);
                    result.put("msg", "图片尺寸不符合要求，要求宽度：" + width + "，实际宽度：" + currWidth + "。");
                    return JSON.toJSONString(result);
                }
                
            }
            if (height > 0)
            {
                BufferedImage img = ImageIO.read(file.getInputStream());
                int currHeight = img.getHeight();
                if (currHeight != height)
                {
                    result.put("status", 0);
                    result.put("msg", "图片尺寸不符合要求，要求高度：" + height + "，实际高度：" + currHeight + "。");
                    return JSON.toJSONString(result);
                }
            }
            if (CollectionUtils.isNotEmpty(widths))
            {
                BufferedImage img = ImageIO.read(file.getInputStream());
                int currWidth = img.getWidth();
                if (!widths.contains(currWidth))
                {
                    result.put("status", 0);
                    result.put("msg", "图片尺寸不符合要求，要求宽度：" + widths + "，实际宽度：" + currWidth + "。");
                    return JSON.toJSONString(result);
                }
            }
            if (CollectionUtils.isNotEmpty(heights))
            {
                BufferedImage img = ImageIO.read(file.getInputStream());
                int currHeight = img.getHeight();
                if (!heights.contains(currHeight))
                {
                    result.put("status", 0);
                    result.put("msg", "图片尺寸不符合要求，要求高度：" + heights + "，实际高度：" + currHeight + "。");
                    return JSON.toJSONString(result);
                }
            }
            String directory = getDirectory(request);// 获取图片目录
            byte[] bt = file.getBytes();
            String fileName = file.getOriginalFilename();
            logger.debug("上传图片原fileName：" + fileName);
            
            String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (imageType == 1 && !"jpg".equals(prefix))
            {
                result.put("status", 0);
                result.put("msg", "图片格式不是jpg格式");
                return JSON.toJSONString(result);
            }
            
            int pointIndex = fileName.lastIndexOf(".");
            String fileExt = fileName.substring(pointIndex);
            String id = Long.toHexString(Long.valueOf(random.nextInt(10) + "" + System.currentTimeMillis() + "" + random.nextInt(10)));
            fileName = directory + id + fileExt.toLowerCase();
            logger.debug("上传图片新fileName：" + fileName);
            result = toUP(bt, fileName);
            result.put("width", imageWidth);
            result.put("height", imageHeight);
            result.put("prompt", prompt);
            if (showHeightAndWidth == 1)
            {
                BufferedImage img = ImageIO.read(file.getInputStream());
                int currHeight = img.getHeight();
                int currWidth = img.getWidth();
                result.put("currHeight", currHeight);
                result.put("currWidth", currWidth);
            }
        }
        catch (Exception e)
        {
            logger.error("上传图片失败", e);
            result.put("status", 0);
            result.put("msg", "文件上传失败");
        }
        return JSON.toJSONString(result);
    }
    
    private Map<String, Object> toUP(byte[] bt, String fileName)
    {
        Map<String, Object> result = new HashMap<>();
        try
        {
            String url = UploadUtil.getClusterClient().upload(bt, fileName);
            result.put("status", 1);
            result.put("url", url);
        }
        catch (FileClientException e)
        {
            logger.error("上传图片异常", e);
            result.put("status", 0);
            result.put("msg", "文件上传失败");
        }
        return result;
    }
    
    /**
     * 批量上传图片
     * 
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/batchFileUpLoad", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ControllerLog(description = "图片管理-批量上传图片")
    @PermissionDesc("批量上传图片")
    public String batchFileUpLoad(@RequestParam("Filedata") MultipartFile file, HttpServletRequest request)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        try
        {
            if (file.isEmpty())
            {
                result.put("status", 0);
                result.put("msg", "请选择文件后上传");
            }
            else if (file.getSize() > 409600)
            {
                result.put("status", 0);
                result.put("msg", "图片不得大于400kb");
            }
            else
            {
                byte[] bt = file.getBytes();
                String fileName = file.getOriginalFilename();
                String oldName = fileName;
                logger.info("上传图片原fileName：" + fileName);
                int pointIndex = fileName.lastIndexOf(".");
                String fileExt = fileName.substring(pointIndex);
                String id = Long.toHexString(Long.valueOf(random.nextInt(10) + "" + System.currentTimeMillis() + "" + random.nextInt(10)));
                fileName = id + fileExt.toLowerCase();
                logger.info("上传图片新fileName：" + fileName);
                
                Map<String, Object> resultMap = new HashMap<>();
                String url;
                try
                {
                    url = UploadUtil.getClusterClient().upload(bt, fileName);
                    resultMap.put("status", "success");
                    result.put("status", 1);
                    result.put("url", url);
                    result.put("msg", "成功");
                    result.put("originalName", oldName);
                }
                catch (FileClientException e)
                {
                    logger.error("文件上传失败", e);
                    resultMap.put("status", "fail");
                    result.put("status", 0);
                    result.put("msg", "失败");
                    result.put("originalName", oldName);
                }
                logger.info("上传图片返回状态：" + resultMap.get("status"));
            }
        }
        catch (Exception e)
        {
            logger.error("上传图片失败", e);
            result.put("status", 0);
            result.put("msg", "文件上传失败");
        }
        return JSON.toJSONString(result);
    }
    
    private String getDirectory(HttpServletRequest request)
    {
        String directoryName = "";
        String referer = request.getHeader("Referer");
        if (referer.contains("/banner/") || referer.contains("/banner/"))
        {
            directoryName = "activity/banner/";// 首页轮播图
        }
        else if (referer.contains("/indexSale/") || referer.contains("/indexSale/"))
        {
            directoryName = "activity/saleWindow/";// 首页特卖位
        }
        else if (referer.contains("/sale/") || referer.contains("/sale/"))
        {
            directoryName = "activity/activitiesCommon/";// 特卖专场
        }
        else if (referer.contains("/brand/") || referer.contains("/brand/"))
        {
            directoryName = "brand/";// 品牌
        }
        else if (referer.contains("/image/") || referer.contains("/image/"))
        {
            if (referer.contains("type=sale"))
            {
                directoryName = "gegeImage/activity/"; // 特卖格格说头像
            }
            else if (referer.contains("type=product"))
            {
                directoryName = "gegeImage/product/"; // 商品格格说头像
            }
        }
        else if (referer.contains("/refund/"))
        {
            directoryName = "orderrefund/applyImages/"; // 退款退货 图片
        }
        else if (referer.contains("/product") || referer.contains("/product") || referer.contains("/klProduct"))
        {
            directoryName = "product/"; // 商品资源图
        }
        else if (referer.contains("/saleTag/") || referer.contains("/saleTag/"))
        {
            directoryName = "saleTag/"; // 特卖标签
        }
        else if (referer.contains("/pic/toBatch"))
        {
            directoryName = "batch/"; // 批量图片上传
        }
        else if (referer.contains("/seller"))
        {
            directoryName = "seller/"; // 批量图片上传
        }
        return directoryName;
    }
    
}
