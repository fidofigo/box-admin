package com.ygg.admin.util.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yangguo on 16/12/22.
 * 导出excel字段用注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelAnnotation
{
    /**
     * excel表头值
     */
    String header();

    /**
     * excel第几列, 从0开始
     */
    int cell();
}
