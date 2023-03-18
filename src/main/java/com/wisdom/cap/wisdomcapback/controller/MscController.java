package com.wisdom.cap.wisdomcapback.controller;

import com.iflytek.cloud.speech.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.sampled.*;

/**
 * @author 陈蒙欣
 * @date 2023/3/17 21:05
 */
@RestController
@RequestMapping("/msc")
public class MscController{
    @Value("${msc.appId}")
    private String APP_ID;

    @RequestMapping(value = "/speechRecognition", method = RequestMethod.POST)
    public String speechRecognition() throws Exception {

        // 设置音频输入流
        AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);
        microphone.open(format);
        microphone.start();

        // 设置音频输出流
        SourceDataLine speaker = AudioSystem.getSourceDataLine(format);
        speaker.open(format);
        speaker.start();

        // 设置语音识别器
        SpeechUtility.createUtility(SpeechConstant.APPID + "=" + APP_ID);
        SpeechRecognizer recognizer = SpeechRecognizer.createRecognizer();
        recognizer.setParameter(SpeechConstant.DOMAIN, "iat");
        recognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        recognizer.setParameter(SpeechConstant.ACCENT, "mandarin");

        // 开始识别语音
        recognizer.startListening(new RecognizerListener() {

            @Override
            public void onVolumeChanged(int i) {
// 处理音量变化事件
            }

            @Override
            public void onBeginOfSpeech() {
                // 处理语音开始事件
            }

            @Override
            public void onEndOfSpeech() {
                // 处理语音结束事件
            }

            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                // 处理语音识别结果
            }

            @Override
            public void onError(SpeechError speechError) {
                // 处理语音识别错误
            }

            @Override
            public void onEvent(int i, int i1, int i2, String s) {

            }
        });

        return "Speech recognition started";
    }
}
