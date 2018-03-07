package com.ygg.admin.util;

import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class CommonConstant
{
    /** 平台使用字符编码 */
    public static final String CHARACTER_ENCODING = "UTF-8";
    
    /**
     * 平台机器唯一标识
     */
    public static final String PLATFORM_IDENTITY_CODE = "1";
    
    /**
     * 每日晚场特卖上新事件，单位：二十四计时制
     */
    public static final int SALE_REFRESH_HOUR_NIGHT = 20;
    
    // 法定节假日 2016
    public static final List<Integer> holiday =
        Arrays.asList(20160609, 20160610, 20160611, 20160915, 20160916, 20160917, 20161001, 20161002, 20161003, 20161004, 20161005, 20161006, 20161007);
    
    public static final String CACHE_KEY_PLATFORM_TYPES = "ygg_admin_platform_types";
}
