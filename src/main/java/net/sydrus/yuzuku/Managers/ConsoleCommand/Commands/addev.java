/*    */ package net.sydrus.yuzuku.Managers.ConsoleCommand.Commands;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.sydrus.yuzuku.FileReader.BCRItem;
/*    */ import net.sydrus.yuzuku.FileReader.BCRItem.Type;
/*    */ import net.sydrus.yuzuku.FileReader.ConfigReader;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.ConsoleDef;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;
/*    */ import net.sydrus.yuzuku.YuzukuBot;
/*    */ 
/*    */ public class addev extends net.sydrus.yuzuku.Constructors.ConsoleCommand
/*    */ {
/*    */   public boolean onCommand(ConsoleDef console, String command, String time, String[] args)
/*    */   {
/* 16 */     if (args.length == 2) {
/* 17 */       YuzukuBot.getInstance().settingsData.reload();
/* 18 */       List<Object> dev = YuzukuBot.getInstance().settingsData.getList(BCRItem.getItem(BCRItem.Type.Developers));
/* 19 */       if (dev.contains(args[1])) {
/* 20 */         Type.messageType(Type.MessageType.Error, "**Has already been added**");
/* 21 */         return true;
/*    */       }
/* 23 */       dev.add(args[1]);
/* 24 */       YuzukuBot.getInstance().settingsData.set(BCRItem.getItem(BCRItem.Type.Developers), dev);
/* 25 */       YuzukuBot.getInstance().settingsData.save();
/* 26 */       Type.messageType(Type.MessageType.Info, "Added");
/*    */     } else {
/* 28 */       Type.messageType(Type.MessageType.Error, "Use \"adDeveloper <encode> <user id>\"");
/*    */     }
/* 30 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\ConsoleCommand\Commands\addev.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */