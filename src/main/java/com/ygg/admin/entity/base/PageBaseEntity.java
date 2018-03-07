package com.ygg.admin.entity.base;

import com.ygg.admin.util.ToString;

import java.util.Map;

/**
 * 分页查询参数
 * Created by LiuGJ on 2017/10/10.
 */
public class PageBaseEntity extends ToString
{
    private int page = 1;

    private int rows = 50;

    public int getPage()
    {
        return Math.max(page, 1);
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public int getRows()
    {
        return rows;
    }

    public void setRows(int rows)
    {
        this.rows = rows;
    }

    /**
     *设置分页查询参数
     * @param param
     */
    public void setPageParam2Map(Map<String, Object> param)
    {
        param.put("start", (getPage() - 1) * getRows());
        param.put("pageSize", getRows());
    }

    /**
     * 设置分页查询参数
     * @param entity
     */
    public void setPageParam2PageableEntity(AbstractPageableEntity entity)
    {
        entity.setPageSize(getRows());
        entity.setStart((getPage() - 1) * getRows());
    }

    public int getOffset() {
        return (getPage() - 1) * getRows();
    }

    public int getLimit() {
        return getRows();
    }
}
