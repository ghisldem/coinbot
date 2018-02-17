package org.gh.coinbot.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

public class EncryptionUtility {
	
	public static String calculateHash(String secret, String url, String algorithm) {

		Mac shaHmac = null;

		try {

			shaHmac = Mac.getInstance(algorithm);

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}

		SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), algorithm);

		try {

			shaHmac.init(secretKey);

		} catch (InvalidKeyException e) {

			e.printStackTrace();
		}

		byte[] hash = shaHmac.doFinal(url.getBytes());
		String check = Hex.encodeHexString(hash);

		return check;
	}

	public static String generateNonce() {

		SecureRandom random = null;
		
		try {

			random = SecureRandom.getInstance("SHA1PRNG");

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}

		random.setSeed(System.currentTimeMillis()); 

		byte[] nonceBytes = new byte[16]; 
		random.nextBytes(nonceBytes); 
		
		String nonce = null;

		try {

			nonce = new String(Base64.getEncoder().encode(nonceBytes), "UTF-8");

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		return nonce;
	}
}