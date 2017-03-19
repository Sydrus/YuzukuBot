/*    */ package net.sydrus.yuzuku.Managers;
/*    */ 
/*    */ public class BotCommand extends Throwable
/*    */ {
/*    */   private static final long serialVersionUID = -5129054224373554452L;
/*    */   
/*    */   public BotCommand(String addonName) {
/*  8 */     super("**The command " + addonName + " is already registered**");
/*    */   }
/*    */   
/*    */   public BotCommand(String paramString, Throwable paramThrowable) {
/* 12 */     super(paramString, paramThrowable);
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\BotCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */