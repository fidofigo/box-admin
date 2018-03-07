package com.ygg.admin.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.servlet.ModelAndView;

import com.ygg.admin.config.YggAdminProperties;

/**
 * Created by guoyd on 2017/8/29.
 */
@Aspect
public class ModelAndViewAspect {

    @Around("execution(* com.ygg.admin.controller..*.*(..))")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object o = joinPoint.proceed(joinPoint.getArgs());

        if (o instanceof ModelAndView) {
            ModelAndView mv = (ModelAndView) o;
            mv.addObject("sellerAdminUrl", YggAdminProperties.getInstance().getProperty("seller.admin.url"));
        }
        return o;
    }
}
