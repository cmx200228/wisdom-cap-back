package com.wisdom.cap.wisdomcapback.util;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.opencv.opencv_core.IplImage;import org.slf4j.Logger;import org.slf4j.LoggerFactory;

import static org.bytedeco.opencv.helper.opencv_imgcodecs.cvSaveImage;


/**
 * @author 陈蒙欣
 * @date 2023/3/21 11:40
 */
public class CameraUtil {
    private static final Logger logger = LoggerFactory.getLogger(CameraUtil.class);

    /**
     * 调用本地摄像头拍照并将图片保存
     * @param filename 保存的文件名
     */
    public static void capture(String filename) {
        FrameGrabber grabber = null;
        try {
            grabber = new OpenCVFrameGrabber(0);
            // 开始捕获
            grabber.start();

            // 从摄像头捕获图像
            Frame frame = grabber.grab();
            IplImage image = null;
            if (frame != null) {
                // 将Frame对象转换成IplImage对象
                OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
                image = converter.convert(frame);
            }

            // 将图像保存为文件
            if (image != null) {
                cvSaveImage(filename, image);
            }
            // 停止捕获
            grabber.stop();
        } catch (Exception e) {
            logger.error("调用本地摄像头拍照并将图片保存失败", e);
        } finally {
            try {
                assert grabber != null;
                grabber.release();
            } catch (Exception e) {
                // do nothing
                logger.error("调用本地摄像头拍照并将图片保存失败", e);
            }
        }
    }
}
