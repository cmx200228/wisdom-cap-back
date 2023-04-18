package com.wisdom.cap.wisdomcapback.controller;

import com.wisdom.cap.wisdomcapback.service.impl.TtsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
