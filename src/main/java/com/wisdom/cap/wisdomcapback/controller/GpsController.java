package com.wisdom.cap.wisdomcapback.controller;

import com.wisdom.cap.wisdomcapback.service.GpsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 86138
 * GPS控制类
 */

@RestController
public class GpsController {
    @Resource
    private GpsService gpsService;

    /**
     * 使用gpsReader对象从GPS模块中读取位置信息
     * @return
     */
    @GetMapping("gps")
    public String index() {
        return gpsService.readGPSData();
    }
}
