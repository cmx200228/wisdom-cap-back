package com.wisdom.cap.wisdomcapback.util;

import java.io.File;
import java.io.FileOutputStream;

import com.iflytek.cloud.speech.*;
import org.springframework.beans.factory.annotation.Value;

/*
public class TtsUtil{

    @Value("${msc.appId}")
    private static String appId;
    @Value("${msc.apiKey}")
    private static String apiKey;
    @Value("${msc.apiSecret}")
    private static String apiSecret;

    private static SpeechSynthesizer speechSynthesizer;

    static {
        SpeechUtility.createUtility(SpeechConstant.APPID + "=" + appId);
        speechSynthesizer = SpeechSynthesizer.createSynthesizer();
        speechSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        speechSynthesizer.setParameter(SpeechConstant.SPEED, "50");
        speechSynthesizer.setParameter(SpeechConstant.VOLUME, "80");
        speechSynthesizer.setParameter(SpeechConstant.PITCH, "50");
        speechSynthesizer.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
    }

    public static void textToSpeech(String text, String filePath) {
        SynthesizeToUriListener synthesizeToUriListener = new SynthesizeToUriListener() {
            public void onBufferProgress(int progress) {
            }

            public void onSynthesizeCompleted(String uri, SpeechError error) {
                if (error == null) {
                    try {
                        FileOutputStream fos = new FileOutputStream(new File(filePath));
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = speechSynthesizer.read(buffer, 0, buffer.length)) != -1) {
                            fos.write(buffer, 0, len);
                        }
                        fos.flush();
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onEvent(int i, int i1, int i2, int i3, Object o, Object o1) {

            }
        };

        speechSynthesizer.synthesizeToUri(text, filePath, synthesizeToUriListener);
    }
}
*/
