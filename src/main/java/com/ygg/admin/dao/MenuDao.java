package com.ygg.admin.dao;

import java.util.List;
import java.util.Map;

public interface MenuDao
{
    
    /**
     * 创建菜单
     * 
     * @param para
     * @return
     * @throws Exception
     */
    int createMenu(Map<String, Object> para);
    
    /**
     * 查询菜单
     * 
     * @param para
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> findAllMenuByPara(Map<String, Object> para);
    
    /**
     * 更新菜单
     * 
     * @param para
     * @return
     * @throws Exception
     */
    int updateMenu(Map<String, Object> para);
    
    int delete(int id);
}
