package com.wisdom.cap.wisdomcapback.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 陈蒙欣
 * @date 2023/4/10 14:43
 */
@SpringBootTest
class GetIpAddressTest {
    @Resource
    GetIpAddress getIpAddress;

    @Test
    void getIpAddress() {
        String ipAddress = getIpAddress.getIpAddress();
        System.out.println(ipAddress);
    }
}
