package com.ygg.admin.service.goods;

import java.util.Map;

public interface GoodsService
{
    /**
     * 根据条件查找商品
     * @param para
     * @return
     * @throws Exception
     */
    String findGoodsByPara(Map<String, Object> para);

    /**
     * 新增商品详情
     * @param para
     * @return
     * @throws Exception
     */
    String saveGoods(Map<String, Object> para);

    /**
     * 删除商品详情
     * @param id：id
     * @return
     * @throws Exception
     */
    String deleteGoods(int id);

    /**
     * 更新商品
     * @param para
     * @return
     * @throws Exception
     */
    String updateGoods(Map<String, Object> para);
}
