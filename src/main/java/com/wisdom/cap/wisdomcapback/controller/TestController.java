package com.wisdom.cap.wisdomcapback.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 陈蒙欣
 * @date 2023/3/25 2:12
 */
@RestController
public class TestController {

    @GetMapping("/news")
    public String getNews() {
        return "{\n" +
                "    \"category\": \"cam.ch_en\",\n" +
                "    \"pages\": [\n" +
                "        {\n" +
                "            \"angle\": 0.380126953125,\n" +
                "            \"exception\": 0,\n" +
                "            \"height\": 4624\n" +
                "}\n" +
                "]\n" +
                "}";
    }
}
