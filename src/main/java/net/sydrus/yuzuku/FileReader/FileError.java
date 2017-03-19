/*    */ package net.sydrus.yuzuku.FileReader;
/*    */ 
/*    */ public class FileError extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 4547984874645364635L;
/*    */   
/*    */   public FileError() {}
/*    */   
/*    */   public FileError(String paramString)
/*    */   {
/* 11 */     super(paramString);
/*    */   }
/*    */   
/*    */   public FileError(String paramString, Throwable paramThrowable) {
/* 15 */     super("Load the file before trying to use it: " + paramString, paramThrowable);
/*    */   }
/*    */   
/*    */   public FileError(Throwable paramThrowable) {
/* 19 */     super(paramThrowable);
/*    */   }
/*    */   
/*    */   protected FileError(String paramString, Throwable paramThrowable, boolean paramBoolean1, boolean paramBoolean2) {
/* 23 */     super(paramString, paramThrowable, paramBoolean1, paramBoolean2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\FileReader\FileError.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */