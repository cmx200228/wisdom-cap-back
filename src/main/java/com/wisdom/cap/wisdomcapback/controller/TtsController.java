package com.wisdom.cap.wisdomcapback.controller;

import com.wisdom.cap.wisdomcapback.service.impl.TtsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileOutputStream;
import java.io.IOException;

@Controller
public class TtsController {
    @Autowired
    private TtsServiceImpl ttsService;

    @GetMapping("/speech")
    public ResponseEntity<byte[]> speech(@RequestParam String text) throws IOException {
        byte[] speechBytes = ttsService.textToSpeech(text);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("speech.mp3").build());
        return new ResponseEntity<>(speechBytes, headers, HttpStatus.OK);
    }

    /**
     * 将Speech.mp3保存到项目路径
     * @param text
     * @return
     * @throws IOException
     */

    @GetMapping("/save")
    public ResponseEntity<String> save(@RequestParam String text) throws IOException {
        byte[] speechBytes = ttsService.textToSpeech(text);

        // 获取当前项目路径
        String projectPath = System.getProperty("user.dir");

        // 拼接文件路径
        String filePath = projectPath + "/speech.mp3";
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(speechBytes);
        }
        return new ResponseEntity<>("File saved successfully", HttpStatus.OK);
    }

}

