package com.wisdom.cap.wisdomcapback.util;

import com.iflytek.cloud.speech.RecognizerListener;
import com.iflytek.cloud.speech.RecognizerResult;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechRecognizer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 语音识别工具类
 * @author 霍嘉佳
 */
public class VoiceRecognizeUtil {
    private static final Logger logger = LoggerFactory.getLogger(VoiceRecognizeUtil.class);
    public static LinkedBlockingQueue<String> lbq = new LinkedBlockingQueue<>();
    /**
     * 语音听写对象， 初始化听写对象
     */
    private static SpeechRecognizer mIat = SpeechRecognizer.createRecognizer();
    /**
     * 监听输入端
     */
    public static LinkedBlockingQueue voiceChangeStart() {
        if (!mIat.isListening()) {
            mIat.startListening(new AbstractRecognizerListener() {
                @Override
                public void onResult(RecognizerResult results, boolean islast) {
                    //如果要解析json结果，请考本项目示例的 com.iflytek.util.JsonParser类
                    String text = parseIatResult(results.getResultString());
                    logger.debug(text);
                    lbq.add(text);
                }
            });
        } else {
            mIat.stopListening();
            lbq.add("over");
        }
        return lbq;
    }

    /**
     * 解析语音听写结果
     *
     * @param json 语音听写结果
     * @return 解析后的字符串
     */
    private static String parseIatResult(String json) {
        StringBuilder ret = new StringBuilder();
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
            logger.error("解析语音听写结果异常:{}", e.getMessage(), e);
        }
        return ret.toString();
    }


    /**
     * 语音识别监听器
     */
    static class AbstractRecognizerListener implements RecognizerListener {
        @Override
        public void onBeginOfSpeech() {
            logger.info("onBeginOfSpeech enter");
        }

        @Override
        public void onEndOfSpeech() {
            logger.info("onEndOfSpeech enter");
        }

        /**
         * 获取听写结果. 获取RecognizerResult类型的识别结果，并对结果进行累加，显示到Area里
         */
        @Override
        public void onResult(RecognizerResult results, boolean islast) {
            logger.info("onResult enter");
        }

        @Override
        public void onVolumeChanged(int volume) {
            logger.info( "onVolumeChanged enter" );
        }

        @Override
        public void onError(SpeechError error) {
            logger.info("onError enter");
            if (null != error) {
                logger.error("onError Code：{}" , error.getErrorCode());
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int agr2, String msg) {
            logger.info("onEvent enter");
            //以下代码用于调试，如果出现问题可以将sid提供给讯飞开发者，用于问题定位排查
			/*if(eventType == SpeechEvent.EVENT_SESSION_ID) {
				System.out.println("sid=="+msg);
			}*/
        }
    }
}
