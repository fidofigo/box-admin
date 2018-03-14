package com.ygg.admin.dao.goods;

import java.util.List;
import java.util.Map;

public interface GoodsDao
{
    /**
     * 根据条件查找商品
     * @param para
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> findGoodsByPara(Map<String, Object> para);

    /**
     * 查找商品总数
     * @param para
     * @return
     * @throws Exception
     */
    int countGoods(Map<String, Object> para);

    /**
     * 新增商品
     * @param para
     * @return
     * @throws Exception
     */
    int saveGoods(Map<String, Object> para);

    /**
     * 修改商品
     * @param para
     * @return
     * @throws Exception
     */
    int updateGoods(Map<String, Object> para);

    /**
     * 删除商品
     * @param id：id
     * @return
     * @throws Exception
     */
    int deleteGoods(int id);
}
