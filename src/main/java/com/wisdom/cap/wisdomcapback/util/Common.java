package com.wisdom.cap.wisdomcapback.util;

/**
 * 公共工具类
 * @author 陈蒙欣
 * @date 2023/4/29 18:40
 */
public class Common {
    /**
     * 是否停止语音识别
     * 使用volatile修饰，保证线程可见性，被修改后能立刻获得值，而不是使用缓存
     */
    public static volatile boolean isStop = false;
    /**
     * 语音识别结果
     */
    public static String result = "";
}
