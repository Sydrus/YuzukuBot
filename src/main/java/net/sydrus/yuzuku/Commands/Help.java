/*    */ package net.sydrus.yuzuku.Commands;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.dv8tion.jda.core.entities.Guild;
/*    */ import net.dv8tion.jda.core.entities.Message;
/*    */ import net.dv8tion.jda.core.entities.TextChannel;
/*    */ import net.dv8tion.jda.core.entities.User;
/*    */ import net.sydrus.yuzuku.Constructors.Command;
/*    */ import net.sydrus.yuzuku.Managers.CommandType;
/*    */ import net.sydrus.yuzuku.Managers.Commands;
/*    */ import net.sydrus.yuzuku.Managers.EmbedManager;
/*    */ import net.sydrus.yuzuku.Managers.GuildManager;
/*    */ import net.sydrus.yuzuku.Managers.HelpManager;
/*    */ import net.sydrus.yuzuku.Managers.LevelType;
/*    */ import net.sydrus.yuzuku.PLugin.CommandInfo;
/*    */ import net.sydrus.yuzuku.YuzukuBot;
/*    */ 
/*    */ public class Help extends Command
/*    */ {
/*    */   public boolean onCommand(User Sender, Message Message, Guild Guild, TextChannel Chat, List<LevelType> type, Boolean isEdited, String[] args)
/*    */   {
/* 22 */     HelpManager adml = new HelpManager();
/* 23 */     adml.setPrefix("{s}", YuzukuBot.guildManager.getPrefix(Guild));
/* 24 */     List<CommandInfo> info = YuzukuBot.getInstance()._CommandManager.getCommands().getCommands(CommandType.Bot);
/*    */     
/* 26 */     for (CommandInfo ifo : info) {
/* 27 */       String command = ifo.command;
/* 28 */       String comment = ifo.comment;
/* 29 */       String level = "";
/* 30 */       if (ifo.level == LevelType.Administrator) {
/* 31 */         level = " (Administrator Only)";
/* 32 */       } else if (ifo.level == LevelType.Developer) {
/* 33 */         level = " (Developer Only)";
/* 34 */       } else if (ifo.level == LevelType.ServerOwner) {
/* 35 */         level = " (Owner Only)";
/*    */       }
/* 37 */       adml.addItem("   {s}" + command, comment + level);
/*    */     }
/*    */     
/* 40 */     Chat.sendMessage(adml.getEmbedManager(net.sydrus.yuzuku.Managers.HelpManager.HelpType.UseComment).getMessage()).queue();
/*    */     
/* 42 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Commands\Help.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */