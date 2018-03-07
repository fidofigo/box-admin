package com.ygg.admin.code;

/**
 * Created by hellokitty on 16-6-24.
 */
public enum DepartMentEnum
{
    DEFAULT(0, ""),

    YUN_YING(1, "运营部"),

    KE_FU(2, "客服部"),

    JI_SHU(3, "技术部"),

    ZHAO_SHANG(4, "招商部"),

    CAI_WU(5, "财务部"),

    WX_YINGXIAO(6, "微信营销部"),

    SHICHANG_YINGXIAO(7, "市场营销部"),

    RENSHI_XINGZHENG(8, "人事行政部"),

    CANG_CHU(9, "仓储部"),

    CAI_GOU(10, "采购部"),

    SPECIAL(11, "特殊"),

    MANAGE(12, "管理部");

    public final int code;

    public final String desc;

    private DepartMentEnum(int code, String desc)
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
        for (DepartMentEnum e : DepartMentEnum.values())
        {
            if (code == e.code)
            {
                return e.desc;
            }
        }
        return "";
    }
}
