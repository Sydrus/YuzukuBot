/*     */ package net.sydrus.yuzuku.Listeners;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.dv8tion.jda.core.JDA;
/*     */ import net.dv8tion.jda.core.entities.Guild;
/*     */ import net.dv8tion.jda.core.entities.Message;
/*     */ import net.dv8tion.jda.core.entities.MessageChannel;
/*     */ import net.dv8tion.jda.core.entities.SelfUser;
/*     */ import net.dv8tion.jda.core.entities.User;
/*     */ import net.dv8tion.jda.core.entities.impl.MessageImpl;
/*     */ import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
/*     */ import net.dv8tion.jda.core.events.message.guild.GuildMessageUpdateEvent;
/*     */ import net.dv8tion.jda.core.requests.RestAction;
/*     */ import net.sydrus.yuzuku.Managers.Commands;
/*     */ import net.sydrus.yuzuku.Managers.GuildManager;
/*     */ import net.sydrus.yuzuku.Managers.LevelType;
/*     */ import net.sydrus.yuzuku.YuzukuBot;
/*     */ 
/*     */ public class DiscordListener extends net.dv8tion.jda.core.hooks.ListenerAdapter
/*     */ {
/*     */   public void onMessageReceived(MessageReceivedEvent e)
/*     */   {
/*  26 */     String cmdStr = "";
/*  27 */     if (e.getChannelType() != net.dv8tion.jda.core.entities.ChannelType.PRIVATE)
/*     */     {
/*  29 */       if (e.getAuthor().isBot()) {
/*  30 */         return;
/*     */       }
/*     */       try {
/*  33 */         if (isPrefix(e.getGuild(), e.getMessage(), YuzukuBot.getInstance().getJDA())) {
/*  34 */           cmdStr = getCommand(e.getGuild(), e.getMessage(), YuzukuBot.getInstance().getJDA());
/*     */           
/*  36 */           Commands cmd = YuzukuBot.getInstance().getCommandManager().getCommands();
/*  37 */           if ((cmd.contains(cmdStr.split(" ")[0].toLowerCase())) && 
/*  38 */             (cmdStr.startsWith(cmdStr))) {
/*  39 */             if (hasLevel(e).contains(cmd.getCommandInfo(cmdStr.split(" ")[0]).level)) {
/*  40 */               cmd.get(cmdStr.split(" ")[0].toLowerCase()).onCommand(e.getAuthor(), 
/*  41 */                 setMessage(e.getMessage()), e.getGuild(), e.getTextChannel(), hasLevel(e), 
/*  42 */                 Boolean.valueOf(e.getMessage().isEdited()), argsToString(cmdStr, Integer.valueOf(1)).split(" "));
/*     */             } else {
/*  44 */               e.getChannel().sendMessage(net.sydrus.yuzuku.String.Administratives.badPermission).queue();
/*     */             }
/*     */           }
/*     */         }
/*     */         else {
/*     */           try {
/*  50 */             if ((YuzukuBot.getInstance().isTextBanned(e.getMessage().getContent())) && 
/*  51 */               (!e.getAuthor().isBot())) {
/*  52 */               e.getMessage().deleteMessage().queue();
/*  53 */               e.getChannel()
/*  54 */                 .sendMessage(sender(e.getAuthor()) + "```css\n\nOps, you used a banned link/word\n\n```")
/*     */                 
/*  56 */                 .queue();
/*     */             }
/*     */           } catch (IOException e1) {
/*  59 */             System.err.println(e1.getMessage());
/*     */           }
/*     */         }
/*     */       } catch (Exception e2) {
/*  63 */         YuzukuBot.getInstance().botErrors += 1;
/*  64 */         e2.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void onGuildMessageUpdate(GuildMessageUpdateEvent e)
/*     */   {
/*     */     try {
/*  72 */       if ((YuzukuBot.getInstance().isTextBanned(e.getMessage().getContent())) && (!e.getAuthor().isBot())) {
/*  73 */         e.getMessage().deleteMessage();
/*  74 */         e.getChannel().sendMessage(sender(e.getAuthor()) + "```css\n\nOps, you used a banned link/word\n\n```");
/*     */       }
/*     */     }
/*     */     catch (Exception e1) {
/*  78 */       System.err.println(e1.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */   private String getCommand(Guild guild, Message message, JDA jda) {
/*  83 */     if (YuzukuBot.guildManager.getPrefix(guild).equals(jda.getSelfUser().getAsMention() + " ")) {
/*  84 */       if ((message.getContent().length() == jda.getSelfUser().getName().length()) || 
/*  85 */         (message.getContent().length() == jda.getSelfUser().getName().length() + 1)) {
/*  86 */         return message.getContent().substring(jda.getSelfUser().getName().length() + 1) + "help";
/*     */       }
/*  88 */       return message.getContent().substring(jda.getSelfUser().getName().length() + 2);
/*     */     }
/*     */     try
/*     */     {
/*  92 */       if (((User)message.getMentionedUsers().get(0)).getAsMention().equals(jda.getSelfUser().getAsMention()))
/*     */       {
/*  94 */         if (message.getContent().split(" ")[0].equals("@" + jda.getSelfUser().getName().split(" ")[0]))
/*     */         {
/*     */ 
/*  97 */           if (message.getContent().substring(jda.getSelfUser().getName().length(), message.getContent().length()).split(" ").length > 0) {
/*  98 */             String itemFinal = message.getContent().substring(jda.getSelfUser().getName().length() + 1);
/*  99 */             if ((itemFinal.isEmpty()) || (itemFinal.equals(" "))) {
/* 100 */               return "help";
/*     */             }
/* 102 */             if (itemFinal.startsWith(" ")) {
/* 103 */               return itemFinal.substring(1);
/*     */             }
/* 105 */             return itemFinal;
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 110 */           return "help";
/*     */         }
/*     */       }
/* 113 */       return message.getContent().substring(1, message.getContent().length());
/*     */     }
/*     */     catch (Exception e) {}
/* 116 */     return message.getContent().substring(1, message.getContent().length());
/*     */   }
/*     */   
/*     */ 
/*     */   public String sender(User user)
/*     */   {
/* 122 */     return "**[<@" + user.getId() + ">]** ";
/*     */   }
/*     */   
/*     */   private List<LevelType> hasLevel(MessageReceivedEvent e) {
/* 126 */     List<LevelType> type = new ArrayList();
/* 127 */     type.add(LevelType.User);
/* 128 */     if (e.getGuild().getOwner().getUser().getId() == e.getAuthor().getId()) {
/* 129 */       type.add(LevelType.ServerOwner);
/*     */     }
/*     */     
/* 132 */     if (YuzukuBot.getInstance().settingsData.getList(net.sydrus.yuzuku.FileReader.BCRItem.getItem(net.sydrus.yuzuku.FileReader.BCRItem.Type.Administrators)).contains(e.getAuthor().getId())) {
/* 133 */       type.add(LevelType.Administrator);
/*     */     }
/* 135 */     if (YuzukuBot.getInstance().isDeveloper(e.getAuthor().getId()).booleanValue()) {
/* 136 */       type.add(LevelType.Developer);
/* 137 */       if (!type.contains(LevelType.Administrator)) {
/* 138 */         type.add(LevelType.Administrator);
/*     */       }
/*     */     }
/* 141 */     return type;
/*     */   }
/*     */   
/*     */   private String argsToString(String args, Integer index) {
/* 145 */     String myString = "";
/* 146 */     String[] argh = args.split(" ");
/* 147 */     for (int i = index.intValue(); i < argh.length; i++) {
/* 148 */       String arg = argh[i] + " ";
/* 149 */       myString = myString + arg;
/*     */     }
/* 151 */     myString = myString.substring(0, myString.length());
/* 152 */     return myString;
/*     */   }
/*     */   
/*     */   private Message setMessage(Message message) {
/* 156 */     List<User> user = new ArrayList();
/* 157 */     int quanty = 0;
/* 158 */     for (User usr : message.getMentionedUsers()) {
/* 159 */       if ((usr.getAsMention().equals(YuzukuBot.getInstance().getJDA().getSelfUser().getAsMention())) && (quanty >= 0))
/*     */       {
/* 161 */         user.add(usr);
/* 162 */       } else if (!usr.getAsMention().equals(YuzukuBot.getInstance().getJDA().getSelfUser().getAsMention())) {
/* 163 */         user.add(usr);
/*     */       }
/*     */     }
/* 166 */     return 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 172 */       new MessageImpl(message.getId(), message.getTextChannel(), message.isWebhookMessage()).setContent(getCommand(message.getGuild(), message, YuzukuBot.getInstance().getJDA())).setTTS(message.isTTS()).setAuthor(message.getAuthor()).setMentionedChannels(message.getMentionedChannels()).setMentionedRoles(message.getMentionedRoles()).setMentionedUsers(user).setMentionsEveryone(message.mentionsEveryone()).setEditedTime(message.getEditedTime()).setPinned(message.isPinned()).setReactions(message.getReactions()).setTime(message.getCreationTime());
/*     */   }
/*     */   
/*     */   private boolean isPrefix(Guild guild, Message message, JDA jda) {
/*     */     try {
/* 177 */       if (YuzukuBot.guildManager.getPrefix(guild).equals(jda.getSelfUser().getAsMention() + " ")) {
/*     */         try {
/* 179 */           if (((User)message.getMentionedUsers().get(0)).getId().equals(jda.getSelfUser().getId())) {
/* 180 */             return true;
/*     */           }
/*     */         } catch (Exception e) {
/* 183 */           return false;
/*     */         }
/*     */       }
/*     */       try {
/* 187 */         if (YuzukuBot.guildManager.getPrefix(guild).equals(message.getContent().substring(0, 1))) {
/* 188 */           return true;
/*     */         }
/*     */         try
/*     */         {
/* 192 */           if (((User)message.getMentionedUsers().get(0)).getAsMention().equals(jda.getSelfUser().getAsMention())) {
/* 193 */             return true;
/*     */           }
/*     */         }
/*     */         catch (Exception localException1) {}
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */         try {
/* 201 */           if (((User)message.getMentionedUsers().get(0)).getAsMention().equals(jda.getSelfUser().getAsMention())) {
/* 202 */             return true;
/*     */           }
/*     */         }
/*     */         catch (Exception localException2) {}
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 210 */       return false;
/*     */     }
/* 212 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Listeners\DiscordListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */