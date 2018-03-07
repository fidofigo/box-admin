package com.ygg.admin.util;

import org.joda.time.DateTime;

/**
 * 缓存表示命名规则：前缀（admin_prod_）_ClassName_methodName(多个可以加a、b、c字母或数字区分)_业务_对象id
 */
public class CacheConstant
{
    
    public static final int CACHE_TIME_SECOND_1 = 1;
    
    /**
     * 缓存一分钟
     */
    public static final int CACHE_TIME_MINUTE_1 = CACHE_TIME_SECOND_1 * 60;
    
    public static final int CACHE_TIME_HOUR_1 = CACHE_TIME_MINUTE_1 * 60;
    
    public static final int CACHE_TIME_DAY_1 = CACHE_TIME_HOUR_1 * 24;
    
    public static final int CACHE_TIME_WEEK_1 = CACHE_TIME_DAY_1 * 7;
    
    public static final int CACHE_TIME_MONTH_1 = CACHE_TIME_DAY_1 * 30;
    
    /**
     * 客服积分操作来源临时记录
     */
    public static final String ADMIN_ACCOUNTSERVICE_UPDATEACCOUNTINTEGRAL = "AccountServiceImpl_updateAccountIntegral_source";
    
    /**
     * 后台登陆用户锁定KEY
     */
    public static final String ADMIN_ADMINREALM_LOCKED_USER = "Adminrealm_doGetAuthenticationInfo_locked_user";
    
    /**
     * 后台登陆用户密码错缓存KEY
     */
    public static final String ADMIN_ADMINREALM_ERROR_PASSWORD_USER = "Adminrealm_doGetAuthenticationInfo_error_password_user";
    
    /**
     * 后台用户外网登陆缓存key
     */
    public static final String ADMIN_USER_LOGIN_FROM_OUTER = "LoginInterceptor_postHandle_user_login_from_outer";
    
    /**
     * 后台用户外网登陆短信验证缓存key
     */
    public static final String ADMIN_USER_LOGIN_FROM_OUTER_SMSCODE = "LoginInterceptor_postHandle_user_login_from_outer_smscode";
    
    /**
     * 缓存IP白名单
     */
    public static final String ADMIN_USER_LOGIN_WHITE_IP_LIST = "LoginInterceptor_preHandle_user_login_white_ip_list";
    
    /**
     * 缓存URL白名单
     */
    public static final String ADMIN_USER_LOGIN_WHITE_URL_LIST = "LoginInterceptor_preHandle_user_login_white_url_list";
    
    /**
     * 外网登陆发送短信次数缓存
     */
    public static final String ADMIN_USER_LOGIN_SEND_SMS_COUNT = "LoginInterceptor_preHandle_user_login_send_sms_count_" + DateTime.now().toString("yyyyMMdd") + "_";
    
    /**
     * 用户权限缓存版本
     */
    public static final String ADMIN_PLATFORM_USER_PERMISSION_VSERSION = "ShiroAdminServiceImpl_findPermissions_permissionSet_VERSION_";
    
    /**
     * 直播随机插入购物信息开关
     */
    public static final String YGG_LIVE_AUTO_BUY = "timer_cache_prod_live_auto_buy_";
    
    /**
     * 直播评论拼壁缓存
     */
    public static final String HQBSLIVE_COMMENT_SHIELD = "hqbsapp_live_shield_%s_%s";
    
    /**
     * 环球捕手直播随机插入购物信息开关
     */
    public static final String HQBS_LIVE_AUTO_BUY = "hqbs_timer_cache_prod_live_auto_buy_";
    
    /**
     * 环球捕手直播随机插入购物信息开关
     */
    public static final String HQBSAPP_LIVE_VIRTUAL_COUNT = "hqbsapp_live_virtual_count_";
    
    /**
     * 微信accessToken
     */
    public static final String HQBS_TOKEN_CACHE = "HQBS_TOKEN_CACHE";
    
    /**
     * 自营超卖预警刷新操作信息 { date:java.util.Date, isRefreshing:boolean, realUserName:String }
     */
    public static final String SELF_PRODUCT_REFRESH_INFO = "self_product_stock_refresh_status";
    
    /**
     * 捕手会员特惠
     */
    public static final String HQBS_MEMBERS_DISCOUNT_PRODUCT = "hqbs_members_discount_product1";
    
    /**
     * 捕手会员专享商品
     */
    public static final String HQBS_MEMBERS_EXCLUSIVE_PRODUCT = "hqbs_members_exclusive_product1";
    
    /**
     * 捕手推荐文章缓存
     */
    public static final String EDITOR_REC_ARTICLE = "editor_rec_article_";
    
    /**
     * 搜索跳转自定义缓存
     */
    public static final String HQBS_SEARCH_JUMP = "hqbs_search_jump";
    
    /**
     * 发现图片缓存
     */
    public static final String HQBS_EDIT_CHOICE_IMG = "hqbs_edit_choice_img_";
    
    /**
     * 发现关联商品缓存
     */
    public static final String HQBS_EDIT_CHOICE_DISPLAY = "hqbs_edit_choice_display_";
    
    /**
     * 发现话题缓存
     */
    public static final String HQBS_EDIT_CHOICE_TOPICE = "hqbs_edit_choice_topice_";
    
}
