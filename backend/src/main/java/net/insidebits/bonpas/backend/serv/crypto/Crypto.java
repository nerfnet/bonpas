package net.insidebits.bonpas.backend.serv2.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Cryptographic helper functions.
 */
public class Crypto {

    /**
     * Verifies the availability of the required algorithms on the system.
     * In the event that this check fails, the application should terminate.
     *
     * @return True if integrity check passed, false if failed.
     */
    public static boolean verifyIntegrity() {
        try {
            MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
        return true;
    }

    /**
     * Converts a sequence of bytes to a Hex representation.
     */
    public static String toHexString(byte[] bytes) {
        char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for ( int j = 0; j < bytes.length; j++ ) {
            v = bytes[j] & 0xFF;
            hexChars[j*2] = hexArray[v/16];
            hexChars[j*2 + 1] = hexArray[v%16];
        }
        return new String(hexChars);
    }
}
