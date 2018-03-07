package com.ygg.admin.service;

import com.ygg.admin.entity.ResultEntity;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 后台管理用户 serviece 改变版本
 */
public interface AdminService
{
    /**
     * 新增权限
     * @return
     * @throws Exception
     */
    int insertPermission(List<Map<String, String>> permissions);

    /**
     * 查询所有权限
     * @param para
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> findAllPermission(Map<String, Object> para, int roleId);

    /**
     * 新增 or 修改 角色
     */
    Map<String, Object> insertRole(int id, String role, String desc, List<Integer> permissionIdList, int departmentId);

    /**
     *  获取角色信息
     * @param para
     * @return
     * @throws Exception
     */
    Map<String, Object> findAllRole(Map<String, Object> para);

    /**
     * 根据ID获取角色信息
     * @param id
     * @return
     * @throws Exception
     */
    Map<String, Object> findRoleById(int id)
        throws Exception;

    /**
     * 查询后台账号
     * @param para
     * @return
     * @throws Exception
     */
    Map<String, Object> findUser(Map<String, Object> para);

    /**
     * 更新用户
     * @param id
     * @param username
     * @param realname
     * @param mobileNumber
     * @param roleIdList
     * @return
     * @throws Exception
     */
    Map<String, Object> updateUser(int id, String username, String realname, String mobileNumber, List<Integer> roleIdList, int departmentId , String email);

    /**
     * 新增用户
     * @param username
     * @param realname
     * @param mobileNumber
     * @param password
     * @param roleIdList
     * @return
     * @throws Exception
     */
    Map<String, Object> createUser(String username, String realname, String mobileNumber, String password, List<Integer> roleIdList, int departmentId,String email)
        throws Exception;

    /**
     * 根据用户名查询权限
     * @param username
     * @return
     * @throws Exception
     */
    Set<String> findRoles(String username);

    /**
     * 根据用户名查找其权限
     *
     * @param username
     * @return
     * @throws Exception
     */
    Set<String> findPermissions(String username);

    /**
     * 刷新权限
     * @return
     * @throws Exception
     */
    String refreshPermission()
        throws Exception;
}
