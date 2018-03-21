package com.ygg.admin.service.shop;

import java.util.Map;

public interface ShopGoodsService
{
    /**
     * 根据条件查找商铺商品
     * @param para
     * @return
     * @throws Exception
     */
    String findShopGoodsByPara(Map<String, Object> para);

    /**
     * 新增商铺商品详情
     * @param para
     * @return
     * @throws Exception
     */
    String saveShopGoods(Map<String, Object> para);

    /**
     * 删除商铺商品详情
     * @param id：id
     * @return
     * @throws Exception
     */
    String deleteShopGoods(int id);

    /**
     * 更新商铺商品
     * @param para
     * @return
     * @throws Exception
     */
    String updateShopGoods(Map<String, Object> para);
}
