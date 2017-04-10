package vngrs.enesgemci.tweetsearch.util;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtilities {

    public static final String SHA256 = "SHA-256";
    private final static String HEX = "0123456789ABCDEF";
    private static String UTF8 = "utf-8";

    // ==============================================================
    //  Hash Utilities
    // ==============================================================

    public static String getSHA256(String str) {
        try {
            return getHash(str, SHA256);
        } catch (Exception e) {
            L.w(e);
            return null;
        }
    }

    public static String getHash(String str, String algorithm) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] bytes = str.getBytes(UTF8);
        MessageDigest md = MessageDigest.getInstance(algorithm);
        byte[] digest = md.digest(bytes);
        BigInteger bigInt = new BigInteger(1, digest);
        String hash = bigInt.toString(16);

        while (hash.length() < 64) {
            hash = "0" + hash;
        }

        return hash;
    }

    // ==============================================================
    //  Encryption
    // ==============================================================

    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA2PRNG");
        sr.setSeed(seed);
        kgen.init(128, sr); // 192 and 256 bits may not be available
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    /**
     * returns null if encounters unsupported encoding
     */
    public static String XOR(String value, String key) {
        byte[] bytevalue = value.getBytes();
        byte[] bytekey = key.getBytes();
        byte[] byteresult = new byte[bytevalue.length];

        for (int i = 0; i < byteresult.length; i++) {
            byteresult[i] = (byte) (bytevalue[i] ^ bytekey[i]);
        }

        try {
            return new String(byteresult, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    }

    // ==============================================================
    // Xor
    // ==============================================================

    public static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    // ==============================================================
    // Crypto utils, mics
    // ==============================================================

    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        }
        return result;
    }

    public static String toHex(byte[] buf) {
        if (buf == null) {
            return "";
        }
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    public static String encrypt(String cleartext, byte[] seed) throws Exception {
        byte[] rawKey = getRawKey(seed);
        byte[] result = encrypt(rawKey, cleartext.getBytes());
        return toHex(result);
    }

    public static String decrypt(String encrypted, byte[] seed) throws Exception {
        byte[] rawKey = getRawKey(seed);
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(rawKey, enc);
        return new String(result);
    }


  /* AES */

    private final static String aesCharacterEncoding = "UTF-8";
    private final static String aesCipherTransformation = "AES/CBC/PKCS5Padding";
    private final static String aesEncryptionAlgorithm = "AES";

    /**
     * <summary> Encrypts plaintext using AES 128bit key and a Chain Block
     * Cipher and returns a base64 encoded string </summary> <param
     * name="plainText">Plain text to encrypt</param> <param name="key">Secret
     * key</param> <returns>Base64 encoded string</returns>
     */
    public static String aesEncrypt(String plainText, String key)
            throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException {
        byte[] plainTextbytes = plainText.getBytes(aesCharacterEncoding);
        byte[] keyBytes = getKeyBytes(key);
        return Base64.encodeToString(encrypt(plainTextbytes, keyBytes, keyBytes), Base64.DEFAULT);
    }

    /**
     * <summary> Decrypts a base64 encoded string using the given key (AES
     * 128bit key and a Chain Block Cipher) </summary> <param
     * name="encryptedText">Base64 Encoded String</param> <param
     * name="key">Secret Key</param> <returns>Decrypted String</returns>
     */
    public static String aesDecrypt(String encryptedText, String key) throws Exception {
        byte[] cipheredBytes = Base64.decode(encryptedText, Base64.DEFAULT);
        byte[] keyBytes = getKeyBytes(key);
        return new String(decrypt(cipheredBytes, keyBytes, keyBytes), aesCharacterEncoding);
    }

    public static byte[] decrypt(byte[] cipherText, byte[] key, byte[] initialVector) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(aesCipherTransformation);
        SecretKeySpec secretKeySpecy = new SecretKeySpec(key, aesEncryptionAlgorithm);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpecy, ivParameterSpec);
        cipherText = cipher.doFinal(cipherText);
        return cipherText;
    }

    public static byte[] encrypt(byte[] plainText, byte[] key, byte[] initialVector) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(aesCipherTransformation);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, aesEncryptionAlgorithm);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        plainText = cipher.doFinal(plainText);
        return plainText;
    }

    private static byte[] getKeyBytes(String key) throws UnsupportedEncodingException {
        byte[] keyBytes = new byte[16];
        byte[] parameterKeyBytes = key.getBytes(aesCharacterEncoding);
        System.arraycopy(parameterKeyBytes, 0, keyBytes, 0, Math.min(parameterKeyBytes.length, keyBytes.length));
        return keyBytes;
    }

}
