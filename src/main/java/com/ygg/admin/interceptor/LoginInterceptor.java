package com.ygg.admin.interceptor;

import com.ygg.admin.entity.User;
import com.ygg.admin.service.SystemConfigService;
import com.ygg.admin.service.UserService;
import com.ygg.admin.util.CommonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Pattern;

/**
 * app端数据验证过滤器
 * 
 * @author zhangyb
 *        
 */
public class LoginInterceptor implements HandlerInterceptor
{
    private Logger logger = Logger.getLogger(LoginInterceptor.class);
    
    // 子网过滤
    private static final Pattern SUB_IP_PATTERN = Pattern.compile("192.168.1.\\d{1,3}");
    
    @Resource
    private UserService userService;
    
    @Resource
    private SystemConfigService systemConfigService;
    
    /**
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用， SpringMVC中的Interceptor拦截器是链式的，可以同时存在
     * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行 ，而且所有的Interceptor中的preHandle方法都会在
     * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的 ，这种中断方式是令preHandle的返
     * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception
    {
//        String username = SecurityUtils.getSubject().getPrincipal() + "";
//        User user = userService.findByUsername(username);
//
//        String admin = request.getParameter("admin");
//        String serverName = request.getServerName();
//        if (StringUtils.isEmpty(admin) && "121.40.73.86".equals(serverName))
//        {
//            response.sendRedirect("http://admin.gegejia.com/yggManager/common/index");
//            return false;
//        }
//
//        // 只对登陆的用户做处理，未登录用户交给shiro过滤器
//        if (user != null)
//        {
//            // 检查当前用户是否已被锁定
//            if (user.getLocked() == 1)
//            {
//                // logger.info("**************拦截到被锁定的用户：" + user.getUsername() + "正在操作************");
//                response.setCharacterEncoding("utf-8");
//                response.setContentType("text/html;charset=utf-8");
//                response.setHeader("Content-disposition", "");
//                PrintWriter writer = response.getWriter();
//                String str = "<div style='margin:50 auto; width:600px; height:100px;'><h3>该账户已被锁定，如要解除锁定，请联系管理员。</h3></div>";
//                writer.write(str);
//                writer.close();
//                return false;
//            }
//
//            // 获取ip白名单
//            List<String> whiteIPList = systemConfigService.findAvailableIps();
//
//            // 获取当前用户ip
//            String ip = CommonUtil.getRemoteIpAddr(request);
//
//            // IP白名单中不包含请求IP
//            if (CollectionUtils.isNotEmpty(whiteIPList) && !whiteIPList.contains(ip))
//            {
//                // 607 ip变动频繁
//                if (ip.startsWith("60") || ip.startsWith("115") || ip.startsWith("125"))
//                {
//                    return true;
//                }
//
//                // 如果是内网子网，则可以直接访问
//                if (SUB_IP_PATTERN.matcher(ip).matches() || "0:0:0:0:0:0:0:1".equals(ip) || "127.0.0.1".equals(ip))
//                {
//                    return true;
//                }
//            }
//        }
        return true;
    }
    
    /**
     * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的， 它的执行时间是在处理器进行处理之
     * 后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行 ，也就是说在这个方法中你可以对ModelAndView进行操
     * 作。这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用 ，这跟Struts2里面的拦截器的执行过程有点像，
     * 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法 ，Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor
     * 或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前 ，要在Interceptor之后调用的内容都写在调用invoke方法之后。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv)
        throws Exception
    {
        
    }
    
    /**
     * 需要preHandle方法的返回值为true时才会执行。 该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行，这个方法的主要作用是用于清理资源的，
     * 当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e)
        throws Exception
    {
        // TODO Auto-generated method stub
        
    }
    
}
