package com.ygg.admin.dao.shop;

import java.util.List;
import java.util.Map;

public interface ShopAccountDao
{
    /**
     * 根据条件查找商铺用户
     * @param para
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> findShopAccountByPara(Map<String, Object> para);

    /**
     * 查找商铺用户总数
     * @param para
     * @return
     * @throws Exception
     */
    int countShopAccount(Map<String, Object> para);

    /**
     * 新增商铺用户
     * @param para
     * @return
     * @throws Exception
     */
    int saveShopAccount(Map<String, Object> para);

    /**
     * 修改商铺用户
     * @param para
     * @return
     * @throws Exception
     */
    int updateShopAccount(Map<String, Object> para);

    /**
     * 删除商铺用户
     * @param id：id
     * @return
     * @throws Exception
     */
    int deleteShopAccount(int id);
}
