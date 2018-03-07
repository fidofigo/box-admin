package com.ygg.admin.filter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class ParamTrimFilterFilter implements Filter
{
    
    public void init(FilterConfig filterConfig)
        throws ServletException
    {
        
    }
    
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
        throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        chain.doFilter(new ParamTrimRequest(request), response);
    }
    
    public void destroy()
    {
        
    }
    
}

/**
 * 对请求参数进行去空格
 */
class ParamTrimRequest extends HttpServletRequestWrapper
{
    private HttpServletRequest request = null;
    
    public ParamTrimRequest(HttpServletRequest request)
    {
        super(request);
        this.request = request;
    }
    
    @Override
    public String getParameter(String name)
    {
        String value = super.getParameter(name);
        if (value == null)
            return null;
        String method = request.getMethod();
        if ("get".equalsIgnoreCase(method) || "post".equalsIgnoreCase(method))
        {
            value = value.trim();
        }
        return value;
    }
    
    @Override
    public String[] getParameterValues(String name)
    {
        String[] r = super.getParameterValues(name);
        String method = request.getMethod();
        if ("get".equalsIgnoreCase(method) || "post".equalsIgnoreCase(method))
        {
            if (r != null)
            {
                for (int i = 0; i < r.length; i++)
                {
                    if (r != null)
                        r[i] = r[i].trim();
                }
            }
            
        }
        return r;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> temp = super.getParameterMap();
        if(temp == null)
            return new LinkedHashMap<String, String[]>(1);

        Map<String, String[]> result = new LinkedHashMap<String, String[]>(temp.size());

        for (Map.Entry<String, String[]> entry : temp.entrySet()) {
            if (entry.getValue().length > 0) {
                result.put(entry.getKey(), trimArr(entry.getValue()));
            }
        }
        return result;
    }

    private String[] trimArr(String[] values) {
        String[] arr = new String[values.length];
        int i = 0;
        for (String value : values) {
            arr[i] = StringUtils.trim(value);
            i++;
        }
        return arr;
    }
}
