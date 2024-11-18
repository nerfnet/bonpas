package net.insidebits.bonpas.backend.serv2.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    /**
     * The NoSuchAlgorithmException should never be thrown, as {@link Crypto#verifyIntegrity()} is
     * called before the application fully initializes, so we choose not to actually handle it here.
     *
     * Verifies whether some MD5 checksum matches the MD5 checksum of some input.
     *
     * @param input            The input to verify.
     * @param expectedChecksum The checksum to check against.
     */
    public static boolean verifyChecksum(String input, String expectedChecksum)  {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
        messageDigest.update(input.getBytes());

        byte[] inputDigest = messageDigest.digest();
        String actualChecksum = Crypto.toHexString(inputDigest);

        return actualChecksum.equalsIgnoreCase(expectedChecksum);
    }
}
