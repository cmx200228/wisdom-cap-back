package com.wisdom.cap.wisdomcapback.service;

import org.opencv.core.Mat;

/**
 * @author 陈蒙欣
 * @date 2023/3/21 9:01
 */
public interface ImageService {
    /**
     * 图片增强
     * @param imageUrl 图片地址
     * @return 增强后的图片地址
     */
    String enhanceThePicture(String imageUrl);

    /**
     * 保存图片
     * @param mat 图片矩阵
     * @return 图片地址
     */
    String saveImage(Mat mat);
}
