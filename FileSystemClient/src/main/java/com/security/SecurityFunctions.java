package com.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.sun.jersey.core.util.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SecurityFunctions {

	public static String encrypt(String strClearText,String strKey) {
		String strData="";

		try {
			SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes("Cp1252"),"Blowfish");
			Cipher cipher=Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
			byte[] encrypted=cipher.doFinal(strClearText.getBytes("Cp1252"));
			strData=new String(Base64.encode(encrypted));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strData;
	}

	public static String decrypt(String strEncrypted,String strKey) throws UnsupportedEncodingException {
		String strData="";
		try {
			byte[] encByte= strEncrypted.getBytes("Cp1252");
			byte[] bytEncrypted = Base64.decode(encByte);
			SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes("Cp1252"),"Blowfish");
			Cipher cipher=Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE, skeyspec);
			byte[] decrypted=cipher.doFinal(bytEncrypted);
			strData=new String(decrypted);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return strData;
	}
	public static String getNewKey()  {
		Key symKey=null;
		BASE64Encoder be = new BASE64Encoder();
		try {
			symKey = KeyGenerator.getInstance("Blowfish").generateKey();
			
		}
		catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return symKey.getEncoded().toString();
	}
	/*public static Key getKeyfromString(String keyString) {
		BASE64Decoder decoder = new BASE64Decoder();
	    byte[] encodedKey =new byte[5000];
		try {
			encodedKey = decoder.decodeBuffer(keyString);
		} catch (IOException e) {
			e.printStackTrace();
		}
        Key key = new SecretKeySpec(encodedKey,0,encodedKey.length, "Blowfish");   
        return key;
	}*/
}