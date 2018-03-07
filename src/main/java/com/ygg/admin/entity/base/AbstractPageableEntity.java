package com.ygg.admin.entity.base;

/**
 * 分页查询 Created by LiuGJ on 2017/10/10.
 */
public class AbstractPageableEntity
{
    /**
     * 开始行数
     */
    private int start;
    
    /**
     * 一次查询出的行数
     */
    private int pageSize;
    
    public int getStart()
    {
        return start;
    }
    
    public void setStart(int start)
    {
        this.start = start;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
}
