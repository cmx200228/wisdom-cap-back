package com.wisdom.cap.wisdomcapback.service.impl;

import com.wisdom.cap.wisdomcapback.controller.ImageController;
import com.wisdom.cap.wisdomcapback.controller.MscController;
import com.wisdom.cap.wisdomcapback.util.PrintedWordUtil;
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
    @Resource
    MscController mscController;
    @Value("${image.path}")
    String imageUrl;
    @Test
    void enhanceThePicture() {
        imageService.enhanceThePicture("IMAGE/pic1.jpg");
    }

    @Test
    void jdk(){
        System.out.println(System.getProperty("java.library.path"));
    }

    @Test
    void print() throws Exception {
        String image = mscController.getImage();
        System.out.println(image);

    }
}
