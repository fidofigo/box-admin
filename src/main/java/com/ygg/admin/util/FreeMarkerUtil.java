package com.ygg.admin.util;

import java.io.*;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.google.common.collect.ImmutableMap;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 主要是用来生成静态的html
 *
 * @author lihc
 *
 */
public class FreeMarkerUtil
{
    
    @SuppressWarnings("deprecation")
    private static Configuration config = new Configuration();
    
    private static ServletContext servletContext = null;
    
    private static Logger logger = Logger.getLogger(FreeMarkerUtil.class);
    
    /**
     * 默认excel标题模版位置
     */
    private static String DEFAULT_EXCEL_HEAD_PATH = "export/excel/head.ftl";
    
    /**
     * 默认excel尾部模版位置
     */
    private static String DEFAULT_EXCEL_TAIL_PATH = "export/excel/foot.ftl";
    
    /**
     * 初始化模板配置
     * 
     * @param _servletContext javax.servlet.ServletContext
     * @param templateDir 模板位置
     */
    public static void initConfig(ServletContext _servletContext, String templateDir)
    {
        servletContext = _servletContext;
        config.setLocale(Locale.CHINA);
        config.setDefaultEncoding("utf-8");
        config.setEncoding(Locale.CHINA, "utf-8");
        config.setServletContextForTemplateLoading(servletContext, templateDir);
        config.setObjectWrapper(new DefaultObjectWrapper());
        
    }
    
    /**
     * @param templateName 模板名字
     * @param root 模板根 用于在模板内输出结果集
     * @param out 输出对象 具体输出到哪里
     */
    public static void createHtml(String templateName, Map<?, ?> root, Writer out)
    {
        try
        {
            
            // Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
            // 获得模板
            Template template = config.getTemplate(templateName, "utf-8");
            // 生成文件（这里是我们是生成html）
            template.process(root, out);
            // System.out.println(template.toString());
            out.flush();
            // StringReader sr = new StringReader(out.toString()) ;
        }
        catch (IOException e)
        {
            logger.error("IOException", e);
        }
        catch (TemplateException e)
        {
            logger.error("TemplateException", e);
        }
        finally
        {
            try
            {
                out.close();
                out = null;
            }
            catch (IOException e)
            {
                logger.error("IOException", e);
            }
        }
    }
    
    public static String createHtml(String templateName, Map<String, Object> root)
    {
        Writer out = null;
        BufferedReader br = null;
        StringBuffer contentHtml = new StringBuffer();
        try
        {
            File tmpFile = File.createTempFile("yggftlhtml" + CommonUtil.GenerateRandomCode(9), ".tmp");
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmpFile), "UTF-8"));
            // 获得模板
            Template template = config.getTemplate(templateName, "utf-8");
            // 生成文件（这里是我们是生成html）
            template.process(root, out);
            out.flush();
            
            br = new BufferedReader(new InputStreamReader(new FileInputStream(tmpFile), "UTF-8"));
            String lineTxt = null;
            while ((lineTxt = br.readLine()) != null)
            {
                contentHtml.append(lineTxt);
                contentHtml.append("\n");
            }
            if (tmpFile.isFile() && tmpFile.exists())
                tmpFile.delete();
            // System.out.println(contentHtml);
        }
        catch (IOException e)
        {
            logger.error("IOException", e);
        }
        catch (TemplateException e)
        {
            logger.error("TemplateException", e);
        }
        finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                    out = null;
                }
                if (br != null)
                {
                    br.close();
                    br = null;
                }
            }
            catch (IOException e)
            {
                logger.error("IOException", e);
            }
        }
        
        return contentHtml.toString();
    }
    
    public static String createFile(String templateName, Map<String, ?> data)
    {
        Writer out = null;
        BufferedReader br = null;
        StringBuffer contentHtml = new StringBuffer();
        try
        {
            File tmpFile = File.createTempFile("temp" + CommonUtil.GenerateRandomCode(9), ".tmp");
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmpFile), "UTF-8"));
            Template template = config.getTemplate(templateName, "utf-8");
            template.process(data, out);
            out.flush();
            
            br = new BufferedReader(new InputStreamReader(new FileInputStream(tmpFile), "UTF-8"));
            String lineTxt = null;
            while ((lineTxt = br.readLine()) != null)
            {
                contentHtml.append(lineTxt);
                contentHtml.append("\n");
            }
            if (tmpFile.isFile() && tmpFile.exists())
            {
                tmpFile.delete();
                
            }
        }
        catch (IOException e)
        {
            logger.error("IOException", e);
        }
        catch (TemplateException e)
        {
            logger.error("TemplateException", e);
        }
        finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                    out = null;
                }
                if (br != null)
                {
                    br.close();
                    br = null;
                }
            }
            catch (IOException e)
            {
                logger.error("IOException", e);
            }
        }
        return contentHtml.toString();
    }

    public static String createExcelHead(List<String> head)
    {
        if (CollectionUtils.isEmpty(head))
        {
            throw new RuntimeException("标题为空");
        }
        return createExcelBody(DEFAULT_EXCEL_HEAD_PATH, ImmutableMap.of("titles", head));
    }

    public static String createExcelTail()
    {
        return createExcelBody(DEFAULT_EXCEL_TAIL_PATH, null);
    }

    public static String createExcelBody(String excelBodyPath, Map<String, Object> data)
    {
        String content = "";
        if (StringUtils.isEmpty(excelBodyPath))
        {
            throw new RuntimeException("模版路径为空");
        }
        try
        {
            Template template = config.getTemplate(excelBodyPath, "utf-8");
            content = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
        }
        catch (IOException e)
        {
            logger.error(e.getMessage(), e);
        }
        catch (TemplateException e)
        {
            logger.error(e.getMessage(), e);
        }
        return content;
    }
}
