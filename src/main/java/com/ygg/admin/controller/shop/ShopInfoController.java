package com.ygg.admin.controller.shop;

import com.alibaba.fastjson.JSON;
import com.ygg.admin.annotation.ControllerLog;
import com.ygg.admin.annotation.PermissionDesc;
import com.ygg.admin.service.shop.ShopInfoService;
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
@RequestMapping("/shopInfo")
public class ShopInfoController
{
    Logger log = Logger.getLogger(ShopInfoController.class);

    @Resource
    private ShopInfoService shopInfoService;

    /**
     * 商铺信息
     * @param request
     * @return
     */
    @RequestMapping("/list")
    @PermissionDesc("商铺信息页")
    public ModelAndView list(HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView();
        try {
            mv.setViewName("shop/shopInfo");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mv.setViewName("error/404");
        }
        return mv;
    }

    /**
     * 商铺信息数据
     * @param page
     * @param rows
     * @param name
     * @param isAvailable
     * @return
     */
    @RequestMapping(value = "/jsonShopInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @PermissionDesc("商铺信息json")
    public String jsonShopInfo(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
        @RequestParam(value = "rows", required = false, defaultValue = "50") int rows,
        @RequestParam(value = "name", required = false, defaultValue = "") String name,
        @RequestParam(value = "isAvailable", required = false, defaultValue = "-1") int isAvailable)
    {
        try {
            Map<String, Object> para = new HashMap<>();
            if (page == 0) {
                page = 1;
            }
            para.put("start", rows * (page - 1));
            para.put("max", rows);
            if (!"".equals(name)) {
                para.put("name", "%" + name + "%");
            }
            if (isAvailable != -1) {
                para.put("isAvailable", isAvailable);
            }
            return shopInfoService.findShopInfoByPara(para);
        } catch (Exception e) {
            log.error("异步加载商铺信息出错：" + e.getMessage(), e);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("rows", new ArrayList<>());
            resultMap.put("total", 0);
            return JSON.toJSONString(resultMap);
        }
    }

    /**
     * 编辑商铺信息
     * @param id
     * @param detailAddress
     * @param code
     * @param mobileNumber
     * @param isAvailable
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateShopInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ControllerLog(description = "编辑商铺信息")
    @PermissionDesc("编辑商铺信息")
    public String saveOrUpdateShopInfo(@RequestParam(value = "id", required = false, defaultValue = "0") int id,
        @RequestParam(value = "name", required = false, defaultValue = "") String name,
        @RequestParam(value = "detailAddress", required = false, defaultValue = "") String detailAddress,
        @RequestParam(value = "code", required = false, defaultValue = "") String code,
        @RequestParam(value = "head", required = false, defaultValue = "") String head,
        @RequestParam(value = "isAvailable", required = false, defaultValue = "1") int isAvailable,
        @RequestParam(value = "mobileNumber", required = false, defaultValue = "") String mobileNumber)
    {
        try {
            Map<String, Object> para = new HashMap<>();
            para.put("name", name);
            para.put("isAvailable", isAvailable);
            para.put("detailAddress", detailAddress);
            para.put("code", code);
            para.put("head", head);
            para.put("mobileNumber", mobileNumber);
            if (id == 0) {
                return shopInfoService.saveShopInfo(para);
            } else {
                para.put("id", id);
                return shopInfoService.updateShopInfo(para);
            }
        } catch (Exception e) {
            Map<String, Object> resultMap = new HashMap<>();
            log.error(e.getMessage(), e);
            resultMap.put("status", 0);
            resultMap.put("msg", "编辑商铺信息失败");
            return JSON.toJSONString(resultMap);
        }
    }

    /**
     * 删除商铺信息
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteShopInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ControllerLog(description = "删除商铺信息")
    @PermissionDesc("删除商铺信息")
    public String deleteShopInfo(HttpServletRequest request, @RequestParam(value = "id", required = true) int id)
    {
        Map<String, Object> result = new HashMap<>();
        try {
            return shopInfoService.deleteShopInfo(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.put("status", 0);
            result.put("msg", "删除商铺信息失败");
        }
        return JSON.toJSONString(result);
    }

    /**
     * 更新商铺信息
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateShopInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @PermissionDesc("更新商铺信息")
    public String updateShopInfo(@RequestParam Map<String, Object> param)
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            return shopInfoService.updateShopInfo(param);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultMap.put("status", 0);
            resultMap.put("msg", "更新商铺信息失败");
        }
        return JSON.toJSONString(resultMap);
    }
}
