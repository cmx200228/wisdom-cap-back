package com.wisdom.cap.wisdomcapback.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *
 * TTS-文字转语音的工具类
 *
 * @author 86138
 * @data
 */
public class TtsUtil {
    public final char[] fileID = {'R', 'I', 'F', 'F'};
    public int fileLength;
    public char[] wavTag = {'W', 'A', 'V', 'E'};
    public char[] fmtHdrID = {'f', 'm', 't', ' '};
    public int fmtHdrLength;
    public short formatTag;
    public short channels;
    public int samplesPerSec;
    public int avgBytesPerSec;
    public short blockAlign;
    public short bitsPerSample;
    public char[] dataHdrID = {'d', 'a', 't', 'a'};
    public int dataHdrLength;

    public byte[] getHeader() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        writeChar(bos, fileID);
        writeInt(bos, fileLength);
        writeChar(bos, wavTag);
        writeChar(bos, fmtHdrID);
        writeInt(bos, fmtHdrLength);
        writeShort(bos, formatTag);
        writeShort(bos, channels);
        writeInt(bos, samplesPerSec);
        writeInt(bos, avgBytesPerSec);
        writeShort(bos, blockAlign);
        writeShort(bos, bitsPerSample);
        writeChar(bos, dataHdrID);
        writeInt(bos, dataHdrLength);
        bos.flush();
        byte[] r = bos.toByteArray();
        bos.close();
        return r;
    }

    private void writeShort(ByteArrayOutputStream bos, int s) throws IOException {
        byte[] mybyte = new byte[2];
        mybyte[1] = (byte) ((s << 16) >> 24);
        mybyte[0] = (byte) ((s << 24) >> 24);
        bos.write(mybyte);
    }

    private void writeInt(ByteArrayOutputStream bos, int n) throws IOException {
        byte[] buf = new byte[4];
        buf[3] = (byte) (n >> 24);
        buf[2] = (byte) ((n << 8) >> 24);
        buf[1] = (byte) ((n << 16) >> 24);
        buf[0] = (byte) ((n << 24) >> 24);
        bos.write(buf);
    }

    private void writeChar(ByteArrayOutputStream bos, char[] id) {
        for (char c : id) {
            bos.write(c);
        }
    }

}
