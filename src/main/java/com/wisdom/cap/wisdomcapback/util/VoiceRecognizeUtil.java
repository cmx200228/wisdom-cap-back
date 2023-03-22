package com.wisdom.cap.wisdomcapback.util;


import com.iflytek.cloud.speech.RecognizerListener;
import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechRecognizer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

// 语音识别工具类
public class VoiceRecognizeUtil {
    // 语音听写对象， 初始化听写对象
    private static SpeechRecognizer mIat = SpeechRecognizer.createRecognizer();
//    监听输入端
    public static String VoiceChangeStart() {
        List<String> result = new ArrayList<>();
        if (!mIat.isListening()) {
            mIat.startListening(new AbstractRecognizerListener() {
                @Override
                public void onResult(RecognizerResult results, boolean islast) {
                    //如果要解析json结果，请考本项目示例的 com.iflytek.util.JsonParser类
                    String text = parseIatResult(results.getResultString());
                    System.out.println(text);
                    if (islast) {
                        // 是最后的输入了，可以进行下一次了
                    }
                    result.add(text);
                }
            });

        } else {
            mIat.stopListening();
        }
        return result.size() > 0 ? result.get(0) : "";
    }


    private static String parseIatResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }


    static class AbstractRecognizerListener implements RecognizerListener {
        @Override
        public void onBeginOfSpeech() {
            System.out.println("onBeginOfSpeech enter");
        }

        @Override
        public void onEndOfSpeech() {
            System.out.println("onEndOfSpeech enter");
        }

        /**
         * 获取听写结果. 获取RecognizerResult类型的识别结果，并对结果进行累加，显示到Area里
         */
        @Override
        public void onResult(RecognizerResult results, boolean islast) {
            System.out.println("onResult enter");
        }

        @Override
        public void onVolumeChanged(int volume) {
//        System.out.println( "onVolumeChanged enter" );
        }

        @Override
        public void onError(SpeechError error) {
            System.out.println("onError enter");
            if (null != error) {
                System.out.println("onError Code：" + error.getErrorCode());
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int agr2, String msg) {
            System.out.println("onEvent enter");
            //以下代码用于调试，如果出现问题可以将sid提供给讯飞开发者，用于问题定位排查
			/*if(eventType == SpeechEvent.EVENT_SESSION_ID) {
				System.out.println("sid=="+msg);
			}*/
        }
    }


}
