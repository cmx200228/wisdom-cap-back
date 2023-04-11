package com.wisdom.cap.wisdomcapback.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 陈蒙欣
 * @date 2023/4/11 13:46
 */
@SpringBootTest
class AudioFrequencyTest {

    @Test
    void wav() {
        AudioFrequency.wav("AUDIO_FREQUENCY/1.wav");
    }

    @Test
    void mp3() {
        AudioFrequency.mp3("AUDIO_FREQUENCY/1.mp3");
    }

    @Test
    void pcm() {
        AudioFrequency.pcm("AUDIO_FREQUENCY/16k.pcm");
    }
}
