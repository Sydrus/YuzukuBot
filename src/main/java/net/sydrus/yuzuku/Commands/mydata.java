/*    */ package net.sydrus.yuzuku.Commands;
/*    */ 
/*    */ import java.time.Month;
/*    */ import java.time.OffsetDateTime;
/*    */ import java.util.List;
/*    */ import net.dv8tion.jda.core.entities.Game;
/*    */ import net.dv8tion.jda.core.entities.Guild;
/*    */ import net.dv8tion.jda.core.entities.Member;
/*    */ import net.dv8tion.jda.core.entities.Message;
/*    */ import net.dv8tion.jda.core.entities.TextChannel;
/*    */ import net.dv8tion.jda.core.entities.User;
/*    */ import net.sydrus.yuzuku.Managers.EmbedManager;
/*    */ import net.sydrus.yuzuku.Managers.HelpManager;
/*    */ import net.sydrus.yuzuku.Managers.LevelType;
/*    */ 
/*    */ public class mydata extends net.sydrus.yuzuku.Constructors.Command
/*    */ {
/*    */   public boolean onCommand(User Sender, Message Message, Guild Guild, TextChannel Chat, List<LevelType> type, Boolean isEdited, String[] args)
/*    */   {
/* 20 */     boolean isVoice = Guild.getMember(Sender).getVoiceState().getChannel() != null;
/* 21 */     String Game = "";
/*    */     try {
/* 23 */       if (Guild.getMember(Sender).getGame().getName() == null) {
/* 24 */         Game = "None";
/*    */       } else {
/* 26 */         Game = Guild.getMember(Sender).getGame().getName();
/*    */       }
/*    */     } catch (Exception e) {
/* 29 */       Game = "None";
/*    */     }
/* 31 */     HelpManager manager = new HelpManager();
/* 32 */     manager.setTitle("**#Your Data:**");
/* 33 */     manager.addItem("**Your Id:** ", Sender.getId());
/* 34 */     manager.addItem("**Owner of the server:** ", type.contains(LevelType.ServerOwner) + "");
/* 35 */     manager.addItem("**Administrator:** ", type.contains(LevelType.Administrator) + "");
/* 36 */     manager.addItem("**Yuzuku bot Developer:** ", type.contains(LevelType.Developer) + "");
/* 37 */     manager.addItem("**Join Date**", getDateAndHour(Guild.getMember(Sender).getJoinDate()));
/* 38 */     manager.addItem("**Current game:**", Game);
/* 39 */     manager.addItem("**Online status:**", Guild.getMember(Sender).getOnlineStatus().toString());
/* 40 */     manager.addItem("**You are on a voice channel:**", isVoice + "");
/* 41 */     Chat.sendMessage(manager.getEmbedManager(net.sydrus.yuzuku.Managers.HelpManager.HelpType.UseCommentWhiteOutBrackets).getMessage()).queue();
/* 42 */     return true;
/*    */   }
/*    */   
/*    */   public String getDateAndHour(OffsetDateTime dtime) {
/* 46 */     return 
/* 47 */       dtime.getYear() + "/" + dtime.getMonth().getValue() + "/" + dtime.getDayOfMonth() + " " + dtime.getHour() + ":" + dtime.getMinute() + ":" + dtime.getSecond();
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Commands\mydata.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */