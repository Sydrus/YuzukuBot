/*     */ package net.sydrus.yuzuku.Commands;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.dv8tion.jda.core.MessageHistory;
/*     */ import net.dv8tion.jda.core.entities.Guild;
/*     */ import net.dv8tion.jda.core.entities.Message;
/*     */ import net.dv8tion.jda.core.entities.TextChannel;
/*     */ import net.dv8tion.jda.core.entities.User;
/*     */ import net.dv8tion.jda.core.exceptions.RateLimitedException;
/*     */ import net.dv8tion.jda.core.requests.RestAction;
/*     */ import net.sydrus.yuzuku.Constructors.Command;
/*     */ import net.sydrus.yuzuku.Managers.EmbedManager;
/*     */ import net.sydrus.yuzuku.Managers.GuildManager;
/*     */ import net.sydrus.yuzuku.Managers.HelpManager;
/*     */ import net.sydrus.yuzuku.Managers.LevelType;
/*     */ import net.sydrus.yuzuku.String.Administratives;
/*     */ import net.sydrus.yuzuku.YuzukuBot;
/*     */ 
/*     */ public class ClearChat extends Command
/*     */ {
/*     */   public boolean onCommand(final User Sender, final Message Message, final Guild Guild, final TextChannel Chat, final List<LevelType> type, final Boolean isEdited, final String[] args)
/*     */   {
/*  25 */     if (isAdministrator(type)) {
/*  26 */       if (args.length == 1)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  61 */         new Thread(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/*  29 */             List<String> Messages = new ArrayList();
/*  30 */             int qnt = 0;
/*  31 */             int count = 0;
/*  32 */             if (args[0].equalsIgnoreCase("help")) {
/*  33 */               ClearChat.this.argsCommand(Sender, Message, Guild, Chat, type, isEdited, args);
/*  34 */               return;
/*     */             }
/*     */             try {
/*  37 */               qnt = Integer.valueOf(args[0]).intValue();
/*     */             } catch (Exception e) {
/*  39 */               Chat.sendMessage(ClearChat.this.embedMessage(ClearChat.this.sender(Sender) + "Enter a number between 1 and 100")).queue();
/*  40 */               return;
/*     */             }
/*  42 */             if ((qnt == 0) || (qnt > 100) || (qnt < 1)) {
/*  43 */               Chat.sendMessage(ClearChat.this.embedMessage(ClearChat.this.sender(Sender) + "Enter a number between 1 and 100")).queue();
/*  44 */               return;
/*     */             }
/*     */             try {
/*  47 */               for (Message msgs : (List)Chat.getHistory().retrievePast(qnt).block()) {
/*  48 */                 Messages.add(msgs.getId());
/*  49 */                 count++;
/*     */               }
/*     */             }
/*     */             catch (RateLimitedException localRateLimitedException) {}
/*  53 */             for (String str : Messages) {
/*     */               try {
/*  55 */                 ((Message)Chat.getMessageById(str).block()).deleteMessage().block();
/*     */               }
/*     */               catch (Exception localException1) {}
/*     */             }
/*  59 */             Chat.sendMessage(ClearChat.this.embedMessage("**Was removed** " + count + " **Messages**")).queue();
/*     */           }
/*     */         })
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  61 */           .start();
/*  62 */       } else if (args.length >= 2) {
/*  63 */         argsCommand(Sender, Message, Guild, Chat, type, isEdited, args);
/*  64 */       } else if (args.length == 0)
/*     */       {
/*  66 */         Chat.sendMessage(embedMessage("To know the available commands enter \"" + YuzukuBot.guildManager.getPrefix(Guild) + "rm help\"")).queue();
/*     */       } else {
/*  68 */         Chat.sendMessage(embedMessage(sender(Sender) + "Enter a number between 1 and 100")).queue();
/*     */       }
/*     */     } else {
/*  71 */       Chat.sendMessage(embedMessage(sender(Sender) + Administratives.AdminOnly)).queue();
/*     */     }
/*  73 */     return true;
/*     */   }
/*     */   
/*     */   private boolean argsCommand(final User Sender, final Message Message, Guild Guild, final TextChannel Chat, List<LevelType> type, Boolean isEdited, final String[] args)
/*     */   {
/*  78 */     if ((args.length == 1) && (args[0].equalsIgnoreCase("help"))) {
/*  79 */       HelpManager hlp = new HelpManager();
/*  80 */       hlp.setPrefix("{s}", YuzukuBot.guildManager.getPrefix(Guild));
/*  81 */       hlp.addItem(" {s}rm <number>", "**Clear the chat according to the defined number** \n");
/*  82 */       hlp.addItem("{s}rm <number> igp <Usuario>", "**Clears chat but ignores entered user's messages** \n");
/*  83 */       hlp.addItem("{s}rm <number> igm <numero>", "**Clears the chat but ignores a number of defined messages** \n");
/*  84 */       hlp.addItem(" {s}rm <number> rmu <usuario>", "**Removes the message from a specific user** \n");
/*  85 */       hlp.addItem("{s}rm <number> jump", "Clears the chat but skips messages as defined in the last option. Ex: {s}rm 5 jump 1: Result: delete, jump, delete, jump, delete");
/*     */       
/*  87 */       Chat.sendMessage(hlp.getEmbedManager(net.sydrus.yuzuku.Managers.HelpManager.HelpType.UseComment).getMessage()).queue();
/*  88 */     } else if ((args.length == 3) && (args[1].equalsIgnoreCase("igp")))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 134 */       new Thread(new Runnable()
/*     */       {
/*     */         public void run()
/*     */         {
/*  91 */           List<String> Messages = new ArrayList();
/*  92 */           int qnt = 0;
/*  93 */           int count = 0;
/*  94 */           User ignore = null;
/*     */           try {
/*  96 */             qnt = Integer.valueOf(args[0]).intValue();
/*     */           } catch (Exception e) {
/*  98 */             Chat.sendMessage(ClearChat.this.embedMessage(ClearChat.this.sender(Sender) + "Enter a number between 1 and 100", Color.RED)).queue();
/*  99 */             return;
/*     */           }
/* 101 */           if ((qnt == 0) || (qnt > 100) || (qnt < 1)) {
/* 102 */             Chat.sendMessage(ClearChat.this.embedMessage(ClearChat.this.sender(Sender) + "Enter a number between 1 and 100", Color.RED)).queue();
/* 103 */             return;
/*     */           }
/*     */           try
/*     */           {
/* 107 */             ignore = (User)Message.getMentionedUsers().get(0);
/*     */           } catch (Exception e) {
/* 109 */             Chat.sendMessage(ClearChat.this.embedMessage(ClearChat.this.sender(Sender) + "**Select a valid user**", Color.RED)).queue();
/* 110 */             return;
/*     */           }
/*     */           try
/*     */           {
/* 114 */             for (Message msgs : (List)Chat.getHistory().retrievePast(100).block()) {
/* 115 */               if (msgs.getAuthor() != ignore) {
/* 116 */                 Messages.add(msgs.getId());
/* 117 */                 count++;
/*     */               }
/* 119 */               if (count == qnt) {
/*     */                 break;
/*     */               }
/*     */             }
/*     */           }
/*     */           catch (RateLimitedException localRateLimitedException) {}
/* 125 */           for (String str : Messages) {
/*     */             try {
/* 127 */               ((Message)Chat.getMessageById(str).block()).deleteMessage().block();
/*     */             }
/*     */             catch (Exception localException1) {}
/*     */           }
/* 131 */           Chat.sendMessage(ClearChat.this.embedMessage("**Was removed**" + count + " **Messages**", Color.RED)).queue();
/*     */         }
/*     */       })
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 134 */         .start();
/*     */     }
/* 135 */     else if ((args.length == 3) && (args[1].equalsIgnoreCase("igm")))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 186 */       new Thread(new Runnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 138 */           List<String> Messages = new ArrayList();
/* 139 */           int qnt = 0;
/* 140 */           int count = 0;
/* 141 */           int ignore = 0;
/* 142 */           int ignored = 0;
/*     */           try {
/* 144 */             qnt = Integer.valueOf(args[0]).intValue();
/*     */           } catch (Exception e) {
/* 146 */             Chat.sendMessage(ClearChat.this.embedMessage(ClearChat.this.sender(Sender) + "Enter a number between 1 and 100", Color.RED)).queue();
/* 147 */             return;
/*     */           }
/* 149 */           if ((qnt == 0) || (qnt > 100) || (qnt < 1)) {
/* 150 */             Chat.sendMessage(ClearChat.this.embedMessage(ClearChat.this.sender(Sender) + "Enter a number between 1 and 100", Color.RED)).queue();
/* 151 */             return;
/*     */           }
/*     */           try {
/* 154 */             ignore = Integer.valueOf(args[2]).intValue();
/*     */           }
/*     */           catch (Exception e) {
/* 157 */             Chat.sendMessage(ClearChat.this.sender(Sender) + "```css\n\nPut on the argument 3 a number between 1 and 50\n\n```").queue();
/* 158 */             return;
/*     */           }
/* 160 */           if ((ignore == 0) || (ignore > 50) || (ignore < 1))
/*     */           {
/* 162 */             Chat.sendMessage(ClearChat.this.sender(Sender) + "```css\n\nPut on the argument 3 a number between 1 and 50\n\n```").queue();
/* 163 */             return;
/*     */           }
/*     */           try {
/* 166 */             for (Message msgs : (List)Chat.getHistory().retrievePast(100).block()) {
/* 167 */               if (ignored == ignore) {
/* 168 */                 Messages.add(msgs.getId());
/* 169 */                 count++;
/*     */               } else {
/* 171 */                 ignored++;
/*     */               }
/*     */             }
/*     */           }
/*     */           catch (RateLimitedException localRateLimitedException) {}
/* 176 */           for (String str : Messages) {
/*     */             try {
/* 178 */               ((Message)Chat.getMessageById(str).block()).deleteMessage().block();
/*     */             }
/*     */             catch (Exception localException1) {}
/*     */           }
/*     */           
/* 183 */           Chat.sendMessage(ClearChat.this.embedMessage("**Messages Removeds** " + count + ", **skipped:**" + ignored)).queue();
/*     */         }
/*     */       })
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 186 */         .start();
/*     */     }
/* 187 */     else if ((args.length == 2) && (args[1].equalsIgnoreCase("jump")))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 230 */       new Thread(new Runnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 190 */           List<String> Messages = new ArrayList();
/* 191 */           int qnt = 0;
/* 192 */           int count = 0;
/* 193 */           boolean ignore = false;
/* 194 */           int ignoreds = 0;
/*     */           try {
/* 196 */             qnt = Integer.valueOf(args[0]).intValue();
/*     */           }
/*     */           catch (Exception e) {
/* 199 */             Chat.sendMessage(ClearChat.this.embedMessage(ClearChat.this.sender(Sender) + "Enter a number between 1 and 100", Color.RED)).queue();
/* 200 */             return;
/*     */           }
/* 202 */           if ((qnt == 0) || (qnt > 100) || (qnt < 1))
/*     */           {
/* 204 */             Chat.sendMessage(ClearChat.this.embedMessage(ClearChat.this.sender(Sender) + "Enter a number between 1 and 100", Color.RED)).queue();
/* 205 */             return;
/*     */           }
/*     */           try
/*     */           {
/* 209 */             for (Message msgs : (List)Chat.getHistory().retrievePast(100).block()) {
/* 210 */               if (ignore) {
/* 211 */                 ignoreds++;
/* 212 */                 ignore = false;
/*     */               } else {
/* 214 */                 Messages.add(msgs.getId());
/* 215 */                 count++;
/* 216 */                 ignore = true;
/*     */               }
/*     */             }
/*     */           }
/*     */           catch (RateLimitedException localRateLimitedException) {}
/* 221 */           for (String str : Messages) {
/*     */             try {
/* 223 */               ((Message)Chat.getMessageById(str).block()).deleteMessage().block();
/*     */             }
/*     */             catch (Exception localException1) {}
/*     */           }
/*     */           
/* 228 */           Chat.sendMessage(ClearChat.this.embedMessage(ClearChat.this.sender(Sender) + count + "**Messages were removed and " + ignoreds + " messages were ignored**")).queue();
/*     */         }
/*     */       })
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 230 */         .start();
/* 231 */     } else if ((args.length == 3) && (args[1].equalsIgnoreCase("rmu")))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 278 */       new Thread(new Runnable()
/*     */       {
/*     */         public void run()
/*     */         {
/* 234 */           List<String> Messages = new ArrayList();
/* 235 */           int qnt = 0;
/* 236 */           int count = 0;
/* 237 */           User ignore = null;
/*     */           try {
/* 239 */             qnt = Integer.valueOf(args[0]).intValue();
/*     */           }
/*     */           catch (Exception e) {
/* 242 */             Chat.sendMessage(ClearChat.this.embedMessage(ClearChat.this.sender(Sender) + "Enter a number between 1 and 100", Color.RED)).queue();
/* 243 */             return;
/*     */           }
/* 245 */           if ((qnt == 0) || (qnt > 100) || (qnt < 1))
/*     */           {
/* 247 */             Chat.sendMessage(ClearChat.this.embedMessage(ClearChat.this.sender(Sender) + "Enter a number between 1 and 100", Color.RED)).queue();
/* 248 */             return;
/*     */           }
/*     */           try
/*     */           {
/* 252 */             ignore = (User)Message.getMentionedUsers().get(0);
/*     */           } catch (Exception e) {
/* 254 */             Chat.sendMessage(ClearChat.this.embedMessage(ClearChat.this.sender(Sender) + "**Put a valid user**", Color.RED)).queue();
/* 255 */             return;
/*     */           }
/*     */           try
/*     */           {
/* 259 */             for (Message msgs : (List)Chat.getHistory().retrievePast(100).block()) {
/* 260 */               if (msgs.getAuthor() == ignore) {
/* 261 */                 Messages.add(msgs.getId());
/* 262 */                 count++;
/*     */               }
/* 264 */               if (count == qnt) {
/*     */                 break;
/*     */               }
/*     */             }
/*     */           }
/*     */           catch (RateLimitedException localRateLimitedException) {}
/* 270 */           for (String str : Messages) {
/*     */             try {
/* 272 */               ((Message)Chat.getMessageById(str).block()).deleteMessage().block();
/*     */             }
/*     */             catch (Exception localException1) {}
/*     */           }
/* 276 */           Chat.sendMessage(ClearChat.this.embedMessage("**Removed** " + count + " **Message**")).queue();
/*     */         }
/*     */       })
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 278 */         .start();
/*     */     } else {
/* 280 */       Chat.sendMessage(embedMessage(sender(Sender) + "**Error, invalid arguments**", Color.RED)).queue();
/*     */     }
/* 282 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Commands\ClearChat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */