package com.wisdom.cap.wisdomcapback.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wisdom.cap.wisdomcapback.util.Base64Util;
import com.wisdom.cap.wisdomcapback.util.PrintedWordUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 陈蒙欣
 * @date 2023/3/17 21:05
 */
@RestController
@RequestMapping("/msc")
public class MscController{
    @Value("${msc.appId}")
    private String APP_ID;

    @Resource
    PrintedWordUtil printedWordUtil;


    /**
     * 返回图片中的信息
     * @return 图片中的信息
     * @throws Exception 异常
     */
    @GetMapping(value = "/image")
    public String getImage() throws Exception {
        //解析返回的图片内容，获取图片的base64编码
        String request = printedWordUtil.getRequest();
        //将字符串转换为json对象
        JSONObject jsonObject = JSON.parseObject(request);
        //获取到text的值
        String string = jsonObject
                .getJSONObject("payload")
                .getJSONObject("ocr_output_text")
                .getString("text");

        //将base64编码解码成UTF-8格式的字符串
        String decode = Base64Util.decode(string);
        JSONObject jsonObject1 = JSON.parseObject(decode);
        JSONArray jsonArray = jsonObject1.getJSONArray("pages")
                .getJSONObject(0)
                .getJSONArray("lines");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
            String content = jsonObject2.getString("content");
            stringBuilder.append(content);
        }
        return jsonObject1.toJSONString();
    }

}
