package com.ygg.admin.dao.goods.impl;

import com.ygg.admin.dao.goods.GoodsBaseDao;
import com.ygg.admin.dao.impl.mybatis.base.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class GoodsBaseDaoImpl extends BaseDaoImpl implements GoodsBaseDao
{
    /**
     * 根据条件查找基础商品
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> findGoodsBaseByPara(Map<String, Object> para)
    {
        return getSqlSession().selectList("GoodsBaseMapper.findAllGoodsBase", para);
    }

    /**
     * 新增基础商品
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public int saveGoodsBase(Map<String, Object> para)
    {
        return getSqlSession().insert("GoodsBaseMapper.saveGoodsBase", para);
    }

    /**
     * 基础商品总数
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public int countGoodsBase(Map<String, Object> para)
    {
        return getSqlSession().selectOne("GoodsBaseMapper.countGoodsBase", para);
    }

    /**
     * 修改基础商品
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public int updateGoodsBase(Map<String, Object> para)
    {
        return getSqlSession().update("GoodsBaseMapper.updateGoodsBase", para);
    }

    /**
     * 删除基础商品
     * @param id：id
     * @return
     * @throws Exception
     */
    @Override
    public int deleteGoodsBase(int id)
    {
        return getSqlSession().delete("GoodsBaseMapper.deleteGoodsBase", id);
    }
}
