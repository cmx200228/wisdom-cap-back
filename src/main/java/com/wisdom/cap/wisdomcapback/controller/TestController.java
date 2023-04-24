package com.wisdom.cap.wisdomcapback.controller;

import com.iflytek.cloud.speech.SpeechUtility;
import com.wisdom.cap.wisdomcapback.util.CameraUtil;
import com.wisdom.cap.wisdomcapback.util.VoiceRecognizeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.LinkedBlockingQueue;

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

    @GetMapping("/string")
    public String getString() {
        SpeechUtility.createUtility("appid=f590a1b8");
        LinkedBlockingQueue linkedBlockingQueue = VoiceRecognizeUtil.voiceChangeStart();
        String s;
        try {
            s = linkedBlockingQueue.take().toString();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return s;
    }
}
