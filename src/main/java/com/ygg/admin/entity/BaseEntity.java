/**
 * Copyright (c) 2017, Alex. All rights reserved.
 */
package com.ygg.admin.entity;

import com.ygg.admin.util.DateTimeUtil;
import com.ygg.admin.util.ToString;
import org.joda.time.DateTimeUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:zhongchao@gegejia.com">zhong</a>
 * @version 1.0 2017/9/30
 * @since 1.0
 */
public class BaseEntity extends ToString implements Serializable
{
    private Integer id;

    private Date createTime;

    private Date updateTime;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public String getCreateTime()
    {
        return DateTimeUtil.date2String(createTime, "yyyy-MM-dd HH:mm:ss");
    }

    public String getUpdateTime()
    {
        return DateTimeUtil.date2String(updateTime, "yyyy-MM-dd HH:mm:ss");
    }

}
