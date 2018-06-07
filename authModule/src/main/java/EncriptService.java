import java.security.*;


public class EncriptService {

    public static String run(String str) throws Exception {
        byte[] plainText = str.getBytes("UTF8");
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update( plainText);
        return new String( messageDigest.digest(), "UTF8");
    }
}



