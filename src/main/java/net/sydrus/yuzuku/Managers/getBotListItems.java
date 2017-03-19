/*    */ package net.sydrus.yuzuku.Managers;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.dv8tion.jda.core.JDA;
/*    */ import net.dv8tion.jda.core.entities.Guild;
/*    */ import net.dv8tion.jda.core.entities.Member;
/*    */ import net.dv8tion.jda.core.entities.TextChannel;
/*    */ import net.dv8tion.jda.core.entities.User;
/*    */ import net.dv8tion.jda.core.entities.VoiceChannel;
/*    */ 
/*    */ public class getBotListItems
/*    */ {
/*    */   public static List<User> getMembers(Guild guild, boolean ignoreCase, String name)
/*    */   {
/* 16 */     List<User> toReturn = new ArrayList();
/* 17 */     for (Member mbr : guild.getMembers()) {
/* 18 */       if (ignoreCase) {
/* 19 */         if (mbr.getUser().getName().toLowerCase().contains(name.toLowerCase())) {
/* 20 */           toReturn.add(mbr.getUser());
/*    */         }
/*    */       }
/* 23 */       else if (mbr.getUser().getName().contains(name)) {
/* 24 */         toReturn.add(mbr.getUser());
/*    */       }
/*    */     }
/*    */     
/* 28 */     return toReturn;
/*    */   }
/*    */   
/*    */   public static List<VoiceChannel> getVoiceChannels(Guild guild, boolean ignoreCase, String name) {
/* 32 */     List<VoiceChannel> toReturn = new ArrayList();
/* 33 */     for (VoiceChannel txt : guild.getVoiceChannels()) {
/* 34 */       if (ignoreCase) {
/* 35 */         if (txt.getName().toLowerCase().contains(name.toLowerCase())) {
/* 36 */           toReturn.add(txt);
/*    */         }
/*    */       }
/* 39 */       else if (txt.getName().contains(name)) {
/* 40 */         toReturn.add(txt);
/*    */       }
/*    */     }
/*    */     
/* 44 */     return toReturn;
/*    */   }
/*    */   
/*    */   public static List<TextChannel> getTextChannels(Guild guild, boolean ignoreCase, String name) {
/* 48 */     List<TextChannel> toReturn = new ArrayList();
/* 49 */     for (TextChannel txt : guild.getTextChannels()) {
/* 50 */       if (ignoreCase) {
/* 51 */         if (txt.getName().toLowerCase().contains(name.toLowerCase())) {
/* 52 */           toReturn.add(txt);
/*    */         }
/*    */       }
/* 55 */       else if (txt.getName().contains(name)) {
/* 56 */         toReturn.add(txt);
/*    */       }
/*    */     }
/*    */     
/* 60 */     return toReturn;
/*    */   }
/*    */   
/*    */   public static List<Guild> getGuilds(JDA jda, boolean ignoreCase, String name) {
/* 64 */     List<Guild> toReturn = new ArrayList();
/* 65 */     for (Guild gld : jda.getGuilds()) {
/* 66 */       if (ignoreCase) {
/* 67 */         if (gld.getName().toLowerCase().contains(name.toLowerCase())) {
/* 68 */           toReturn.add(gld);
/*    */         }
/*    */       }
/* 71 */       else if (gld.getName().contains(name)) {
/* 72 */         toReturn.add(gld);
/*    */       }
/*    */     }
/*    */     
/* 76 */     return toReturn;
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\getBotListItems.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */