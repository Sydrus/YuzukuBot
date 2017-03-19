/*    */ package net.sydrus.yuzuku.Managers;
/*    */ 
/*    */ import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
/*    */ import java.security.InvalidAlgorithmParameterException;
/*    */ import java.security.InvalidKeyException;
/*    */ import javax.crypto.BadPaddingException;
/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.IllegalBlockSizeException;
/*    */ import javax.crypto.spec.IvParameterSpec;
/*    */ import javax.crypto.spec.SecretKeySpec;
/*    */ 
/*    */ public class Cipher3DES
/*    */ {
/*    */   SecretKeySpec chave;
/*    */   IvParameterSpec iv;
/*    */   Cipher cifrador;
/*    */   
/*    */   public Cipher3DES(String key, int vector) throws Exception
/*    */   {
/* 20 */     if (key.length() != 24) {
/* 21 */       throw new Exception("The key can not be longer than 24 characters");
/*    */     }
/* 23 */     String vectorr = vector + "";
/* 24 */     if (vectorr.length() != 8) {
/* 25 */       throw new Exception("The vector can not be longer than 8 characters");
/*    */     }
/* 27 */     this.cifrador = Cipher.getInstance("DESede/CBC/PKCS5Padding");
/* 28 */     this.chave = new SecretKeySpec(key.getBytes("UTF8"), "DESede");
/* 29 */     this.iv = new IvParameterSpec(vectorr.getBytes());
/*    */   }
/*    */   
/*    */   public String encryptText(String original) throws java.io.UnsupportedEncodingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
/*    */   {
/* 34 */     byte[] plaintext = original.getBytes("UTF8");
/* 35 */     this.cifrador.init(1, this.chave, this.iv);
/* 36 */     byte[] cipherText = this.cifrador.doFinal(plaintext);
/* 37 */     return new String(Base64.encode(cipherText));
/*    */   }
/*    */   
/*    */   public String decryptText(String hidden) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
/*    */   {
/* 42 */     byte[] hiddentext = Base64.decode(hidden);
/* 43 */     this.cifrador.init(2, this.chave, this.iv);
/* 44 */     byte[] originalText = this.cifrador.doFinal(hiddentext);
/* 45 */     return new String(originalText);
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\Cipher3DES.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */