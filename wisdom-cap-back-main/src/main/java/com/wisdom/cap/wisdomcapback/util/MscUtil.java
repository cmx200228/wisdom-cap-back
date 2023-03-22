package com.wisdom.cap.wisdomcapback.util;

import com.iflytek.cloud.speech.RecognizerListener;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechRecognizer;

/**
 * @author 陈蒙欣
 * @date 2023/3/17 22:14
 */
public class MscUtil {
    private SpeechRecognizer recognizer;

    /** 构造函数，创建SpeechRecognizer实例
     *
     */
    public MscUtil() {
        recognizer = SpeechRecognizer.createRecognizer();
    }

    /** 初始化方法，设置相关参数
     *
     * @param appId 应用ID
     */
    public void init(String appId) {
        recognizer.setParameter(SpeechConstant.APPID, appId);
        recognizer.setParameter(SpeechConstant.DOMAIN, "iat");
        recognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        recognizer.setParameter(SpeechConstant.ACCENT, "mandarin");
    }

    /** 开始识别方法，传入音频数据和RecognizerListener
     *
     * @param audioData 音频数据
     * @param listener RecognizerListener
     */
    public void startRecognize(byte[] audioData, RecognizerListener listener) {
        recognizer.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
        recognizer.startListening(listener);
        recognizer.writeAudio(audioData, 0, audioData.length);
        recognizer.stopListening();
    }

    /** 停止识别方法
     *
     */
    public void stopRecognize() {
        recognizer.cancel();
    }
}
