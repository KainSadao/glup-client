package com.glup.client.Utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Kain on 18/10/2016.
 */

public class Aes {
    private final static String KEY = "4d1$hk3yka1z3n:)";
    private static byte[] key;
    private static SecretKeySpec secretKey;

    private static void setKey(){
        MessageDigest sha = null;
        try {
            key = KEY.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            sha.update(key);
            System.arraycopy(key, 0, key, 0, 16);
            secretKey = new SecretKeySpec(key,"AES");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String string){

        try {
            setKey();
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.encodeToString(cipher.doFinal(string.getBytes("UTF-8")), Base64.DEFAULT).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String encryptedString){
        try {
            setKey();
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.decode(encryptedString, Base64.DEFAULT)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
