package com.example.myapplication.utils;

import junit.framework.TestCase;

public class MD5UtilsTest extends TestCase {

    public void testMd5() {
        String text = "HelloWorld";
        String md5Hash = MD5Utils.md5(text);
        System.out.println("MD5加密结果：" + md5Hash);
    }

    public void testTestMd5() {
        String text = "HelloWorld";
        MD5Utils md5Utils = new MD5Utils(text);
        String md5Hash = md5Utils.md5();
        System.out.println("MD5加密结果：" + md5Hash);
    }
}