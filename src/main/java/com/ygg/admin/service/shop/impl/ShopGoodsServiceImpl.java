package com.ygg.admin.service.shop.impl;

import com.alibaba.fastjson.JSON;
import com.ygg.admin.dao.shop.ShopGoodsDao;
import com.ygg.admin.service.shop.ShopGoodsService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("shopGoodsService")
public class ShopGoodsServiceImpl implements ShopGoodsService
{
    Logger logger = Logger.getLogger(ShopGoodsServiceImpl.class);

    @Resource
    private ShopGoodsDao shopGoodsDao;

    /**
     * 根据条件查找商铺商品
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public String findShopGoodsByPara(Map<String, Object> para)
    {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> rows = shopGoodsDao.findShopGoodsByPara(para);
        resultMap.put("rows", rows);
        resultMap.put("total", shopGoodsDao.countShopGoods(para));
        return JSON.toJSONString(resultMap);
    }

    /**
     * 新增商铺商品
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public String saveShopGoods(Map<String, Object> para)
    {
        Map<String, Object> resultMap = new HashMap<>();
        if (shopGoodsDao.saveShopGoods(para) > 0) {
            resultMap.put("status", 1);
            resultMap.put("msg", "新增成功");
        } else {
            resultMap.put("status", 0);
            resultMap.put("msg", "新增失败");
        }
        return JSON.toJSONString(resultMap);
    }

    /**
     * 删除商铺商品
     * @param id：id
     * @return
     * @throws Exception
     */
    @Override
    public String deleteShopGoods(int id)
    {
        Map<String, Object> resultMap = new HashMap<>();
        if (shopGoodsDao.deleteShopGoods(id) > 0) {
            resultMap.put("status", 1);
            resultMap.put("msg", "删除成功");
        } else {
            resultMap.put("status", 0);
            resultMap.put("msg", "删除失败");
        }
        return JSON.toJSONString(resultMap);
    }

    /**
     * 更新商铺商品
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public String updateShopGoods(Map<String, Object> para)
    {
        Map<String, Object> resultMap = new HashMap<>();
        if (shopGoodsDao.updateShopGoods(para) > 0) {
            resultMap.put("status", 1);
            resultMap.put("msg", "更新成功");
        } else {
            resultMap.put("status", 0);
            resultMap.put("msg", "更新失败");
        }
        return JSON.toJSONString(resultMap);
    }
}
