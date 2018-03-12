package com.ygg.admin.controller;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSSClient;
import com.google.common.base.Splitter;
import com.ygg.admin.annotation.ControllerLog;
import com.ygg.admin.annotation.PermissionDesc;
import com.ygg.admin.util.AliyunOSSClientUtil;
import com.ygg.admin.util.OSSClientConstants;
import com.ygg.admin.util.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.util.*;

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

            String originalFilename = file.getOriginalFilename();
            String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
            Random random = new Random();
            String name = random.nextInt(10000) + System.currentTimeMillis() + substring;

            result = toUP(file, OSSClientConstants.FOLDER + name);
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
    
    private Map<String, Object> toUP(MultipartFile file, String name)
    {
        Map<String, Object> result = new HashMap<>();

        OSSClient ossClient = AliyunOSSClientUtil.getOSSClient();

        String ossName = AliyunOSSClientUtil.uploadObject2OSS(ossClient, file, name);

        String[] split = name.split("/");
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        String imgUrl = ossClient.generatePresignedUrl(OSSClientConstants.BACKET_NAME, OSSClientConstants.FOLDER + split[split.length - 1], expiration).toString();
        String[] splitUrl = imgUrl.split("\\?");

        result.put("status", 1);
        result.put("url", splitUrl[0]);

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

                result = toUP(file, OSSClientConstants.FOLDER + fileName);
                resultMap.put("status", "success");
                result.put("status", 1);
                result.put("msg", "成功");
                result.put("originalName", oldName);

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
    
}
