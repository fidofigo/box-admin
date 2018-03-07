package com.ygg.admin.filter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2016/7/29.
 */
public class AdminExceptionResolver implements HandlerExceptionResolver
{
    public static final String AJAX_ACCEPT_CONTENT_TYPE = "text/html;type=ajax";
    public static final String AJAX_SOURCE_PARAM = "ajaxSource";

    private static Logger logger = Logger.getLogger(AdminExceptionResolver.class);
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
    {
        logger.error(ex.getMessage(), ex);
        if (ex instanceof UnauthorizedException)
        {
            return new ModelAndView("/auth/noPermission");
        }
        else if (ex instanceof IllegalArgumentException)
        {
            if (isAjaxRequest(request)) {
                try {
                    IllegalArgumentException iae = (IllegalArgumentException) ex;
                    JSONObject result = new JSONObject();
                    result.put("status", -1);
                    result.put("message", iae.getMessage());
                    OutputStream stream = response.getOutputStream();
                    stream.write(result.toJSONString().getBytes());
                    stream.flush();
                    stream.close();
                } catch (IOException e) {
                }
                return null;
            } else {
                ModelAndView mv = new ModelAndView("/error/404");
                mv.addObject("errorMessage", "资源不存在。" + getExceptionStack(ex));
                return mv;
            }
        }
        else
        {
            if (StringUtils.contains(ex.getMessage(), "not found"))
            {
                ModelAndView mv = new ModelAndView("/error/404");
                mv.addObject("errorMessage", "资源不存在。" + getExceptionStack(ex));
                return mv;
            }
            else if (ex instanceof FileNotFoundException)
            {
                ModelAndView mv = new ModelAndView("/error/404");
                mv.addObject("errorMessage", "文件不存在。" + getExceptionStack(ex));
                return mv;
            }
            else if (ex instanceof NullPointerException)
            {
                ModelAndView mv = new ModelAndView("/error/500");
                mv.addObject("errorMessage", "服务器发生异常。" + getExceptionStack(ex));
                return mv;
            }
            else
            {
                ModelAndView mv = new ModelAndView("/error/500");
                mv.addObject("errorMessage", ex.getMessage());
                return mv;
            }
        }
    }
    
    public static String getExceptionStack(Exception e)
    {
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        StringBuilder result = new StringBuilder(e.toString() + "\n");
        for (int index = stackTraceElements.length - 1; index >= 0; --index)
        {
            result.append("at [").append(stackTraceElements[index].getClassName()).append(",");
            result.append(stackTraceElements[index].getFileName()).append(",");
            result.append(stackTraceElements[index].getMethodName()).append(",");
            result.append(stackTraceElements[index].getLineNumber()).append("]\n");
        }
        return result.toString();
    }

    public boolean isAjaxRequest(HttpServletRequest request){
        String header = request.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equals(header) ? true:false;
        return isAjax;
    }
}
