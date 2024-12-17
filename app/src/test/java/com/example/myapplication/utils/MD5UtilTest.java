package com.example.myapplication.utils;

import junit.framework.TestCase;

public class MD5UtilTest extends TestCase {

    public void testMd5() {
        String text = "HelloWorld";
        String md5Hash = MD5Util.md5(text);
        System.out.println("MD5加密结果：" + md5Hash);
    }

    public void testTestMd5() {
        String text = "HelloWorld";
        MD5Util md5Util = new MD5Util(text);
        String md5Hash = md5Util.md5();
        System.out.println("MD5加密结果：" + md5Hash);
    }
}