package com.wisdom.cap.wisdomcapback.util;

import com.google.gson.Gson;
import okhttp3.HttpUrl;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 陈蒙欣
 * @date 2023/4/24 16:02
 */
public class WebsocketUtil {

    public static boolean wsCloseFlag = false;
    private static final Gson gson = new Gson();

    private String requestString;

    /**
     * 语音合成Websocket接口
     */
    public void websocketWork(String wsUrl, OutputStream outputStream , String text, String tte , String vcn){
        try {
            URI uri = new URI(wsUrl);
            WebSocketClient webSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    System.out.println("ws建立连接成功...");
                }

                @Override
                public void onMessage(String text) {
                    JsonParse myJsonParse = gson.fromJson(text, JsonParse.class);
                    System.out.println("收到服务端消息：" + text);
                    if (myJsonParse.code != 0) {
                        System.out.println("发生错误，错误码为：" + myJsonParse.code);
                        System.out.println("本次请求的sid为：" + myJsonParse.sid);
                    }
                    if (myJsonParse.data != null) {
                        try {
                            byte[] textBase64Decode = Base64.getDecoder().decode(myJsonParse.data.audio);
                            outputStream.write(textBase64Decode);
                            outputStream.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (myJsonParse.data.status == 2) {
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println("本次请求的sid==>" + myJsonParse.sid);
                            // 可以关闭连接，释放资源
                            wsCloseFlag = true;
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    System.out.println("ws链接已关闭，本次请求完成...");
                    wsCloseFlag = false;
                }

                @Override
                public void onError(Exception e) {
                    System.out.println("发生错误 " + e.getMessage());
                }
            };
            // 建立连接
            webSocketClient.connect();
            while (!webSocketClient.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                Thread.sleep(100);
            }
            // 发送音频
            MyThread webSocketThread = new MyThread(webSocketClient,requestString);
            webSocketThread.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // 线程来发送音频与参数
    class MyThread extends Thread {
        WebSocketClient webSocketClient;

        String requestJson;//请求参数json串
        public MyThread(WebSocketClient webSocketClient) {
            this.webSocketClient = webSocketClient;
        }
        public MyThread(WebSocketClient webSocketClient , String requestJson) {
            this.webSocketClient = webSocketClient;
            this.requestJson = requestJson;
        }

        @Override
        public void run() {
            try {
                // 发送音频
                webSocketClient.send(requestJson);
                // 等待服务端返回完毕后关闭
                while (!wsCloseFlag) {
                    Thread.sleep(200);
                }
                webSocketClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 鉴权方法
    public static String getAuthUrl(String hostUrl, String apiKey, String apiSecret) throws Exception {
        java.net.URL url = new URL(hostUrl);
        // 时间
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        // 拼接
        String preStr = "host: " + url.getHost() + "\n" +
                "date: " + date + "\n" +
                "GET " + url.getPath() + " HTTP/1.1";
        // SHA256加密
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "hmacsha256");
        mac.init(spec);
        byte[] hexDigits = mac.doFinal(preStr.getBytes(StandardCharsets.UTF_8));
        // Base64加密
        String sha = Base64.getEncoder().encodeToString(hexDigits);
        // 拼接
        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
        // 拼接地址
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath())).newBuilder().
                addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8))).
                addQueryParameter("date", date).
                addQueryParameter("host", url.getHost()).
                build();

        return httpUrl.toString();
    }

    public String buildRequest(String APP_ID , String text, String tte , String vcn) {
        String string = "{\n" +
                "  \"common\": {\n" +
                "    \"app_id\": \"" + APP_ID + "\"\n" +
                "  },\n" +
                "  \"business\": {\n" +
                "    \"aue\": \"lame\",\n" +
                "     \"sfl\": 1,\n" +
                "    \"tte\": \"" + tte + "\",\n" +
                "    \"vcn\": \"" + vcn + "\",\n" +
                "    \"pitch\": 50,\n" +
                "    \"speed\": 50\n" +
                "  },\n" +
                "  \"data\": {\n" +
                "    \"status\": 2,\n" +
                "    \"text\": \"" + Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8)) + "\"\n" +
                "  }\n" +
                "}";
        this.requestString = string;
        return requestString;
    }

    //返回的json结果拆解
    class JsonParse {
        int code;
        String message;
        String sid;
        Data data;
    }

    class Data {
        int status;
        String audio;
    }
}
