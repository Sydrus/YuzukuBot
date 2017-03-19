/*    */ package net.sydrus.yuzuku.Constructors;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.List;
/*    */ import net.dv8tion.jda.core.JDA;
/*    */ import net.dv8tion.jda.core.entities.Guild;
/*    */ import net.dv8tion.jda.core.entities.Message;
/*    */ import net.dv8tion.jda.core.entities.TextChannel;
/*    */ import net.dv8tion.jda.core.entities.User;
/*    */ import net.sydrus.yuzuku.Managers.EmbedManager;
/*    */ import net.sydrus.yuzuku.Managers.LevelType;
/*    */ import net.sydrus.yuzuku.YuzukuBot;
/*    */ 
/*    */ 
/*    */ public abstract class Command
/*    */ {
/*    */   public abstract boolean onCommand(User paramUser, Message paramMessage, Guild paramGuild, TextChannel paramTextChannel, List<LevelType> paramList, Boolean paramBoolean, String[] paramArrayOfString);
/*    */   
/*    */   public JDA getAPI()
/*    */   {
/* 21 */     return YuzukuBot.getInstance().getJDA();
/*    */   }
/*    */   
/*    */   public boolean isAdministrator(List<LevelType> type) {
/* 25 */     if (type.contains(LevelType.Administrator)) {
/* 26 */       return true;
/*    */     }
/* 28 */     if (type.contains(LevelType.Developer)) {
/* 29 */       return true;
/*    */     }
/* 31 */     return false;
/*    */   }
/*    */   
/*    */   public String sender(User user) {
/* 35 */     return "**[<@" + user.getId() + ">]** ";
/*    */   }
/*    */   
/*    */   public String usage(String msg) {
/* 39 */     return "**Usage**: " + msg + ".\n";
/*    */   }
/*    */   
/*    */   public String description(String msg) {
/* 43 */     return "**Description**: " + msg + ".\n";
/*    */   }
/*    */   
/*    */   public Message embedMessage(String text) {
/* 47 */     return embedMessage(text, YuzukuBot.defaultMessageColor);
/*    */   }
/*    */   
/*    */   public Message embedMessage(String text, Color color) {
/* 51 */     EmbedManager emb = new EmbedManager();
/* 52 */     emb.setDescription(text);
/* 53 */     emb.setColor(color);
/* 54 */     return emb.getMessage();
/*    */   }
/*    */   
/*    */   public String argsToString(String[] args, Integer index) {
/* 58 */     String myString = "";
/* 59 */     for (int i = index.intValue(); i < args.length; i++) {
/* 60 */       String arg = args[i] + " ";
/* 61 */       myString = myString + arg;
/*    */     }
/* 63 */     myString = myString.substring(0, myString.length() - 1);
/* 64 */     return myString;
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Constructors\Command.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */