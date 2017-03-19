/*    */ package net.sydrus.yuzuku.Managers.ConsoleCommand.Commands;
/*    */ 
/*    */ import net.dv8tion.jda.core.JDA;
/*    */ import net.dv8tion.jda.core.Permission;
/*    */ import net.dv8tion.jda.core.entities.Guild;
/*    */ import net.dv8tion.jda.core.entities.Role;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.ConsoleDef;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;
/*    */ import net.sydrus.yuzuku.YuzukuBot;
/*    */ 
/*    */ public class ViewRolesPermissions extends net.sydrus.yuzuku.Constructors.ConsoleCommand
/*    */ {
/*    */   public boolean onCommand(ConsoleDef console, String command, String time, String[] args)
/*    */   {
/* 16 */     if (args.length == 2) {
/* 17 */       Guild guild = null;
/* 18 */       Role role = null;
/*    */       try {
/* 20 */         guild = YuzukuBot.getInstance().getJDA().getGuildById(args[0]);
/* 21 */         Type.messageType(Type.MessageType.Info, "**Server name:** " + guild.getName());
/*    */       } catch (Exception e) {
/* 23 */         Type.messageType(Type.MessageType.Error, "**Error on get server**");
/* 24 */         return true;
/*    */       }
/*    */       try {
/* 27 */         role = guild.getRoleById(args[1]);
/* 28 */         Type.messageType(Type.MessageType.Info, "**Role name:** " + role.getName());
/*    */       } catch (Exception e) {
/* 30 */         Type.messageType(Type.MessageType.Error, "**Error on get role**");
/*    */       }
/*    */       try {
/* 33 */         console.sendText("Permissions:");
/* 34 */         for (Permission rl : role.getPermissions()) {
/* 35 */           console.sendText("Permission name: " + rl.name());
/*    */         }
/* 37 */         console.sendText(" ");
/* 38 */         console.sendText("Role Mention: " + role.getAsMention());
/* 39 */         console.sendText("Role Id: " + role.getId());
/* 40 */         console.sendText("Role Name: " + role.getName());
/* 41 */         console.sendText("Role PermissionsRaw: " + role.getPermissionsRaw());
/* 42 */         console.sendText("Role Position: " + role.getPosition());
/* 43 */         console.sendText("Role Color: " + role.getColor());
/* 44 */         console.sendText("Role CreationTime: " + role.getCreationTime());
/* 45 */         console.sendText("Role Guild: " + role.getGuild());
/* 46 */         console.sendText("Role is Managed: " + role.isManaged());
/* 47 */         console.sendText("Role is Hoisted: " + role.isHoisted());
/* 48 */         console.sendText("Role is Mentionable :" + role.isMentionable());
/* 49 */         console.sendText("Role Permission: " + role.getPermissions().size());
/*    */       } catch (Exception e) {
/* 51 */         Type.messageType(Type.MessageType.Error, "Error on get role permissions");
/*    */       }
/*    */     } else {
/* 54 */       Type.messageType(Type.MessageType.Error, "Use: proles <serverId> <roleId>");
/*    */     }
/* 56 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\ConsoleCommand\Commands\ViewRolesPermissions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */