package com.ygg.admin.util.mvc;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Form表单提交时，将字符串转化为Date类型
 * Created by luliru on 2016/9/2.
 */
public class CustomDateEditor extends PropertyEditorSupport
{
    
    private DateFormat format;
    
    public CustomDateEditor(DateFormat format)
    {
        this.format = format;
    }
    
    @Override
    public void setAsText(String text)
        throws IllegalArgumentException
    {
        Date date = null;
        try
        {
            date = format.parse(text);
        }
        catch (ParseException e)
        {
            throw new IllegalArgumentException(e);
        }
        setValue(date);
    }
}
