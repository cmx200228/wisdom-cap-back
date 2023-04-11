package com.wisdom.cap.wisdomcapback.util;

import com.wisdom.cap.wisdomcapback.exception.BusinessException;
import com.wisdom.cap.wisdomcapback.exception.BusinessExceptionEnum;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.slf4j.Logger;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 音频播放工具类
 * @author 陈蒙欣
 * @date 2023/4/11 9:47
 */
public class AudioFrequency {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AudioFrequency.class);
    private AudioFrequency(){}

    /**
     * 播放wav格式音频
     * @param path 音频路径
     */
    public static void wav(String path){
        AudioInputStream as;
        try {
            //音频文件在项目根目录的AUDIO_FREQUENCY文件夹下
            as = AudioSystem.getAudioInputStream(new File(path));
            AudioFormat format = as.getFormat();
            SourceDataLine sdl;
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            sdl = (SourceDataLine) AudioSystem.getLine(info);
            sdl.open(format);
            sdl.start();
            int nBytesRead = 0;
            byte[] abData = new byte[512];
            while (nBytesRead != -1) {
                nBytesRead = as.read(abData, 0, abData.length);
                if (nBytesRead >= 0) {
                    sdl.write(abData, 0, nBytesRead);
                }
            }
            //关闭SourceDataLine
            sdl.drain();
            sdl.close();
        }catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            LOGGER.error(e.getMessage() , "wav音频播放失败: {}");
        }
    }

    /**
     * 播放mp3格式音频
     * @param path 音频路径
     */
    public static void mp3(String path){
        try {
            Player player = new Player(new FileInputStream(path));
            player.play();
        } catch (JavaLayerException | FileNotFoundException e) {
            LOGGER.error("mp3音频播放失败", e);
            throw new BusinessException(BusinessExceptionEnum.VOICE_PLAY);
        }
    }

    /**
     * 播放pcm格式音频
     * @param path 音频路径
     */
    public static void pcm(String path){
        // 设置音频参数
        AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
        // 获取音频输入流
        AudioInputStream audioInputStream = null;
        SourceDataLine sourceDataLine = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(path));
            // 打开音频输出流
            sourceDataLine = AudioSystem.getSourceDataLine(audioFormat);
            sourceDataLine.open(audioFormat);
            // 开始播放
            sourceDataLine.start();
            int count;
            byte[] tempBuffer = new byte[1024];
            while ((count = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
                if (count > 0) {
                    // 写入数据到混频器
                    sourceDataLine.write(tempBuffer, 0, count);
                }
            }
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            LOGGER.error("pcm音频播放失败", e);
            throw new BusinessException(BusinessExceptionEnum.VOICE_PLAY);
        }finally {
            // 关闭流
            assert sourceDataLine != null;
            sourceDataLine.drain();
            sourceDataLine.close();
            try {
                assert audioInputStream != null;
                audioInputStream.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage() , "关闭流失败 ：{}");
            }
        }


    }
}
