package com.wisdom.cap.wisdomcapback.util;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * 录制pcm格式音频
 * 录音时长根据需要可调
 * @author 陈蒙欣
 * @date 2023/4/29 21:35
 */
public class AudioRecorder {
    /**
     * 采样率
     */
    private static final int SAMPLE_RATE = 16000;
    /**
     * 位长
     */
    private static final int BITS_PER_SAMPLE = 16;
    /**
     * 声道数
     */
    private static final int CHANNELS = 1;
    private static final AudioFormat AUDIO_FORMAT = new AudioFormat(SAMPLE_RATE, BITS_PER_SAMPLE, CHANNELS, true, false);
    private static final int BUFFER_SIZE = 4096;

    private final TargetDataLine line;
    private final ByteArrayOutputStream outputStream;
    private static Date begin;
    private static Date end;

    private Thread thread;

    /**
     * 初始化，创建录音对象
     * @throws LineUnavailableException 线路不可用异常
     */
    public AudioRecorder() throws LineUnavailableException {
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, AUDIO_FORMAT);
        if (!AudioSystem.isLineSupported(info)) {
            throw new LineUnavailableException("Line not supported");
        }
        line = (TargetDataLine) AudioSystem.getLine(info);
        line.open(AUDIO_FORMAT, BUFFER_SIZE);
        outputStream = new ByteArrayOutputStream();
    }

    /**
     * 开始录音
     */
    public void start() {
        line.start();
        System.out.println("开始录音");
        begin = new Date();
        thread = new Thread(() -> {
            byte[] buffer = new byte[BUFFER_SIZE];
            while (true) {
                int bytesRead = line.read(buffer, 0, BUFFER_SIZE);
                if (bytesRead > 0) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * 停止并保存录音
     * @return 文件名
     * @throws IOException IO异常
     */
    public String stop() throws IOException {
        String saveToFile = saveToFile();
        System.out.println("停止录音");
        end = new Date();
        System.out.println("录音时长：" + (end.getTime() - begin.getTime()) + "ms");
        line.stop();
        line.close();
        outputStream.close();
        thread.interrupt();
        return saveToFile;
    }

    public byte[] getAudioData() {
        return outputStream.toByteArray();
    }

    /**
     * 将录音文件保存到本地
     * @return 文件名
     * @throws IOException IO异常
     */
    public String saveToFile() throws IOException {
        String fileName = "AUDIO_FREQUENCY/" + System.currentTimeMillis() + ".pcm";
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        fileOutputStream.write(getAudioData());
        fileOutputStream.close();
        return fileName;
    }
}
