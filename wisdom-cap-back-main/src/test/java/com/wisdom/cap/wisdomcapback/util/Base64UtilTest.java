package com.wisdom.cap.wisdomcapback.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;

/**
 * @author 陈蒙欣
 * @date 2023/3/20 14:16
 */
class Base64UtilTest {

    @Test
    void decode() {
        String jsonString = "{\"header\":{\"code\":0,\"message\":\"success\",\"sid\":\"ocr000e0a9b@hu186fda49b3205c3882\"},\"payload\":{\"ocr_output_text\":{\"compress\":\"raw\",\"encoding\":\"utf8\",\"format\":\"json\",\"seq\":\"0\",\"status\":\"3\",\"text\":\"ewogICAiY2F0ZWdvcnkiIDogIm解析这个json字符串，我想要获得text的内容\"}}}";
        JSONObject jsonObject = JSON.parseObject(jsonString);
        String text = jsonObject.getJSONObject("payload").getJSONObject("ocr_output_text").getString("text");
        System.out.println(text);
    }
}
