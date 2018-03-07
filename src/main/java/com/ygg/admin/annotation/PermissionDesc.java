package com.ygg.admin.annotation;

import java.lang.annotation.*;

/**
 * Created by hellokitty on 16-6-22.
 *
 * 控制器权限中文备注
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionDesc {
    String value() default "";
}
