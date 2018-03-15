package com.ygg.admin.dao.goods.impl;

import com.ygg.admin.dao.goods.GoodsDao;
import com.ygg.admin.dao.impl.mybatis.base.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class GoodsDaoImpl extends BaseDaoImpl implements GoodsDao
{
    /**
     * 根据条件查找商品
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> findGoodsByPara(Map<String, Object> para)
    {
        return getSqlSession().selectList("GoodsMapper.findAllGoods", para);
    }

    /**
     * 新增商品
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public int saveGoods(Map<String, Object> para)
    {
        return getSqlSession().insert("GoodsMapper.saveGoods", para);
    }

    /**
     * 商品总数
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public int countGoods(Map<String, Object> para)
    {
        return getSqlSession().selectOne("GoodsMapper.countGoods", para);
    }

    /**
     * 修改商品
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public int updateGoods(Map<String, Object> para)
    {
        return getSqlSession().update("GoodsMapper.updateGoods", para);
    }

    /**
     * 删除商品
     * @param id：id
     * @return
     * @throws Exception
     */
    @Override
    public int deleteGoods(int id)
    {
        return getSqlSession().delete("GoodsMapper.deleteGoods", id);
    }
}
