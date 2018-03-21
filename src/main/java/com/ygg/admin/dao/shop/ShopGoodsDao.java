package com.ygg.admin.dao.shop;

import java.util.List;
import java.util.Map;

public interface ShopGoodsDao
{
    /**
     * 根据条件查找商铺商品
     * @param para
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> findShopGoodsByPara(Map<String, Object> para);

    /**
     * 查找商铺商品总数
     * @param para
     * @return
     * @throws Exception
     */
    int countShopGoods(Map<String, Object> para);

    /**
     * 新增商铺商品
     * @param para
     * @return
     * @throws Exception
     */
    int saveShopGoods(Map<String, Object> para);

    /**
     * 修改商铺商品
     * @param para
     * @return
     * @throws Exception
     */
    int updateShopGoods(Map<String, Object> para);

    /**
     * 删除商铺商品
     * @param id：id
     * @return
     * @throws Exception
     */
    int deleteShopGoods(int id);
}
