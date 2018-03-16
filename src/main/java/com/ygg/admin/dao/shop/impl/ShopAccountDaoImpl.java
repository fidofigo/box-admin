package com.ygg.admin.dao.shop.impl;

import com.ygg.admin.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.admin.dao.shop.ShopAccountDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ShopAccountDaoImpl extends BaseDaoImpl implements ShopAccountDao
{
    /**
     * 根据条件查找商铺用户
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> findShopAccountByPara(Map<String, Object> para)
    {
        return getSqlSession().selectList("ShopAccountMapper.findAllShopAccount", para);
    }

    /**
     * 新增商铺用户
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public int saveShopAccount(Map<String, Object> para)
    {
        return getSqlSession().insert("ShopAccountMapper.saveShopAccount", para);
    }

    /**
     * 商铺用户总数
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public int countShopAccount(Map<String, Object> para)
    {
        return getSqlSession().selectOne("ShopAccountMapper.countShopAccount", para);
    }

    /**
     * 修改商铺用户
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public int updateShopAccount(Map<String, Object> para)
    {
        return getSqlSession().update("ShopAccountMapper.updateShopAccount", para);
    }

    /**
     * 删除商铺用户
     * @param id：id
     * @return
     * @throws Exception
     */
    @Override
    public int deleteShopAccount(int id)
    {
        return getSqlSession().delete("ShopAccountMapper.deleteShopAccount", id);
    }
}
