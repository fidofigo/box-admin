package com.ygg.admin.controller.goods;

import com.alibaba.fastjson.JSON;
import com.ygg.admin.annotation.ControllerLog;
import com.ygg.admin.annotation.PermissionDesc;
import com.ygg.admin.service.goods.CategoryService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/category")
public class CategoryController
{
    Logger log = Logger.getLogger(CategoryController.class);

    @Resource
    private CategoryService categoryService;

    /**
     * 类目
     * @param request
     * @return
     */
    @RequestMapping("/list")
    @PermissionDesc("类目页")
    public ModelAndView list(HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView();
        try {
            mv.setViewName("goods/category");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mv.setViewName("error/404");
        }
        return mv;
    }

    /**
     * 类目数据
     * @param page
     * @param rows
     * @param categoryId
     * @param isAvailable
     * @return
     */
    @RequestMapping(value = "/jsonCategory", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @PermissionDesc("类目json")
    public String jsonCategory(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
        @RequestParam(value = "rows", required = false, defaultValue = "50") int rows,
        @RequestParam(value = "categoryId", required = false, defaultValue = "-1") int categoryId,
        @RequestParam(value = "isAvailable", required = false, defaultValue = "-1") int isAvailable)
    {
        try {
            Map<String, Object> para = new HashMap<>();
            if (page == 0) {
                page = 1;
            }
            para.put("start", rows * (page - 1));
            para.put("max", rows);
            if (categoryId != -1) {
                para.put("categoryId", categoryId);
            }
            if (isAvailable != -1) {
                para.put("isAvailable", isAvailable);
            }
            return categoryService.findCategoryByPara(para);
        } catch (Exception e) {
            log.error("异步加载类目出错：" + e.getMessage(), e);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("rows", new ArrayList<>());
            resultMap.put("total", 0);
            return JSON.toJSONString(resultMap);
        }
    }

    /**
     * 编辑类目
     * @param id
     * @param name
     * @param sequence
     * @param isAvailable
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateCategory", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ControllerLog(description = "编辑类目")
    @PermissionDesc("编辑类目")
    public String saveOrUpdateCategory(@RequestParam(value = "id", required = false, defaultValue = "0") int id,
        @RequestParam(value = "name", required = false, defaultValue = "") String name,
        @RequestParam(value = "sequence", required = false, defaultValue = "1") int sequence,
        @RequestParam(value = "isAvailable", required = false, defaultValue = "1") int isAvailable)
    {
        try {
            Map<String, Object> para = new HashMap<>();
            para.put("name", name);
            para.put("isAvailable", isAvailable);
            para.put("sequence", sequence);
            para.put("name", name);
            if (id == 0) {
                return categoryService.saveCategory(para);
            } else {
                para.put("id", id);
                return categoryService.updateCategory(para);
            }
        } catch (Exception e) {
            Map<String, Object> resultMap = new HashMap<>();
            log.error(e.getMessage(), e);
            resultMap.put("status", 0);
            resultMap.put("msg", "编辑类目失败");
            return JSON.toJSONString(resultMap);
        }
    }

    /**
     * 删除类目
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteCategory", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ControllerLog(description = "删除类目")
    @PermissionDesc("删除类目")
    public String deleteCategory(HttpServletRequest request, @RequestParam(value = "id", required = true) int id)
    {
        Map<String, Object> result = new HashMap<>();
        try {
            return categoryService.deleteCategory(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.put("status", 0);
            result.put("msg", "删除类目失败");
        }
        return JSON.toJSONString(result);
    }

    /**
     * 更新类目
     * @param param
     * @return
     */
    @RequestMapping(value = "/updateCategory", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @PermissionDesc("更新类目")
    public String updateCategory(@RequestParam Map<String, Object> param)
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            return categoryService.updateCategory(param);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            resultMap.put("status", 0);
            resultMap.put("msg", "更新类目失败");
        }
        return JSON.toJSONString(resultMap);
    }
}
