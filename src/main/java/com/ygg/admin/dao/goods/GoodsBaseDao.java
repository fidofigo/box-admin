package com.ygg.admin.dao.goods;

import java.util.List;
import java.util.Map;

public interface GoodsBaseDao
{
    /**
     * 根据条件查找基础商品
     * @param para
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> findGoodsBaseByPara(Map<String, Object> para);

    /**
     * 查找基础商品总数
     * @param para
     * @return
     * @throws Exception
     */
    int countGoodsBase(Map<String, Object> para);

    /**
     * 新增基础商品
     * @param para
     * @return
     * @throws Exception
     */
    int saveGoodsBase(Map<String, Object> para);

    /**
     * 修改基础商品
     * @param para
     * @return
     * @throws Exception
     */
    int updateGoodsBase(Map<String, Object> para);

    /**
     * 删除基础商品
     * @param id：id
     * @return
     * @throws Exception
     */
    int deleteGoodsBase(int id);
}
