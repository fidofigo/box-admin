package com.ygg.admin.code;

import java.util.HashMap;
import java.util.Map;

public enum ControllerMappingEnum
{
    admin("ShopAdminController", "管理员"),
    
    menu("MenuController", "菜单"),

    goodsBase("GoodsBaseController", "基础商品"),

    goods("GoodsController", "商品"),

    category("CategoryController", "类目"),

    shopAccount("ShopAccountController", "商铺用户"),

    ;
    
    final String controllerName;
    
    final String controllerDesc;
    
    ControllerMappingEnum(String controllerName, String controllerDesc)
    {
        this.controllerName = controllerName;
        this.controllerDesc = controllerDesc;
    }
    
    public static String getControllerNameByName(String name)
    {
        for (ControllerMappingEnum e : ControllerMappingEnum.values())
        {
            if (e.name().equals(name))
            {
                return e.controllerName;
            }
        }
        return null;
    }
    
    public static String getControllerDescByName(String name)
    {
        ControllerMappingEnum mapping = ValueMap.valuesMap.get(name);
        if (mapping != null)
        {
            return mapping.getControllerDesc();
        }
        return null;
    }
    
    public String getControllerName()
    {
        return controllerName;
    }
    
    public String getControllerDesc()
    {
        return controllerDesc;
    }
    
    private static class ValueMap
    {
        private static final Map<String, ControllerMappingEnum> valuesMap = new HashMap();
        static
        {
            for (ControllerMappingEnum mapping : ControllerMappingEnum.values())
            {
                valuesMap.put(mapping.getControllerName(), mapping);
            }
        }
    }
    
}
