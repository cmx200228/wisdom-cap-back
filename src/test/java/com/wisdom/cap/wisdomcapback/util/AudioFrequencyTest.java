package com.wisdom.cap.wisdomcapback.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

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
        AudioFrequency.mp3("AUDIO_FREQUENCY/1682322905208.mp3");
    }

    @Test
    void pcm() {
        try {
            String path = "AUDIO_FREQUENCY/aa.pcm";
            AudioFrequency.pcm("AUDIO_FREQUENCY/1682777801720.pcm");
        } catch (IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
}
