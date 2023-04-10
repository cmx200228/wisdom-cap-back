package com.wisdom.cap.wisdomcapback.util;

import com.alibaba.fastjson2.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 图片转文字工具类
 *
 * @author 陈蒙欣
 * @date 2023/3/20 10:54
 */
@Component
public class PrintedWordUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrintedWordUtil.class);
    @Value("${image.request_url}")
    private String requestUrl;

    @Value("${image.api_id}")
    private String appid;

    @Value("${image.path}")
    private String imagePath;

    @Value("${image.api_secret}")
    private String appSecret;

    @Value("${image.api_key}")
    private String appKey;

    /**
     * 读取文件内容
     * @throws  Exception 读取文件异常
     * @return 返回文件内容
     */
    public String getRequest() throws Exception {
        URL realUrl = new URL(buildRequestUrl());
        URLConnection connection = realUrl.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-type","application/json");

        OutputStream out = httpURLConnection.getOutputStream();
        String params = buildParam();
        out.write(params.getBytes());
        out.flush();
        InputStream is;
        try{
            is = httpURLConnection.getInputStream();
        }catch (Exception e){
            is = httpURLConnection.getErrorStream();
            throw new Exception("make request error:"+"code is "+httpURLConnection.getResponseMessage()+readAllBytes(is , StandardCharsets.UTF_8.name()));
        }
        return readAllBytes(is , StandardCharsets.UTF_8.name());
    }

    /**
     * 拼接请求url
     * @return 返回拼接后的url
     */
    public String buildRequestUrl(){
        URL url;
        // 替换调schema前缀 ，原因是URL库不支持解析包含ws,wss schema的url
        String  httpRequestUrl = requestUrl.replace("ws://", "http://").replace("wss://","https://" );
        try {
            url = new URL(httpRequestUrl);
            //获取当前日期并格式化
            SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            String date = format.format(new Date());

            String host = url.getHost();
            if (url.getPort()!=80 && url.getPort() !=443){
                host = host +":"+ url.getPort();
            }
            String builder = "host: " + host + "\n" +
                    "date: " + date + "\n" +
                    "POST " + url.getPath() + " HTTP/1.1";
            Charset charset = StandardCharsets.UTF_8;
            Mac mac = Mac.getInstance("hmacsha256");
            SecretKeySpec spec = new SecretKeySpec(appSecret.getBytes(charset), "hmacsha256");
            mac.init(spec);
            byte[] hexDigits = mac.doFinal(builder.getBytes(charset));
            String sha = Base64.getEncoder().encodeToString(hexDigits);

            String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", appKey, "hmac-sha256", "host date request-line", sha);
            String authBase = Base64.getEncoder().encodeToString(authorization.getBytes(charset));
            return String.format("%s?authorization=%s&host=%s&date=%s", requestUrl, URLEncoder.encode(authBase,"UTF-8"), URLEncoder.encode(host,"UTF-8"), URLEncoder.encode(date,"UTF-8"));

        } catch (Exception e) {
            throw new RuntimeException("错误"+e.getMessage());
        }
    }

    /**
     * 组装请求参数
     * 直接使用示例参数，
     * 替换部分值
     * @return 参数字符串
     */
    private String  buildParam() throws IOException {
        JsonParse jsonParse = new JsonParse();
        jsonParse.header = new Header();
        jsonParse.payload = new Payload();
        jsonParse.parameter = new Parameter();
        jsonParse.header.app_id = appid;
        jsonParse.header.status = 3;
        jsonParse.parameter.ocr = new Ocr();
        jsonParse.parameter.ocr.language = "ch_en";
        jsonParse.parameter.ocr.ocr_output_text = new OcrOutputText();
        jsonParse.parameter.ocr.ocr_output_text.encoding = "utf8";
        jsonParse.parameter.ocr.ocr_output_text.compress = "raw";
        jsonParse.parameter.ocr.ocr_output_text.format = "json";
        jsonParse.payload.image = new Image();
        jsonParse.payload.image.encoding = "jpg";
        jsonParse.payload.image.image = Base64.getEncoder().encodeToString(read(imagePath));
        jsonParse.payload.image.status = 3;
        return JSON.toJSONString(jsonParse);
    }

    /**
     * 将图片文件转换成字节数组
     * @return 字节数组
     */
    public static byte[] read(String filePath) throws IOException {
        InputStream in = Files.newInputStream(Paths.get(filePath));
        byte[] data = inputStream2ByteArray(in);
        in.close();
        return data;
    }

    /**
     * 将输入流转换成字节数组
     * @param in 输入流
     * @return 字节数组
     */
    private static byte[] inputStream2ByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n ;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

    /**
     * 读取流数据
     * @param is 流
     * @return 字符串
     * @throws IOException 异常
     */
    private String readAllBytes(InputStream is , String charsetName) throws IOException {
        try (InputStreamReader isr = new InputStreamReader(is, charsetName);
             BufferedReader reader = new BufferedReader(isr)) {
            StringBuilder sb = new StringBuilder();
            char[] buf = new char[1024];
            int len;
            while ((len = reader.read(buf)) != -1) {
                sb.append(buf, 0, len);
            }
            return sb.toString();
        }
    }
    /**
     * 辅助类，分别对应返回的json中的header和payload
     */
    static class JsonParse {
        public Header header;
        public Payload payload;
        public Parameter parameter;
    }
    static class Header{
        public Integer status;
        public String app_id;
    }
    static class Image{
        public String encoding;
        public String image;
        public Integer status;
    }

    static class Parameter{
        public Ocr ocr;
    }

    static class Ocr{
        public String language;
        public OcrOutputText ocr_output_text;
    }

    static class OcrOutputText{
        public String encoding;
        public String compress;
        public String format;
    }
    static class Payload{
        public Image image;
    }
}
