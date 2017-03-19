/*    */ package net.sydrus.yuzuku.Managers.ConsoleCommand.Commands;
/*    */ 
/*    */ import net.dv8tion.jda.core.JDA;
/*    */ import net.dv8tion.jda.core.entities.Guild;
/*    */ import net.dv8tion.jda.core.exceptions.RateLimitedException;
/*    */ import net.dv8tion.jda.core.managers.GuildController;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.ConsoleDef;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;
/*    */ import net.sydrus.yuzuku.YuzukuBot;
/*    */ 
/*    */ public class ban extends net.sydrus.yuzuku.Constructors.ConsoleCommand
/*    */ {
/*    */   public boolean onCommand(ConsoleDef console, String command, String time, String[] args)
/*    */   {
/* 16 */     if (args.length == 2) {
/* 17 */       console.sendText("Antes do jda");
/* 18 */       Guild gd = YuzukuBot.getInstance().getJDA().getGuildById(args[0]);
/*    */       try {
/* 20 */         console.sendText("No try");
/* 21 */         gd.getController().ban(args[1], 0).block();
/* 22 */         console.sendText("Foi");
/*    */       } catch (RateLimitedException e) {
/* 24 */         console.sendText("Erro: " + e.getMessage());
/*    */       }
/*    */     }
/*    */     else {
/* 28 */       Type.messageType(Type.MessageType.Error, "Use \"ban <Server id> <user id>\"");
/*    */     }
/* 30 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\ConsoleCommand\Commands\ban.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */