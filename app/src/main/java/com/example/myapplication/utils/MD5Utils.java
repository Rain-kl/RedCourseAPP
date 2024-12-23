package com.example.myapplication.utils;

import java.security.MessageDigest;

public class MD5Utils {
    String input;

    public MD5Utils(String input) {
        this.input = input;
    }

    public String md5() {
        return MD5Utils.md5(input);
    }

    public static String md5(String input) {
        try {
            // 获取MD5加密算法实例
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 将字符串转成字节数组，并更新消息摘要
            md.update(input.getBytes());
            // 生成 MD5 摘要
            byte[] digest = md.digest();
            // 将字节数组转换成十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("MD5加密出错", e);
        }
    }

    public static void main(String[] args) {
        String text = "HelloWorld";
        String md5Hash = md5(text);
        System.out.println("MD5加密结果：" + md5Hash);
    }
}