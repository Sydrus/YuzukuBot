/*    */ package net.sydrus.yuzuku.audio;
/*    */ 
/*    */ public class AudioException extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 8172527932742632531L;
/*  6 */   private static ExceptionType type = ExceptionType.none;
/*    */   
/*    */   public ExceptionType getExceptionType() {
/*  9 */     return type;
/*    */   }
/*    */   
/*    */   private static String getExceptionType(String error, ExceptionType bool) {
/* 13 */     if (bool == ExceptionType.isFatal) {
/* 14 */       type = ExceptionType.isFatal;
/* 15 */       return "[FATAL] " + error;
/*    */     }
/* 17 */     if (bool == ExceptionType.isError) {
/* 18 */       type = ExceptionType.isError;
/* 19 */     } else if (bool == ExceptionType.IsWarning) {
/* 20 */       type = ExceptionType.IsWarning;
/*    */     }
/* 22 */     return error;
/*    */   }
/*    */   
/*    */   public AudioException(String error, Throwable reason)
/*    */   {
/* 27 */     super(error, reason);
/*    */   }
/*    */   
/*    */   public AudioException(String error, Throwable reason, ExceptionType type) {
/* 31 */     super(getExceptionType(error, type), reason);
/*    */   }
/*    */   
/*    */   public AudioException(String error, ExceptionType type) {
/* 35 */     super(error);
/*    */   }
/*    */   
/*    */   public static enum ExceptionType {
/* 39 */     isFatal,  IsWarning,  isError,  none;
/*    */     
/*    */     private ExceptionType() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\audio\AudioException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */