package com.wisdom.cap.wisdomcapback.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class WordRecognizeTest {

//    @Test
//    public void  wordrecognizetest() throws Exception {
//        String filename = "D:/IDEA/Project/A-Project/wisdom-cap-back/IMAGE/pic1.jpg";
//        CameraUtil.capture(filename);
//        String image = mscController.getImage();
//        System.out.println(image);
//    }

    @Autowired
    private WordsRecognize wordsRecognize;
    @Test
    public void testWordRecognizer() throws Exception {
        String image = wordsRecognize.recognizeWords();
        System.out.println("2："+image);
    }

}
