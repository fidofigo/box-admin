package com.ygg.admin.dao.shop;

import java.util.List;
import java.util.Map;

public interface ShopInfoDao
{
    /**
     * 根据条件查找商铺信息
     * @param para
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> findShopInfoByPara(Map<String, Object> para);

    /**
     * 查找商铺信息总数
     * @param para
     * @return
     * @throws Exception
     */
    int countShopInfo(Map<String, Object> para);

    /**
     * 新增商铺信息
     * @param para
     * @return
     * @throws Exception
     */
    int saveShopInfo(Map<String, Object> para);

    /**
     * 修改商铺信息
     * @param para
     * @return
     * @throws Exception
     */
    int updateShopInfo(Map<String, Object> para);

    /**
     * 删除商铺信息
     * @param id：id
     * @return
     * @throws Exception
     */
    int deleteShopInfo(int id);
}
