/**
 * Copyright (c) 2017, Alex. All rights reserved.
 */
package com.ygg.admin.util;

/**
 * 加/解密工具类
 *
 * @author <a href="mailto:zhongchao@gegejia.com">zhong</a>
 * @version 1.0 2017/9/30
 * @since 1.0
 */
public class EDUtils
{
    private final static String ENCRYPT_KEY = "gegejie2017";

    /**
     * 对明文字符串加密
     *
     * @param cleartext 明文
     * @return
     */
    public static String encrypt(String cleartext)
    {
        return DESUtils.encrypt(cleartext, ENCRYPT_KEY).replaceAll("=", "");
    }

    /**
     * 红包id加密，返回前端
     *
     * @param code
     * @return
     */
    public static String decrypt(String code)
    {
        int mod = code.length() % 4;
        for (int i = 0; i < 4 -mod; i++)
        {
            code += "=";
        }

        return DESUtils.decrypt(code, ENCRYPT_KEY);
    }

    public static void main(String[] args)
    {
        System.out.println(encrypt("1"));
    }
}
