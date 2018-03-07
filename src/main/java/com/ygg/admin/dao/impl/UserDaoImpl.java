package com.ygg.admin.dao.impl;

import com.ygg.admin.dao.UserDao;
import com.ygg.admin.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.admin.entity.Role;
import com.ygg.admin.entity.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl implements UserDao
{
    
    @Override
    public User createUser(User user)
    {
        int status = getSqlSessionAdmin().insert("UserMapper.createUser", user);
        if (status != 1)
        {
            return null;
        }
        return user;
    }
    
    @Override
    public int updateUser(Map<String, Object> para)
    {
        return getSqlSessionAdmin().update("UserMapper.updateUser", para);
    }
    
    @Override
    public int insertRole(Role para)
    {
        return getSqlSessionAdmin().insert("UserMapper.insertRole", para);
    }
    
    @Override
    public int deleteAllRoleByUserId(int userId)
    {
        return getSqlSessionAdmin().delete("UserMapper.deleteAllRoleByUserId", userId);
    }
    
    @Override
    public int updateRole(Role para)
    {
        return getSqlSessionAdmin().update("UserMapper.updateRole", para);
    }

    @Override
    public int insertPermission(Map<String, Object> para)
    {
        return getSqlSessionAdmin().insert("UserMapper.insertPermission", para);
    }

    @Override
    public int insertUserRole(Map<String, Object> para)
    {
        return getSqlSessionAdmin().insert("UserMapper.insertUserRole", para);
    }
    
    @Override
    public List<Integer> findAllRoleIdByUserId(int userId)
    {
        List<Integer> reList = getSqlSessionAdmin().selectList("UserMapper.findAllRoleIdByUserId", userId);
        return reList == null ? new ArrayList<>() : reList;
    }

    @Override
    public int insertRolePermission(Map<String, Object> para)
    {
        return getSqlSessionAdmin().insert("UserMapper.insertRolePermission", para);
    }

    @Override
    public int deleteAllPermissionByRole(int roleId)
    {
        return getSqlSessionAdmin().delete("UserMapper.deleteAllPermissionByRole", roleId);
    }

    @Override
    public Set<String> findRoles(String username)
    {
        List<String> reList = getSqlSessionAdmin().selectList("UserMapper.findRolesByUsername", username);
        reList = reList == null ? new ArrayList<>() : reList;
        Set<String> setResult = new HashSet<>();
        for (String role : reList)
        {
            setResult.add(role);
        }
        return setResult;
    }
    
    @Override
    public List<Map<String, Object>> findRolesForShow(Map<String, Object> para)
    {
        List<Map<String, Object>> reList = getSqlSessionAdmin().selectList("UserMapper.findRolesForShow", para);
        reList = reList == null ? new ArrayList<>() : reList;
        return reList;
    }
    
    @Override
    public int countRolesForShow()
    {
        return getSqlSessionAdmin().selectOne("UserMapper.countRolesForShow");
    }
    
    @Override
    public Set<String> findPermissions(String username)
    {
        List<String> reList = getSqlSessionAdmin().selectList("UserMapper.findPermissionByUsername", username);
        Set<String> setResult = new HashSet<>();
        for (String permission : reList)
        {
            setResult.add(permission);
        }
        return setResult;
    }
    
    @Override
    public User findUserByUsername(String username)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        map.put("start", 0);
        map.put("max", 1);
        List<User> users = findUserByPara(map);
        if (users.size() > 0)
        {
            return users.get(0);
        }
        return null;
    }
    
    @Override
    public List<User> findUserByPara(Map<String, Object> para)
    {
        return getSqlSessionAdmin().selectList("UserMapper.findUserByPara", para);
    }
    
    @Override
    public User findUserById(int id)
    {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        List<User> reList = findUserByPara(map);
        if (reList.size() < 1)
        {
            return null;
        }
        return reList.get(0);
    }
    
    @Override
    public int countUserByPara(Map<String, Object> para)
    {
        return getSqlSessionAdmin().selectOne("UserMapper.countUserByPara", para);
    }
    
    @Override
    public List<Map<String, Object>> findPermissionForAdd()
    {
        return getSqlSessionAdmin().selectList("UserMapper.findPermissionForAdd");
    }
    
    @Override
    public List<Integer> findPermissionByRoleId(int id)
    {
        return getSqlSessionAdmin().selectList("UserMapper.findPermissionByRoleId", id);
    }
    
    @Override
    public int batchUpdateLockStatus(Map<String, Object> para)
    {
        return getSqlSessionAdmin().update("UserMapper.batchUpdateLockStatus", para);
    }
    
    @Override
    public List<Map<String, Object>> findUserByIds(List<Object> ids)
    {
        return getSqlSessionAdmin().selectList("UserMapper.findUserByIds", ids);
    }
    
    @Override
    public List<User> findUserByRole(String role)
    {
        return getSqlSessionAdmin().selectList("UserMapper.findUserByRole", role);
    }
    
    @Override
    public List<Map<String, Object>> findAllUserCode()
    {
        return getSqlSessionAdmin().selectList("UserMapper.findAllUserCode");
    }

    @Override
    public List<Map<String, Object>> findAllUserCodeIgnoreLocked() {
        return getSqlSessionAdmin().selectList("UserMapper.findAllUserCodeIgnoreLocked");
    }
}
