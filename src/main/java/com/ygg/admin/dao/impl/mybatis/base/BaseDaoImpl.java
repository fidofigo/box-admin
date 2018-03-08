package com.ygg.admin.dao.impl.mybatis.base;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;

public class BaseDaoImpl
{
    @Resource
    private SqlSessionTemplate sqlSessionTemplateAdmin;

    protected SqlSessionTemplate getSqlSessionAdmin()
    {
        return sqlSessionTemplateAdmin;
    }
}
