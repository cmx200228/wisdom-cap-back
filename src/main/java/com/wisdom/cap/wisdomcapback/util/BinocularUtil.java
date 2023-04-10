package com.wisdom.cap.wisdomcapback.util;

import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.opencv.opencv_calib3d.StereoBM;
import org.bytedeco.opencv.opencv_core.Mat;

/**
 * 双目工具类
 * @author 陈蒙欣
 * @date 2023/3/30 10:46
 */
public class BinocularUtil {
    public static String getObstacle(){
        // 初始化摄像头
        OpenCVFrameGrabber grabber1 = new OpenCVFrameGrabber(1);
        OpenCVFrameGrabber grabber2 = new OpenCVFrameGrabber(2);
        try {
            grabber1.start();
            grabber2.start();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        }


        // 创建用于图像处理的Mat对象
        Mat image1 = new Mat();
        Mat image2 = new Mat();
        Mat gray1 = new Mat();
        Mat gray2 = new Mat();

        // 创建BM算法对象
        StereoBM stereo = StereoBM.create();
        return "obstacle";
    }
}
