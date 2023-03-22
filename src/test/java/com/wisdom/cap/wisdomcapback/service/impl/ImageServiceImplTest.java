package com.wisdom.cap.wisdomcapback.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author 陈蒙欣
 * @date 2023/3/21 9:07
 */
@SpringBootTest
class ImageServiceImplTest {

    @Resource
    private ImageServiceImpl imageService;
    @Value("${image.path}")
    String imageUrl;
    @Test
    void enhanceThePicture() {
        imageService.enhanceThePicture("IMAGE/pic.jpg");
    }

    @Test
    void jdk(){
        System.out.println(System.getProperty("java.library.path"));
    }
}
