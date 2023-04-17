package com.wisdom.cap.wisdomcapback.util;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.*;

/**
 * OpenCV工具类，使用javacv进行图像处理
 * @author 陈蒙欣
 * @date 2023/3/21 8:54
 */
@Component
@Scope(value = "singleton")
public class OpenCVUtil {

    private static final Logger logger = LoggerFactory.getLogger(OpenCVUtil.class);

    /**
     * 加载图片
     * @param imagePath 图片路径
     * @return 返回加载后的Mat对象
     */
    public static Mat loadImage(String imagePath) {
        return imread(imagePath);
    }

    /**
     * 保存图片
     * @param image 待保存的图片
     * @param outputPath 图片保存路径
     */
    public static void saveImage(Mat image, String outputPath) {
        imwrite(outputPath, image);
    }

    /**
     * 改变图片大小
     * @param image 原始图片
     * @param width 目标宽度
     * @param height 目标高度
     * @return 返回大小改变后的图片
     */
    public static Mat resizeImage(Mat image, int width, int height) {
        Mat resizedImage = new Mat();
        resize(image, resizedImage, new Size(width, height));
        return resizedImage;
    }

    /**
     * 图像对比度增强
     * @param image 待增强的图片
     * @return 返回增强后的图片
     */
    public static Mat imageContrastEnhance(Mat image) {
        logger.info("图片对比度增强");
        cvtColor(image, image, COLOR_BGR2GRAY);
        Mat destination = new Mat(image.rows(), image.cols(), image.type());
        equalizeHist(image, destination);
        return destination;
    }

    /**
     * 图像增强
     * 使用高斯滤波器，高斯滤镜可减少图像中的噪声并使它看起来更好(或更高分辨率)
     * @param image 待增强的图片
     * @return 返回增强后的图片
     */
    public static Mat imageDefinitionEnhance(Mat image) {
        logger.info("图片清晰度增强");
        Mat destination = new Mat(image.rows(), image.cols(), image.type());
        GaussianBlur(image, destination, new Size(0, 0), 10);
        Core.addWeighted(image, 1.5, destination, -0.5, 0, destination);
        return destination;
    }

    /**
     * 图像去噪
     * @param image 待去噪的图片
     * @param ksize 去噪程度，数值越大去噪效果越明显
     * @return 返回去噪后的图片
     */
    public static Mat denoiseImage(Mat image, int ksize) {
        Mat denoisedImage = new Mat();
        bilateralFilter(image, denoisedImage, ksize, ksize * 2, ksize / 2);
        return denoisedImage;
    }

    /**
     * 保存图片
     * @param image 待保存的图片
     * @return 返回图片保存路径
     */
    public static String saveImage(Mat image){
        String path = "IMAGE/dispose.jpg";
        imwrite(path, image);
        return path;
    }
}
