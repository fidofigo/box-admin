package com.ygg.admin.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ygg.admin.dao.SystemConfigDao;
import com.ygg.admin.service.SystemConfigService;

@Service("systemConfigService")
public class SystemConfigServiceImpl implements SystemConfigService
{
    @Resource
    private SystemConfigDao systemConfigDao;
    
    @Override
    public Map<String, Object> jsonWhiteIpInfo(Map<String, Object> para)
    {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> infoList = systemConfigDao.findWhiteIpInfo(para);
        int total = 0;
        for (Map<String, Object> map : infoList)
        {
            map.put("isAvailable", ((int)map.get("isAvailableCode") == 1) ? "可用" : "停用");// 使用状态
            map.put("createTime", ((Timestamp)map.get("createTime")).toString());
            map.put("createUser", map.get("realname") == null ? map.get("username") : map.get("realname"));
        }
        total = systemConfigDao.countWhiteIpInfo(para);
        resultMap.put("rows", infoList);
        resultMap.put("total", total);
        return resultMap;
    }
    
    @Override
    public Map<String, Object> jsonWhiteURLInfo(Map<String, Object> para)
    {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> infoList = systemConfigDao.findWhiteURLInfo(para);
        int total = 0;
        for (Map<String, Object> map : infoList)
        {
            map.put("isAvailable", ((int)map.get("isAvailableCode") == 1) ? "可用" : "停用");// 使用状态
            map.put("createTime", ((Timestamp)map.get("createTime")).toString());
            map.put("createUser", map.get("realname") == null ? map.get("username") : map.get("realname"));
        }
        total = systemConfigDao.countWhiteURLInfo(para);
        resultMap.put("rows", infoList);
        resultMap.put("total", total);
        return resultMap;
    }
    
    @Override
    public int saveOrUpdateWhiteIp(Map<String, Object> para)
    {
        int id = (int)para.get("id");
        if (id == 0)
        {
            return systemConfigDao.addWhiteIp(para);
        }
        else
        {
            return systemConfigDao.updateWhiteIp(para);
        }
    }
    
    @Override
    public int saveOrUpdateWhiteURL(Map<String, Object> para)
    {
        int id = (int)para.get("id");
        if (id == 0)
        {
            return systemConfigDao.addWhiteURL(para);
        }
        else
        {
            return systemConfigDao.updateWhiteURL(para);
        }
    }
    
    @Override
    public int updateWhiteURLStatus(Map<String, Object> para)
    {
        return systemConfigDao.updateWhiteURLStatus(para);
    }
    
    @Override
    public int updateWhiteIpStatus(Map<String, Object> para)
    {
        return systemConfigDao.updateWhiteIpStatus(para);
    }
    
    @Override
    public List<String> findAvailableIps()
    {
        return systemConfigDao.findAvailableIps();
    }
    
    @Override
    public List<String> findAvailableURLs()
    {
        return systemConfigDao.findAvailableURLs();
    }
    
}
