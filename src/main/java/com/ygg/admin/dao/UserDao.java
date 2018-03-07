package com.ygg.admin.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ygg.admin.entity.Role;
import com.ygg.admin.entity.User;

/**
 * 管理员DAO
 * 
 * @author zhangyb
 *
 */
public interface UserDao
{
    /**
     * 创建新的管理员
     * 
     * @param user
     * @return
     * @throws Exception
     */
    User createUser(User user);
    
    /**
     * 根据username查询user
     * 
     * @param username
     * @return
     * @throws Exception
     */
    User findUserByUsername(String username);

    List<User> findUserByPara(Map<String, Object> para);

    User findUserById(int id);
    
    int countUserByPara(Map<String, Object> para);
    
    /**
     * 修改管理员密码
     */
    int updateUser(Map<String, Object> para);
    
    /**
     * 插入角色信息
     * 
     * @param para
     * @return
     * @throws Exception
     */
    int insertRole(Role para);
    
    /**
     * 更新角色信息
     * 
     * @param para
     * @return
     * @throws Exception
     */
    int updateRole(Role para);

    /**
     * 插入权限信息
     * 
     * @param para
     * @return
     * @throws Exception
     */
    int insertPermission(Map<String, Object> para);

    /**
     * 插入用户角色关系
     * 
     * @param para
     * @return
     * @throws Exception
     */
    int insertUserRole(Map<String, Object> para);
    
    /**
     * 根据userId删除角色
     * 
     * @param userId
     * @return
     * @throws Exception
     */
    int deleteAllRoleByUserId(int userId);
    
    /**
     * 根据userId查询用户角色Id
     * 
     * @param userId
     * @return
     * @throws Exception
     */
    List<Integer> findAllRoleIdByUserId(int userId);

    /**
     * 插入角色权限关系
     * 
     * @param para
     * @return
     * @throws Exception
     */
    int insertRolePermission(Map<String, Object> para);
    
    /**
     * 根据roleId删除角色权限关系
     * 
     * @param roleId
     * @return
     * @throws Exception
     */
    int deleteAllPermissionByRole(int roleId);

    /**
     * 根据用户名查找其角色
     * 
     * @param username
     * @return
     * @throws Exception
     */
    Set<String> findRoles(String username);
    
    /**
     * 查询所有可用角色
     * 
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> findRolesForShow(Map<String, Object> para);
    
    int countRolesForShow();
    
    /**
     * 根据用户名查找其权限
     * 
     * @param username
     * @return
     * @throws Exception
     */
    Set<String> findPermissions(String username);
    
    /**
     * 查找系统所有权限
     * 
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> findPermissionForAdd();
    
    /**
     * 根据roleId 查询所有权限
     * 
     * @param id
     * @return
     * @throws Exception
     */
    List<Integer> findPermissionByRoleId(int id);
    
    int batchUpdateLockStatus(Map<String, Object> para);
    
    List<Map<String, Object>> findUserByIds(List<Object> ids);
    
    List<User> findUserByRole(String role);
    
    List<Map<String, Object>> findAllUserCode();

    List<Map<String,Object>> findAllUserCodeIgnoreLocked();
}
