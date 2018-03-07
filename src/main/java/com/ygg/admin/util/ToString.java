/**
 * Copyright (c) 2017, Alex. All rights reserved.
 */
package com.ygg.admin.util;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author <a href="mailto:zhongchao@gegejia.com">zhong</a>
 * @version 1.0 2017/9/30
 * @since 1.0
 */
public class ToString
{
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
