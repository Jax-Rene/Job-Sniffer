package com.zhuangjy.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Author:王旗
 * Date:2015/5/14 11:07
 * Description:
 */
public class CryptoUtil {

    private static final Logger logger = LoggerFactory.getLogger(CryptoUtil.class);

    private static byte[] iv = new byte[] { 82, 98, 50, 3, -16, 124, -40, -114, -87, -40, 37, 23, -56, 23, -33, 75 };
    private static byte[] aesKey = new byte[] { -42, 23, 67, -86, 19, 29, -11, 84, 94, 111, 75, -104, 71, 88, 86, -21 };

    public static String decryptApi(String input) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        if (input == null) {
            return null;
        }
        byte[] ba = doAes(asBin(input), Cipher.DECRYPT_MODE);
        if (ba == null) {
            return null;
        }
        return new String(ba);
    }

    private static byte[] doAes(byte[] input, int opmode) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        if (input == null) {
            return null;
        }
        Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        ecipher.init(opmode, new SecretKeySpec(aesKey, "AES"), new IvParameterSpec(iv));
        return ecipher.doFinal(input);
    }

    private static byte[] asBin(String src) {
        // 长度必须是32的倍数才能解密
        int length = StringUtils.hasText(src) ? src.length() : 0;
        if (length < 32 || length % 32 != 0) {
            logger.error("error while prepare encrypt api, source is not 16 multiple: {}", src);
            return null;
        }
        try {
            byte[] encrypted = new byte[src.length() / 2];
            for (int i = 0; i < src.length() / 2; i++) {
                int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);

                encrypted[i] = (byte) (high * 16 + low);
            }
            return encrypted;
        } catch (Exception e) {
            logger.error("error while convert to byte[]: {}", src);
            return null;
        }
    }

    public static void main(String[] args) throws Exception{
        byte[] bytes = doAes("".getBytes(),1);
        String before = parseByte2HexStr(bytes);
        System.out.println("加密前:" + before);
        String after = decryptApi(before);
        System.out.println("解密:" + after);
    }

    /**将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
    
}
