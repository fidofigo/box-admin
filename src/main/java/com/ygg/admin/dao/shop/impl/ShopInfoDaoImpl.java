package com.ygg.admin.dao.shop.impl;

import com.ygg.admin.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.admin.dao.shop.ShopInfoDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ShopInfoDaoImpl extends BaseDaoImpl implements ShopInfoDao
{
    /**
     * 根据条件查找商铺信息
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> findShopInfoByPara(Map<String, Object> para)
    {
        return getSqlSession().selectList("ShopInfoMapper.findAllShopInfo", para);
    }

    /**
     * 新增商铺信息
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public int saveShopInfo(Map<String, Object> para)
    {
        return getSqlSession().insert("ShopInfoMapper.saveShopInfo", para);
    }

    /**
     * 商铺信息总数
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public int countShopInfo(Map<String, Object> para)
    {
        return getSqlSession().selectOne("ShopInfoMapper.countShopInfo", para);
    }

    /**
     * 修改商铺信息
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public int updateShopInfo(Map<String, Object> para)
    {
        return getSqlSession().update("ShopInfoMapper.updateShopInfo", para);
    }

    /**
     * 删除商铺信息
     * @param id：id
     * @return
     * @throws Exception
     */
    @Override
    public int deleteShopInfo(int id)
    {
        return getSqlSession().delete("ShopInfoMapper.deleteShopInfo", id);
    }
}
