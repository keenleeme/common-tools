package com.latrell.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 *
 * @author liz
 * @date 2020-06-01 14:28:10
 **/
public class Md5Utils {

    /**
     * MD5加密类
     *
     * @param str 要加密的字符串
     * @return java.lang.String 加密后的字符串
     * @author liz
     * @date 2020-06-01 14:28:15
     **/
    public static String md5Encode(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            int i;
            StringBuilder buf = new StringBuilder();
            for (int offset = 0; offset < byteDigest.length; offset++) {
                i = byteDigest[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            // 32位加密
            return buf.toString().toUpperCase();
            // 16位的加密
//            return buf.toString().substring(8, 24).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void main(String[] args) {
        System.out.println(md5Encode("123456"));
    }
}
