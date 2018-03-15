package com.ygg.admin.service.goods.impl;

import com.alibaba.fastjson.JSON;
import com.ygg.admin.dao.goods.CategoryDao;
import com.ygg.admin.service.goods.CategoryService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService
{
    Logger logger = Logger.getLogger(CategoryServiceImpl.class);

    @Resource
    private CategoryDao categoryDao;

    /**
     * 根据条件查找类目
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public String findCategoryByPara(Map<String, Object> para)
    {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> rows = categoryDao.findCategoryByPara(para);
        resultMap.put("rows", rows);
        resultMap.put("total", categoryDao.countCategory(para));
        return JSON.toJSONString(resultMap);
    }

    /**
     * 新增类目
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public String saveCategory(Map<String, Object> para)
    {
        Map<String, Object> resultMap = new HashMap<>();
        if (categoryDao.saveCategory(para) > 0) {
            resultMap.put("status", 1);
            resultMap.put("msg", "新增成功");
        } else {
            resultMap.put("status", 0);
            resultMap.put("msg", "新增失败");
        }
        return JSON.toJSONString(resultMap);
    }

    /**
     * 删除类目
     * @param id：id
     * @return
     * @throws Exception
     */
    @Override
    public String deleteCategory(int id)
    {
        Map<String, Object> resultMap = new HashMap<>();
        if (categoryDao.deleteCategory(id) > 0) {
            resultMap.put("status", 1);
            resultMap.put("msg", "删除成功");
        } else {
            resultMap.put("status", 0);
            resultMap.put("msg", "删除失败");
        }
        return JSON.toJSONString(resultMap);
    }

    /**
     * 更新类目
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public String updateCategory(Map<String, Object> para)
    {
        Map<String, Object> resultMap = new HashMap<>();
        if (categoryDao.updateCategory(para) > 0) {
            resultMap.put("status", 1);
            resultMap.put("msg", "更新成功");
        } else {
            resultMap.put("status", 0);
            resultMap.put("msg", "更新失败");
        }
        return JSON.toJSONString(resultMap);
    }
}
