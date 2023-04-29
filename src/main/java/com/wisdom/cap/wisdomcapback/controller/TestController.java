package com.wisdom.cap.wisdomcapback.controller;

import com.wisdom.cap.wisdomcapback.exception.BusinessException;
import com.wisdom.cap.wisdomcapback.exception.BusinessExceptionEnum;
import com.wisdom.cap.wisdomcapback.util.AudioRecorder;
import com.wisdom.cap.wisdomcapback.util.CameraUtil;
import com.wisdom.cap.wisdomcapback.util.VoiceRecognizeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

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
        String result;
        try {
            AudioRecorder audioRecorder = new AudioRecorder();
            audioRecorder.start();
            // 5秒后停止录音
            Thread.sleep(5000);
            String stop = audioRecorder.stop();
            result = VoiceRecognizeUtil.voiceChange(stop);
        } catch (LineUnavailableException | InterruptedException | IOException e) {
            throw new BusinessException(BusinessExceptionEnum.VOICE_RECOGNIZE);
        }
        return result;
    }
}
