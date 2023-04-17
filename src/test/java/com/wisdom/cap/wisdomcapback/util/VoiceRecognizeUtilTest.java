package com.wisdom.cap.wisdomcapback.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 陈蒙欣
 * @date 2023/4/11 20:07
 */
@SpringBootTest
class VoiceRecognizeUtilTest {

    @Test
    void voiceChangeStart() {

        VoiceRecognizeUtil.voiceChangeStart();
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        VoiceRecognizeUtil.voiceChangeStart();
    }
}
