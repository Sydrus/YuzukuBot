/*    */ package net.sydrus.yuzuku.Managers.ConsoleCommand.Commands;
/*    */ 
/*    */ import net.dv8tion.jda.core.entities.Guild;
/*    */ import net.dv8tion.jda.core.entities.Role;
/*    */ import net.dv8tion.jda.core.exceptions.RateLimitedException;
/*    */ import net.dv8tion.jda.core.managers.GuildController;
/*    */ import net.dv8tion.jda.core.requests.RestAction;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.ConsoleDef;
/*    */ import net.sydrus.yuzuku.YuzukuBot;
/*    */ 
/*    */ public class addrole extends net.sydrus.yuzuku.Constructors.ConsoleCommand
/*    */ {
/*    */   public boolean onCommand(ConsoleDef console, String command, String time, String[] args)
/*    */   {
/* 15 */     if (args.length == 3) {
/* 16 */       Guild gd = YuzukuBot.getInstance().getJDA().getGuildById(args[0]);
/* 17 */       gd.getController().addRolesToMember(gd.getMemberById(args[1]), new Role[] { gd.getRoleById(args[2]) }).queue();
/*    */       try {
/* 19 */         gd.getController().addRolesToMember(gd.getMemberById(args[1]), new Role[] { gd.getRoleById(args[2]) }).block();
/*    */       } catch (RateLimitedException e) {
/* 21 */         e.printStackTrace();
/*    */       }
/*    */     } else {
/* 24 */       net.sydrus.yuzuku.Managers.ConsoleCommand.Type.messageType(net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType.Error, "Use \"adrole <encode> <user id>\"");
/*    */     }
/* 26 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\ConsoleCommand\Commands\addrole.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */