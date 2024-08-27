package com.ilot.utils.security;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.ilot.utils.Status;
import com.ilot.utils.Utilities;
import com.ilot.utils.contract.Response;
import com.ilot.utils.dto.UserDto;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

/**
 *
 * @author FOSSOU
 */
public class ManageAes {

    public ManageAes() {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }
    // nly

    // nly


    public static String encryptAes(String data_a_encoded, String secretCode) {
        String data_encoded = "";
        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipherAes = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
            byte[] decodedKey = Base64.decode(secretCode.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKey = new SecretKeySpec(decodedKey, "AES");
            data_encoded = Aes.encrypt(data_a_encoded.getBytes(Charset.forName("UTF-8")), secretKey, cipherAes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data_encoded;


    }
    public static String decryptAes(String data_a_encoded, String secretCode) {
        String data_decoded = "";
        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipherAes = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
            byte[] decodedKey = Base64.decode(secretCode.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKey = new SecretKeySpec(decodedKey, "AES");
            data_decoded = Aes.decrypt(Base64.decode(data_a_encoded.getBytes(StandardCharsets.UTF_8)), secretKey, cipherAes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data_decoded;
    }
}
