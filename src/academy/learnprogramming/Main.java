package academy.learnprogramming;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class Main {

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException {

        // Static String for example
        String passwordToHash = "myPassword";

        // creating a salt

        byte[] salt = createSalt();

        byte[] salt1 = getSalt();

        String securityPassword = get_SecurePassword(passwordToHash, salt1);
        System.out.println("The MD5 salt value is " + securityPassword);

        // create a hash

        String securedPassword = get_SecurePassword(passwordToHash, salt);
        System.out.println(securedPassword);
        String md5Value = md5("Nateberman87");
        System.out.println(md5Value);
    }

    private static byte[] createSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    // Method to generate the hash
    // It takes a password and the Salt as input arguments

    private static String get_SecurePassword(String passwordToHash, byte[] salt) {
        String generatedPassword = null;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(salt);
            byte[] bytes = messageDigest.digest(passwordToHash.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i <bytes.length; i++) {
                stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    //Generating MD5 without salt

    public static String md5(String input) {
        String md5 = null;

        if (null == input) return null;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(input.getBytes(), 0, input.length());
            md5 = new BigInteger(1, messageDigest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }

    //Generating MD5 with salt

    private static byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
        //Always use a SecureRandom generator

        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
        //Create array for salt

        byte[] salt = new byte[16];
        //Get Random salt

        secureRandom.nextBytes(salt);
        //return salt
        return salt;
    }
}
