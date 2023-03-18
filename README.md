# wisdom-cap-back
# wisdom-cap-back
前期准备：1、语音文件录入、SDK实现语音识别
2、文字转语音，SDK实现文字转语音
将两个功能均封装为工具类，能够直接调用
这两个语音均应考虑音频格式转码问题，音频输入输出格式参考科大讯飞官网https://www.xfyun.cn/services/lfasr
树莓派的默认音频格式是PCM（Pulse-code modulation）格式，采样率为48kHz，采样位数为16位，单声道（Mono），可以在Java程序中使用Java Sound API来访问ALSA驱动程序，并进行音频采集和处理。
同时需要考虑录音降噪的问题，可使用Beads，这是一个用于实时音频处理的Java库。它提供了许多用于分离、降噪和处理音频的类和方法。其中，Beads中的BTrack类可以实现音频分离，而NoiseGate类可以实现降噪。也可使用JavaCV，是一个用于计算机视觉和音频处理的Java库。它提供了一个简单的接口来访问OpenCV和FFmpeg等库。JavaCV中的OpenCV库可以用于音频分离和降噪。

