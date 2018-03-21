package com.ygg.admin.service.shop;

import java.util.Map;

public interface ShopInfoService
{
    /**
     * 根据条件查找商铺信息
     * @param para
     * @return
     * @throws Exception
     */
    String findShopInfoByPara(Map<String, Object> para);

    /**
     * 新增商铺信息详情
     * @param para
     * @return
     * @throws Exception
     */
    String saveShopInfo(Map<String, Object> para);

    /**
     * 删除商铺信息详情
     * @param id：id
     * @return
     * @throws Exception
     */
    String deleteShopInfo(int id);

    /**
     * 更新商铺信息
     * @param para
     * @return
     * @throws Exception
     */
    String updateShopInfo(Map<String, Object> para);
}
