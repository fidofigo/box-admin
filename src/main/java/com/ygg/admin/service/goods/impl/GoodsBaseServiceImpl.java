package com.ygg.admin.service.goods.impl;

import com.alibaba.fastjson.JSON;
import com.ygg.admin.dao.goods.GoodsBaseDao;
import com.ygg.admin.service.goods.GoodsBaseService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("goodsBaseService")
public class GoodsBaseServiceImpl implements GoodsBaseService
{
    Logger logger = Logger.getLogger(GoodsBaseServiceImpl.class);

    @Resource
    private GoodsBaseDao goodsBaseDao;

    /**
     * 根据条件查找基础商品
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public String findGoodsBaseByPara(Map<String, Object> para)
    {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> rows = goodsBaseDao.findGoodsBaseByPara(para);
        resultMap.put("rows", rows);
        resultMap.put("total", goodsBaseDao.countGoodsBase(para));
        return JSON.toJSONString(resultMap);
    }

    /**
     * 新增基础商品
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public String saveGoodsBase(Map<String, Object> para)
    {
        Map<String, Object> resultMap = new HashMap<>();
        if (goodsBaseDao.saveGoodsBase(para) > 0) {
            resultMap.put("status", 1);
            resultMap.put("msg", "新增成功");
        } else {
            resultMap.put("status", 0);
            resultMap.put("msg", "新增失败");
        }
        return JSON.toJSONString(resultMap);
    }

    /**
     * 删除基础商品
     * @param id：id
     * @return
     * @throws Exception
     */
    @Override
    public String deleteGoodsBase(int id)
    {
        Map<String, Object> resultMap = new HashMap<>();
        if (goodsBaseDao.deleteGoodsBase(id) > 0) {
            resultMap.put("status", 1);
            resultMap.put("msg", "删除成功");
        } else {
            resultMap.put("status", 0);
            resultMap.put("msg", "删除失败");
        }
        return JSON.toJSONString(resultMap);
    }

    /**
     * 更新基础商品
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public String updateGoodsBase(Map<String, Object> para)
    {
        Map<String, Object> resultMap = new HashMap<>();
        if (goodsBaseDao.updateGoodsBase(para) > 0) {
            resultMap.put("status", 1);
            resultMap.put("msg", "更新成功");
        } else {
            resultMap.put("status", 0);
            resultMap.put("msg", "更新失败");
        }
        return JSON.toJSONString(resultMap);
    }
}
