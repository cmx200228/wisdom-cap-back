package com.wisdom.cap.wisdomcapback.service.impl;

import okhttp3.*;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import java.io.IOException;
import java.util.Base64;

@Service
public class TtsServiceImpl {
        private static final String URL = "http://api.xfyun.cn/v1/service/v1/tts";
        private static final String APP_ID = "f590a1b8";
        private static final String API_KEY = "e0f3f05fce831621feaf4a7fdc51f61f";

        public byte[] textToSpeech(String text) throws IOException {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = new FormBody.Builder()
                    .add("text", text)
                    .build();
            Request request = new Request.Builder()
                    .url(URL)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("X-Appid", APP_ID)
                    .addHeader("X-CurTime", String.valueOf(System.currentTimeMillis() / 1000))
                    .addHeader("X-Param", "eyJhdWUiOiJQcmltYXJ5IiwiaXBob25lIjoiMCIsInRpbWVzdGFtcCI6IjE2MTgwMTc4MzYifQ==")
                    .addHeader("X-CheckSum", buildCheckSum(API_KEY, String.valueOf(System.currentTimeMillis() / 1000)))
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().bytes();
        }

        private String buildCheckSum(String apiKey, String curTime) {
            String md5 = DigestUtils.md5DigestAsHex((apiKey + curTime).getBytes());
            return Base64.getEncoder().encodeToString(md5.getBytes());
        }
}
