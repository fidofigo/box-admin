package com.ygg.admin.service.goods;

import java.util.Map;

public interface CategoryService
{
    /**
     * 根据条件查找类目
     * @param para
     * @return
     * @throws Exception
     */
    String findCategoryByPara(Map<String, Object> para);

    /**
     * 新增类目详情
     * @param para
     * @return
     * @throws Exception
     */
    String saveCategory(Map<String, Object> para);

    /**
     * 删除类目详情
     * @param id：id
     * @return
     * @throws Exception
     */
    String deleteCategory(int id);

    /**
     * 更新类目
     * @param para
     * @return
     * @throws Exception
     */
    String updateCategory(Map<String, Object> para);
}
