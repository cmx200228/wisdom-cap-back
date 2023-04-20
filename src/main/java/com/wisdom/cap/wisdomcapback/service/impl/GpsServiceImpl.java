package com.wisdom.cap.wisdomcapback.service.impl;

import com.fazecast.jSerialComm.SerialPort;
import org.springframework.stereotype.Service;

/**
 * GPS定位实现类
 * @author 86138
 */

public class GpsServiceImpl {
    private SerialPort serialPort;

    /**
     * 创建GpsServiceImpl构造函数
     * @param portName 指定GPS模块所连接的串口名称
     */
    public GpsServiceImpl(String portName) {
        serialPort = SerialPort.getCommPort(portName);
        /*设置串口通信参数，包括波特率9600，数据位8位，停止位1位，无奇偶校验。*/
        serialPort.setComPortParameters(9600, 8, 1, SerialPort.NO_PARITY);
        /*设置串口读取超时时间*/
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 1000, 0);
    }

    /**
     * 负责读取从GPS模块返回的数据，并将其转换为字符串格式返回。
     * @return
     */
    public String readGPSData() {
        byte[] readBuffer = new byte[1024];
        String data = "";
        int numRead = serialPort.readBytes(readBuffer, readBuffer.length);
        if (numRead > 0) {
            data = new String(readBuffer, 0, numRead);
        }
        return data;
    }
}
