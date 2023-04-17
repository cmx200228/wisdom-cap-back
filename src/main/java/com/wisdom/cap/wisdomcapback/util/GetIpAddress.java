package com.wisdom.cap.wisdomcapback.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wisdom.cap.wisdomcapback.exception.BusinessException;
import com.wisdom.cap.wisdomcapback.exception.BusinessExceptionEnum;
import com.wisdom.cap.wisdomcapback.model.TO.HttpClientResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 陈蒙欣
 * @date 2023/4/10 14:15
 */
@Component
public class GetIpAddress {
    @Value("${gaode.ip.url}")
    private String url;
    @Value("${gaode.private_key}")
    private String privateKey;

    @Value("${gaode.key}")
    private String key;

    public String getIpAddress() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            String ip = localHost.getHostAddress();
            Map<String , String> params = new HashMap<>();
            params.put("key" , key);
            params.put("ip",ip);
            params.put("sig" , ParameterEncryptionUtil.generateSignature(params , privateKey));
            HttpClientResult httpClientResult = HttpClientUtils.doGet(url, params);
            if (httpClientResult.getCode() == 200){
                System.out.println(httpClientResult.getContent());
                JSONObject jsonObject = JSON.parseObject(httpClientResult.getContent());
                return jsonObject.getString("province");
            }
        } catch (Exception e) {
            throw new BusinessException(BusinessExceptionEnum.SYSTEM_ERROR);
        }
        return "";
    }
}
