package com.wisdom.cap.wisdomcapback.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 用于存放加密解密的方法
 *
 * @author 陈蒙欣
 * @date 2023/4/24 10:17
 */
public class Encoding {

    private Encoding() throws IllegalAccessException {
        throw new IllegalAccessException("工具类不能实例化");
    }

    /**
     * 用于生成HMAC-SHA256加密字符串
     *
     * @param secret 密钥
     * @param message 消息
     * @return 加密后的字符串
     * @throws Exception 异常
     */
    public static String hmacSHA256(String secret, String message) throws Exception {
        String hash;
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        hmacSha256.init(secret_key);
        byte[] bytes = hmacSha256.doFinal(message.getBytes());
        hash = byteArrayToHexString(bytes);
        return hash;
    }

    /**
     * 将加密后的字节数组转换成字符串
     *
     * @param b 字节数组
     * @return 字符串
     */
    public  static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b!=null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                hs.append('0');
            }
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }
}
