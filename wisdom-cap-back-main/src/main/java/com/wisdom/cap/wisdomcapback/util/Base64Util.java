package com.wisdom.cap.wisdomcapback.util;

import java.util.Base64;

/**
 * Base64工具类
 * @author 陈蒙欣
 * @date 2023/3/20 13:56
 */
public class Base64Util {
    public Base64Util() throws IllegalAccessException {
        throw new IllegalAccessException("工具类不能实例化");
    }
    public static String encode(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    public static String decode(String str) {
        byte[] decodedBytes = Base64.getDecoder().decode(str);
        return new String(decodedBytes);
    }
}
