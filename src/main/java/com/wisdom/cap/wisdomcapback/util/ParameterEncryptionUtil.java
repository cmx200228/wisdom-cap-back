package com.wisdom.cap.wisdomcapback.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 对高德请求进行加密
 * @author 陈蒙欣
 * @date 2023/4/10 14:03
 */
public class ParameterEncryptionUtil {
    private ParameterEncryptionUtil() {
    }
    /**
     * 生成签名
     * @param params 请求参数
     * @param privateKey 私钥
     * @return 签名字符串
     */
    public static String generateSignature(Map<String, String> params, String privateKey) {
        // 将参数按照键名的升序排序
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        // 将排序后的参数按照key=value的形式拼接
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            String value = params.get(key);
            sb.append(key).append("=").append(value).append("&");
        }
        // 删除最后一个&
        sb.delete(sb.length()-1, sb.length());
        // 添加私钥
        sb.append(privateKey);
        // 进行MD5加密
        return md5(sb.toString());
    }

    /**
     * MD5加密
     * @param str 待加密字符串
     * @return 加密后字符串
     */
    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                //将byte类型的变量转换成一个十六进制字符串，先使用使用"& 0xff"操作将byte类型的值转换成等价的无符号整数，即0~255之间的数，
                // 然后再使用Integer.toHexString()方法将其转换成十六进制字符串
                String s = Integer.toHexString(b & 0xff);
                if (s.length() == 1) {
                    sb.append("0");
                }
                sb.append(s);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
