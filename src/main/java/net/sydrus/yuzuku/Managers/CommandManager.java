/*    */ package net.sydrus.yuzuku.Managers;
/*    */ 
/*    */ import net.sydrus.yuzuku.Commands.music;
/*    */ import net.sydrus.yuzuku.PLugin.CommandInfo;
/*    */ import net.sydrus.yuzuku.YuzukuBot;
/*    */ 
/*    */ public class CommandManager
/*    */ {
/*  9 */   private Commands cmd = new Commands();
/*    */   
/*    */   public CommandManager()
/*    */   {
/* 13 */     this.cmd.register(new CommandInfo("adm", " - **View the administrative commands**", LevelType.Administrator), new net.sydrus.yuzuku.Commands.AdmCommand());
/*    */     
/*    */ 
/*    */ 
/* 17 */     this.cmd.register(new CommandInfo("stop", " - **Disconnect bot**", LevelType.Developer), new net.sydrus.yuzuku.Commands.StopCommand());
/* 18 */     this.cmd.register(new CommandInfo("help", " - **See bot commands**", LevelType.User), new net.sydrus.yuzuku.Commands.Help());
/* 19 */     this.cmd.register(new CommandInfo("clear", " - **Clear chat**", LevelType.Administrator), new net.sydrus.yuzuku.Commands.ClearChat());
/* 20 */     this.cmd.register(new CommandInfo("setgame", " - **Set the bot game**", LevelType.Developer), new net.sydrus.yuzuku.Commands.setgame());
/* 21 */     this.cmd.register(new CommandInfo("status", " - **Show the bot status**", LevelType.User), new net.sydrus.yuzuku.Commands.status());
/*    */     
/* 23 */     this.cmd.register(new CommandInfo("myin", " - **Show your user's data**", LevelType.User), new net.sydrus.yuzuku.Commands.mydata());
/* 24 */     this.cmd.register(new CommandInfo("uinfo", " - **Show a user's information**", LevelType.Administrator), new net.sydrus.yuzuku.Commands.infouser());
/*    */     
/* 26 */     this.cmd.register(new CommandInfo("plhelp", " - **Show plugin core commands**", LevelType.User), new net.sydrus.yuzuku.Commands.HelpPlugins());
/* 27 */     this.cmd.register(new CommandInfo("music", " - **Play music**", LevelType.User), new music());
/* 28 */     this.cmd.register(new CommandInfo("setprefix", " - **Set the prefix to use the bot**", LevelType.ServerOwner), new net.sydrus.yuzuku.Commands.setPrefix());
/*    */     
/* 30 */     this.cmd.register(new CommandInfo("add", " - **Get the link to add to " + YuzukuBot.botName + " user discord guild**", LevelType.User), new net.sydrus.yuzuku.Commands.add());
/*    */     
/* 32 */     this.cmd.register(new CommandInfo("oserver", " - **Enter in the Yuzuku Bot official server**", LevelType.User), new net.sydrus.yuzuku.Commands.OficialServer());
/*    */   }
/*    */   
/*    */   public Commands getCommands() {
/* 36 */     return this.cmd;
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\CommandManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */