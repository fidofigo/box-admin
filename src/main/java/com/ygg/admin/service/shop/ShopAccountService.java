package com.ygg.admin.service.shop;

import java.util.Map;

public interface ShopAccountService
{
    /**
     * 根据条件查找商铺用户
     * @param para
     * @return
     * @throws Exception
     */
    String findShopAccountByPara(Map<String, Object> para);

    /**
     * 新增商铺用户详情
     * @param para
     * @return
     * @throws Exception
     */
    String saveShopAccount(Map<String, Object> para);

    /**
     * 删除商铺用户详情
     * @param id：id
     * @return
     * @throws Exception
     */
    String deleteShopAccount(int id);

    /**
     * 更新商铺用户
     * @param para
     * @return
     * @throws Exception
     */
    String updateShopAccount(Map<String, Object> para);
}
