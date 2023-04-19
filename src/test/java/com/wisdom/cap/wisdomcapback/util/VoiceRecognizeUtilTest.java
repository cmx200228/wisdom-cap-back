package com.wisdom.cap.wisdomcapback.util;

import com.iflytek.cloud.speech.SpeechUtility;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author 陈蒙欣
 * @date 2023/4/11 20:07
 */
@SpringBootTest
class VoiceRecognizeUtilTest {

    @Test
    void voiceChangeStart() throws InterruptedException {
        SpeechUtility.createUtility("appid=f590a1b8");
        LinkedBlockingQueue linkedBlockingQueue = VoiceRecognizeUtil.voiceChangeStart();
        String s = linkedBlockingQueue.take().toString();
        System.out.println(s);
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        VoiceRecognizeUtil.voiceChangeStart();
    }
}
