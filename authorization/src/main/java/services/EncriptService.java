package services;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EncriptService {
    public static String generate(String inString){
        String outString = null;
        byte[] plainText = new byte[0];
        try {
            plainText = inString.getBytes("UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("HmacMD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SecretKey MD5key = keyGen.generateKey();

        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacMD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            mac.init(MD5key);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        mac.update(plainText);
        try {
            outString = new String(mac.doFinal(), "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return outString;
    }
}



