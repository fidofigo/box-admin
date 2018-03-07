package com.ygg.admin.service;

import java.util.List;
import java.util.Map;

public interface SystemConfigService
{
    
    Map<String, Object> jsonWhiteIpInfo(Map<String, Object> para);
    
    Map<String, Object> jsonWhiteURLInfo(Map<String, Object> para);
    
    int saveOrUpdateWhiteIp(Map<String, Object> para);
    
    int saveOrUpdateWhiteURL(Map<String, Object> para);
    
    int updateWhiteURLStatus(Map<String, Object> para);
    
    int updateWhiteIpStatus(Map<String, Object> para);
    
    List<String> findAvailableIps();

    List<String> findAvailableURLs();
    
}
