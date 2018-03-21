package com.ygg.admin.controller.shop;

import com.alibaba.fastjson.JSON;
import com.ygg.admin.annotation.ControllerLog;
import com.ygg.admin.annotation.PermissionDesc;
import com.ygg.admin.service.shop.ShopGoodsService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/shopGoods")
public class ShopGoodsController
{
    Logger log = Logger.getLogger(ShopGoodsController.class);

    @Resource
    private ShopGoodsService shopGoodsService;

    /**
     * 商铺商品
     * @param request
     * @return
     */
    @RequestMapping("/list/{shopId}")
    @PermissionDesc("商铺商品页")
    public ModelAndView list(@PathVariable("shopId") int shopId, HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView();
        try {
            mv.setViewName("shop/shopGoods");
            mv.addObject("shopId", shopId + "");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mv.setViewName("error/404");
        }
        return mv;
    }

    /**
     * 商铺商品数据
     * @param page
     * @param rows
     * @param shopId
     * @param isAvailable
     * @return
     */
    @RequestMapping(value = "/jsonShopGoods", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @PermissionDesc("商铺商品json")
    public String jsonShopGoods(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
        @RequestParam(value = "rows", required = false, defaultValue = "50") int rows,
        @RequestParam(value = "shopId", required = true) int shopId,
        @RequestParam(value = "isAvailable", required = false, defaultValue = "-1") int isAvailable)
    {
        try {
            Map<String, Object> para = new HashMap<>();
            if (page == 0) {
                page = 1;
            }
            para.put("start", rows * (page - 1));
            para.put("max", rows);
            para.put("shopId", shopId);
            if (isAvailable != -1) {
                para.put("isAvailable", isAvailable);
            }
            return shopGoodsService.findShopGoodsByPara(para);
        } catch (Exception e) {
            log.error("异步加载商铺商品出错：" + e.getMessage(), e);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("rows", new ArrayList<>());
            resultMap.put("total", 0);
            return JSON.toJSONString(resultMap);
        }
    }

    /**
     * 编辑商铺商品
     * @param id
     * @param goodsId
     * @param shopId
     * @param goodsBaseId
     * @param isAvailable
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateShopGoods", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ControllerLog(description = "编辑商铺商品")
    @PermissionDesc("编辑商铺商品")
    public String saveOrUpdateShopGoods(@RequestParam(value = "id", required = false, defaultValue = "0") int id,
        @RequestParam(value = "categoryId", required = false, defaultValue = "0") int categoryId,
        @RequestParam(value = "goodsId", required = false, defaultValue = "0") int goodsId,
        @RequestParam(value = "shopId", required = false, defaultValue = "0") int shopId,
        @RequestParam(value = "goodsBaseId", required = false, defaultValue = "") int goodsBaseId,
        @RequestParam(value = "stock", required = false, defaultValue = "") int stock,
        @RequestParam(value = "isAvailable", required = false, defaultValue = "1") int isAvailable)
    {
        try {
            Map<String, Object> para = new HashMap<>();
            para.put("categoryId", categoryId);
            para.put("isAvailable", isAvailable);
            para.put("goodsId", goodsId);
            para.put("goodsBaseId", goodsBaseId);
            para.put("shopId", shopId);
            para.put("stock", stock);
            if (id == 0) {
                return shopGoodsService.saveShopGoods(para);
            } else {
                para.put("id", id);
                return shopGoodsService.updateShopGoods(para);
            }
        } catch (Exception e) {
            Map<String, Object> resultMap = new HashMap<>();
            log.error(e.getMessage(), e);
            resultMap.put("status", 0);
            resultMap.put("msg", "编辑商铺商品失败");
            return JSON.toJSONString(resultMap);
        }
    }

    /**
     * 删除商铺商品
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteShopGoods", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ControllerLog(description = "删除商铺商品")
    @PermissionDesc("删除商铺商品")
    public String deleteShopGoods(HttpServletRequest request, @RequestParam(value = "id", required = true) int id)
    {
        Map<String, Object> result = new HashMap<>();
        try {
            return shopGoodsService.deleteShopGoods(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.put("status", 0);
            result.put("msg", "删除商铺商品失败");
        }
        return JSON.toJSONString(result);
    }

    /**
     * 更新商铺商品
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateShopGoods", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @PermissionDesc("更新商铺商品")
    public String updateShopGoods(@RequestParam Map<String, Object> param)
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            return shopGoodsService.updateShopGoods(param);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultMap.put("status", 0);
            resultMap.put("msg", "更新商铺商品失败");
        }
        return JSON.toJSONString(resultMap);
    }
}
