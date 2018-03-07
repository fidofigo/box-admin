package com.ygg.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ygg.admin.annotation.ControllerLog;
import com.ygg.admin.entity.ResultEntity;
import com.ygg.admin.service.MenuService;

@Controller
@RequestMapping("/menu")
public class MenuController
{
    @Resource
    private MenuService menuService;
    
    private static Logger logger = Logger.getLogger(MenuController.class);

    @RequestMapping("/menuList")
    public ModelAndView menuList(@RequestParam(value = "pid", required = false, defaultValue = "0") String pid)
    {
        ModelAndView mv = new ModelAndView("menu/list");
        mv.addObject("pid", pid);
        return mv;
    }
    
    @RequestMapping(value = "/jsonMenuList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object jsonMenuList(HttpServletRequest request, @RequestParam(value = "pid", required = false, defaultValue = "0") String pid)
    {
        try
        {
            List<Map<String, Object>> parentMenu = menuService.listMenu(Integer.valueOf(pid));
            return JSON.toJSONString(parentMenu);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            return ResultEntity.getFailResultList();
        }
    }
    
    /**
     * 新增meun
     * @param request
     * @param pid
     * @param sequence
     * @param url
     * @return
     * @
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ControllerLog(description = "菜单管理-新增/编辑菜单")
    public Object update(HttpServletRequest request,//
        @RequestParam(value = "id", required = false, defaultValue = "0") String id,//
        @RequestParam(value = "pid", required = false, defaultValue = "0") String pid,//
        @RequestParam(value = "sequence", required = false, defaultValue = "0") int sequence,//
        @RequestParam(value = "text", required = false, defaultValue = "") String text,//
        @RequestParam(value = "url", required = false, defaultValue = "") String url//
    )
    {
        try
        {
            //内部用用，判断就弱一点吧。。。能用就好
            Map<String, Object> para = new HashMap<String, Object>();
            para.put("id", id);
            para.put("pid", pid);
            para.put("sequence", sequence);
            para.put("text", text);
            para.put("url", url);
            int status = menuService.createOrUpdate(para);
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("status", status);
            return JSON.toJSONString(result);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            return ResultEntity.getFailResult("操作失败");
        }
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ControllerLog(description = "菜单管理-删除菜单")
    public Object delete(HttpServletRequest request, @RequestParam(value = "id", required = true) int id)
    {
        try
        {
            Map<String, Object> resultMap = new HashMap<>();
            int resultStatus = menuService.delete(id);
            if (resultStatus != 1)
            {
                resultMap.put("status", 0);
                resultMap.put("msg", "删除失败");
            }
            else
            {
                resultMap.put("status", 1);
                resultMap.put("msg", "删除成功");
            }
            return JSON.toJSONString(resultMap);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            return ResultEntity.getFailResult("操作失败");
        }
    }

    @RequestMapping(value = "/druid")
    public String druid(HttpServletRequest req)

    {
        return "redirect:/druid/sql.html";
    }

}
