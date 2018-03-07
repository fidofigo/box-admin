package com.ygg.admin.dao.impl;

import com.ygg.admin.dao.MenuDao;
import com.ygg.admin.dao.impl.mybatis.base.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("menuDao")
public class MenuDaoImpl extends BaseDaoImpl implements MenuDao
{
    
    @Override
    public int createMenu(Map<String, Object> para)
    {
        return getSqlSessionAdmin().insert("MenuMapper.createMenu", para);
    }
    
    @Override
    public List<Map<String, Object>> findAllMenuByPara(Map<String, Object> para)
    {
        return getSqlSessionAdmin().selectList("MenuMapper.findAllMenuByPara", para);
    }
    
    @Override
    public int updateMenu(Map<String, Object> para)
    {
        return getSqlSessionAdmin().update("MenuMapper.updateMenu", para);
    }
    
    @Override
    public int delete(int id)
    {
        return getSqlSessionAdmin().delete("MenuMapper.delete", id);
    }
    
}
