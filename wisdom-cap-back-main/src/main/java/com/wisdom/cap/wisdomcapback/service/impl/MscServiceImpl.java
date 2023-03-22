package com.wisdom.cap.wisdomcapback.service.impl;

import com.iflytek.cloud.speech.*;
import com.wisdom.cap.wisdomcapback.service.MscService;
import org.springframework.stereotype.Service;

/**
 * @author 陈蒙欣
 * @date 2023/3/17 22:18
 */
@Service("mscService")
public class MscServiceImpl implements MscService, SynthesizeToUriListener {
    @Override
    public void onBufferProgress(int i) {

    }

    @Override
    public void onSynthesizeCompleted(String s, SpeechError speechError) {

    }

    @Override
    public void onEvent(int i, int i1, int i2, int i3, Object o, Object o1) {

    }
}
