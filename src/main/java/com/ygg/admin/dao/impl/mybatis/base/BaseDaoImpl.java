package com.ygg.admin.dao.impl.mybatis.base;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;

public class BaseDaoImpl
{
    @Resource
    private SqlSessionTemplate sqlSessionTemplateAdmin;

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    protected SqlSessionTemplate getSqlSession()
    {
        return sqlSessionTemplate;
    }

    protected SqlSessionTemplate getSqlSessionAdmin()
    {
        return sqlSessionTemplateAdmin;
    }
}
