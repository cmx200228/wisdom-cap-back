package com.wisdom.cap.wisdomcapback.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.iflytek.cloud.speech.*;
import com.wisdom.cap.wisdomcapback.util.Base64Util;
import com.wisdom.cap.wisdomcapback.util.PrintedWordUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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

    @Resource
    PrintedWordUtil printedWordUtil;


    /**
     * 返回图片中的信息
     * @return 图片中的信息
     * @throws Exception 异常
     */
    @GetMapping(value = "/image")
    public String getImage() throws Exception {
        //解析返回的图片内容，获取图片的base64编码
        String request = printedWordUtil.getRequest();
        //将字符串转换为json对象
        JSONObject jsonObject = JSON.parseObject(request);
        //获取到text的值
        String string = jsonObject
                .getJSONObject("payload")
                .getJSONObject("ocr_output_text")
                .getString("text");

        //将base64编码解码成UTF-8格式的字符串
        String decode = Base64Util.decode(string);
        JSONObject jsonObject1 = JSON.parseObject(decode);
        JSONArray jsonArray = jsonObject1.getJSONArray("pages")
                .getJSONObject(0)
                .getJSONArray("lines");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
            String content = jsonObject2.getString("content");
            stringBuilder.append(content);
        }

        return stringBuilder.toString();
    }

    @GetMapping(value = "/speechRecognition")
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
