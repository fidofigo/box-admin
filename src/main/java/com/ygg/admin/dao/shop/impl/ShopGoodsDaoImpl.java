package com.ygg.admin.dao.shop.impl;

import com.ygg.admin.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.admin.dao.shop.ShopGoodsDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ShopGoodsDaoImpl extends BaseDaoImpl implements ShopGoodsDao
{
    /**
     * 根据条件查找商铺商品
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> findShopGoodsByPara(Map<String, Object> para)
    {
        return getSqlSession().selectList("ShopGoodsMapper.findAllShopGoods", para);
    }

    /**
     * 新增商铺商品
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public int saveShopGoods(Map<String, Object> para)
    {
        return getSqlSession().insert("ShopGoodsMapper.saveShopGoods", para);
    }

    /**
     * 商铺商品总数
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public int countShopGoods(Map<String, Object> para)
    {
        return getSqlSession().selectOne("ShopGoodsMapper.countShopGoods", para);
    }

    /**
     * 修改商铺商品
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public int updateShopGoods(Map<String, Object> para)
    {
        return getSqlSession().update("ShopGoodsMapper.updateShopGoods", para);
    }

    /**
     * 删除商铺商品
     * @param id：id
     * @return
     * @throws Exception
     */
    @Override
    public int deleteShopGoods(int id)
    {
        return getSqlSession().delete("ShopGoodsMapper.deleteShopGoods", id);
    }
}
