package com.latrell.common.config.rsa;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * DES 加解密算法工具类
 *
 * @author liz
 * @date 2022/12/12-19:12
 */
public class DESUtil {

    private static final String DES_ALGORITHM = "DES";
    private static final String PASSWORD = "9588028820109132570743325311898426347857298773549468758875018579537757772163084478873699447306034466200616411960574122434059469100235892702736860872901247123456";

    /**
     * 加密
     *
     * @param data byte[]
     * @return byte[]
     */
    public static byte[] encrypt(byte[] data) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(PASSWORD.getBytes());
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
            SecretKey secureKey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secureKey, random);
            // 获取数据并加密,正式执行加密操作
            return cipher.doFinal(data);
        } catch (Throwable e) {
            throw new RuntimeException("加密字符串[" + new String(data) + "]时遇到异常", e);
        }
    }

    /**
     * 解密
     *
     * @param data byte[]
     * @return byte[]
     */
    public static byte[] decrypt(byte[] data) {
        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom random = new SecureRandom();
            // 创建一个DESKeySpec对象
            DESKeySpec desKey = new DESKeySpec(PASSWORD.getBytes());
            // 创建一个密匙工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
            // 将DESKeySpec对象转换成SecretKey对象
            SecretKey secureKey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, secureKey, random);
            // 真正开始解密操作
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + new String(data) + "]时遇到异常", e);
        }
    }

    public static void main(String[] args) {
        String data = "{\"手机号\": \"15241523602|~&&~|13620145874\",\"姓名\": \"张三|~&&~|李四\", \"邮箱\": \"137451@qq.com|~&&~|45287@163.com\"}";
        byte[] encrypt = DESUtil.encrypt(data.getBytes(StandardCharsets.UTF_8));
        System.out.println("加密后的密文：" + new String(encrypt));
        byte[] encrypt1 = DESUtil.decrypt(encrypt);
        System.out.println("解密后的明文：" + new String(encrypt1));
    }
}
