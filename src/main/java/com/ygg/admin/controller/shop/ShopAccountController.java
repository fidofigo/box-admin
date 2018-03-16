package com.ygg.admin.controller.shop;

import com.alibaba.fastjson.JSON;
import com.ygg.admin.annotation.ControllerLog;
import com.ygg.admin.annotation.PermissionDesc;
import com.ygg.admin.service.shop.ShopAccountService;
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
@RequestMapping("/shopAccount")
public class ShopAccountController
{
    Logger log = Logger.getLogger(ShopAccountController.class);

    @Resource
    private ShopAccountService shopAccountService;

    /**
     * 商铺用户
     * @param request
     * @return
     */
    @RequestMapping("/list")
    @PermissionDesc("商铺用户页")
    public ModelAndView list(HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView();
        try {
            mv.setViewName("shop/shopAccount");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mv.setViewName("error/404");
        }
        return mv;
    }

    /**
     * 商铺用户数据
     * @param page
     * @param rows
     * @param email
     * @param isAvailable
     * @return
     */
    @RequestMapping(value = "/jsonShopAccount", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @PermissionDesc("商铺用户json")
    public String jsonShopAccount(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
        @RequestParam(value = "rows", required = false, defaultValue = "50") int rows,
        @RequestParam(value = "email", required = false, defaultValue = "") String email,
        @RequestParam(value = "isAvailable", required = false, defaultValue = "-1") int isAvailable)
    {
        try {
            Map<String, Object> para = new HashMap<>();
            if (page == 0) {
                page = 1;
            }
            para.put("start", rows * (page - 1));
            para.put("max", rows);
            if (!"".equals(email)) {
                para.put("email", "%" + email + "%");
            }
            if (isAvailable != -1) {
                para.put("isAvailable", isAvailable);
            }
            return shopAccountService.findShopAccountByPara(para);
        } catch (Exception e) {
            log.error("异步加载商铺用户出错：" + e.getMessage(), e);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("rows", new ArrayList<>());
            resultMap.put("total", 0);
            return JSON.toJSONString(resultMap);
        }
    }

    /**
     * 编辑商铺用户
     * @param id
     * @param email
     * @param pwd
     * @param mobileNumber
     * @param isAvailable
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateShopAccount", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ControllerLog(description = "编辑商铺用户")
    @PermissionDesc("编辑商铺用户")
    public String saveOrUpdateShopAccount(@RequestParam(value = "id", required = false, defaultValue = "0") int id,
        @RequestParam(value = "email", required = false, defaultValue = "") String email,
        @RequestParam(value = "pwd", required = false, defaultValue = "") String pwd,
        @RequestParam(value = "isAvailable", required = false, defaultValue = "1") int isAvailable,
        @RequestParam(value = "shopId", required = false, defaultValue = "0") int shopId,
        @RequestParam(value = "mobileNumber", required = false, defaultValue = "") String mobileNumber)
    {
        try {
            Map<String, Object> para = new HashMap<>();
            para.put("email", email);
            para.put("isAvailable", isAvailable);
            para.put("pwd", pwd);
            para.put("shopId", shopId);
            para.put("mobileNumber", mobileNumber);
            if (id == 0) {
                return shopAccountService.saveShopAccount(para);
            } else {
                para.put("id", id);
                return shopAccountService.updateShopAccount(para);
            }
        } catch (Exception e) {
            Map<String, Object> resultMap = new HashMap<>();
            log.error(e.getMessage(), e);
            resultMap.put("status", 0);
            resultMap.put("msg", "编辑商铺用户失败");
            return JSON.toJSONString(resultMap);
        }
    }

    /**
     * 删除商铺用户
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteShopAccount", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ControllerLog(description = "删除商铺用户")
    @PermissionDesc("删除商铺用户")
    public String deleteShopAccount(HttpServletRequest request, @RequestParam(value = "id", required = true) int id)
    {
        Map<String, Object> result = new HashMap<>();
        try {
            return shopAccountService.deleteShopAccount(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.put("status", 0);
            result.put("msg", "删除商铺用户失败");
        }
        return JSON.toJSONString(result);
    }

    /**
     * 更新商铺用户
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateShopAccount", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @PermissionDesc("更新商铺用户")
    public String updateShopAccount(@RequestParam Map<String, Object> param)
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            return shopAccountService.updateShopAccount(param);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultMap.put("status", 0);
            resultMap.put("msg", "更新商铺用户失败");
        }
        return JSON.toJSONString(resultMap);
    }
}
