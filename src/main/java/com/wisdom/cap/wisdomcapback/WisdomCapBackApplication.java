package com.wisdom.cap.wisdomcapback;

import com.pi4j.io.gpio.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author 陈蒙欣
 * @date 2023/4/19
 */

@SpringBootApplication
public class WisdomCapBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(WisdomCapBackApplication.class, args);
        //用于树莓派GPIO
        final GpioController gpio = GpioFactory.getInstance();
        // 配置GPIO 18、23、24、25、21为输入引脚
        final GpioPinDigitalInput[] pins = {
                gpio.provisionDigitalInputPin(RaspiPin.GPIO_18, PinPullResistance.PULL_DOWN),
                gpio.provisionDigitalInputPin(RaspiPin.GPIO_23, PinPullResistance.PULL_DOWN),
                gpio.provisionDigitalInputPin(RaspiPin.GPIO_24, PinPullResistance.PULL_DOWN),
                gpio.provisionDigitalInputPin(RaspiPin.GPIO_25, PinPullResistance.PULL_DOWN),
                gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN)
        };
        // 循环监听GPIO电平变化
        while (true) {
            for (GpioPinDigitalInput pin : pins) {
                if (pin.isHigh()) {
                    System.out.println(pin.getName() + " is high");
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
