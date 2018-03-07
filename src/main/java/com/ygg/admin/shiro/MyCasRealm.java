package com.ygg.admin.shiro;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasAuthenticationException;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;

import com.ygg.admin.service.AdminService;
import com.ygg.admin.util.SpringBeanUtil;

/**
 * 单点登录认证和授权
 * Created by lzj on 2016/11/22.
 */
public class MyCasRealm extends CasRealm
{

    Logger log = Logger.getLogger(MyCasRealm.class);

    private AdminService adminService = null;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
    {
        String username = (String)principals.getPrimaryPrincipal();
        if (adminService == null)
        {
            adminService = (AdminService)SpringBeanUtil.getBean("shiroAdminService");
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        try
        {
            authorizationInfo.setRoles(adminService.findRoles(username));
            authorizationInfo.setStringPermissions(adminService.findPermissions(username));
        }
        catch (Exception e)
        {
            log.error("登陆获取权限失败", e);
            return null;
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
        throws AuthenticationException
    {
        CasToken casToken = (CasToken)token;
        if (token == null)
        {
            return null;
        }
        
        String ticket = (String)casToken.getCredentials();
        if (!org.apache.shiro.util.StringUtils.hasText(ticket))
        {
            return null;
        }
        
        TicketValidator ticketValidator = ensureTicketValidator();
        
        try
        {
            // contact CAS server to validate service ticket
            Assertion casAssertion = ticketValidator.validate(ticket, getCasService());
            // get principal, user id and attributes
            AttributePrincipal casPrincipal = casAssertion.getPrincipal();
            String userId = casPrincipal.getName();
            
            Map<String, Object> attributes = casPrincipal.getAttributes();
            // refresh authentication token (user id + remember me)
            //默认rememberMe3天
            casToken.setUserId(userId);
            casToken.setRememberMe(true);
            List<Object> principals = CollectionUtils.asList(userId, attributes);
            PrincipalCollection principalCollection = new SimplePrincipalCollection(principals, getName());
            //显式调用赋予权限
            this.doGetAuthorizationInfo(principalCollection);
            return new SimpleAuthenticationInfo(principalCollection, ticket);
        }
        catch (TicketValidationException e)
        {
            throw new CasAuthenticationException("Unable to validate ticket [" + ticket + "]", e);
        }
    }
}
