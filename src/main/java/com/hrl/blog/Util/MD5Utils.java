package com.hrl.blog.Util;

import ch.qos.logback.core.net.SyslogOutputStream;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    public static String code(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            int i;
            StringBuffer sb = new StringBuffer("");
            for (int offset = 0;offset<byteDigest.length; offset++){
                i=byteDigest[offset];
                if(i<0)
                    i+=256;
                if(i<16)
                    sb.append("0");
                    sb.append(Integer.toHexString(i));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(code("123456"));
    }
}
