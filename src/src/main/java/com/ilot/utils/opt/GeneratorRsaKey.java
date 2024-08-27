package com.ilot.utils.opt;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Log
@Component
public class GeneratorRsaKey {
	//ce code permet de générer les clés rsa
	public static void geneerateKeyPair() throws FileNotFoundException, IOException, NoSuchAlgorithmException {
		log.info("***********debut génrétion de cle RSA*********");
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(2048);
		KeyPair kp = kpg.generateKeyPair();
		PrivateKey aPrivate = kp.getPrivate();
		PublicKey aPublic = kp.getPublic();
		
		try (FileOutputStream outPrivate = new FileOutputStream("key.priv")) {
		    outPrivate.write(aPrivate.getEncoded());
		}

		try (FileOutputStream outPublic = new FileOutputStream("key.pub")) {
		    outPublic.write(aPublic.getEncoded());
		}
		
		System.out.println("Private key: " + aPrivate.getFormat());

		System.out.println("Public key: " + aPublic.getFormat());
		log.info("***********Fin génrétion de cle RSA*********");
	}
	
	//récupère la clé privé
	public static PrivateKey loadPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		File privateKeyFile = new File("key.priv");
		byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());

		KeyFactory privateKeyFactory = KeyFactory.getInstance("RSA");
		EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
		PrivateKey privateKey = privateKeyFactory.generatePrivate(privateKeySpec);
		return privateKey;
	}
	
	//récupère la clé plublic
	public static PublicKey loadPublicKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		File publicKeyFile = new File("key.pub");
		byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());

		KeyFactory publicKeyFactory = KeyFactory.getInstance("RSA");
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
		PublicKey publicKey = publicKeyFactory.generatePublic(publicKeySpec);
		
		return publicKey;
	}
	
	public static byte[] encryptData(String datas) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, IOException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		Cipher encryptCipher = Cipher.getInstance("RSA");
		encryptCipher.init(Cipher.ENCRYPT_MODE, loadPublicKey());
		
		//convertion des données en byte
		byte[] datasBytes = datas.getBytes(StandardCharsets.UTF_8);
		byte[] encryptedDatasBytes = encryptCipher.doFinal(datasBytes);
		return encryptedDatasBytes;
	}
	
	public static String decryptData(byte[] encryptedMessageBytes) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, IOException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		Cipher decryptCipher = Cipher.getInstance("RSA");
		decryptCipher.init(Cipher.ENCRYPT_MODE, loadPrivateKey());
		
		byte[] decryptedDatasBytes = decryptCipher.doFinal(encryptedMessageBytes);
		String decryptMessage = new String(decryptedDatasBytes, StandardCharsets.UTF_8);
		return decryptMessage;
	}
}
