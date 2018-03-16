package com.ygg.admin.service.shop.impl;

import com.alibaba.fastjson.JSON;
import com.ygg.admin.dao.shop.ShopAccountDao;
import com.ygg.admin.service.shop.ShopAccountService;
import com.ygg.admin.util.CommonUtil;
import com.ygg.admin.util.InviteCodeUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("shopAccountService")
public class ShopAccountServiceImpl implements ShopAccountService
{
    Logger logger = Logger.getLogger(ShopAccountServiceImpl.class);

    @Resource
    private ShopAccountDao shopAccountDao;

    /**
     * 根据条件查找商铺用户
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public String findShopAccountByPara(Map<String, Object> para)
    {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> rows = shopAccountDao.findShopAccountByPara(para);
        resultMap.put("rows", rows);
        resultMap.put("total", shopAccountDao.countShopAccount(para));
        return JSON.toJSONString(resultMap);
    }

    /**
     * 新增商铺用户
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public String saveShopAccount(Map<String, Object> para)
    {
        Map<String, Object> resultMap = new HashMap<>();
        if (shopAccountDao.saveShopAccount(para) > 0) {
            int id = Integer.valueOf(para.get("id").toString());

            para.put("code", InviteCodeUtil.getInviteCode(id));
            String secretKey = "";
            try
            {
                secretKey = CommonUtil.generateAccountSign(id);
                para.put("secretKey", secretKey);
            }
            catch (Exception e)
            {
                para.put("secretKey", secretKey);
            }

            shopAccountDao.updateShopAccount(para);
            resultMap.put("status", 1);
            resultMap.put("msg", "新增成功");
        } else {
            resultMap.put("status", 0);
            resultMap.put("msg", "新增失败");
        }
        return JSON.toJSONString(resultMap);
    }

    /**
     * 删除商铺用户
     * @param id：id
     * @return
     * @throws Exception
     */
    @Override
    public String deleteShopAccount(int id)
    {
        Map<String, Object> resultMap = new HashMap<>();
        if (shopAccountDao.deleteShopAccount(id) > 0) {
            resultMap.put("status", 1);
            resultMap.put("msg", "删除成功");
        } else {
            resultMap.put("status", 0);
            resultMap.put("msg", "删除失败");
        }
        return JSON.toJSONString(resultMap);
    }

    /**
     * 更新商铺用户
     * @param para
     * @return
     * @throws Exception
     */
    @Override
    public String updateShopAccount(Map<String, Object> para)
    {
        Map<String, Object> resultMap = new HashMap<>();
        if (shopAccountDao.updateShopAccount(para) > 0) {
            resultMap.put("status", 1);
            resultMap.put("msg", "更新成功");
        } else {
            resultMap.put("status", 0);
            resultMap.put("msg", "更新失败");
        }
        return JSON.toJSONString(resultMap);
    }
}
