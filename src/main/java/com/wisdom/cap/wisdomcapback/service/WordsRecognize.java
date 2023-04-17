package com.wisdom.cap.wisdomcapback.service;

import com.wisdom.cap.wisdomcapback.controller.MscController;
import com.wisdom.cap.wisdomcapback.util.CameraUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 调用摄像头拍照，存储照片 -> 解析照片文字
 *
 */

@Component
public class WordsRecognize {

    private MscController mscController;

    @Autowired
    public WordsRecognize(MscController mscController) {
        this.mscController = mscController;
    }

    public String recognizeWords() throws Exception {

        /**
         * 调用摄像头，拍摄照片，规定存储照片地址
         */
        String filename = "D:/IDEA/Project/A-Project/wisdom-cap-back/IMAGE/pic1.jpg";
        CameraUtil.capture(filename);
        /**
         * 读取照片文字（json格式）
         */
        String image = mscController.getImage();
        System.out.println("1：" + image);
//        调用解析json字串方法，提取文字部分，返回提取出的字串
        String imageWords = parseImage(image);
        return imageWords;
    }

    /**
     * 解析图片识别结果
     *
     * @param json 图片识别结果
     * @return 解析后的字符串
     */
    private static String parseImage(String json) {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray pages = jsonObject.getJSONArray("pages");
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < pages.length(); i++) {
            JSONArray lines = pages.getJSONObject(i).getJSONArray("lines");
            for (int j = 0; j < lines.length(); j++) {
                content.append(lines.getJSONObject(j).getString("content"));
            }
        }
        System.out.println("3：" + content);
        return content.toString();
    }

}
