/*    */ package net.sydrus.yuzuku.Managers.ConsoleCommand;
/*    */ 
/*    */ import java.util.Calendar;
/*    */ 
/*    */ public class Type
/*    */ {
/*    */   public static enum MessageType
/*    */   {
/*  9 */     Info,  Warning,  Error,  Uncknow,  God;
/*    */     
/*    */     private MessageType() {} }
/*    */   
/* 13 */   public static void messageType(MessageType type, String text) { String toReturn = text;
/* 14 */     if (type == MessageType.Error) {
/* 15 */       toReturn = timeToConsole() + "[Error] " + text;
/* 16 */       System.err.println(toReturn);
/*    */     }
/* 18 */     if (type == MessageType.Info) {
/* 19 */       toReturn = timeToConsole() + "[Info] " + text;
/* 20 */       System.out.println(toReturn);
/*    */     }
/*    */     
/* 23 */     if (type == MessageType.Warning) {
/* 24 */       toReturn = timeToConsole() + "[Warning] " + text;
/* 25 */       System.out.println(toReturn);
/*    */     }
/* 27 */     if (type == MessageType.God) {
/* 28 */       toReturn = timeToConsole() + "[God] " + text;
/* 29 */       System.out.println(toReturn);
/*    */     }
/* 31 */     if (type == MessageType.Uncknow) {
/* 32 */       toReturn = timeToConsole() + "[Uncknow] " + text;
/* 33 */       System.out.println(toReturn);
/*    */     }
/*    */   }
/*    */   
/*    */   public static String timeToConsole() {
/* 38 */     Calendar calendar = Calendar.getInstance(java.util.TimeZone.getDefault());
/* 39 */     int hour = calendar.get(11);
/* 40 */     int minute = calendar.get(12);
/* 41 */     int second = calendar.get(13);
/* 42 */     return "[" + hour + ":" + minute + ":" + second + "] ";
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\ConsoleCommand\Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */