package com.wisdom.cap.wisdomcapback.controller;

import com.wisdom.cap.wisdomcapback.service.impl.TtsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.IOException;

@Controller
public class TtsController {
    @Resource
    private TtsServiceImpl ttsService;

    @GetMapping("/speech")
    public ResponseEntity<String> speech(@RequestParam String text) throws IOException {
        String s = ttsService.textToSpeech(text);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    /**
     * 将Speech.mp3保存到项目路径
     * @param text
     * @return
     * @throws IOException
     */

    @GetMapping("/save")
    public ResponseEntity<String> save(@RequestParam String text) throws IOException {
//        byte[] speechBytes = ttsService.textToSpeech(text);
//
//        // 获取当前项目路径
//        String projectPath = System.getProperty("user.dir");
//
//        // 拼接文件路径
//        String filePath = projectPath + "/speech.wav";
//        AudioInputStream audioInputStream;
//        try {
//            audioInputStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(speechBytes));
//        } catch (UnsupportedAudioFileException e) {
//            throw new RuntimeException(e);
//        }
//        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new File(filePath));
//        audioInputStream.close();
//        try (FileOutputStream fos = new FileOutputStream(filePath)) {
//            fos.write(speechBytes);
//        }
        return new ResponseEntity<>("File saved successfully", HttpStatus.OK);
    }

}

