package com.ygg.admin.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class YggAdminProperties
{
    private Logger log = Logger.getLogger(YggAdminProperties.class);
    
    private static YggAdminProperties instance = new YggAdminProperties();
    
    private Properties props = null;
    static
    {
        instance.init();
    }
    
    private YggAdminProperties()
    {
        props = new Properties();
        
    }
    
    public void init()
    {
        InputStream in = null;
        try
        {
            in = this.getClass().getClassLoader().getResourceAsStream("config/config.properties");
            if (props == null)
            {
                props = new Properties();
            }
            props.load(in);
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
        }
        finally
        {
            try
            {
                if (in != null)
                {
                    in.close();
                }
            }
            catch (IOException e)
            {
                log.error(e.getMessage(), e);
            }
        }
    }
    
    public static YggAdminProperties getInstance()
    {
        return instance;
    }
    
    public String getProperty(String key)
    {
        Object value = props.get(key);
        if (value == null)
            return null;
        return value.toString();
    }
}
