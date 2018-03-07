package com.ygg.admin.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ygg.admin.entity.User;

/**
 * 管理员DAO
 * 
 * @author zhangyb
 *
 */
public interface AdminDao
{

    int insertPermission(List list);

    List<Map<String, Object>> findPermissionByPara(Map<String, Object> para);

    int insertRole(String role, String description, int departmentId);

    int updateRole(int id, String role, String description, int departmentId);

    int insertRelationRolePermission(List list);

    int deleteRelationRolePermissionByRoleId(int roleId);

    List<Integer> findPermissionIdByRoleId(int roleId);

    List<Map<String, Object>> findRoleByPara(Map<String, Object> para);

    Map<String, Object> findRoleById(int id)
        throws Exception;

    List<User> findUserByPara(Map<String, Object> para);

    int countUserByPara(Map<String, Object> para);

    Set<String> findRolesByUsername(String username);

    List<Integer> findAllRoleIdByUserId(int userId);

    User createUser(User user);

    int updateUser(Map<String, Object> para);

    int insertUserRole(Map<String, Object> para);

    int deleteAllRoleByUserId(int userId);

    Set<String> findPermissions(String username);

    int updatePermissionByPara(Map<String, Object> para);

    int addPermission(Map<String, Object> permission);

    List<Integer> findPermissionByRoleId(int roleId);
}
