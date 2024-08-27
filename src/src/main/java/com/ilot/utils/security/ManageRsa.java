package com.ilot.utils.security;



import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;

import org.bouncycastle.util.encoders.Base64;



/**
 *
 * @author FOSSOU
 */
public class ManageRsa {

    public ManageRsa() {
    }

    public static String decryptRsa(String data_encoded) {
        String data_decoded = "";
        try {
            Cipher cipher = Cipher.getInstance(OssFixeUtils.ALGORITHM_RSA);
            Rsa rsa = new Rsa();
            //utilisation de la clé privé pour décrypter
            data_decoded = Rsa.decrypt(Base64.decode(data_encoded.getBytes(StandardCharsets.UTF_8)), rsa.getPrivateKey(), cipher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data_decoded;
    }

    public static String encryptRsa(String data) {
        String data_encoded = "";
        try {
            Cipher cipher = Cipher.getInstance(OssFixeUtils.ALGORITHM_RSA);
            Rsa rsa = new Rsa();
            data_encoded = Rsa.encrypt(data.getBytes(StandardCharsets.UTF_8), rsa.getPublicKey(), cipher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data_encoded;
    }

}
