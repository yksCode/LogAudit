package com.dataAuditLog;
/*
 *@工具类
 */
public class util {

    public static String json2String(){
        return null;
    }

    //将字节流转换成十六进制
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
