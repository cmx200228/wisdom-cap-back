package com.wisdom.cap.wisdomcapback.controller;

import com.wisdom.cap.wisdomcapback.service.impl.GpsServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author 86138
 * GPS控制类
 */

@Controller
@RequestMapping("/GPS")
public class GpsController {
    @Resource
    private GpsServiceImpl gpsService;

    /*实例化GpsServiceImpl<串口名称为“/dev/ttyACM0”,这通常是Linux系统下用于连接串口设备的默认名称>*/
    public GpsController() {
        gpsService = new GpsServiceImpl("/dev/ttyACM0");
    }

    /**
     * 使用gpsReader对象从GPS模块中读取位置信息并将其添加到Model对象中
     * @param model
     * @return
     */
    @PutMapping ()
    public String index(Model model) {
        String gpsData = gpsService.readGPSData();
        model.addAttribute("gpsData", gpsData);
        return gpsData;
    }
}
