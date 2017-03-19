/*     */ package net.sydrus.yuzuku.Managers.ConsoleCommand.Commands;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.time.OffsetDateTime;
/*     */ import java.util.List;
/*     */ import net.dv8tion.jda.core.JDA;
/*     */ import net.dv8tion.jda.core.entities.Guild;
/*     */ import net.dv8tion.jda.core.entities.Member;
/*     */ import net.dv8tion.jda.core.entities.TextChannel;
/*     */ import net.dv8tion.jda.core.entities.User;
/*     */ import net.dv8tion.jda.core.entities.VoiceChannel;
/*     */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
/*     */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;
/*     */ import net.sydrus.yuzuku.YuzukuBot;
/*     */ 
/*     */ public class info extends net.sydrus.yuzuku.Constructors.ConsoleCommand
/*     */ {
/*     */   public boolean onCommand(net.sydrus.yuzuku.Managers.ConsoleCommand.ConsoleDef console, String command, String time, String[] args)
/*     */   {
/*  20 */     System.out.println(args[0]);
/*  21 */     System.out.println(args[1]);
/*  22 */     if ((args.length == 2) && (args[0].equalsIgnoreCase("server"))) {
/*     */       try {
/*  24 */         Guild guild = YuzukuBot.getInstance().getJDA().getGuildById(args[1]);
/*     */         
/*  26 */         System.out.println("");
/*  27 */         Type.messageType(Type.MessageType.Info, "**Name:** " + guild.getName());
/*  28 */         Type.messageType(Type.MessageType.Info, "**Id:** " + guild.getId());
/*  29 */         Type.messageType(Type.MessageType.Info, "**Creation Time:** " + guild.getCreationTime().toString());
/*  30 */         Type.messageType(Type.MessageType.Info, "**Icon Id:** " + guild.getIconId());
/*  31 */         Type.messageType(Type.MessageType.Info, "**Icon Url:** " + guild.getIconUrl());
/*  32 */         Type.messageType(Type.MessageType.Info, "**Splash Id:** " + guild.getSplashId());
/*  33 */         Type.messageType(Type.MessageType.Info, "**Splash Url:** " + guild.getSplashUrl());
/*  34 */         Type.messageType(Type.MessageType.Info, "**Check Verification:** " + guild.checkVerification());
/*  35 */         Type.messageType(Type.MessageType.Info, "**Afk Channel Name:** " + guild.getAfkChannel().getName());
/*  36 */         Type.messageType(Type.MessageType.Info, "**Afk Channel Id:** " + guild.getAfkChannel().getId());
/*  37 */         Type.messageType(Type.MessageType.Info, "**Afk\tTimeout:** " + guild.getAfkTimeout().getSeconds());
/*  38 */         Type.messageType(Type.MessageType.Info, "**Default Notification Level:** " + guild
/*  39 */           .getDefaultNotificationLevel().toString());
/*  40 */         Type.messageType(Type.MessageType.Info, "**Emotes:** " + guild.getEmotes().size());
/*  41 */         Type.messageType(Type.MessageType.Info, "**Members:** " + guild.getMembers().size());
/*  42 */         Type.messageType(Type.MessageType.Info, "**Owner Name:** " + guild.getOwner().getUser().getName());
/*  43 */         Type.messageType(Type.MessageType.Info, "**Owner Id:** " + guild.getOwner().getUser().getId());
/*  44 */         Type.messageType(Type.MessageType.Info, "**Public Channel Name:** " + guild.getPublicChannel().getName());
/*  45 */         Type.messageType(Type.MessageType.Info, "**Public Channel Id:** " + guild.getPublicChannel().getId());
/*  46 */         Type.messageType(Type.MessageType.Info, "**Region Name:** " + guild.getRegion().name());
/*  47 */         Type.messageType(Type.MessageType.Info, "**Roles:** " + guild.getRoles().size());
/*  48 */         Type.messageType(Type.MessageType.Info, "**Text Channel:** " + guild.getTextChannels().size());
/*  49 */         Type.messageType(Type.MessageType.Info, "**Voice Channels:** " + guild.getVoiceChannels().size());
/*  50 */         Type.messageType(Type.MessageType.Info, "**Voice States:** " + guild.getVoiceStates().size());
/*  51 */         Type.messageType(Type.MessageType.Info, "**Is Available:** " + guild.isAvailable());
/*     */       }
/*     */       catch (Exception e) {
/*  54 */         Type.messageType(Type.MessageType.Error, "**This server not exist**");
/*     */       }
/*  56 */     } else if ((args.length == 3) && (args[0].equalsIgnoreCase("tchannel"))) {
/*     */       try
/*     */       {
/*  59 */         TextChannel textChannel = YuzukuBot.getInstance().getJDA().getGuildById(args[1]).getTextChannelById(args[2]);
/*  60 */         System.out.println("");
/*  61 */         Type.messageType(Type.MessageType.Info, "**Name:** " + textChannel.getName());
/*  62 */         Type.messageType(Type.MessageType.Info, "**Id:** " + textChannel.getId());
/*  63 */         Type.messageType(Type.MessageType.Info, "**Position:** " + textChannel.getPosition());
/*  64 */         Type.messageType(Type.MessageType.Info, "**Position Raw:** " + textChannel.getPositionRaw());
/*  65 */         Type.messageType(Type.MessageType.Info, "**Topic:** " + textChannel.getTopic());
/*  66 */         Type.messageType(Type.MessageType.Info, "**Creation Time:** " + getDateAndHour(textChannel.getCreationTime()));
/*  67 */         Type.messageType(Type.MessageType.Info, "**Cached History:** " + textChannel
/*  68 */           .getHistory().getCachedHistory().size());
/*  69 */         Type.messageType(Type.MessageType.Info, "**Members:** " + textChannel.getMembers().size());
/*  70 */         Type.messageType(Type.MessageType.Info, "**Guild:** " + textChannel.getGuild().getName());
/*  71 */         Type.messageType(Type.MessageType.Info, "**Permission Overrides:** " + textChannel
/*  72 */           .getPermissionOverrides().size());
/*  73 */         Type.messageType(Type.MessageType.Info, "**Pinned Messages:** " + 
/*  74 */           ((List)textChannel.getPinnedMessages().block()).size());
/*  75 */         Type.messageType(Type.MessageType.Info, "**Role Permission Overrides:** " + textChannel
/*  76 */           .getRolePermissionOverrides().size());
/*     */       }
/*     */       catch (Exception e) {
/*  79 */         Type.messageType(Type.MessageType.Error, "**This Text Channel not exist**");
/*     */       }
/*  81 */     } else if ((args.length == 3) && (args[0].equalsIgnoreCase("vchannel"))) {
/*     */       try
/*     */       {
/*  84 */         VoiceChannel voiceChannel = YuzukuBot.getInstance().getJDA().getGuildById(args[1]).getVoiceChannelById(args[2]);
/*  85 */         System.out.println("");
/*  86 */         Type.messageType(Type.MessageType.Info, "**Name:** " + voiceChannel.getName());
/*  87 */         Type.messageType(Type.MessageType.Info, "**Id:** " + voiceChannel.getId());
/*  88 */         Type.messageType(Type.MessageType.Info, "**Position:** " + voiceChannel.getPosition());
/*  89 */         Type.messageType(Type.MessageType.Info, "**Position Raw:** " + voiceChannel.getPositionRaw());
/*  90 */         Type.messageType(Type.MessageType.Info, "**User Limit:** " + voiceChannel.getUserLimit());
/*  91 */         Type.messageType(Type.MessageType.Info, "**Creation Time:** " + getDateAndHour(voiceChannel.getCreationTime()));
/*  92 */         Type.messageType(Type.MessageType.Info, "**Bitrate:** " + voiceChannel.getBitrate());
/*  93 */         Type.messageType(Type.MessageType.Info, "**Members:** " + voiceChannel.getMembers().size());
/*  94 */         Type.messageType(Type.MessageType.Info, "**Guild:** " + voiceChannel.getGuild().getName());
/*  95 */         Type.messageType(Type.MessageType.Info, "**Permission Overrides:** " + voiceChannel
/*  96 */           .getPermissionOverrides().size());
/*  97 */         Type.messageType(Type.MessageType.Info, "**Role Permission Overrides:** " + voiceChannel
/*  98 */           .getRolePermissionOverrides().size());
/*     */       }
/*     */       catch (Exception e) {
/* 101 */         Type.messageType(Type.MessageType.Error, "**This Voice Channel not exist**");
/*     */       }
/* 103 */     } else if ((args.length == 3) && (args[0].equalsIgnoreCase("users"))) {
/*     */       try {
/* 105 */         Guild gd = YuzukuBot.getInstance().getJDA().getGuildById(args[1]);
/* 106 */         User user = gd.getMemberById(args[2]).getUser();
/* 107 */         Type.messageType(Type.MessageType.Info, "**Name:** " + user.getName());
/* 108 */         Type.messageType(Type.MessageType.Info, "**Id:** " + user.getId());
/* 109 */         Type.messageType(Type.MessageType.Info, "**Avatar Id:** " + user.getAvatarId());
/* 110 */         Type.messageType(Type.MessageType.Info, "**Avatar Url:** " + user.getAvatarUrl());
/* 111 */         Type.messageType(Type.MessageType.Info, "**Default Avatar Id:** " + user.getDefaultAvatarId());
/* 112 */         Type.messageType(Type.MessageType.Info, "**Default Avatar Url:** " + user.getDefaultAvatarUrl());
/* 113 */         Type.messageType(Type.MessageType.Info, "**Discriminator:** " + user.getDiscriminator());
/* 114 */         Type.messageType(Type.MessageType.Info, "**Join Date:** " + getDateAndHour(gd.getMember(user).getJoinDate()));
/* 115 */         Type.messageType(Type.MessageType.Info, "**Creation Time:** " + getDateAndHour(user.getCreationTime()));
/* 116 */         Type.messageType(Type.MessageType.Info, "**Has Private Channel:** " + user.hasPrivateChannel());
/* 117 */         if (user.hasPrivateChannel()) {
/* 118 */           Type.messageType(Type.MessageType.Info, "Private Channel id:** " + user.getPrivateChannel().getId());
/*     */         }
/* 120 */         Type.messageType(Type.MessageType.Info, "**Is Bot:** " + user.isBot());
/* 121 */         Type.messageType(Type.MessageType.Info, "**Is Fake:** " + user.isFake());
/* 122 */         Type.messageType(Type.MessageType.Info, "**As Mention:** " + user.getAsMention());
/* 123 */         Type.messageType(Type.MessageType.Info, "**Owner of the server:** " + isOwner(user, gd));
/* 124 */         Type.messageType(Type.MessageType.Info, "**Administrator:** " + isAdm(user));
/* 125 */         Type.messageType(Type.MessageType.Info, "**Yumi Bot Developer:** " + 
/* 126 */           YuzukuBot.getInstance().isDeveloper(user.getId()));
/*     */       }
/*     */       catch (Exception e) {
/* 129 */         Type.messageType(Type.MessageType.Error, "**This user not exist**");
/*     */       }
/*     */     } else {
/* 132 */       Type.messageType(Type.MessageType.Error, "Type \"server serverId\", \"tchannel serverId channelId\", \"vchannel serverId channelId\", \"users serverId userId\"");
/*     */     }
/*     */     
/*     */ 
/* 136 */     return true;
/*     */   }
/*     */   
/*     */   private boolean isOwner(User user, Guild guild) {
/* 140 */     if (user.getId().equals(guild.getOwner().getUser().getId())) {
/* 141 */       return true;
/*     */     }
/* 143 */     return false;
/*     */   }
/*     */   
/*     */   public String getDateAndHour(OffsetDateTime dtime) {
/* 147 */     return 
/* 148 */       dtime.getYear() + "/" + dtime.getMonth().getValue() + "/" + dtime.getDayOfMonth() + " " + dtime.getHour() + ":" + dtime.getMinute() + ":" + dtime.getSecond();
/*     */   }
/*     */   
/*     */ 
/*     */   private boolean isAdm(User user)
/*     */   {
/* 154 */     if (YuzukuBot.getInstance().settingsData.getList(net.sydrus.yuzuku.FileReader.BCRItem.getItem(net.sydrus.yuzuku.FileReader.BCRItem.Type.Administrators)).contains(user.getId())) {}
/*     */     
/*     */ 
/* 157 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\ConsoleCommand\Commands\info.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */