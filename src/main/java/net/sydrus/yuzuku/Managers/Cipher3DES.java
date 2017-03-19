package net.sydrus.yuzuku.Managers;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@SuppressWarnings("restriction")
public class Cipher3DES {
	SecretKeySpec chave;
	IvParameterSpec iv;
	Cipher cifrador;

	public Cipher3DES(String key, int vector) throws Exception {
		if (key.length() != 24) {
			throw new Exception("The key can not be longer than 24 characters");
		}
		String vectorr = vector + "";
		if(vectorr.length() != 8) {
			throw new Exception("The vector can not be longer than 8 characters");
		}
		cifrador = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		chave = new SecretKeySpec(key.getBytes("UTF8"), "DESede");
		iv = new IvParameterSpec(vectorr.getBytes());
	}

	public String encryptText(String original) throws UnsupportedEncodingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		byte[] plaintext = original.getBytes("UTF8");
		cifrador.init(Cipher.ENCRYPT_MODE, chave, iv);
		byte[] cipherText = cifrador.doFinal(plaintext);
		return new String(Base64.encode(cipherText));
	}

	public String decryptText(String hidden) throws InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException {
		byte[] hiddentext = Base64.decode(hidden);
		cifrador.init(Cipher.DECRYPT_MODE, chave, iv);
		byte[] originalText = cifrador.doFinal(hiddentext);
		return new String(originalText);
	}
}