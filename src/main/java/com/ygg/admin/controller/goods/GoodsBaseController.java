package com.ygg.admin.controller.goods;

import com.alibaba.fastjson.JSON;
import com.ygg.admin.annotation.ControllerLog;
import com.ygg.admin.annotation.PermissionDesc;
import com.ygg.admin.service.goods.GoodsBaseService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/goodsBase")
public class GoodsBaseController
{
    Logger log = Logger.getLogger(GoodsBaseController.class);

    @Resource
    private GoodsBaseService goodsBaseService;

    /**
     * 基础商品
     * @param request
     * @return
     */
    @RequestMapping("/list")
    @PermissionDesc("基础商品页")
    public ModelAndView list(HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView();
        try {
            mv.setViewName("goods/goodsBase");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mv.setViewName("error/404");
        }
        return mv;
    }

    /**
     * 基础商品数据
     * @param page
     * @param rows
     * @param categoryId
     * @param isAvailable
     * @return
     */
    @RequestMapping(value = "/jsonGoodsBase", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @PermissionDesc("基础商品json")
    public String jsonGoodsBase(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
        @RequestParam(value = "rows", required = false, defaultValue = "50") int rows,
        @RequestParam(value = "categoryId", required = false, defaultValue = "-1") int categoryId,
        @RequestParam(value = "isAvailable", required = false, defaultValue = "-1") int isAvailable)
    {
        try {
            Map<String, Object> para = new HashMap<>();
            if (page == 0) {
                page = 1;
            }
            para.put("start", rows * (page - 1));
            para.put("max", rows);
            if (categoryId != -1) {
                para.put("categoryId", categoryId);
            }
            if (isAvailable != -1) {
                para.put("isAvailable", isAvailable);
            }
            return goodsBaseService.findGoodsBaseByPara(para);
        } catch (Exception e) {
            log.error("异步加载基础商品出错：" + e.getMessage(), e);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("rows", new ArrayList<>());
            resultMap.put("total", 0);
            return JSON.toJSONString(resultMap);
        }
    }

    /**
     * 编辑基础商品
     * @param id
     * @param name
     * @param brand
     * @param country
     * @param isAvailable
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateGoodsBase", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ControllerLog(description = "编辑基础商品")
    @PermissionDesc("编辑基础商品")
    public String saveOrUpdateGoodsBase(@RequestParam(value = "id", required = false, defaultValue = "0") int id,
        @RequestParam(value = "name", required = false, defaultValue = "") String name,
        @RequestParam(value = "categoryId", required = false, defaultValue = "1") int categoryId,
        @RequestParam(value = "isAvailable", required = false, defaultValue = "1") int isAvailable,
        @RequestParam(value = "brand", required = false, defaultValue = "0") String brand,
        @RequestParam(value = "country", required = false, defaultValue = "") String country)
    {
        try {
            Map<String, Object> para = new HashMap<>();
            para.put("name", name);
            para.put("isAvailable", isAvailable);
            para.put("categoryId", categoryId);
            para.put("name", name);
            para.put("brand", brand);
            para.put("country", country);
            if (id == 0) {
                return goodsBaseService.saveGoodsBase(para);
            } else {
                para.put("id", id);
                return goodsBaseService.updateGoodsBase(para);
            }
        } catch (Exception e) {
            Map<String, Object> resultMap = new HashMap<>();
            log.error(e.getMessage(), e);
            resultMap.put("status", 0);
            resultMap.put("msg", "编辑基础商品失败");
            return JSON.toJSONString(resultMap);
        }
    }

    /**
     * 删除基础商品
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteGoodsBase", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ControllerLog(description = "删除基础商品")
    @PermissionDesc("删除基础商品")
    public String deleteGoodsBase(HttpServletRequest request, @RequestParam(value = "id", required = true) int id)
    {
        Map<String, Object> result = new HashMap<>();
        try {
            return goodsBaseService.deleteGoodsBase(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.put("status", 0);
            result.put("msg", "删除基础商品失败");
        }
        return JSON.toJSONString(result);
    }

    /**
     * 更新基础商品
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateGoodsBase", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @PermissionDesc("更新基础商品")
    public String updateGoodsBase(@RequestParam Map<String, Object> param)
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            return goodsBaseService.updateGoodsBase(param);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultMap.put("status", 0);
            resultMap.put("msg", "更新基础商品失败");
        }
        return JSON.toJSONString(resultMap);
    }
}
