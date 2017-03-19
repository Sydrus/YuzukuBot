/*    */ package net.sydrus.yuzuku.FileReader;
/*    */ 
/*    */ public class BCRItem {
/*    */   public static enum Type {
/*  5 */     Developers,  Administrators,  BannedTexts;
/*    */     
/*    */     private Type() {} }
/*    */   
/*  9 */   public static String getItem(Type type) { if (type == Type.Administrators)
/* 10 */       return "administrators";
/* 11 */     if (type == Type.BannedTexts)
/* 12 */       return "txtbnd";
/* 13 */     if (type == Type.Developers) {
/* 14 */       return "developers";
/*    */     }
/* 16 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\FileReader\BCRItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */