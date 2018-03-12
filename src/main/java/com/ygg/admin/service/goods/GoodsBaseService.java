package com.ygg.admin.service.goods;

import java.util.Map;

public interface GoodsBaseService
{
    /**
     * 根据条件查找基础商品
     * @param para
     * @return
     * @throws Exception
     */
    String findGoodsBaseByPara(Map<String, Object> para);

    /**
     * 新增基础商品详情
     * @param para
     * @return
     * @throws Exception
     */
    String saveGoodsBase(Map<String, Object> para);

    /**
     * 删除基础商品详情
     * @param id：id
     * @return
     * @throws Exception
     */
    String deleteGoodsBase(int id);

    /**
     * 更新基础商品
     * @param para
     * @return
     * @throws Exception
     */
    String updateGoodsBase(Map<String, Object> para);
}
