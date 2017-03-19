/*    */ package net.sydrus.yuzuku.Managers.ConsoleCommand;
/*    */ 
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Commands.ViewRolesPermissions;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Commands.ViewServerRoles;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Commands.addev;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Commands.ban;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Commands.disableConsole;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Commands.info;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Commands.message;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Commands.stop;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Commands.unban;
/*    */ 
/*    */ public class RegisterCommand
/*    */ {
/* 15 */   private ConsoleCmdManager command = new ConsoleCmdManager();
/*    */   
/*    */   public RegisterCommand()
/*    */   {
/* 19 */     this.command.register("stop", new stop());
/* 20 */     this.command.register("disableconsole", new disableConsole());
/* 21 */     this.command.register("list", new net.sydrus.yuzuku.Managers.ConsoleCommand.Commands.list());
/* 22 */     this.command.register("addeveloper", new addev());
/* 23 */     this.command.register("info", new info());
/* 24 */     this.command.register("message", new message());
/* 25 */     this.command.register("serverrole", new ViewServerRoles());
/* 26 */     this.command.register("proles", new ViewRolesPermissions());
/* 27 */     this.command.register("ban", new ban());
/* 28 */     this.command.register("unban", new unban());
/*    */   }
/*    */   
/*    */   public ConsoleCmdManager getCmdManager() {
/* 32 */     return this.command;
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\ConsoleCommand\RegisterCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */