package com.wisdom.cap.wisdomcapback.util;

import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialFactory;

import java.io.IOException;

public class GPSUtil {
    private static String utctime = "";
    /**
     * 纬度
     */
    private static String lat = "";
    private static String ulat = "";
    /**
     * 经度
     */
    private static String lon = "";
    private static String ulon = "";
    private static String numSv = "";
    private static String msl = "";
    private static String cogt = "";
    private static String cogm = "";
    private static String sog = "";
    private static String kph = "";

    public static String getUTCTime() {
        return utctime;
    }

    public static String getLatitude() {
        return lat + ulat;
    }

    public static String getLatLon() {
        return lon + "," + lat;
    }

    public static String getNumberOfSatellites() {
        return numSv;
    }

    public static String getAltitude() {
        return msl;
    }

    public static String getTrueNorthHeading() {
        return cogt + "°";
    }

    public static String getMagneticNorthHeading() {
        return cogm + "°";
    }

    public static String getGroundSpeedKnots() {
        return sog + "Kn";
    }

    public static String getGroundSpeedKmPerHour() {
        return kph + "Km/h";
    }

    /**
     * 读取GPS数据
     *
     * @return 1：成功；0：失败
     * @throws IOException
     */
    public static int readGPSData() throws IOException {
        Serial serial = SerialFactory.createInstance();
        serial.open("/dev/ttyUSB0", 9600);
        System.out.println("打开端口成功");
        serial.addListener(event -> {
            try {
                String data = event.getAsciiString();
                if (data.contains("$GNGGA")) {
                    String[] parts = data.split(",");
                    if (parts.length < 10) {
                        System.out.println("格式错误");
                        return;
                    }
                    lat = convertToDegrees(parts[2], parts[3]);
                    System.out.println("纬度"+lat);
                    lon = convertToDegrees(parts[4], parts[5]);
                    System.out.println("经度"+lon);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        serial.removeListener();
        serial.close();
        System.out.println("关闭端口成功");

        return 1;
    }

    /**
     * 将GPS数据转换为度
     *
     * @param raw 原始数据
     * @param dir 方向
     * @return 度
     */
    private static String convertToDegrees(String raw, String dir) {
        Double value = Double.parseDouble(raw);
        int degrees = (int) Math.floor(value / 100);
        double minutes = value % 100;
        double decimal = degrees + (minutes / 60);
        if (dir.equals("S") || dir.equals("W")) {
            decimal = -decimal;
        }
        return String.format("%.6f", decimal);
    }
}
