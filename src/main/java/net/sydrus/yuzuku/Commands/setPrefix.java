/*    */ package net.sydrus.yuzuku.Commands;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.List;
/*    */ import net.dv8tion.jda.core.entities.Guild;
/*    */ import net.dv8tion.jda.core.entities.Message;
/*    */ import net.dv8tion.jda.core.entities.TextChannel;
/*    */ import net.dv8tion.jda.core.entities.User;
/*    */ import net.dv8tion.jda.core.requests.RestAction;
/*    */ import net.sydrus.yuzuku.Constructors.Command;
/*    */ import net.sydrus.yuzuku.FileReader.ConfigReader;
/*    */ import net.sydrus.yuzuku.Managers.GuildManager;
/*    */ import net.sydrus.yuzuku.Managers.LevelType;
/*    */ import net.sydrus.yuzuku.YuzukuBot;
/*    */ 
/*    */ public class setPrefix extends Command
/*    */ {
/*    */   public boolean onCommand(User Sender, Message Message, Guild Guild, TextChannel Chat, List<LevelType> type, Boolean isEdited, String[] args)
/*    */   {
/* 20 */     String prefix = "!, $, %, &, *, (, ), _, -, =, +, /, ?, <, >, ;, :, \",\", \".\"";
/* 21 */     if ((args.length == 1) && (args[0].equalsIgnoreCase("reset"))) {
/*    */       try {
/* 23 */         ConfigReader reader = YuzukuBot.guildManager.getServerConfig(Guild);
/* 24 */         if (reader.contains("guildPrefix")) {
/* 25 */           reader.set("guildPrefix", null);
/* 26 */           reader.save();
/* 27 */           Chat.sendMessage(embedMessage("**Character has ben reseted**")).queue();
/*    */         } else {
/* 29 */           Chat.sendMessage(embedMessage("**No prefix setted**", Color.RED)).queue();
/*    */         }
/*    */       } catch (Exception e) {
/* 32 */         Chat.sendMessage(embedMessage("**Error on choose prefix!**", Color.RED)).queue();
/*    */       }
/*    */       
/* 35 */     } else if (args.length == 1) {
/* 36 */       if (args[0].length() == 1) {
/* 37 */         if (prefix.contains(args[0])) {
/* 38 */           Chat.sendMessage(embedMessage("**Character changed to:** (" + args[0] + ")")).queue();
/* 39 */           YuzukuBot.guildManager.setPrefix(Guild, args[0].charAt(0));
/*    */         } else {
/* 41 */           Chat.sendMessage(embedMessage("**Enter a prefix that contains in the list** \n " + prefix)).queue();
/*    */         }
/*    */       } else {
/* 44 */         Chat.sendMessage(embedMessage("**Enter an argument that contains only one character**")).queue();
/*    */       }
/*    */       
/*    */     }
/*    */     else {
/* 49 */       Chat.sendMessage(embedMessage("**Enter a prefix that contains only one character and is in the list** \n " + prefix + "\n **Or type setprefix reset**")).queue();
/*    */     }
/* 51 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Commands\setPrefix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */