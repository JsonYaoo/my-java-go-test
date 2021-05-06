package com.jsonyao.encryption;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

public class SignUtils {
	
	private static String md5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(str.getBytes("UTF-8"));
		byte[] digest = md.digest();
		
		return DatatypeConverter.printHexBinary(digest);
	}
	
	private static String sha(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA");
		md.update(str.getBytes("UTF-8"));
		byte[] digest = md.digest();
		
		return DatatypeConverter.printHexBinary(digest);
	}
	
	private static String sha256(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(str.getBytes("UTF-8"));
		byte[] digest = md.digest();
		
		return DatatypeConverter.printHexBinary(digest);
	}
	
	public static PublicKey toPublicKey(String str) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		byte[] bytes = Base64.getDecoder().decode(str);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
		
		return keyFactory.generatePublic(keySpec);
	}
	
	public static PrivateKey toPrivateKey(String str) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		byte[] bytes = Base64.getDecoder().decode(str);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
		
		return keyFactory.generatePrivate(keySpec);
	}
	
	
	public static String rsaEncrypt(String str, Key key) throws Exception{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] ret = cipher.doFinal(str.getBytes("UTF-8"));
		
		return Base64.getEncoder().encodeToString(ret);
	}
	
	public static String rsaDecrypt(String str, Key key) throws Exception{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, key);
		
		byte[] ret = cipher.doFinal(Base64.getDecoder().decode(str));
		return new String(ret, "UTF-8");
	}
	
	public static String rsaSign(PrivateKey privateKey, String str) throws Exception {
		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initSign(privateKey);
		signature.update(str.getBytes("UTF-8"));
		
		byte[] bytes = signature.sign();
		return Base64.getEncoder().encodeToString(bytes);
	}
	
	public static boolean rsaVerifySign(PublicKey publicKey, String str, String sign) throws Exception {
		Signature signature = Signature.getInstance("MD5withRSA");
		signature.initVerify(publicKey);
		signature.update(str.getBytes("UTF-8"));
		
		return signature.verify(Base64.getDecoder().decode(sign));
	}
	
	
	
	public static void main(String[] args) throws  Exception {
		String base64Str = Base64.getEncoder().encodeToString("CAF".getBytes("ASCII"));
		//System.out.println(base64Str);
		
		String base64Str2 = new String(Base64.getDecoder().decode(base64Str), "ASCII");
		//System.out.println(base64Str2);
		
		String input = "hello zhaowa";
//		System.out.println(md5(input));
//		System.out.println(sha(input));
//		System.out.println(sha256(input));
		
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4TJnRm1VeQRcOR452hec3Wb21\n"
				+ "O8rLFSXJZTUn/cZ5hlYEjwEKe2nljNHKvA37w7xDbLM6hxxBOhqlJ1SMqgP+1XL0\n"
				+ "BI0ZPVn/SEH60gK5pbtwrHtigkycbkvkL3QjNI9WS+i7JrB97VNB/xEGErfFSHY+\n"
				+ "fA08sAq/0u33gFfuSQIDAQAB";
		String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALhMmdGbVV5BFw5H\n"
				+ "jnaF5zdZvbU7yssVJcllNSf9xnmGVgSPAQp7aeWM0cq8DfvDvENsszqHHEE6GqUn\n"
				+ "VIyqA/7VcvQEjRk9Wf9IQfrSArmlu3Cse2KCTJxuS+QvdCM0j1ZL6LsmsH3tU0H/\n"
				+ "EQYSt8VIdj58DTywCr/S7feAV+5JAgMBAAECgYBqqhrUT1yHIpDfeNahDjqVtGCx\n"
				+ "hZuHEgQ+nU64iI3YQ8GA/esST/8oFepNta06KzzIoR7SHuRhc+l78Flk1/lbBmpt\n"
				+ "5whynxLY/2HGUVB2kiI+aCeNxvSWp6Ud9+ZYRpQJRNm2lBwr2/TyKIQj01fkV+mj\n"
				+ "F0ioLG9Wyqr8h2yOAQJBANsOqEuCT98wh9lQvmKaDOtj4/xQOz1ruoyfb/KSGU/t\n"
				+ "oT7jJlb0PBNnP2TWgamGUtY1jOjS6wcaUdEWmRVuauECQQDXYVZj6vyA6SwmgP9m\n"
				+ "oSq7mviesC6b3FKNZ1uKx+5nYGOG8pRF0M1fFGWlGz0g6Qr+prjwaPXLf6556ZH+\n"
				+ "KRhpAkBobf1nOfFv2kf5HtgUU5JzKUTHxUvohRIC9gM9Zc7xXryvKUTe0UJOkbsU\n"
				+ "DHr14VZeTlslp19qHG8Cub4zDFehAkEAww96yD9XtAB5Zd3KcU5hf0sZA88YRzxA\n"
				+ "36PN+mCb/7ACM4Oa2agDd6rna6LBt/6XYI3qTIEiqszWXb8143OgkQJAZtLhuJZ5\n"
				+ "QErAgR0Uq0zaaBy/iKG+04yPcX8Hqf0ADYzQWhih/JIX+1ooKtVOogKQMSP/SVdJ\n"
				+ "5OxtPwjbF5YHFg==";
		
		publicKey = publicKey.replace("\n", "");
		privateKey = privateKey.replace("\n", "");
		String rsaEncryptStr = rsaEncrypt(input, toPublicKey(publicKey));
		String rsaDecryptStr = rsaDecrypt(rsaEncryptStr, toPrivateKey(privateKey));
		
		//System.out.println(rsaDecryptStr);
		
		String sign = rsaSign(toPrivateKey(privateKey), input);
		System.out.println(rsaVerifySign(toPublicKey(publicKey), input, sign));
	}
}
