package com.wisdom.cap.wisdomcapback.service.impl;

import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SynthesizeToUriListener;
import com.wisdom.cap.wisdomcapback.service.TtsService;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 86138
 * @data
 */
public class TtsServiceImpl implements TtsService, SynthesizeToUriListener {
    private static Map<String, Boolean> voiceFile=new HashMap<String,Boolean>();

    //设置生成文件队列
    public static void setVoice(String name,Boolean have){
        TtsServiceImpl.voiceFile.put(name,have);
    }

    //查看文件是否在队列中
    public static  Boolean checkDone(String name){
        Boolean don=TtsServiceImpl.voiceFile.get(name);
        if(don==null){
            return false;
        }
        return true;
    }

    //清除队列中的信息
    public static void delDone(String name){
        TtsServiceImpl.voiceFile.remove(name);
    }

    //返回合成监听器
    public static SynthesizeToUriListener getSynthesize(){
        return new SynthesizeToUriListener() {
            @Override
            public void onBufferProgress(int i) {
                System.out.println("当前进度: "+i+"%");
            }

            @Override
            public void onSynthesizeCompleted(String uri, SpeechError speechError) {
                if(speechError!=null)
                {
                    speechError.printStackTrace();
                }else{
                    System.out.println("生成文件"+uri);
                    //将生成的文件保存到队列中
                    TtsServiceImpl.setVoice(uri,true);
                }
            }

            @Override
            public void onEvent(int i, int i1, int i2, int i3, Object o, Object o1) {

            }
        };
    }

    @Override
    public void onBufferProgress(int i) {
        System.out.println("当前进度: "+i+"%");
    }

    @Override
    public void onSynthesizeCompleted(String uri, SpeechError speechError) {
        if(speechError!=null)
        {
            speechError.printStackTrace();
        }else{
            System.out.println("生成文件"+uri);
            //将生成的文件保存到队列中
            TtsServiceImpl.setVoice(uri,true);
        }
    }

    @Override
    public void onEvent(int i, int i1, int i2, int i3, Object o, Object o1) {

    }

    //获取文件名
    public static String getFileName(String name){
        //获取文件名
        StringBuffer fileName=new StringBuffer(System.getProperty("user.dir"))
                .append(File.separator).append("src")
                .append(File.separator).append("main")
                .append(File.separator).append(name); //获取文件路径

        System.out.println(fileName.toString());

        return fileName.toString();
    }

    /**
     * @param fileLeng  转换文件长度
     * @param srate  采样率 - 8000,16000等
     * @param channel 通道数量 - 单声道= 1，立体声= 2等。
     * @param format 每个样本的位数（这里是16）
     **/
    public static byte[] getWAVHeader(long fileLeng, int srate, int channel, int format) {

        byte[] header = new byte[44];
        long totalDataLen = fileLeng + 36;
        long bitrate = srate * channel * format;

        header[0] = 'R';
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        header[12] = 'f';
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        header[16] = (byte) format;
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = 1;
        header[21] = 0;
        header[22] = (byte) channel;
        header[23] = 0;
        header[24] = (byte) (srate & 0xff);
        header[25] = (byte) ((srate >> 8) & 0xff);
        header[26] = (byte) ((srate >> 16) & 0xff);
        header[27] = (byte) ((srate >> 24) & 0xff);
        header[28] = (byte) ((bitrate / 8) & 0xff);
        header[29] = (byte) (((bitrate / 8) >> 8) & 0xff);
        header[30] = (byte) (((bitrate / 8) >> 16) & 0xff);
        header[31] = (byte) (((bitrate / 8) >> 24) & 0xff);
        header[32] = (byte) ((channel * format) / 8);
        header[33] = 0;
        header[34] = 16;
        header[35] = 0;
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (fileLeng  & 0xff);
        header[41] = (byte) ((fileLeng >> 8) & 0xff);
        header[42] = (byte) ((fileLeng >> 16) & 0xff);
        header[43] = (byte) ((fileLeng >> 24) & 0xff);
        return header;
    }

}
