package com.ygg.admin.util.mvc;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 自定义的文件上传解析器
 * Created by luliru on 2017/1/13.
 */
public class CustomizedMultipartResolver extends CommonsMultipartResolver {

    @Override
    public boolean isMultipart(javax.servlet.http.HttpServletRequest request) {
        String uri = request.getRequestURI();
//        System.out.println(uri);
        //过滤使用百度UEditor的URI
        if (uri.indexOf("ueditor/handle") > 0) {     //此处拦截路径即为上面编写的controller路径
            return false;
        }
        return super.isMultipart(request);
    }
}
