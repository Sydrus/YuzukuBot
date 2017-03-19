/*    */ package net.sydrus.yuzuku.Managers.ConsoleCommand.Commands;
/*    */ 
/*    */ import net.dv8tion.jda.core.JDA;
/*    */ import net.dv8tion.jda.core.entities.Guild;
/*    */ import net.dv8tion.jda.core.managers.GuildController;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.ConsoleDef;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;
/*    */ import net.sydrus.yuzuku.YuzukuBot;
/*    */ 
/*    */ public class unban extends net.sydrus.yuzuku.Constructors.ConsoleCommand
/*    */ {
/*    */   public boolean onCommand(ConsoleDef console, String command, String time, String[] args)
/*    */   {
/* 15 */     if (args.length == 2) {
/* 16 */       Guild gd = YuzukuBot.getInstance().getJDA().getGuildById(args[0]);
/* 17 */       gd.getController().unban(args[1]);
/*    */     } else {
/* 19 */       Type.messageType(Type.MessageType.Error, "Use \"unban  <serverId> <user id>\"");
/*    */     }
/* 21 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\ConsoleCommand\Commands\unban.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */