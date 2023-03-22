package com.wisdom.cap.wisdomcapback;

import com.wisdom.cap.wisdomcapback.controller.TtsController;
import com.wisdom.cap.wisdomcapback.service.TtsService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootTest
@Component
//@RunWith:启动这个单元测试类，传递固定的参数必须是SpringRunner的实例类型
@RunWith(SpringRunner.class)
class WisdomCapBackApplicationTests {
    /**
     * @author 86138
     * TTS 测试案例
     * @throws IOException
     */
    @Test
    void contextLoads() throws IOException {
        TtsController ttsController = new TtsController();
        ttsController.changeToVoice("我是小菜鸡！");
    }



}
