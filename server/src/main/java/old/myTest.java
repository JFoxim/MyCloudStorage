package old;

import sun.plugin2.message.Message;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import java.security.*;
import javax.crypto.*;

public class myTest {
    public static void main(String[] args) {

        //uuidTest();
        //encript();
        //dd();
        //MessageAuth();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date dt = new Date();
        System.out.println(dt);

    }

    public static void dd(){
        for (int i = 0; i < 30; i++) {
            int res = (UUID.randomUUID().toString().hashCode() + UUID.randomUUID().toString().hashCode());
            System.out.println(res);
        }
    }

    public static void uuidTest(){
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString().length());
        String str = "1234";
        System.out.println(str.hashCode());
    }

    public static void encript(){
        byte[] plainText = new byte[0];
        try {
            plainText = "TestText".getBytes("UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        System.out.println( "\n" + messageDigest.getProvider().getInfo() );

        messageDigest.update(plainText);
        System.out.println( "\nDigest: " );
        try {
            System.out.println( new String(messageDigest.digest(), "UTF8") );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void MessageAuth(){
        String[] strArray = new String[4];
        strArray[0] = "TestString";
        byte[] plainText = new byte[0];
        try {
            plainText = strArray[0].getBytes("UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println( "\nStart generating key" );
        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("HmacMD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SecretKey MD5key = keyGen.generateKey();
        System.out.println( "Finish generating key" );


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

        System.out.println("\n" + mac.getProvider().getInfo() );
        System.out.println("\nMAC: " );
        try {
            System.out.println(new String(mac.doFinal(), "UTF8") );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}


