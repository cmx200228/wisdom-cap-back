package com.wisdom.cap.wisdomcapback.service;

/**
 * @author 陈蒙欣
 * @date 2023/4/29 23:12
 */
public interface GpsService {
    /**
     * 从GPS模块中读取位置信息
     * @return 位置信息
     */
    String readGPSData();
}
