package com.ygg.admin.dao.goods;

import java.util.List;
import java.util.Map;

public interface CategoryDao
{
    /**
     * 根据条件查找类目
     * @param para
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> findCategoryByPara(Map<String, Object> para);

    /**
     * 查找类目总数
     * @param para
     * @return
     * @throws Exception
     */
    int countCategory(Map<String, Object> para);

    /**
     * 新增类目
     * @param para
     * @return
     * @throws Exception
     */
    int saveCategory(Map<String, Object> para);

    /**
     * 修改类目
     * @param para
     * @return
     * @throws Exception
     */
    int updateCategory(Map<String, Object> para);

    /**
     * 删除类目
     * @param id：id
     * @return
     * @throws Exception
     */
    int deleteCategory(int id);
}
