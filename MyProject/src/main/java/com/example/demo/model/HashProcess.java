package com.example.demo.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//Class Member static 方法形成共用
public class HashProcess {
	//將明文bytes array+salt array 進行Hash產生 source 可能是username or password
	//第二個參數 private key 稱呼salt
	public static String getSecureHash(String source, byte[] salt) {
		//Hash演算後的Hash values(byte array)->字串
        String generatedsource = null;
        try {
        	//產生sha 512 bits  java.security packages MessageDigest抽象類別 採用工廠模式
        	//類別多型化的語法
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt); //演算法加入salt(private key)
            //將傳遞進來souce字串 配合編碼(UTF-8) 轉換成byte array
            byte[] bytes = md.digest(source.getBytes(StandardCharsets.UTF_8));
            //建構字元緩存區Buffer
            StringBuilder sb = new StringBuilder();
            //走訪演算出來每一個byte
            for (int i = 0; i < bytes.length; i++) {
            	//將每hash byte轉換成一個字元(16進制) oxff 十六進制編碼 0-9 A-F
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //將StringBuilder累積的字串整個倒出來
            generatedsource = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedsource;
    }

}