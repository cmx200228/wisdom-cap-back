package com.wisdom.cap.wisdomcapback.controller;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.SynthesizeToUriListener;
import com.wisdom.cap.wisdomcapback.service.impl.TtsServiceImpl;
import com.wisdom.cap.wisdomcapback.util.TtsUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @author 86138
 * @date 2023/3/21 22:00
 */
@RestController
@RequestMapping("/msc")
public class TtsController {
    @Value("${msc.appId}")
    private static String APP_ID;

    @RequestMapping ("/TTS")
    public static String changeToVoice(String data) throws IOException{
        //官网的id
        SpeechUtility.createUtility(APP_ID);  //这里设置在讯飞官网上设置的appid......
        //合成监听器
        SynthesizeToUriListener synthesizeToUriListener=TtsServiceImpl.getSynthesize();
        //默认是生成pcm文件，所以后面有一步是转换成wav文件才能正常播放
        String fileName=TtsServiceImpl.getFileName("tts_test.mp3");
        TtsServiceImpl.delDone(fileName);

        //1.创建SpeechSynthesizer对象
        SpeechSynthesizer mTts=SpeechSynthesizer.createSynthesizer();
        //2.合成参数设置,<<MSC Reference Manual>> SpeechSynthesizer类
        mTts.setParameter(SpeechConstant.VOICE_NAME,"xiaoyan");//设置发音人，共有
        mTts.setParameter(SpeechConstant.SPEED,"50");//设置语速，范围0~100
        mTts.setParameter(SpeechConstant.PITCH,"50");//设置语调，范围0~100
        mTts.setParameter(SpeechConstant.VOLUME,"50");//设置音量，范围0~100

        //3.开始合成
        //设置合成音频保存位置,下面为合成的保存位置，实际上并不需要，因此先注释掉
        mTts.synthesizeToUri(data,fileName, synthesizeToUriListener);

        //设置最长时间
        int timeOut=30;
        int star=0;

        //检验文件是否生成
        while(!TtsServiceImpl.checkDone(fileName)){
            try{
                Thread.sleep(1000);
                star++;
                if(star>timeOut){
                    throw new Exception("合成超时"+timeOut+"秒！");
                }
            } catch (Exception e) {
                //log.error("Exception:{}",e.getMessage());
                return e.getMessage();
            }
        }
        String mp3Path = fileName.replaceAll(".pcm",".mp3");
        pcmToMP3(fileName,mp3Path); //转换文件
        deleteFile(fileName);  //删除原来的pcm文件
        return mp3Path;
    }

    private static void deleteFile(String filename) {
        File file=new File(filename);
        file.delete();
    }

    //下面是将pcm转换为MP3的代码
    private static void pcmToMP3(String src, String target) throws IOException {
        FileInputStream fis = new FileInputStream(src);
        FileOutputStream fos = new FileOutputStream(target);

        // 计算长度
        byte[] buf = new byte[1024 * 4];
        int size = fis.read(buf);
        int PCMSize = 0;
        while (size != -1) {
            PCMSize += size;
            size = fis.read(buf);
        }
        fis.close();

        // 填入参数，比特率等等。这里用的是16位单声道 8000 hz
        TtsUtil util = new TtsUtil();

        // 长度字段 = 内容的大小（PCMSize) + 头部字段的大小(不包括前面4字节的标识符RIFF以及fileLength本身的4字节)
        util.fileLength = PCMSize + (44 - 8);
        util.FmtHdrLeth = 16;
        util.BitsPerSample = 16;
        util.Channels = 1;
        util.FormatTag = 0x0001;
        util.SamplesPerSec = 16000;
        util.BlockAlign = (short) (util.Channels * util.BitsPerSample / 8);
        util.AvgBytesPerSec = util.BlockAlign * util.SamplesPerSec;
        util.DataHdrLeth = PCMSize;

        byte[] h = util.getHeader();

        assert h.length == 44; // WAV标准，头部应该是44字节
        // write header
        fos.write(h, 0, h.length);
        // write data stream
        fis = new FileInputStream(src);
        size = fis.read(buf);
        while (size != -1) {
            fos.write(buf, 0, size);
            size = fis.read(buf);
        }
        fis.close();
        fos.close();
        System.out.println("Convert OK!");
    }
}
