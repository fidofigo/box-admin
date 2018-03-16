package com.ygg.admin.util;

import java.util.Random;

/**
 * Created by fidofigo on 16/7/29.
 */
public class InviteCodeUtil
{

    /**自定义进制(0,1没有加入,容易与o,l混淆)*/
    private static final char[] hex = new char[] { 'q', 'w', 'e', '8', 'a', 's', '2', 'd', 'z', 'x', '9', 'c', '7', 'p', '5', 'i', 'k', '3', 'm', 'j', 'u', 'f', 'r', '4', 'v', 'y', 'l', 't', 'n', '6', 'b', 'g', 'h' };
    /**自动补全组(不能与自定义进制有重复)*/
    private static final char completion = 'O';
    /**进制长度*/
    private static final int len = hex.length;
    /**序列最小长度*/
    private static final int minLen = 6;

    /**
     * 根据ID生成六位随机码
     * @param userId ID
     * @return 随机码
     */
    public static String getInviteCode(long userId) {
        char[] buf = new char[32];
        int charPos = 32;

        while ((userId / len) > 0) {
            buf[--charPos] = hex[(int)(userId % len)];
            userId /= len;
        }
        buf[--charPos] = hex[(int) (userId % len)];
        String str = new String(buf, charPos, (32 - charPos));
        //不够长度的自动随机补全
        if (str.length() < minLen) {
            StringBuffer sb = new StringBuffer();
            sb.append(completion);
            Random rnd = new Random();
            for (int i = 1; i < minLen - str.length(); i++) {
                sb.append(hex[rnd.nextInt(len)]);
            }
            str += sb.toString();
        }
        return str;
    }

    /**
     * 根据邀请码获得用户ID
     * @param inviteCode 邀请码
     * @return 随机码
     */
    public static long inviteCodeToUserId(String inviteCode) {
        char chs[] = inviteCode.toCharArray();
        long userId = 0L;
        for(int i = 0; i < chs.length; i++) {
            int ind = 0 ;
            for(int j=0; j < len; j++) {
                if(chs[i] == hex[j]) {
                    ind=j;
                    break;
                }
            }
            if(chs[i] == completion) {
                break;
            }
            if(i > 0) {
                userId = userId * len + ind;
            } else {
                userId = ind;
            }
        }
        return userId;
    }
}
