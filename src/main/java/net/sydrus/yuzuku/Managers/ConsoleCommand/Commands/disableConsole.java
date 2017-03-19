/*    */ package net.sydrus.yuzuku.Managers.ConsoleCommand.Commands;
/*    */ 
/*    */ import net.sydrus.yuzuku.FileReader.ConfigReader;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Console;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.ConsoleDef;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
/*    */ import net.sydrus.yuzuku.YuzukuBot;
/*    */ 
/*    */ public class disableConsole extends net.sydrus.yuzuku.Constructors.ConsoleCommand
/*    */ {
/*    */   public boolean onCommand(ConsoleDef console, String command, String time, String[] args)
/*    */   {
/* 13 */     if (args.length == 2) {
/* 14 */       YuzukuBot.getInstance().settingsData.set("ConsoleCommands", Boolean.valueOf(false));
/* 15 */       YuzukuBot.getInstance().settingsData.save();
/* 16 */       System.out.println("Console disabled");
/* 17 */       YuzukuBot.getInstance().console.updateState();
/*    */     } else {
/* 19 */       Type.messageType(net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType.Error, "Use \"disableconsole <mail> <pass>\"");
/*    */     }
/* 21 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\ConsoleCommand\Commands\disableConsole.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */