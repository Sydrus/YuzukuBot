/*     */ package net.sydrus.yuzuku.Managers.ConsoleCommand.Commands;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import net.dv8tion.jda.core.JDA;
/*     */ import net.dv8tion.jda.core.entities.Guild;
/*     */ import net.dv8tion.jda.core.entities.TextChannel;
/*     */ import net.dv8tion.jda.core.entities.User;
/*     */ import net.sydrus.yuzuku.Managers.ConsoleCommand.ConsoleDef;
/*     */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
/*     */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;
/*     */ import net.sydrus.yuzuku.Managers.getBotListItems;
/*     */ import net.sydrus.yuzuku.YuzukuBot;
/*     */ 
/*     */ public class message extends net.sydrus.yuzuku.Constructors.ConsoleCommand
/*     */ {
/*  18 */   TextChannel channel = null;
/*     */   
/*     */   public boolean onCommand(ConsoleDef console, String command, String time, String[] args)
/*     */   {
/*  22 */     if (args.length == 0) {
/*  23 */       Type.messageType(Type.MessageType.Error, "Args for use this command");
/*  24 */       Type.messageType(Type.MessageType.Error, "message set <server id> <channel id>");
/*  25 */       Type.messageType(Type.MessageType.Error, "message send <text>");
/*  26 */       Type.messageType(Type.MessageType.Error, "message clear");
/*  27 */     } else if ((args.length == 1) && (args[0].equalsIgnoreCase("info"))) {
/*     */       try {
/*  29 */         if (this.channel == null) {
/*  30 */           console.sendText("No channels defined");
/*     */         } else {
/*  32 */           console.sendText("Channel info");
/*  33 */           console.sendText("Source server: " + this.channel.getGuild().getName());
/*  34 */           console.sendText("Source server id: " + this.channel.getGuild().getId());
/*  35 */           console.sendText("Channel name: " + this.channel.getName());
/*  36 */           console.sendText("Channel id: " + this.channel.getId());
/*     */         }
/*     */       } catch (Exception e) {
/*  39 */         console.sendText(Type.MessageType.Error, "Error fetching channel");
/*     */       }
/*     */     }
/*  42 */     else if ((args.length == 3) && (args[0].equalsIgnoreCase("set"))) {
/*  43 */       Guild guild = null;
/*  44 */       String[] setDef = argsToString(args, Integer.valueOf(1)).split(" ");
/*     */       try {
/*  46 */         guild = YuzukuBot.getInstance().getJDA().getGuildById(setDef[0]);
/*     */       } catch (Exception e) {
/*  48 */         console.sendText(Type.MessageType.Error, "Error on get server");
/*  49 */         return true;
/*     */       }
/*     */       try {
/*  52 */         this.channel = guild.getTextChannelById(setDef[1]);
/*  53 */         console.sendText("Channel set to");
/*  54 */         console.sendText("Channel Name: " + this.channel.getName());
/*  55 */         console.sendText("Chnnel id: " + this.channel.getId());
/*     */       } catch (Exception e) {
/*  57 */         console.sendText(Type.MessageType.Error, "Error on get Channel");
/*  58 */         return true;
/*     */       }
/*  60 */     } else if ((args.length > 1) && (args[0].equalsIgnoreCase("send"))) {
/*  61 */       if (this.channel == null) {
/*  62 */         console.sendText(Type.MessageType.Error, "Channel not set");
/*  63 */         return true;
/*     */       }
/*     */       try {
/*  66 */         this.channel.sendMessage(getMention(argsToString(args, Integer.valueOf(1)))).queue();
/*  67 */         Type.messageType(Type.MessageType.Info, "To Channel [" + this.channel
/*  68 */           .getName() + "] -> " + argsToString(args, Integer.valueOf(1)));
/*     */       } catch (NullPointerException e) {
/*  70 */         console.sendText(Type.MessageType.Error, "Error on send message, verify if channel exist");
/*     */       }
/*  72 */     } else if ((args.length == 1) && (args[0].equalsIgnoreCase("clear"))) {
/*     */       try {
/*  74 */         console.sendText("Channel removed");
/*     */       } catch (Exception e) {
/*  76 */         console.sendText("Error on remove channel");
/*     */       }
/*     */     } else {
/*  79 */       Type.messageType(Type.MessageType.Error, "Args for use this command");
/*  80 */       Type.messageType(Type.MessageType.Error, "message set <server id> <channel id>");
/*  81 */       Type.messageType(Type.MessageType.Error, "message send <text>");
/*  82 */       Type.messageType(Type.MessageType.Error, "message clear");
/*     */     }
/*  84 */     return true;
/*     */   }
/*     */   
/*     */   private String getMention(String Text) {
/*  88 */     String toReturn = Text;
/*  89 */     List<String> mention = new ArrayList();
/*  90 */     if (Text.contains("@")) {
/*  91 */       for (String itens : Text.split(" ")) {
/*  92 */         if (itens.startsWith("@")) {
/*  93 */           mention.add(itens);
/*     */         }
/*     */       }
/*     */     }
/*  97 */     Object userNamement = new HashMap();
/*  98 */     if (!mention.isEmpty()) {
/*  99 */       for (String ment : mention) {
/*     */         try {
/* 101 */           ((HashMap)userNamement).put(ment, 
/* 102 */             ((User)getBotListItems.getMembers(this.channel.getGuild(), true, ment.replace("@", "")).get(0)).getAsMention());
/*     */         }
/*     */         catch (Exception localException) {}
/*     */       }
/* 106 */       for (String txt : mention) {
/* 107 */         if (((HashMap)userNamement).containsKey(txt)) {
/*     */           try {
/* 109 */             toReturn = toReturn.replace(txt, (CharSequence)((HashMap)userNamement).get(txt));
/*     */           }
/*     */           catch (Exception localException1) {}
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 117 */     return toReturn;
/*     */   }
/*     */   
/*     */   private String argsToString(String[] args, Integer index) {
/* 121 */     String myString = "";
/* 122 */     for (int i = index.intValue(); i < args.length; i++) {
/* 123 */       String arg = args[i] + " ";
/* 124 */       myString = myString + arg;
/*     */     }
/* 126 */     myString = myString.substring(0, myString.length());
/* 127 */     if (myString.startsWith(" ")) {
/* 128 */       myString = myString.substring(1, myString.length());
/*     */     }
/* 130 */     return myString;
/*     */   }
/*     */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\ConsoleCommand\Commands\message.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */