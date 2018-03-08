package com.ygg.admin.controller;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ygg.admin.annotation.ControllerLog;
import com.ygg.admin.annotation.PermissionDesc;
import com.ygg.admin.entity.ResultEntity;
import com.ygg.admin.entity.User;
import com.ygg.admin.service.MenuService;
import com.ygg.admin.service.UserService;
import com.ygg.admin.util.CommonUtil;

@Controller
@RequestMapping("/common")
public class CommonController
{
    @Resource
    private MenuService menuService;

    @Resource
    private UserService userService;

    private static Logger logger = Logger.getLogger(CommonController.class);

    /**
     * 获取菜单数据  新  启用
     * @return
     * @
     */
    @RequestMapping(value = "/menu", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @PermissionDesc("菜单")
    public Object menu(HttpServletRequest request)

    {
        try
        {
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser == null)
            {
                // 未登陆
                return "";
            }
            String nodes = request.getParameter("nodes");
            List<Integer> stateList = new ArrayList<>();
            if (nodes != null && !nodes.equals("0"))
            {
                String[] msArr = nodes.split("-");
                for (String string : msArr)
                {
                    if (org.apache.commons.lang.StringUtils.isNotEmpty(string))
                    {
                        stateList.add(Integer.valueOf(string));
                    }
                }
            }
            String username = currentUser.getPrincipal() + "";
            List<Map<String, Object>> menuList = menuService.findMenuByUsername(username, stateList);
            return JSON.toJSONString(menuList);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            return ResultEntity.getFailResult("操作失败");
        }
    }

    /**
     * 获取菜单数据   弃用
     * @param request
     * @return
     * @
     */
    @RequestMapping(value = "/menuInfo", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @PermissionDesc("获取菜单数据")
    public Object userInfo(HttpServletRequest request)
    {
        try
        {
            Subject currentUser = SecurityUtils.getSubject();
            if (currentUser == null)
            {
                // 未登陆
                return "";
            }
            String username = currentUser.getPrincipal() + "";
            Set<String> menuIdSet = userService.findPermissions(username);
            List<Integer> menuIdList = new ArrayList<Integer>();
            for (String string : menuIdSet)
            {
                menuIdList.add(Integer.valueOf(string));
            }
            String nodes = request.getParameter("nodes");
            List<Integer> stateList = new ArrayList<Integer>();
            if (nodes != null && !nodes.equals("0"))
            {
                String[] msArr = nodes.split("-");
                for (String string : msArr)
                {
                    stateList.add(Integer.valueOf(string));
                }
            }
            List<Map<String, Object>> menuList = menuService.loadTree(menuIdList, stateList);
            return JSON.toJSONString(menuList);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            return ResultEntity.getFailResult("操作失败");
        }
    }
    
    @RequestMapping("/index")
    @PermissionDesc("首页")
    public ModelAndView index()
    {
        return new ModelAndView("user/index");
    }

    @RequestMapping(value = "updateOwnPWD", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ControllerLog(description = "管理员管理-修改自己密码")
    @PermissionDesc("修改自己密码")
    public Object updateOwnPWD(@RequestParam(value = "pwd", required = false, defaultValue = "") String pwd,
        @RequestParam(value = "pwd1", required = false, defaultValue = "") String _pwd, @RequestParam(value = "oldpwd", required = true) String oldpwd)
    {
        try
        {
            //todo  改密码后 其他账号退出登入
            Map<String, Object> result = new HashMap<>();
            if (!pwd.equals(_pwd))
            {
                result.put("status", 0);
                result.put("msg", "两次输入的密码不一致");
                return JSON.toJSONString(result);
            }
            Subject currentUser = SecurityUtils.getSubject();
            User user;
            int id = 0;
            String username;
            if (currentUser != null)
            {
                username = currentUser.getPrincipal() + "";
                user = userService.findByUsername(username);
                if (user == null)
                {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("status", 0);
                    map.put("msg", "操作失败， 当前未登入！");
                    return JSON.toJSONString(map);
                }
                id = user.getId();
            }
            else
            {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("status", 0);
                map.put("msg", "操作失败， 当前未登入！");
                return JSON.toJSONString(map);
            }
            oldpwd = CommonUtil.strToMD5(oldpwd + username);
            if (!user.getPassword().equals(oldpwd))
            {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("status", 0);
                map.put("msg", "原密码错误！");
                return JSON.toJSONString(map);
            }
            Map<String, Object> map = userService.changePassword(id, pwd);
            return JSON.toJSONString(map);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            return ResultEntity.getFailResult("操作失败");
        }
    }
}
