package com.wisdom.cap.wisdomcapback.service.impl;

import com.wisdom.cap.wisdomcapback.service.ImageService;
import com.wisdom.cap.wisdomcapback.util.OpenCVUtil;
import org.opencv.core.Mat;
import org.springframework.stereotype.Service;

/**
 * @author 陈蒙欣
 * @date 2023/3/21 9:04
 */
@Service("imageService")
public class ImageServiceImpl implements ImageService {
    /**
     * 图片增强
     *
     * @param imageUrl 图片地址
     * @return 增强后的图片地址
     */
    @Override
    public String enhanceThePicture(String imageUrl) {
        Mat mat = OpenCVUtil.loadImage(imageUrl);
        Mat mat2 = OpenCVUtil.imageContrastEnhance(mat);
        return OpenCVUtil.saveImage(mat2);
    }

    /**
     * 保存图片
     *
     * @param mat 图片矩阵
     * @return 图片地址
     */
    @Override
    public String saveImage(Mat mat) {
        return OpenCVUtil.saveImage(mat);
    }
}
