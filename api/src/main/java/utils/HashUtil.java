package utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jandie
 */
public class HashUtil {
    /**
     * Creates a new instance of HashUtil. This should not be used.
     */
    public HashUtil() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * Hashes a password based on a password string and salt.
     *
     * @param password  The password to be hashed.
     * @param salt      The salt used for hashing the password.
     * @param algorithm The algorithm used to create the hash.
     * @param encoding  The type of encoding of the password.
     * @return A hashed password with salt.
     */
    public static String hashPassword(String password, String salt,
                                      String algorithm, String encoding) {
        try {
            if (salt == null) return null;

            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(salt.getBytes(encoding));
            byte[] bytes = md.digest(password.getBytes(encoding));

            return String.format("%064x", new java.math.BigInteger(1, bytes));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(HashUtil.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Generates a pseudo randomized salt.
     *
     * @return A pseudo randomized salt.
     * @throws NoSuchAlgorithmException Algorithm doesn't exist.
     */
    public static String generateSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[32];
        sr.nextBytes(salt);

        return String.format("%064x", new java.math.BigInteger(1, salt));
    }
}
