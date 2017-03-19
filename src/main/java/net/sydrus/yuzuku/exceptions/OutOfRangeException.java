/*    */ package net.sydrus.yuzuku.exceptions;
/*    */ 
/*    */ public class OutOfRangeException extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public OutOfRangeException() {
/*  8 */     super("**You have exceeded the character limit**");
/*    */   }
/*    */   
/*    */   public OutOfRangeException(Throwable throlable) {
/* 12 */     super("**You have exceeded the character limit**", throlable);
/*    */   }
/*    */   
/*    */   public OutOfRangeException(int number) {
/* 16 */     super("**You have exceeded the character limit:** " + number);
/*    */   }
/*    */   
/*    */   public OutOfRangeException(int maxNumber, int number) {
/* 20 */     super("**You have exceeded the limit of " + maxNumber + ", current** " + number);
/*    */   }
/*    */   
/*    */   public OutOfRangeException(String text, Throwable throlable) {
/* 24 */     super(text, throlable);
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\exceptions\OutOfRangeException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */