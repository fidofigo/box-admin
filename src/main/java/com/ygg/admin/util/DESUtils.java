/**
 * Copyright (c) 2017, Alex. All rights reserved.
 */
package com.ygg.admin.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * des加密算法
 *
 * @author <a href="mailto:zhongchao@gegejia.com">zhong</a>
 * @version 1.0 2017/7/19
 * @since 1.0
 */
public class DESUtils
{
    private static String coder_name = "DES";
    private static String algorithm = "DES/ECB/PKCS5Padding";
    private final static String encoding = "UTF-8";

    private static byte[] initKey(byte[] password)
        throws Exception
    {
        KeyGenerator generator = KeyGenerator.getInstance(coder_name);

        //初始化密钥生成器
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(password);
        generator.init(56, secureRandom);

        //生成密钥
        SecretKey secretKey = generator.generateKey();

        //获取二进制密钥

        return secretKey.getEncoded();
    }

    private static Key toKey(byte[] password)
        throws Exception
    {
        return new SecretKeySpec(initKey(password), coder_name);
    }

    /**
     * 创建密码器
     *
     * @param mode
     * @return
     */
    private static Cipher initCipher(int mode, byte[] password)
        throws Exception
    {
        //获得AES密钥
        Key key = toKey(password);

        //创建密码器
        Cipher cipher = Cipher.getInstance(algorithm);

        //初始化，设置为加密模式
        cipher.init(mode, key);

        return cipher;
    }

    private static byte[] encrypt(byte[] data, byte[] password)
        throws Exception
    {
        Cipher cipher = initCipher(Cipher.ENCRYPT_MODE, password);
        return cipher.doFinal(data);
    }

    public static String encrypt( String data, String password)
    {
        try
        {
            byte[] result = encrypt(data.getBytes(encoding), password.getBytes());
            return Base64.getUrlEncoder().encodeToString(result);
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    private static byte[] decrypt(byte[] data, byte[] password)
        throws Exception
    {
        Cipher cipher = initCipher(Cipher.DECRYPT_MODE, password);
        return cipher.doFinal(data);
    }

    public static String decrypt( String data, String password)
    {
        try
        {
            byte[] result = decrypt(Base64.getUrlDecoder().decode(data), password.getBytes());
            return new String(result, encoding);
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }
}
