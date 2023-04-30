package com.wisdom.cap.wisdomcapback.service.impl;

import com.wisdom.cap.wisdomcapback.exception.BusinessException;
import com.wisdom.cap.wisdomcapback.exception.BusinessExceptionEnum;
import com.wisdom.cap.wisdomcapback.service.GpsService;
import com.wisdom.cap.wisdomcapback.util.GPSUtil;
import org.springframework.stereotype.Service;

/**
 * GPS定位实现类
 * @author 86138
 */

@Service("gpsService")
public class GpsServiceImpl implements GpsService {
    /**
     * 从GPS模块中读取位置信息
     *
     * @return 位置信息
     */
    @Override
    public String readGPSData() {
        String gps;
        try {
            int flag = GPSUtil.readGPSData();
            Thread.sleep(1000);
            System.out.println("flag:" + flag);
            if (flag == 1) {
                System.out.println("GPS数据读取成功");
                gps = GPSUtil.getLatLon();
            } else {
                System.out.println("GPS数据读取失败");
                gps = "GPS数据读取失败";
            }
        } catch (Exception e) {
            throw new BusinessException(BusinessExceptionEnum.GPS_READ);
        }
        return gps;
    }
}
