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
    public static void pcm(String path) throws IOException, LineUnavailableException {
        try (FileInputStream fis = new FileInputStream(path)){
            AudioFormat.Encoding encoding =  new AudioFormat.Encoding("PCM_SIGNED");
            //编码格式，采样率，每个样本的位数，声道，帧长（字节），帧数，是否按big-endian字节顺序存储
            AudioFormat format = new AudioFormat(encoding,8000, 16, 1, 2, 8000 ,false);
            SourceDataLine auline;
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

            try {
                auline = (SourceDataLine) AudioSystem.getLine(info);
                auline.open(format);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            auline.start();
            byte[] b = new byte[256];
            try {
                while(fis.read(b)>0) {
                    auline.write(b, 0, b.length);
                }
                auline.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
