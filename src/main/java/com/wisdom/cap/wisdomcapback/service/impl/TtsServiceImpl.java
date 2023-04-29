package com.wisdom.cap.wisdomcapback.service.impl;

import com.wisdom.cap.wisdomcapback.exception.BusinessException;
import com.wisdom.cap.wisdomcapback.exception.BusinessExceptionEnum;
import com.wisdom.cap.wisdomcapback.util.WebsocketUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service("ttsService")
public class TtsServiceImpl {
    @Value("${xunfei.tts_url}")
    private String URL;
    @Value("${xunfei.tts_appid}")
    private String APP_ID;
    @Value("${xunfei.tts_api_key}")
    private String API_KEY;
    @Value("${xunfei.tts_api_secret}")
    private String API_SECRET;
    @Value("${xunfei.tts_output_file_path}")
    String OUTPUT_FILE_PATH;


    public String textToSpeech(String text){
        try {
            String path = OUTPUT_FILE_PATH + System.currentTimeMillis() + ".mp3";
            String wsUrl = WebsocketUtil.getAuthUrl(URL, API_KEY, API_SECRET).replace("https://", "wss://");
            OutputStream outputStream = Files.newOutputStream(Paths.get(path));
            WebsocketUtil websocketUtil = new WebsocketUtil();
            websocketUtil.buildRequest(APP_ID, text , "UTF8" , "aisxping");
            websocketUtil.websocketWork(wsUrl, outputStream, text, "UTF8", "aisxping");
            return path;
        } catch (Exception e) {
            throw new BusinessException(BusinessExceptionEnum.SYSTEM_ERROR);
        }
    }




}
