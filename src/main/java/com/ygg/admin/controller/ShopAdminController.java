package com.ygg.admin.controller;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ygg.admin.annotation.PermissionDesc;
import com.ygg.admin.code.DepartMentEnum;
import com.ygg.admin.dao.AdminDao;
import com.ygg.admin.entity.ResultEntity;
import com.ygg.admin.service.AdminService;

/**
 * 便利店admin Created by LiuGJ on 2017/9/30.
 */
@Controller
@RequestMapping("admin")
public class ShopAdminController
{
    private Logger log = Logger.getLogger(ShopAdminController.class);

    @Resource(name = "shiroAdminService")
    private AdminService adminService;

    @Resource
    private AdminDao adminDao;

    @RequestMapping("/list")
    @PermissionDesc("管理员列表")
    public ModelAndView list()

    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.setViewName("admin/list");
            Map<String, Object> result = adminService.findAllRole(new HashMap<String, Object>());
            List<Map<String, Object>> roleList = (List<Map<String, Object>>) result.get("rows");
            LinkedHashMap<String , List<Map<String, Object>>> departmentRoleMap = new LinkedHashMap<>();
            for (Map<String, Object> role : roleList) {
                String department = DepartMentEnum.getDescByCode((int) role.get("departmentId"));
                if (departmentRoleMap.get(department) == null) {
                    List<Map<String, Object>> roles = Lists.newArrayList(role);
                    departmentRoleMap.put(department, roles);
                } else {
                    departmentRoleMap.get(department).add(role);
                }
            }
            mv.addObject("departmentCode", DepartMentEnum.values());
            Map<String, Object> option = new HashMap<>();
            option.put("id", 0);
            option.put("role", "");
            roleList.add(0, option);
            mv.addObject("roleList", roleList);
            mv.addObject("departmentRoleMap", departmentRoleMap);
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
            mv.setViewName("error/404");
        }
        return mv;
    }

    @RequestMapping(value = "/addUser", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @PermissionDesc("新增后台用户")
    public String addUser(@RequestParam(value = "roles", required = true) String roles, @RequestParam(value = "name", required = true) String name,//
        @RequestParam(value = "realname", required = true) String realname,//
        @RequestParam(value = "mobileNumber", required = false, defaultValue = "") String mobileNumber,//
        @RequestParam(value = "pwd", required = true) String pwd,//
        @RequestParam(value = "departmentId", required = true) int departmentId,
        @RequestParam(value = "email", required = true) String email
    )
    {
        try
        {
            List<Integer> roleIdList = new ArrayList<>();
            if (roles.indexOf(",") > 0)
            {
                String[] arr = roles.split(",");
                for (String cur : arr)
                {
                    roleIdList.add(Integer.valueOf(cur));
                }
            }
            Map<String, Object> result = adminService.createUser(name, realname, mobileNumber, pwd, roleIdList, departmentId,email);
            return JSON.toJSONString(result);
        }
        catch (Exception e)
        {
            Map<String, Object> result = new HashMap<>();
            log.error("新增角色失败", e);
            result.put("status", 0);
            result.put("msg", "新增失败");
            return JSON.toJSONString(result);
        }
    }

    @RequestMapping(value = "/editUser", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @PermissionDesc("编辑用户")
    public String editUser(@RequestParam(value = "id", required = true) int id, //
        @RequestParam(value = "roles", required = true) String roles,//
        @RequestParam(value = "name", required = true) String name,//
        @RequestParam(value = "realname", required = true) String realname, //
        @RequestParam(value = "mobileNumber", required = false, defaultValue = "") String mobileNumber, //
        @RequestParam(value = "departmentId", required = false, defaultValue = "0") int departmentId,
        @RequestParam(value = "email", required = true) String email
    )
    {
        try
        {
            List<Integer> roleIdList = new ArrayList<>();
            if (roles.indexOf(",") > 0)
            {
                String[] arr = roles.split(",");
                for (String cur : arr)
                {
                    roleIdList.add(Integer.valueOf(cur));
                }
            }
            if (roleIdList.size() == 0)
            {
                Map<String, Object> result = new HashMap<>();
                result.put("status", 0);
                result.put("msg", "请选择至少一个角色");
                return JSON.toJSONString(result);
            }
            Map<String, Object> result = adminService.updateUser(id, name, realname, mobileNumber, roleIdList, departmentId , email);
            return JSON.toJSONString(result);
        }
        catch (Exception e)
        {
            Map<String, Object> result = new HashMap<>();
            log.error("修改失败", e);
            result.put("status", 0);
            result.put("msg", "修改失败");
            return JSON.toJSONString(result);
        }
    }

    @RequestMapping(value = "/copyRole", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @PermissionDesc("复制角色")
    public String copyRole(
        @RequestParam(value = "roleId", required = true) int id, //
        @RequestParam(value = "roleName", required = true) String roleName
    )
    {
        try
        {
            Map<String, Object> role = adminService.findRoleById(id);
            int roleId = Integer.valueOf(role.get("id") + "" );
            List<Integer> pids = adminDao.findPermissionByRoleId(roleId);
            adminService.insertRole(0,roleName, (String)role.get("description"),pids , Integer.valueOf(role.get("departmentId") + ""));
            Map<String, Object> result = new HashMap<>();
            result.put("status", 1);
            return JSON.toJSONString(result);
        }
        catch (Exception e)
        {
            Map<String, Object> result = new HashMap<>();
            log.error("修改失败", e);
            result.put("status", 0);
            result.put("msg", "修改失败");
            return JSON.toJSONString(result);
        }
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @PermissionDesc("异步获取管理员信息")
    public Object userInfo(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
        @RequestParam(value = "rows", required = false, defaultValue = "50") int rows, @RequestParam(value = "name", required = false, defaultValue = "") String name,
        @RequestParam(value = "realname", required = false, defaultValue = "") String realname,
        @RequestParam(value = "departmentId", required = false, defaultValue = "0") int departmentId,
        @RequestParam(value = "lockStatus", required = false, defaultValue = "-1") int lockStatus, @RequestParam(value = "roleId", required = false, defaultValue = "0") int roleId)
    {
        try
        {
            Map<String, Object> para = new HashMap<>();
            if (page == 0)
            {
                page = 1;
            }
            para.put("start", rows * (page - 1));
            para.put("max", rows);
            if (!"".equals(name))
            {
                para.put("username", name);
            }
            if (!"".equals(realname))
            {
                para.put("realname", realname);
            }
            if (departmentId != 0)
            {
                para.put("departmentId", departmentId);
            }
            if (roleId != 0)
            {
                para.put("roleId", roleId);
            }
            if (lockStatus != -1)
            {
                para.put("locked", lockStatus);
            }
            Map re = adminService.findUser(para);
            return JSON.toJSONString(re);
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
            return ResultEntity.getFailResultList();
        }
    }

    @RequestMapping("/roleList")
    @PermissionDesc("角色列表")
    public ModelAndView roleList()

    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.setViewName("admin/roleList");
            List<Map<String, Object>> permissionList = adminService.findAllPermission(new HashMap<String, Object>(), 0);
            Map<String, Object> result = adminService.findAllRole(new HashMap<String, Object>());
            List<Map<String, Object>> roleList = (List<Map<String, Object>>) result.get("rows");
            Map<String, Object> option = new HashMap<>();
            option.put("id", 0);
            option.put("role", "");
            roleList.add(0, option);
            mv.addObject("roleList", roleList);
            mv.addObject("permissionList", permissionList);
            mv.addObject("departmentCode", DepartMentEnum.values());
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
            mv.setViewName("error/404");
        }
        return mv;
    }

    @RequestMapping(value = "/updateRole", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @PermissionDesc("新增更新角色")
    public String addRole(@RequestParam(value = "role", required = true) String role, //
        @RequestParam(value = "resource", required = true) String resource, //
        @RequestParam(value = "desc", required = true) String desc,//
        @RequestParam(value = "departmentId", required = false, defaultValue = "0") int departmentId,//
        @RequestParam(value = "id", required = false, defaultValue = "0") int id)
    {
        try
        {
            if ("".equals(role) || "".equals(desc))
            {
                Map<String, Object> result = new HashMap<>();
                result.put("status", 0);
                result.put("msg", "请填写完整信息！");
                return JSON.toJSONString(result);
            }
            List<Integer> permissionIdList = new ArrayList<>();
            if (resource.indexOf(",") > 0)
            {
                String[] arr = resource.split(",");
                for (String cur : arr)
                {
                    permissionIdList.add(Integer.valueOf(cur));
                }
            }
            if (permissionIdList.size() == 0)
            {
                Map<String, Object> result = new HashMap<>();
                result.put("status", 0);
                result.put("msg", "请选择权限再保存！");
                return JSON.toJSONString(result);
            }
            if (departmentId == 0)
            {
                Map<String, Object> result = new HashMap<>();
                result.put("status", 0);
                result.put("msg", "请选择部门！");
                return JSON.toJSONString(result);
            }
            Map<String, Object> result = adminService.insertRole(id, role, desc, permissionIdList, departmentId);
            return JSON.toJSONString(result);
        }
        catch (Exception e)
        {
            Map<String, Object> result = new HashMap<>();
            log.error("新增角色失败", e);
            result.put("status", 0);
            result.put("msg", "新增失败！");
            return JSON.toJSONString(result);
        }
    }

    @RequestMapping(value = "/roleInfo", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @PermissionDesc("异步获取角色信息")
    public String roleInfo()
    {
        try
        {
            Map<String, Object> result = adminService.findAllRole(new HashMap<String, Object>());
            return JSON.toJSONString(result);
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
            Map<String, Object> result = new HashMap<>();
            result.put("rows", new ArrayList());
            result.put("total", 0);
            return JSON.toJSONString(result);
        }
    }

    /**
     * 插入权限
     *
     * @param permission  eg : AccountController,list,用户列表;AccountController,export,用户列表导出
     */
    @RequestMapping(value = "/addPermission", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @PermissionDesc("插入权限")
    public String addPermission(@RequestParam(value = "permission", required = true) String permission)
    {
        try
        {
            List<Map<String, String>> permissions = new ArrayList<>();
            for (String s : permission.split(";"))
            {
                String[] info = s.trim().split(",");

                Map<String, String> cuPer = new HashMap<>();
                cuPer.put("category", info[0].trim());
                cuPer.put("permission", info[0].trim() + "_" + info[1].trim());
                cuPer.put("platformTypeId", info[2].trim());
                cuPer.put("description", info[3].trim());
                permissions.add(cuPer);

            }
            int status = adminService.insertPermission(permissions);
            Map<String, Object> result = new HashMap<>();
            result.put("status", status > 0 ? 1 : 0);
            result.put("msg", status > 0 ? "ok" : "fail");
            return JSON.toJSONString(result);
        }
        catch (Exception e)
        {
            Map<String, Object> result = new HashMap<>();
            log.error("新增角色失败", e);
            result.put("status", 0);
            result.put("msg", "新增失败");
            return JSON.toJSONString(result);
        }
    }

    @RequestMapping(value = "/editRoleForm", produces = "application/json;charset=UTF-8")
    @PermissionDesc("修改角色信息")
    public ModelAndView editRoleForm(@RequestParam(value = "roleId", required = false, defaultValue = "0") int roleId)

    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.setViewName("admin/editRoleForm");
            Map<String, Object> info = adminService.findRoleById(roleId);
            if (info == null)
            {
                return mv;
            }
            mv.addObject("id", roleId + "");
            mv.addObject("role", info.get("role") + "");
            mv.addObject("description", info.get("description") + "");
            int departmentId = Integer.valueOf(info.get("departmentId") + "" );
            mv.addObject("departmentId", departmentId);
            mv.addObject("departmentCode", DepartMentEnum.values());
            List<Map<String, Object>> permissionList = adminService.findAllPermission(new HashMap<String, Object>(), roleId);
            mv.addObject("permissionList", permissionList);
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
            mv.setViewName("error/404");
        }
        return mv;
    }

    @RequestMapping(value = "/refreshPermission", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @PermissionDesc("刷新权限")
    public String refreshPermission()

    {
        try
        {
            return adminService.refreshPermission();
        }
        catch (Exception e)
        {
            Map<String, Object> result = new HashMap<>();
            log.error("刷新权限失败", e);
            result.put("status", 0);
            result.put("msg", "失败");
            return JSON.toJSONString(result);
        }
    }

    /**
     * 版本信息页面
     *
     * @return
     */
    @RequestMapping("/versionList")
    public String versionList()
    {
        return "admin/versionList";
    }

}
