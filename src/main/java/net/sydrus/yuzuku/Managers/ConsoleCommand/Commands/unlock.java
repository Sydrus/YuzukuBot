/*    */ package net.sydrus.yuzuku.Managers.ConsoleCommand.Commands;
/*    */ 
/*    */ import net.sydrus.yuzuku.FileReader.ConfigReader;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.ConsoleDef;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;
/*    */ import net.sydrus.yuzuku.YuzukuBot;
/*    */ 
/*    */ public class unlock extends net.sydrus.yuzuku.Constructors.ConsoleCommand
/*    */ {
/*    */   public boolean onCommand(ConsoleDef console, String command, String time, String[] args)
/*    */   {
/* 13 */     if (YuzukuBot.getInstance().getusers().isEmpty()) {
/* 14 */       YuzukuBot.getInstance().console.setLockStatus(getClass(), false);
/* 15 */       Type.messageType(Type.MessageType.Info, "Unlocked");
/* 16 */       Type.messageType(Type.MessageType.Warning, "**No account is registered, type \"regc <mail> <pass>\" to register an account**");
/* 17 */       return true;
/*    */     }
/* 19 */     if (args.length == 2) {
/* 20 */       if (YuzukuBot.getInstance().getusers().contains(args[0])) {
/* 21 */         if (YuzukuBot.getInstance().getusers().getString(args[0]).equals(args[1])) {
/* 22 */           YuzukuBot.getInstance().console.setLockStatus(getClass(), false);
/* 23 */           Type.messageType(Type.MessageType.Info, "Unlocked");
/*    */         } else {
/* 25 */           Type.messageType(Type.MessageType.Error, "**Invalid Mail or Passworkd**");
/*    */         }
/*    */       } else {
/* 28 */         Type.messageType(Type.MessageType.Error, "**Invalid Mail or Password**");
/*    */       }
/*    */     } else {
/* 31 */       Type.messageType(Type.MessageType.Error, "**Use** \"unlock <mail> <pass>\"");
/*    */     }
/* 33 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\ConsoleCommand\Commands\unlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */