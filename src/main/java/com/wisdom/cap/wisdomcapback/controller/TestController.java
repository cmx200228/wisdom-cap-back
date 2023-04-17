package com.wisdom.cap.wisdomcapback.controller;

import com.wisdom.cap.wisdomcapback.util.CameraUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 陈蒙欣
 * @date 2023/3/25 2:12
 */
@RestController
public class TestController {

    @GetMapping("/pictrue")
    public void getNews() {
        //树莓派保存地址
        CameraUtil.capture("/home/zhihuimao/Downloads/pic.jpg");
//        CameraUtil.capture("F:/pic.jpg");
    }
}
