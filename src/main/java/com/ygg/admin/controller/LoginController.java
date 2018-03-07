package com.ygg.admin.controller;

import com.alibaba.fastjson.JSON;
import com.ygg.admin.annotation.ControllerLog;
import com.ygg.admin.annotation.PermissionDesc;
import com.ygg.admin.config.YggAdminProperties;
import com.ygg.admin.dao.UserDao;
import com.ygg.admin.entity.ResultEntity;
import com.ygg.admin.service.ShiroService;
import com.ygg.admin.service.UserService;
import com.ygg.admin.util.CacheConstant;
import com.ygg.admin.util.CommonUtil;
import com.ygg.admin.util.RedisUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoginController
{
    
    Logger log = Logger.getLogger(LoginController.class);
    
    /*private CacheServiceIF cache = CacheManager.getClient();*/

    @Autowired
    private RedisUtil redisUtil;

    @Resource
    private ShiroService shiroService;
    
    @Resource
    private UserService userService;


    @Resource
    private UserDao userDao;

    /**
     * 登陆页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/tlogin")
    public ModelAndView loginForm(HttpServletRequest request)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("auth/login");
        return mv;
    }

//    /**
//     * 登陆页面
//     */
//    @RequestMapping("/tlogin")
//    @PermissionDesc("登陆页面")
//    public String loginForm()
//    {
//        return "redirect:" + YggAdminProperties.getInstance().getProperty("sso.login.url");
//    }

    
    /**
     * shiro
     */
    @RequestMapping("/shiroLogin")
    @ResponseBody
    @ControllerLog(description = "管理员管理-登录")
    @PermissionDesc("登入")
    public Object shiroLogin(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "name", required = true) String name,
        @RequestParam(value = "pwd", required = true) String pwd)
    {
        try
        {
//            Object sessionCode = request.getSession().getAttribute("verifyCode");
//            if (sessionCode == null || !(sessionCode + "").equalsIgnoreCase(code))
//            {
//                Map<String, Object> map = new HashMap<>();
//                map.put("status", 0);
//                map.put("msg", "验证码错误");
//                return JSON.toJSONString(map);
//            }
            List<Map<String, Object>> userMap =  userDao.findAllUserCode();
            pwd = CommonUtil.strToMD5(pwd + name);
            Subject currentUser = shiroService.login(name, pwd);
            if (currentUser == null)
            {
                // 登陆失败
                Map<String, Object> map = new HashMap<>();
                map.put("status", 0);
                String msg = "用户名或者密码错误";
                //失败判断
                Object lockedName = redisUtil.get(CacheConstant.ADMIN_ADMINREALM_LOCKED_USER + name);
                if (lockedName != null)
                {
                    msg = "该用户已锁定";
                    redisUtil.remove(CacheConstant.ADMIN_ADMINREALM_LOCKED_USER + name);
                }
                else
                {
                    Object errorPassId = redisUtil.get(CacheConstant.ADMIN_ADMINREALM_ERROR_PASSWORD_USER + name);
                    if (errorPassId != null)
                    {
                        //密码错误
                        Integer loginErrorTimes =
                            Integer.parseInt(request.getSession().getAttribute("loginErrorTimes") == null ? "0" : request.getSession().getAttribute("loginErrorTimes") + "");
                        if (loginErrorTimes >= 3)
                        {
                            //锁定用户
                            int status = userService.updateUserLocked(Integer.parseInt(errorPassId + ""), 1);
                            if (status == 1)
                            {
                                msg = "该用户已锁定";
                                request.getSession().setAttribute("loginErrorTimes", null);
                            }
                            else
                            {
                                log.error("锁定用户失败，用户：" + name);
                            }
                        }
                        else
                        {
                            loginErrorTimes++;
                            request.getSession().setAttribute("loginErrorTimes", loginErrorTimes);
                        }
                        redisUtil.remove(CacheConstant.ADMIN_ADMINREALM_ERROR_PASSWORD_USER + name);
                    }
                }
                map.put("msg", msg);
                return JSON.toJSONString(map);
            }
            // 登陆成功
            Map<String, Object> result = new HashMap<>();
            result.put("status", 1);
            result.put("msg", "登陆成功");
            return JSON.toJSONString(result);
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
            return ResultEntity.getFailResult("操作失败");
        }
    }
    
    @RequestMapping("/noPermission")
    public ModelAndView noPermission()
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("auth/noPermission");
        return mv;
    }
    
    /**
     * 退出
     * 
     * @return
     * @
     */
    @RequestMapping("/lgout")
    @ControllerLog(description = "管理员管理-登出")
    public String lgout()
    {
        return "redirect:" + YggAdminProperties.getInstance().getProperty("sso.login.url");
    }
}
