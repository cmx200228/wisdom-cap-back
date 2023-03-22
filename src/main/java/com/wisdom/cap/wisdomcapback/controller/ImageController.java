package com.wisdom.cap.wisdomcapback.controller;

import com.wisdom.cap.wisdomcapback.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 陈蒙欣
 * @date 2023/3/21 9:26
 */
@RestController
public class ImageController {
    @Value("${image.path}")
    String imageUrl;
    @Resource
    private ImageService imageService;

    @GetMapping("/enhance")
    public void enhanceThePicture() {
        imageService.enhanceThePicture(imageUrl);
    }
}
