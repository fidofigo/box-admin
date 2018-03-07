package com.ygg.admin.code;

import java.util.HashMap;
import java.util.Map;

public class CommonEnum
{

    /**
     * 通用是否
     */
    public enum COMMON_IS
    {
        NO("0"), // 否
        YES("1"); // 是

        private String value;

        COMMON_IS(String iValue)
        {
            this.value = iValue;
        }

        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 快递跟踪状态。
     *
     * 依据快递100API制定初版。 状态 4～7 需要与快递100签订增值服务才能开通。
     * 
     * @author Administrator
     * 
     */
    public static enum LogisticsStateEnum
    {
        
        /** 0 - 在途中 */
        ON_WAY("在途中"),
        
        /** 1 - 已揽收 */
        POSTED("已揽收"),
        
        /** 2 - 疑难 */
        IN_TROUBLE("问题件"),
        
        /** 3 - 已签收 */
        RECEIVED("已签收"),
        
        /** 4 - 退签 */
        REJECTED("退签"),
        
        /** 5 - 同城派送中 */
        CITY_WIDE_ON_WAY("同城派送中"),
        
        /** 6 - 退回 */
        RETURNED("退回"),
        
        /** 7 - 转单 */
        SEND_AGAIN("转单");
        
        /** 描述 */
        final String description;
        
        private LogisticsStateEnum(String description)
        {
            this.description = description;
        }
        
        public static String getDescription(int ordinal)
        {
            for (LogisticsStateEnum e : LogisticsStateEnum.values())
            {
                if (e.ordinal() == ordinal)
                {
                    return e.description;
                }
            }
            return "";
        }
    }
    
    /** 订单物流处理状态 **/
    public static enum LogisticOpStatus
    {
        NOT_DEAL(1, "未处理"), DEALING(2, "处理中"), DEALED(3, "已处理");
        
        int code;
        
        String desc;
        
        private LogisticOpStatus(int code, String desc)
        {
            this.code = code;
            this.desc = desc;
        }
        
        public static String getDescription(int code)
        {
            for (LogisticOpStatus e : LogisticOpStatus.values())
            {
                if (e.code == code)
                {
                    return e.desc;
                }
            }
            return "";
        }
    }
    
    public enum RefundStatusEnum
    {
        /** 0 - 没有用 ，只做占位符 */
        NORMAL("占位符"),
        /** 1 */
        APPLY("申请中"),
        /** 2 */
        WAIT_RETURN_OF_GOODS("待退货"),
        /** 3 */
        WAIT_RETURN_OF_MONEY("待退款"),
        /** 4 */
        SUCCESS("退款成功"),
        /** 5 */
        CLOSE("退款关闭"),
        /** 6 */
        CANCEL("退款取消"),
        
        RETREAT("财务打回");
        
        final String description;
        
        private RefundStatusEnum(String description)
        {
            this.description = description;
        }
        
        public String getDescription()
        {
            return description;
        }
        
        /**
         * 根据排序值查询描述
         * 
         * @param ordinal
         * @return
         */
        public static String getDescriptionByOrdinal(int ordinal)
        {
            for (RefundStatusEnum e : RefundStatusEnum.values())
            {
                if (ordinal == e.ordinal())
                {
                    return e.description;
                }
            }
            return "";
        }
    }
    
    /**
     * 商品渠道
     */
    public enum ProductChannelEnum
    {
        GGJ(1, "格格家"), HQBS(2, "环球捕手"), YW(3, "燕网"), THIRD(4, "第三方"), B2B(5, "B2B"), MBYD(6, "脉宝云店"), YWJS(7, "燕窝酵素"), BSDL(8, "捕手代理");
        
        private int code;
        
        private String desc;
        
        ProductChannelEnum(int code, String desc)
        {
            this.code = code;
            this.desc = desc;
        }
        
        public int getCode()
        {
            return code;
        }
        
        public String getDesc()
        {
            return desc;
        }

        public static String getDescByCode(int code)
        {
            for (ProductChannelEnum productChannelEnum : ProductChannelEnum.values())
            {
                if (code == productChannelEnum.getCode())
                    return productChannelEnum.getDesc();
            }
            return "";
        }
    }
    
    public static enum RefundReasonTypeEnum
    {
        /** 0 - 没有用 ，只做占位符 */
        RefundOnly((short)1, "仅退款"), RefundAndFoods((short)2, "退款并退货"),;
        private short code;
        
        final String description;
        
        private RefundReasonTypeEnum(short code, String description)
        {
            this.code = code;
            this.description = description;
        }
        
        public String getDescription()
        {
            return description;
        }
        
        public short getCode()
        {
            return code;
        }
        
        public void setCode(short code)
        {
            this.code = code;
        }
        
        private static Map<Short, String> codeMap = new HashMap<Short, String>();
        static
        {
            RefundReasonTypeEnum[] codes = RefundReasonTypeEnum.values();
            for (int i = 0; i < codes.length; i++)
            {
                codeMap.put(codes[i].getCode(), codes[i].getDescription());
            }
        }
        
        public static String getDesc(short code)
        {
            return codeMap.get(code);
        }
    }
    
    /**
     * app订单来源
     * 
     * @author Administrator
     *
     */
    public static enum OrderAppChannelEnum
    {
        
        /** 0 */
        IOS(0, "IOS"),
        
        /** 1 */
        ANDROID_OTHER(1, "Android-其他"),
        
        /** 2 */
        ANDROID_360(2, "Android-360"),
        
        /** 3 */
        ANDROID_MI(3, "Android-小米"),
        
        /** 4 */
        ANDROID_91(4, "Android-91"),
        
        /** 5 */
        ANDROID_BAIDU(5, "Android-百度"),
        
        /** 6 */
        ANDROID_WAN_DOU_JIA(6, "Android-豌豆荚"),
        
        /** 7 */
        ANDROID_YING_YONG_BAO(7, "Android-应用宝"),
        
        /** 8 */
        ANDROID_TAOBAO(8, "Android-淘宝"),
        
        /** 9 */
        ANDROID_VIVO(9, "Android-vivo"),
        
        /** 10 */
        ANDROID_INDEX(10, "Android-官网"),
        
        /** 11 */
        ANDROID_MOBILE(11, "移动网页"),
        
        /** 12 */
        ANDROID_SOUGOU(12, "Android-搜狗"),
        
        /** 13 */
        ANDROID_ANZHI(13, "Android-安智"),
        
        /** 14 */
        ANDROID_SUNINGYIGOU(14, "Android-苏宁易购"),
        
        /** 15 */
        ANDROID_HUAWEI(15, "Android-华为"),
        
        /** 16 */
        ANDROID_GUANGDIANTONG_YUANSHENG(16, "Android-广点通-原生"),
        
        /** 17 */
        ANDROID_GUANGDIANTONG_FEED(17, "Android-广点通-FEED流"),
        
        /** 18 */
        ANDROID_JINRITOUTIAO(18, "Android-今日头条"),
        
        /** 19 */
        IOS_VEST1(19, "历史副IOS已弃用"),
        
        /** 20 */
        IOS_VEST2(20, "IOS女神版"),
        
        /** 21武汉光点通 */
        ANDROID_GUANGDIANTONG_WUHANG(21, "Android-广点通-WUHANG"),
        
        /** 22微信拼团 */
        GEGETUAN_WECHAT(22, "格格团-微信"),
        
        /** 23魅族 */
        MEIZU(23, "Meizu-魅族"),
        
        /** 24 */
        GEGETUAN_APP(24, "格格团-APP"),
        
        /** 25 */
        IOS_slave(25, "iOS【1】"),
        
        /** 26 */
        GEGETUAN_APP_IOS(26, "格格团-APPIOS"),
        
        /** 27 */
        GEGETUAN_APP_ANDROID(27, "格格团-APP安卓"),
        
        /** 28 */
        QUAN_QIU_BU_SHOU(28, "环球捕手"),
        
        /** 29 */
        YAN_WANG(29, "燕网"),
        
        /** 30 */
        SAMSUNG_ANDROID(30, "三星安卓"),
        
        /** 31 */
        LENOVO_ANDROID(31, "联想安卓"),
        
        /** 32 */
        GiONEE_ANDROID(32, "金立安卓"),
        
        /** 33 */
        LETV_ANDROID(33, "乐视安卓"),
        
        /** 34 */
        AWL_ANDROID(34, "锤子安卓"),
        
        /** 35 */
        OPPO_ANDROID(35, "OPPO安卓"),
        
        /** 36 */
        JIFENG_ANDROID(36, "机锋安卓"),
        
        /** 37 */
        MUMAYI_ANDROID(37, "木蚂蚁安卓"),
        
        /** 38 */
        YINYONGHUI_ANDROID(38, "应用汇安卓"),
        
        /** 39 */
        YOUYI_ANDROID(39, "优亿市场安卓"),
        
        /** 40 */
        IOS3_ANDROID(40, "IOS专业版"),
        
        IOS_42(42, "格格家新主app"),
        
        /** 201 */
        IOS_GEGE_YOUXUAN(201, "格格优选"),
        
        /** 202 */
        IOS_ZUIMEI_CHIHUO(202, "最美吃货"),
        
        /** 203 */
        IOS_GEGE_HAIGOU(203, "格格海购"),
        
        /** 204 */
        IOS_MEISHI_TUANGOU(204, "美食团购"),
        
        /** 205 */
        IOS_MEISHI_CAIPU(205, "美食菜谱"),
        
        IOS_211(211, "机刷-元气"),
        
        IOS_212(212, "机刷-尼姬"),
        
        IOS_214(214, "机刷-赞梦"),
        
        IOS_215(215, "机刷-格蜜"),
        
        IOS_208(208, "诗意微博"),
        
        IOS_213(213, "美食严选"),
        
        /** b2b 订单渠道类型 */
        B2B_PILIANG(220, "PC-批量"),
        
        B2B_XIADAN(221, "PC-下单"),
        
        B2B_DS_H5(222, "纤衡-H5"),
        
        B2B_DS_JIN_HUO(223, "纤衡-进货单"),
        
        B2B_DS_FA_HUO(224, "纤衡-发货单"),
        
        ;
        
        final String description;
        
        int code;// 添加新编码
        
        OrderAppChannelEnum(int code, String description)
        {
            this.code = code;
            this.description = description;
        }
        
        public String getDescription()
        {
            return description;
        }
        
        public int getCode()
        {
            return code;
        }
        
        /**
         * 根据排序值查询描述
         *
         * @param code
         * @return
         */
        public static String getDescriptionByOrdinal(int code)
        {
            for (OrderAppChannelEnum e : OrderAppChannelEnum.values())
            {
                if (code == e.getCode())
                {
                    return e.description;
                }
            }
            return "";
        }
    }
    
    public enum QqbsOrderAppChannelEnum
    {
        
        IOS(0, "IOS"),
        
        ANDROID_INDEX(1, "Android-官网"), ANDROID_360(2, "Android-360"),
        
        ANDROID_YING_YONG_BAO(3, "Android-应用宝"), ANDROID_NINE(4, "Android-91"), ANDROID_BAIDU(5, "Android-百度"),
        
        ANDROID_WAN_DOU_JIA(6, "Android-豌豆荚"), ANDROID_XIAO_MI(7, "Android-小米"),
        
        ANDROID_TAOBAO(8, "Android-淘宝"),
        
        ANDROID_VIVO(9, "Android-vivo"),
        
        ANDROID_OTHER(10, "Android-其他"),
        
        ANDROID_SOUGOU(12, "Android-搜狗"),
        
        ANDROID_ANZHI(13, "Android-安智"),
        
        ANDROID_SUNINGYIGOU(14, "Android-苏宁易购"),
        
        ANDROID_HUAWEI(15, "Android-华为"),
        
        ANDROID_GUANGDIANTONG_YUANSHENG(16, "Android-广点通-原生"),
        
        ANDROID_GUANGDIANTONG_FEED(17, "Android-广点通-FEED流"),
        
        ANDROID_JINRITOUTIAO(18, "Android-今日头条"),
        
        ANDROID_GUANGDIANTONG_WUHANG(21, "Android-广点通-WUHANG"),
        
        MEIZU(23, "Meizu-魅族"),
        
        WEB(28, "网页"),
        
        SAMSUNG_ANDROID(30, "Android-三星"),
        
        LENOVO_ANDROID(31, "Android-联想"),
        
        GiONEE_ANDROID(32, "Android-金立"),
        
        LETV_ANDROID(33, "Android-乐视"),
        
        AWL_ANDROID(34, "Android-锤子"),
        
        OPPO_ANDROID(35, "Android-OPPO"),
        
        JIFENG_ANDROID(36, "Android-机锋"),
        
        MUMAYI_ANDROID(37, "Android-木蚂蚁"),
        
        YINYONGHUI_ANDROID(38, "Android-应用汇"),
        
        YOUYI_ANDROID(39, "Android-优亿市场"),
        
        IOS3_ANDROID(40, "Android-百度神马"),
        
        MA_JIA(41, "马甲1");
        
        private final String description;
        
        int code;// 添加新编码
        
        QqbsOrderAppChannelEnum(int code, String description)
        {
            this.code = code;
            this.description = description;
        }
        
        public String getDescription()
        {
            return description;
        }
        
        public int getCode()
        {
            return code;
        }
        
        /**
         * 根据排序值查询描述
         *
         * @param code
         * @return
         */
        public static String getDescriptionByOrdinal(int code)
        {
            for (QqbsOrderAppChannelEnum e : QqbsOrderAppChannelEnum.values())
            {
                
                if (code == e.getCode())
                {
                    return e.description;
                }
            }
            return "";
        }
    }


    public enum MbcsOrderAppChannelEnum
    {
        IOS(0, "IOS"),

        ANDROID_OTHER(1, "Android-其他"),
        ANDROID_360(2, "Android-360"),

        ANDROID_XIAOMI(3, "Android-小米"),
        ANDROID_BAIDU(5, "Android-百度"),

        ANDROID_YYB(7, "Android-yyb"),

        ANDROID_TAOBAO(8, "Android-淘宝"),

        ANDROID_VIVO(9, "Android-vivo"),

        ANDROID_GEGEJIA(10, "Android-gegejia"),

        ANDROID_SOUGOU(12, "Android-搜狗"),

        ANDROID_ANZHI(13, "Android-安智"),

        ANDROID_HUAWEI(15, "Android-华为"),

        ANDROID_MEIZU(23, "Meizu-魅族"),

        WEB(28, "网页"),

        SAMSUNG_ANDROID(30, "Android-三星"),

        LENOVO_ANDROID(31, "Android-联想"),

        LETV_ANDROID(33, "Android-乐视"),

        AWL_ANDROID(34, "Android-锤子"),

        OPPO_ANDROID(35, "Android-OPPO"),

        JIFENG_ANDROID(36,"Android-机锋"),

        MUMAYI_ANDROID(37, "Android-木蚂蚁"),

        YOUYI_ANDROID(39, "Android-优亿市场"),

        ANDROID_BAIDUSM(40, "Android-百度神马");

        private final String description;

        int code;// 添加新编码

        MbcsOrderAppChannelEnum(int code, String description)
        {
            this.code = code;
            this.description = description;
        }

        public String getDescription()
        {
            return description;
        }

        public int getCode()
        {
            return code;
        }

        /**
         * 根据排序值查询描述
         *
         * @param code
         * @return
         */
        public static String getDescriptionByOrdinal(int code)
        {
            for (QqbsOrderAppChannelEnum e : QqbsOrderAppChannelEnum.values())
            {

                if (code == e.getCode())
                {
                    return e.description;
                }
            }
            return "";
        }
    }


    public enum YanWangOrderAppChannelEnum
    {
        
        /**
         * 29
         */
        YAN_WANG(29, "网页"),;
        final String description;
        
        int code;// 添加新编码
        
        YanWangOrderAppChannelEnum(int code, String description)
        {
            this.code = code;
            this.description = description;
        }
        
        public String getDescription()
        {
            return description;
        }
        
        public int getCode()
        {
            return code;
        }
        
        /**
         * 根据排序值查询描述
         *
         * @param code
         * @return
         */
        public static String getDescriptionByOrdinal(int code)
        {
            for (YanWangOrderAppChannelEnum e : YanWangOrderAppChannelEnum.values())
            {
                
                if (code == e.getCode())
                {
                    return e.description;
                }
            }
            return "";
        }
    }
    
    /**
     * 短信类型 1：注册验证码；2：忘记密码；3：自定义短信；4：发货短信
     * 
     * @author zhangyb
     * 
     */
    public enum SmsBizEnum
    {
        
        /** 1 */
        REGISTER(1, "注册验证码"),
        
        /** 2 */
        FORGET_PWD(2, "忘记密码"),
        
        /** 3 */
        CUSTOM(3, "自定义短信"),
        
        /** 4 */
        SENDGOODS(4, "发货短信"),
        
        /** 5 */
        OVERSEASREMIND(5, "海外购节假日提醒短信"),
        
        /** 6 */
        SMSMONEYREMIND(6, "短信余额提醒"),
        
        /** 7 先报关后又物流的商家下单时发送短息 */
        BAOGUANFIRSTWULIULATER(7, "下单提醒短信"),
        
        /** 8 签收提醒短信 */
        RECEIVE_GOODS(8, "签收提醒短信"),
        
        /** 9 提醒商家发货短信 */
        REMIND_SELLER_SENDGOODS(9, "提醒商家发货短信"),
        
        /** 10 提醒商家回复订单问题 */
        REMIND_SELLER_ORDER_QUESTION(10, "提醒商家回复订单问题"),
        
        ORDER_CUSTOMS_ERROR(11, "清关异常提醒短信"),
        
        AGREE_REFUND_APPLY(12, "同意退款申请"),
        
        CANCEL_REFUND(13, "重置，取消退款"),
        
        HAVE_REFUNDED(14, "已打款"),
        
        SUBMIT_REFUND(15, "提交财务打款");
        
        final int code;
        
        final String desc;
        
        SmsBizEnum(int code, String desc)
        {
            this.code = code;
            this.desc = desc;
        }
        
        public int getCode()
        {
            return code;
        }
        
        public String getDesc()
        {
            return desc;
        }
        
        public static String getDescByCode(int code)
        {
            for (SmsBizEnum e : SmsBizEnum.values())
            {
                if (code == e.getCode())
                {
                    return e.desc;
                }
            }
            return "";
        }
    }
    
    public static enum BankTypeEnum
    {
        /** 0 - 只占茅坑不拉屎 */
        NORMAL("占位符"),
        
        /** 1 */
        BANK_ICBC("中国工商银行"),
        
        /** 2 */
        BANK_ABCHINA("中国农业银行"),
        
        /** 3 */
        BANK_CHINA("中国银行"),
        
        /** 4 */
        BANK_CCB("中国建设银行"),
        
        /** 5 */
        BANK_PSBC("中国邮政储蓄银行"),
        
        /** 6 */
        BANK_COMM("交通银行"),
        
        /** 7 */
        BANK_MERCHANTS("招商银行"),
        
        /** 8 */
        BANK_CEB("中国光大银行"),
        
        /** 9 */
        BANK_CITIC("中信银行"),
        
        /** 10 */
        ZHI_FU_BAO("支付宝");
        
        final String description;
        
        private BankTypeEnum(String description)
        {
            this.description = description;
        }
        
        /**
         * 根据排序值查询描述
         * 
         * @param ordinal
         * @return
         */
        public static String getDescriptionByOrdinal(int ordinal)
        {
            for (BankTypeEnum e : BankTypeEnum.values())
            {
                if (ordinal == e.ordinal())
                {
                    return e.description;
                }
            }
            return "";
        }
    }
    
    public static enum BusinessTypeEnum
    {
        /** 0 - 占位符 */
        NORMAL("占位符"),
        
        /** 1 */
        ADMINISTRATOR_MANAGEMENT("管理员管理"),
        
        /** 2 */
        USER_MANAGEMENT("用户管理"),
        
        /** 3 */
        ORDER_MANAGEMENT("订单管理"),
        
        /** 4 */
        SELL_MANAGEMENT("特卖管理"),
        
        /** 5 */
        PRODUCT_MANAGEMENT("商品管理"),
        
        /** 6 */
        CATEGORY_MANAGEMENT("分类管理"),
        
        /** 7 */
        SELLER_MANAGEMENT("商家管理"),
        
        /** 8 */
        SALE_MANAGEMENT("促销管理"),
        
        /** 9 */
        BRAND_MANAGEMENT("品牌管理"),
        
        /** 10 */
        SALESERVICE_MANAGEMENT("售后管理"),
        
        /** 11 */
        DATA_COLLECTION("数据统计"),
        
        /** 12 */
        SMS_MANAGEMENT("短信管理"),
        
        /** 13 */
        FENXIAO_MANAGEMENT("分销管理"),
        
        /** 14 */
        SYSTEM_TOOL("系统工具"),
        
        FINACE_MANAGEMENT("财务管理"),
        
        GOLD_COIN("亲密度管理");
        
        final String description;
        
        private BusinessTypeEnum(String description)
        {
            this.description = description;
        }
        
        /**
         * 根据排序值查询描述
         * 
         * @param ordinal
         * @return
         */
        public static String getDescriptionByOrdinal(int ordinal)
        {
            for (BusinessTypeEnum e : BusinessTypeEnum.values())
            {
                if (ordinal == e.ordinal())
                {
                    return e.description;
                }
            }
            return "";
        }
    }
    
    /** 使用origin获取值 禁止 中间插入新的 */
    public static enum OperationTypeEnum
    {
        /** 0 - 占位符 */
        NORMAL("占位符"),
        
        /** 1 */
        MODIFY_ORDER_PRICE("订单改价"),
        
        /** 2 */
        MODIFY_ORDER_STATUS("订单改状态"),
        
        /** 3 */
        HAND_CREATE_ORDER("手动创建订单"),
        
        /** 4 */
        MODIFY_PRODUCT_STOCK("调整库存"),
        
        /** 5 */
        MODIFY_PRODUCT_PRICE("商品改价"),
        
        /** 6 */
        MODIFY_SELL_COUNT("修改销量"),
        
        /** 7 */
        MODIFY_SELLER_NAME("修改商品商家"),
        
        MODIFY_PRODUCT_STATUS("修改商品状态"),
        
        MODIFY_PRODUCT_TIME("修改商品销售时间"),
        
        MODIFY_PRODUCT_SALE_STATUS("商品上下架"),
        
        MODIFY_SELL_TIME("修改特卖时间"),
        
        MODIFY_SELL_PRODUCT("修改特卖关联商品"),
        
        MODIFY_SELL_PRODUCT_TIME("修改特卖商品销售时间"),
        
        MODIFY_BANNER_PRODUCT("修改banner关联商品"),
        
        MODIFY_USER_INTEGRAL("调整用户积分"),
        
        SEND_COUPON("优惠券发放"),
        
        SEND_COUPON_CODE("优惠码生成"),
        
        CREATE_COUPON_DETAIL("新增优惠券类型"),
        
        CHANGE_COUPON_DETAIL_STATUS("更改优惠券状态"),
        
        PARTNER_AUDIT_STATUS("合伙人审核"),
        
        CREATE_PARTNER("创建合伙人"),
        
        CANCEL_PARTNER_STATUS("取消合伙人资格"),
        
        RESET_PARTNER_STATUS("恢复合伙人资格"),
        
        MODIFY_PRODUCT_CODE("修改商品编码"),
        
        CREATE_INVITED_RELATION("手动创建邀请关系"),
        
        MODIFY_ORDER_REMARK("订单改商家备注"),
        
        MODIFY_ORDER_REMARK2("订单改客服备注"),
        
        ADD_GEGEWELFARE_PRODUCT("新增格格福利商品"),
        
        MODIFY_GEGEWELFARE_PRODUCT("修改格格福利商品"),
        
        EXPORT_ORDER("导出订单"),
        
        MODIFY_ORDER_ADDRESS("手动修改订单收货地址"),
        
        MODIFY_BASE_PRODUCT_PRICE("基本商品改价"),
        
        MODIFY_BASE_PRODUCT_SUbMIT("修改基本商品结算"),
        
        MODIFY_PRODUCT("修改商品"),
        
        HAND_CREATE_REFUND_ORDER("新增退款退货订单"),
        
        DELETE_GEGEWELFARE_PRODUCT("删除格格福利商品"),
        
        ADJUST_GOLD_COIN("调整亲密度额度"),
        
        CREATE_PRODUCT("新增商品"),
        
        HQBS_UNBUNDLING("环球捕手账号解绑"),
        
        HQBS_UNBUNDLING_UNIONACCOUNTID("环球捕手改绑微信账号"),
        
        HQBS_UPDATE_MEMBER("环球捕手手动修改会员");
        
        final String description;
        
        private OperationTypeEnum(String description)
        {
            this.description = description;
        }
        
        /**
         * 根据排序值查询描述
         * 
         * @param ordinal
         * @return
         */
        public static String getDescriptionByOrdinal(int ordinal)
        {
            for (OperationTypeEnum e : OperationTypeEnum.values())
            {
                if (ordinal == e.ordinal())
                {
                    return e.description;
                }
            }
            return "";
        }
    }
    
    public static enum LogLevelEnum
    {
        /** 0 - 只占茅坑不拉屎 */
        NORMAL("占位符"),
        
        /** 1 */
        LEVEL_ONE("订单操作日志"),
        
        /** 2 */
        LEVEL_TWO("运维操作日志"),
        
        /** 3 */
        LEVEL_THREE("用户操作日志"),
        
        /** 4 */
        LEVEL_FOUR("促销操作日志"),
        
        /**5*/
        LEVEL_FIVE("第三方订单日志");
        
        final String description;
        
        private LogLevelEnum(String description)
        {
            this.description = description;
        }
        
        /**
         * 根据排序值查询描述
         * 
         * @param ordinal
         * @return
         */
        public static String getDescriptionByOrdinal(int ordinal)
        {
            for (LogLevelEnum e : LogLevelEnum.values())
            {
                if (ordinal == e.ordinal())
                {
                    return e.description;
                }
            }
            return "";
        }
    }
    
    public static enum PRODUCT_TYPE
    {
        
        ORDINARY_GOODS(0), // 普通商品
        LARGE_GROUP(1), // 系统自动团
        NEW_GROUP(2), // 引流团
        HELP_GROUP(3), // 助力团
        RED_GROUP(4); // 网红团
        
        private int value;
        
        private PRODUCT_TYPE(int value)
        {
            this.value = value;
        }
        
        public int getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 团队状态
     */
    public static enum TEAM_STATUS
    {
        TRANSACTION(1), // 开团成功（进行中）
        SUCCESS(2), // 组团成功
        FAIL(3); // 组团失败
        private int value;
        
        private TEAM_STATUS(int iValue)
        {
            this.value = iValue;
        }
        
        public int getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 账号绑定关系，主账号类型
     */
    public enum ACCOUNT_BINDING_MASTER_TYPE
    {
        MOBILE(1), UNION(2);
        
        private int type;
        
        ACCOUNT_BINDING_MASTER_TYPE(int sType)
        {
            type = sType;
        }
        
        public int getType()
        {
            return type;
        }
    }
    
    /**
     * 用户积分操作类型
     */
    public static enum ACCOUNT_OPERATE_POINT_TYPE
    {
        SHOPPING_BACK_POINT(1, "购物返积分"), // 购物返积分
        SYSTEM_MODIFY(2, "系统调整"), // 系统调整
        SHOPPING_USED(3, "购物抵现"), // 购物抵现
        ORDER_REFUND_BACK(4, "订单退款返还"), // 订单退款返还
        EXCHANGE_COUPON(5, "兑换优惠券"), // 兑换优惠券
        POINT_WITHDRAWALS(6, "积分提现"), // 积分提现
        BUDDY_FIRST_ORDER(7, "小伙伴首次下单"), // 小伙伴首次下单
        BUDDY_AGAIN_ORDER(8, "小伙伴重复下单"), // 小伙伴重复下单
        SIGNIN_REWARD(9, "签到奖励"), // 签到奖励
        COMMENT_REWARD(10, "评价送积分"), // 评价送积分
        BUDDY_PERPETUAL_ORDER(11, "合伙人永久收益"), // 合伙人永久收益
        SHOPPING_USED_CANCAL(12, "购物抵现取消"), // 购物抵现取消
        EXCHANGE_PRODUCT_USED(13, "积分换购商品"), // 积分换购
        ACTIVITIES_REWARD(14, "活动送积分"), ACCOUNT_INVITE_ORDER(15, "好友下单送积分"), BINDING_MOBILE(16, "合并账号"), ACTIVITIES_USED(17, "活动减积分");
        
        private int value;
        
        private String desc;
        
        private ACCOUNT_OPERATE_POINT_TYPE(int iValue, String desc)
        {
            this.value = iValue;
            this.desc = desc;
        }
        
        public int getValue()
        {
            return this.value;
        }
        
        public static String getDesc(int ordinal)
        {
            for (ACCOUNT_OPERATE_POINT_TYPE e : ACCOUNT_OPERATE_POINT_TYPE.values())
            {
                if (ordinal == e.value)
                {
                    return e.desc;
                }
            }
            return "" + ordinal;
        }
    }
    
    public static enum SMS_TEMPLATE
    {
        GGJ_TEMPLATE(1, "【格格家】"), //格格家
        HQBS_TEMPLATE(2, "【环球捕手】"); //环球捕手
        
        private int value;
        
        private String desc;
        
        private SMS_TEMPLATE(int value, String desc)
        {
            this.value = value;
            this.desc = desc;
        }
        
        public int getValue()
        {
            return this.value;
        }
        
        public static String getDesc(int ordinal)
        {
            for (SMS_TEMPLATE e : SMS_TEMPLATE.values())
            {
                if (ordinal == e.value)
                {
                    return e.desc;
                }
            }
            return "" + ordinal;
        }
    }
    
    /**
     * SKU销售渠道
     */
    public static enum SKU_CHANNEL
    {
        GGJ(1, "【格格家】"), //格格家
        HQBS(2, "【环球捕手】"); //环球捕手
        
        private int value;
        
        private String desc;
        
        private SKU_CHANNEL(int value, String desc)
        {
            this.value = value;
            this.desc = desc;
        }
        
        public int getValue()
        {
            return this.value;
        }
        
        public static String getDesc(int ordinal)
        {
            for (SMS_TEMPLATE e : SMS_TEMPLATE.values())
            {
                if (ordinal == e.value)
                {
                    return e.desc;
                }
            }
            return "" + ordinal;
        }
    }
    
    public static enum CASH_STATUS
    {
        CHECK_PENDING(0, "待审核"), APPROVE(1, "财务审核通过"), REFUSED(2, "财务审核拒绝"), REMIT_SUCCESS(3, "直接打款成功"), HAND_REMIT_SUCCESS(4, "手动打款成功"), REFUSE(5, "拒绝打款");
        
        private int value;
        
        private String desc;
        
        private CASH_STATUS(int value, String desc)
        {
            this.value = value;
            this.desc = desc;
        }
        
        public int getValue()
        {
            return this.value;
        }
        
        public static String getDesc(int ordinal)
        {
            for (SMS_TEMPLATE e : SMS_TEMPLATE.values())
            {
                if (ordinal == e.value)
                {
                    return e.desc;
                }
            }
            return "" + ordinal;
        }
    }


    /**
     * 捕手资源位搜索关键字类别
     */
    public static enum QQBS_SEARCH_RESOURCE_TYPE
    {
        BANNER(1),//banner位
        EDITOR(2),//捕手推荐
        SALE(3);//超值热卖

        private int value;

        private QQBS_SEARCH_RESOURCE_TYPE(int value)
        {
            this.value = value;
        }

        public int getValue()
        {
            return this.value;
        }
    }
    
    public static void main(String[] args)
    {
        System.out.println(ACCOUNT_OPERATE_POINT_TYPE.getDesc(1));
    }
}
