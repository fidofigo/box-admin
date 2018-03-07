package com.ygg.admin.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ygg.admin.entity.User;

public interface UserService
{
    /**
     * 创建账户
     * 
     * @return
     * @throws Exception
     */
    Map<String, Object> createUser(String username, String realname, String mobileNumber, String password, List<Integer> roleIdList)
        throws Exception;
    
    Map<String, Object> updateUser(int id, String username, String realname, String mobileNumber, List<Integer> roleIdList);
    
    int updateUserLocked(int id, int lockedStatus);
    
    Map<String, Object> listUser(Map<String, Object> para);
    
    /**
     * 修改密码
     * 
     * @param userId
     * @param newPassword
     * @throws Exception
     */
    Map<String, Object> changePassword(int userId, String newPassword)
        throws Exception;
    
    /**
     * 创建 or 更新 角色
     * 
     * @param id
     * @param role
     * @param menuIdList
     * @param desc
     * @return
     * @throws Exception
     */
    Map<String, Object> createOrUpdateRole(int id, String role, List<Integer> permissionIdList, String desc);
    
    /**
     * 插入权限
     * 
     * @param para
     * @return
     * @throws Exception
     */
    int insertPermission(String permission, String description);
    
    /**
     * 添加用户-角色关系
     * 
     * @param userId
     * @param roleIds
     * @throws Exception
     */
    void correlationRoles(Long userId, Long... roleIds);
    
    /**
     * 移除用户-角色关系
     * 
     * @param userId
     * @param roleIds
     * @throws Exception
     */
    void uncorrelationRoles(Long userId, Long... roleIds);
    
    /**
     * 根据用户名查找用户
     * 
     * @param username
     * @return
     * @throws Exception
     */
    User findByUsername(String username);
    
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
    Map<String, Object> findRolesForShow();
    
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
    
    int updateUser(User user);
    
    int batchUpdateLockStatus(Map<String, Object> para);
    
    /**
     * 查找所有用户
     * @return
     * @throws Exception
     */
    List<User> findAllUser();
    
    List<Map<String, Object>> findUserByIds(List<Object> ids);
    
    /**
     * 根据角色查找用户
     * @param string
     * @return
     * @throws Exception
     */
    List<User> findUserByRole(String role);
    
    List<Map<String, Object>> findAllUserCode();
    
    User findUserById(int auditUserId);

    List<Map<String, Object>> findAllUserCodeIgnoreLocked();
}
