/**
 * Copyright (c) 2017, Alex. All rights reserved.
 */
package com.ygg.admin.view;

/**
 * @author <a href="mailto:zhongchao@gegejia.com">zhong</a>
 * @version 1.0 2017/9/30
 * @since 1.0
 */
public class ResultView
{
    private int status;

    private String msg;

    private Object data;

    public int getStatus()
    {
        return status;
    }

    public String getMsg()
    {
        return msg;
    }

    public Object getData()
    {
        return data;
    }

    public static ResultView success() {
        ResultView view = new ResultView();
        view.status = 1;
        view.msg = "成功";
        return view;
    }

    public static ResultView success(Object data) {
        ResultView view = success();
        view.data = data;
        return view;
    }

    public static ResultView fail(int status, String msg) {
        ResultView view = new ResultView();
        view.status = status;
        view.msg = msg;
        return view;
    }
}
