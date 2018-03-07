package com.ygg.admin.service;

import org.apache.shiro.subject.Subject;

public interface ShiroService
{
    /**
     * 登陆操作
     * 
     * @param name
     * @param pwd
     * @return
     * @throws Exception
     */
    Subject login(String name, String pwd);
    
}
