package com.ygg.admin.controller.goods;

import com.alibaba.fastjson.JSON;
import com.ygg.admin.annotation.ControllerLog;
import com.ygg.admin.annotation.PermissionDesc;
import com.ygg.admin.service.goods.GoodsService;
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
@RequestMapping("/goods")
public class GoodsController
{
    Logger log = Logger.getLogger(GoodsController.class);

    @Resource
    private GoodsService goodsService;

    /**
     * 商品
     * @param request
     * @return
     */
    @RequestMapping("/list")
    @PermissionDesc("商品页")
    public ModelAndView list(HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView();
        try {
            mv.setViewName("goods/goods");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mv.setViewName("error/404");
        }
        return mv;
    }

    /**
     * 商品数据
     * @param page
     * @param rows
     * @param name
     * @param isAvailable
     * @return
     */
    @RequestMapping(value = "/jsonGoods", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @PermissionDesc("商品json")
    public String jsonGoods(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
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
            return goodsService.findGoodsByPara(para);
        } catch (Exception e) {
            log.error("异步加载商品出错：" + e.getMessage(), e);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("rows", new ArrayList<>());
            resultMap.put("total", 0);
            return JSON.toJSONString(resultMap);
        }
    }

    /**
     * 编辑商品
     * @param id
     * @param name
     * @param goodsBaseId
     * @param country
     * @param isAvailable
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateGoods", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ControllerLog(description = "编辑商品")
    @PermissionDesc("编辑商品")
    public String saveOrUpdateGoods(@RequestParam(value = "id", required = false, defaultValue = "0") int id,
        @RequestParam(value = "name", required = false, defaultValue = "") String name,
        @RequestParam(value = "categoryId", required = false, defaultValue = "1") int categoryId,
        @RequestParam(value = "isAvailable", required = false, defaultValue = "1") int isAvailable,
        @RequestParam(value = "goodsBaseId", required = false, defaultValue = "0") String goodsBaseId,
        @RequestParam(value = "image1", required = false, defaultValue = "") String image1,
        @RequestParam(value = "image2", required = false, defaultValue = "") String image2,
        @RequestParam(value = "image3", required = false, defaultValue = "") String image3,
        @RequestParam(value = "image4", required = false, defaultValue = "") String image4,
        @RequestParam(value = "image5", required = false, defaultValue = "") String image5,
        @RequestParam(value = "desc", required = false, defaultValue = "") String desc,
        @RequestParam(value = "income", required = false, defaultValue = "") String income,
        @RequestParam(value = "limit", required = false, defaultValue = "") String limit,
        @RequestParam(value = "marketPrice", required = false, defaultValue = "") String marketPrice,
        @RequestParam(value = "salesPrice", required = false, defaultValue = "") String salesPrice)
    {
        try {
            Map<String, Object> para = new HashMap<>();
            para.put("name", name);
            para.put("isAvailable", isAvailable);
            para.put("categoryId", categoryId);
            para.put("image1", image1);
            para.put("image2", image2);
            para.put("image3", image3);
            para.put("image4", image4);
            para.put("image5", image5);
            para.put("goodsBaseId", goodsBaseId);
            para.put("desc", desc);
            para.put("income", income);
            para.put("limit", limit);
            para.put("marketPrice", marketPrice);
            para.put("salesPrice", salesPrice);
            if (id == 0) {
                return goodsService.saveGoods(para);
            } else {
                para.put("id", id);
                return goodsService.updateGoods(para);
            }
        } catch (Exception e) {
            Map<String, Object> resultMap = new HashMap<>();
            log.error(e.getMessage(), e);
            resultMap.put("status", 0);
            resultMap.put("msg", "编辑商品失败");
            return JSON.toJSONString(resultMap);
        }
    }

    /**
     * 删除商品
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteGoods", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ControllerLog(description = "删除商品")
    @PermissionDesc("删除商品")
    public String deleteGoods(HttpServletRequest request, @RequestParam(value = "id", required = true) int id)
    {
        Map<String, Object> result = new HashMap<>();
        try {
            return goodsService.deleteGoods(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.put("status", 0);
            result.put("msg", "删除商品失败");
        }
        return JSON.toJSONString(result);
    }

    /**
     * 更新商品
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateGoods", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @PermissionDesc("更新商品")
    public String updateGoods(@RequestParam Map<String, Object> param)
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            return goodsService.updateGoods(param);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultMap.put("status", 0);
            resultMap.put("msg", "更新商品失败");
        }
        return JSON.toJSONString(resultMap);
    }
}
