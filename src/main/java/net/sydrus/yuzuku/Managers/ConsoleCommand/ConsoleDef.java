/*    */ package net.sydrus.yuzuku.Managers.ConsoleCommand;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class ConsoleDef
/*    */ {
/*    */   private Console console;
/*    */   
/*    */   public ConsoleDef(Console console) {
/* 10 */     this.console = console;
/*    */   }
/*    */   
/*    */   public void sendText(String text) {
/* 14 */     System.out.println(Type.timeToConsole() + "[Info] " + text);
/*    */   }
/*    */   
/*    */   public void sendText(Type.MessageType type, String text) {
/* 18 */     String toReturn = text;
/* 19 */     if (type == Type.MessageType.Error) {
/* 20 */       toReturn = Type.timeToConsole() + "[Error] " + text;
/* 21 */       System.err.println(toReturn);
/*    */     }
/* 23 */     if (type == Type.MessageType.Info) {
/* 24 */       toReturn = Type.timeToConsole() + "[Info] " + text;
/* 25 */       System.out.println(toReturn);
/*    */     }
/*    */     
/* 28 */     if (type == Type.MessageType.Warning) {
/* 29 */       toReturn = Type.timeToConsole() + "[Warning] " + text;
/* 30 */       System.out.println(toReturn);
/*    */     }
/* 32 */     if (type == Type.MessageType.Uncknow) {
/* 33 */       toReturn = Type.timeToConsole() + "[Uncknow] " + text;
/* 34 */       System.out.println(toReturn);
/*    */     }
/*    */   }
/*    */   
/*    */   public Console getConsole() {
/* 39 */     return this.console;
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\ConsoleCommand\ConsoleDef.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */