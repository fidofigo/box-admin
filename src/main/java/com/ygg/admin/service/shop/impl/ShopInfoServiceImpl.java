package com.ygg.admin.service.shop.impl;

import com.alibaba.fastjson.JSON;
import com.ygg.admin.dao.shop.ShopInfoDao;
import com.ygg.admin.service.shop.ShopInfoService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("shopInfoService")
public class ShopInfoServiceImpl implements ShopInfoService
{
    Logger logger = Logger.getLogger(ShopInfoServiceImpl.class);

    @Resource
    private ShopInfoDao shopInfoDao;

    /**
     * 根据条件查找商铺信息
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public String findShopInfoByPara(Map<String, Object> para)
    {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> rows = shopInfoDao.findShopInfoByPara(para);
        resultMap.put("rows", rows);
        resultMap.put("total", shopInfoDao.countShopInfo(para));
        return JSON.toJSONString(resultMap);
    }

    /**
     * 新增商铺信息
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public String saveShopInfo(Map<String, Object> para)
    {
        Map<String, Object> resultMap = new HashMap<>();
        if (shopInfoDao.saveShopInfo(para) > 0) {
            resultMap.put("status", 1);
            resultMap.put("msg", "新增成功");
        } else {
            resultMap.put("status", 0);
            resultMap.put("msg", "新增失败");
        }
        return JSON.toJSONString(resultMap);
    }

    /**
     * 删除商铺信息
     * @param id：id
     * @return
     * @throws Exception
     */
    @Override
    public String deleteShopInfo(int id)
    {
        Map<String, Object> resultMap = new HashMap<>();
        if (shopInfoDao.deleteShopInfo(id) > 0) {
            resultMap.put("status", 1);
            resultMap.put("msg", "删除成功");
        } else {
            resultMap.put("status", 0);
            resultMap.put("msg", "删除失败");
        }
        return JSON.toJSONString(resultMap);
    }

    /**
     * 更新商铺信息
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public String updateShopInfo(Map<String, Object> para)
    {
        Map<String, Object> resultMap = new HashMap<>();
        if (shopInfoDao.updateShopInfo(para) > 0) {
            resultMap.put("status", 1);
            resultMap.put("msg", "更新成功");
        } else {
            resultMap.put("status", 0);
            resultMap.put("msg", "更新失败");
        }
        return JSON.toJSONString(resultMap);
    }
}
