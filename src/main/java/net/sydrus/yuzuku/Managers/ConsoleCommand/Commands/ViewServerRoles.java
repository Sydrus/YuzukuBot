/*    */ package net.sydrus.yuzuku.Managers.ConsoleCommand.Commands;
/*    */ 
/*    */ import net.dv8tion.jda.core.JDA;
/*    */ import net.dv8tion.jda.core.entities.Guild;
/*    */ import net.dv8tion.jda.core.entities.Role;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.ConsoleDef;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;
/*    */ import net.sydrus.yuzuku.YuzukuBot;
/*    */ 
/*    */ public class ViewServerRoles extends net.sydrus.yuzuku.Constructors.ConsoleCommand
/*    */ {
/*    */   public boolean onCommand(ConsoleDef console, String command, String time, String[] args)
/*    */   {
/* 15 */     if (args.length == 1) {
/* 16 */       Guild guild = null;
/*    */       try {
/* 18 */         guild = YuzukuBot.getInstance().getJDA().getGuildById(args[0]);
/*    */       } catch (Exception e) {
/* 20 */         Type.messageType(Type.MessageType.Error, "Error on get server");
/* 21 */         return true;
/*    */       }
/*    */       try {
/* 24 */         console.sendText("Server Roles");
/* 25 */         for (Role rl : guild.getRoles()) {
/* 26 */           console.sendText("Role name: " + rl.getName() + ". id(" + rl.getId() + ")");
/*    */         }
/*    */       } catch (Exception e) {
/* 29 */         Type.messageType(Type.MessageType.Error, "Error on get server roles");
/*    */       }
/*    */     } else {
/* 32 */       Type.messageType(Type.MessageType.Error, "Use \"serverrole <serverId>\"");
/*    */     }
/* 34 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\ConsoleCommand\Commands\ViewServerRoles.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */