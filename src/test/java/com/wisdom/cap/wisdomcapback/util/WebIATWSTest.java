package com.wisdom.cap.wisdomcapback.util;

import org.junit.jupiter.api.Test;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

/**
 * @author 陈蒙欣
 * @date 2023/4/29 16:23
 */
class WebIATWSTest {

    @Test
    void voiceChange() {
        try {
            String s = VoiceRecognizeUtil.voiceChange("AUDIO_FREQUENCY/16k_10.pcm");
            System.out.println(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void voiceChangeStarted() {
        try {
            AudioRecorder audioRecorder = new AudioRecorder();
            audioRecorder.start();
            // 5秒后停止录音
            Thread.sleep(5000);
            String stop = audioRecorder.stop();
            String s = VoiceRecognizeUtil.voiceChange(stop);
            System.out.println(s);
        } catch (LineUnavailableException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
