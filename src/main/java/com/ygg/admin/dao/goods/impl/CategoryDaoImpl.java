package com.ygg.admin.dao.goods.impl;

import com.ygg.admin.dao.goods.CategoryDao;
import com.ygg.admin.dao.impl.mybatis.base.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CategoryDaoImpl extends BaseDaoImpl implements CategoryDao
{
    /**
     * 根据条件查找类目
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> findCategoryByPara(Map<String, Object> para)
    {
        return getSqlSession().selectList("CategoryMapper.findAllCategory", para);
    }

    /**
     * 新增类目
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public int saveCategory(Map<String, Object> para)
    {
        return getSqlSession().insert("CategoryMapper.saveCategory", para);
    }

    /**
     * 类目总数
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public int countCategory(Map<String, Object> para)
    {
        return getSqlSession().selectOne("CategoryMapper.countCategory", para);
    }

    /**
     * 修改类目
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public int updateCategory(Map<String, Object> para)
    {
        return getSqlSession().update("CategoryMapper.updateCategory", para);
    }

    /**
     * 删除类目
     * @param id：id
     * @return
     * @throws Exception
     */
    @Override
    public int deleteCategory(int id)
    {
        return getSqlSession().delete("CategoryMapper.deleteCategory", id);
    }
}
