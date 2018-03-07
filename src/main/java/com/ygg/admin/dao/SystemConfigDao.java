package com.ygg.admin.dao;

import java.util.List;
import java.util.Map;

public interface SystemConfigDao
{
    
    List<Map<String, Object>> findWhiteIpInfo(Map<String, Object> para);
    
    int countWhiteIpInfo(Map<String, Object> para);
    
    List<Map<String, Object>> findWhiteURLInfo(Map<String, Object> para);
    
    int countWhiteURLInfo(Map<String, Object> para);
    
    int addWhiteIp(Map<String, Object> para);
    
    int updateWhiteIp(Map<String, Object> para);
    
    int addWhiteURL(Map<String, Object> para);
    
    int updateWhiteURL(Map<String, Object> para);
    
    int updateWhiteURLStatus(Map<String, Object> para);
    
    int updateWhiteIpStatus(Map<String, Object> para);
    
    List<String> findAvailableIps();
    
    List<String> findAvailableURLs();
    
}
