package com.ygg.admin.service;

import java.util.List;
import java.util.Map;

public interface MenuService
{
    /**
     * 查询菜单 pid=0
     * 
     * @param pid
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> listMenu(Integer pid);
    
    /**
     * 插入 OR 修改menu
     * 
     * @param para
     * @return
     * @throws Exception
     */
    int createOrUpdate(Map<String, Object> para);
    
    /**
     * 根据权限加载目录加载目录
     * 
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> loadTree(List<Integer> menuIdList, List<Integer> stateList)
        throws Exception;
    
    int delete(int id);

    /**
     * 根据用户名查询菜单信息
     * @param username
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> findMenuByUsername(String username, List<Integer> stateList)
        throws Exception;
}
