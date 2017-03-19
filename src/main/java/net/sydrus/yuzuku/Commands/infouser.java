/*    */ package net.sydrus.yuzuku.Commands;
/*    */ 
/*    */ import java.time.Month;
/*    */ import java.time.OffsetDateTime;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.dv8tion.jda.core.entities.Guild;
/*    */ import net.dv8tion.jda.core.entities.Member;
/*    */ import net.dv8tion.jda.core.entities.Message;
/*    */ import net.dv8tion.jda.core.entities.PrivateChannel;
/*    */ import net.dv8tion.jda.core.entities.TextChannel;
/*    */ import net.dv8tion.jda.core.entities.User;
/*    */ import net.dv8tion.jda.core.requests.RestAction;
/*    */ import net.sydrus.yuzuku.FileReader.ConfigReader;
/*    */ import net.sydrus.yuzuku.Managers.LevelType;
/*    */ import net.sydrus.yuzuku.YuzukuBot;
/*    */ 
/*    */ public class infouser extends net.sydrus.yuzuku.Constructors.Command
/*    */ {
/*    */   public boolean onCommand(User Sender, Message Message, Guild Guild, TextChannel Chat, List<LevelType> type, Boolean isEdited, String[] args)
/*    */   {
/* 22 */     if (args.length >= 1) {
/*    */       try {
/* 24 */         List<String> data = new ArrayList();
/*    */         
/* 26 */         User user = (User)Message.getMentionedUsers().get(0);
/* 27 */         OffsetDateTime dtime = Guild.getMember(user).getJoinDate();
/*    */         
/* 29 */         data.add("Name: " + user.getName());
/* 30 */         data.add("Id: " + user.getId());
/* 31 */         data.add("Avatar Id: " + user.getAvatarId());
/* 32 */         data.add("Avatar Url: " + user.getAvatarUrl());
/* 33 */         data.add("Default Avatar Id: " + user.getDefaultAvatarId());
/* 34 */         data.add("Default Avatar Url: " + user.getDefaultAvatarUrl());
/* 35 */         data.add("Discriminator: " + user.getDiscriminator());
/* 36 */         data.add("Creation Time: " + getDateAndHour(user.getCreationTime()));
/* 37 */         data.add("Join Date: " + getDateAndHour(dtime));
/* 38 */         data.add("Has Private Channel: " + user.hasPrivateChannel());
/* 39 */         if (user.hasPrivateChannel()) {
/* 40 */           data.add("Private Channel id: " + user.getPrivateChannel().getId());
/*    */         }
/* 42 */         data.add("Is Bot: " + user.isBot());
/* 43 */         data.add("Is Fake: " + user.isFake());
/* 44 */         data.add("As Mention: " + user.getAsMention());
/* 45 */         data.add("Owner of the server: " + isOwner(user, Guild));
/* 46 */         data.add("Administrator: " + isAdm(user));
/* 47 */         data.add("Yuzuku Bot Developer: " + YuzukuBot.getInstance().isDeveloper(user.getId()));
/* 48 */         String endItems = "";
/* 49 */         for (String itemslist : data) {
/* 50 */           endItems = endItems + itemslist + "\n";
/*    */         }
/*    */         
/* 53 */         Chat.sendMessage(Format("You data:\n\n" + endItems)).queue();
/*    */       } catch (Exception e) {
/* 55 */         Chat.sendMessage(Format("**Invalid user**")).queue();
/*    */       }
/*    */     } else {
/* 58 */       Chat.sendMessage(Format("Use: /uinfo <userMention>")).queue();
/*    */     }
/*    */     
/* 61 */     return true;
/*    */   }
/*    */   
/*    */   public String getDateAndHour(OffsetDateTime dtime) {
/* 65 */     return 
/* 66 */       dtime.getYear() + "/" + dtime.getMonth().getValue() + "/" + dtime.getDayOfMonth() + " " + dtime.getHour() + ":" + dtime.getMinute() + ":" + dtime.getSecond();
/*    */   }
/*    */   
/*    */   private boolean isOwner(User user, Guild guild) {
/* 70 */     if (user.getId().equals(guild.getOwner().getUser().getId())) {
/* 71 */       return true;
/*    */     }
/* 73 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */   private boolean isAdm(User user)
/*    */   {
/* 79 */     if (YuzukuBot.getInstance().settingsData.getList(net.sydrus.yuzuku.FileReader.BCRItem.getItem(net.sydrus.yuzuku.FileReader.BCRItem.Type.Administrators)).contains(user.getId())) {}
/*    */     
/*    */ 
/* 82 */     return false;
/*    */   }
/*    */   
/*    */   private String Format(String text) {
/* 86 */     return "```css\n\n" + text + "\n\n```";
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Commands\infouser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */