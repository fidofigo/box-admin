package com.ygg.admin.service.goods.impl;

import com.alibaba.fastjson.JSON;
import com.ygg.admin.dao.goods.GoodsDao;
import com.ygg.admin.service.goods.GoodsService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService
{
    Logger logger = Logger.getLogger(GoodsServiceImpl.class);

    @Resource
    private GoodsDao goodsDao;

    /**
     * 根据条件查找商品
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public String findGoodsByPara(Map<String, Object> para)
    {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> rows = goodsDao.findGoodsByPara(para);
        resultMap.put("rows", rows);
        resultMap.put("total", goodsDao.countGoods(para));
        return JSON.toJSONString(resultMap);
    }

    /**
     * 新增商品
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public String saveGoods(Map<String, Object> para)
    {
        Map<String, Object> resultMap = new HashMap<>();
        if (goodsDao.saveGoods(para) > 0) {
            resultMap.put("status", 1);
            resultMap.put("msg", "新增成功");
        } else {
            resultMap.put("status", 0);
            resultMap.put("msg", "新增失败");
        }
        return JSON.toJSONString(resultMap);
    }

    /**
     * 删除商品
     * @param id：id
     * @return
     * @throws Exception
     */
    @Override
    public String deleteGoods(int id)
    {
        Map<String, Object> resultMap = new HashMap<>();
        if (goodsDao.deleteGoods(id) > 0) {
            resultMap.put("status", 1);
            resultMap.put("msg", "删除成功");
        } else {
            resultMap.put("status", 0);
            resultMap.put("msg", "删除失败");
        }
        return JSON.toJSONString(resultMap);
    }

    /**
     * 更新商品
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public String updateGoods(Map<String, Object> para)
    {
        Map<String, Object> resultMap = new HashMap<>();
        if (goodsDao.updateGoods(para) > 0) {
            resultMap.put("status", 1);
            resultMap.put("msg", "更新成功");
        } else {
            resultMap.put("status", 0);
            resultMap.put("msg", "更新失败");
        }
        return JSON.toJSONString(resultMap);
    }
}
