package com.latrell.common.config.rsa;

import com.latrell.common.util.Md5Utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

/**
 * Base64 加密工具类
 *
 * @author liz
 * @date 2022/12/12-17:29
 */
public class Base64Utils {

    public static void main(String[] args) {
//        byte[] encode = Base64.getEncoder().encode("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCY0n6xhxll-usEADf0k7Uje3S7M891oguY23n0VqiBlU-nOqeee1q1bhSOHJ4Aa_cQ15Lcw5ZT1iaZTM78PtibeV_rHMyLkVVClYdag3L7SzRpmarFnos5cBOCI2iT8C8KQ0q4NOjvnmJaXbNeQ6Yw21eGbKxCuYIiexZObLNjywIDAQAB".getBytes(StandardCharsets.UTF_8));
//        System.out.println("加密密文：" + new String(encode));
//        byte[] encodes = Base64.getDecoder().decode(encode);
//        System.out.println("解密明文：" + new String(encodes));
//
//        System.out.println(UUID.randomUUID());

        System.out.println(Md5Utils.md5Encode("137673@qq.com"));
        System.out.println(Md5Utils.md5Encode("李四"));
    }

}
