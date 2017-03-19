/*     */ package net.sydrus.yuzuku.Commands;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.dv8tion.jda.core.entities.Guild;
/*     */ import net.dv8tion.jda.core.entities.Member;
/*     */ import net.dv8tion.jda.core.entities.Message;
/*     */ import net.dv8tion.jda.core.entities.TextChannel;
/*     */ import net.dv8tion.jda.core.entities.User;
/*     */ import net.dv8tion.jda.core.requests.RestAction;
/*     */ import net.sydrus.yuzuku.FileReader.BCRItem;
/*     */ import net.sydrus.yuzuku.FileReader.BCRItem.Type;
/*     */ import net.sydrus.yuzuku.FileReader.ConfigReader;
/*     */ import net.sydrus.yuzuku.Managers.EmbedManager;
/*     */ import net.sydrus.yuzuku.Managers.GuildManager;
/*     */ import net.sydrus.yuzuku.Managers.HelpManager;
/*     */ import net.sydrus.yuzuku.Managers.LevelType;
/*     */ import net.sydrus.yuzuku.String.Administratives;
/*     */ import net.sydrus.yuzuku.YuzukuBot;
/*     */ 
/*     */ public class AdmCommand extends net.sydrus.yuzuku.Constructors.Command
/*     */ {
/*     */   public boolean onCommand(User Sender, Message Message, Guild Guild, TextChannel Chat, List<LevelType> type, Boolean isEdited, String[] args)
/*     */   {
/*  26 */     if (!isAdministrator(type)) {
/*  27 */       Chat.sendMessage(embedMessage(Administratives.AdminOnly, Color.RED)).queue();
/*  28 */       return true;
/*     */     }
/*  30 */     YuzukuBot.getInstance().settingsData.reload();
/*  31 */     if (!YuzukuBot.getInstance().settingsData.contains(BCRItem.getItem(BCRItem.Type.Administrators))) {
/*  32 */       List<String> item = new ArrayList();
/*  33 */       item.add("");
/*  34 */       YuzukuBot.getInstance().settingsData.set(BCRItem.getItem(BCRItem.Type.Administrators), item);
/*  35 */       YuzukuBot.getInstance().settingsData.save();
/*     */     }
/*     */     
/*  38 */     if ((args.length == 1) && (args[0].equalsIgnoreCase("list"))) {
/*     */       try {
/*  40 */         List<Object> admlist = YuzukuBot.getInstance().settingsData.getList(BCRItem.getItem(BCRItem.Type.Administrators));
/*  41 */         String adm = "";
/*  42 */         for (Object user : admlist) {
/*  43 */           adm = adm + getAPI().getUserById(user.toString()).getName() + "\n\n";
/*     */         }
/*     */         
/*  46 */         Chat.sendMessage(embedMessage("**Registered Administrators:**\n" + adm)).queue();
/*     */       } catch (Exception e) {
/*  48 */         Chat.sendMessage(embedMessage("Failed to find user list", Color.YELLOW)).queue();
/*     */       }
/*     */     } else {
/*  51 */       if ((args.length >= 2) && (args[0].equalsIgnoreCase("add")))
/*     */         try {
/*  53 */           YuzukuBot.getInstance().settingsData.reload();
/*  54 */           List<Object> admlist = YuzukuBot.getInstance().settingsData.getList(BCRItem.getItem(BCRItem.Type.Administrators));
/*  55 */           String userId = ((User)Message.getMentionedUsers().get(0)).getId();
/*  56 */           if (Guild.getOwner().getUser().getId().equals(userId)) {
/*  57 */             Chat.sendMessage(embedMessage("This user is owner the server", Color.YELLOW));
/*  58 */             return true;
/*     */           }
/*  60 */           if (admlist.contains(userId)) {
/*  61 */             Chat.sendMessage(embedMessage("This user has already been added", Color.YELLOW)).queue();
/*  62 */             return true;
/*     */           }
/*  64 */           settingsDataAddItem(BCRItem.Type.Administrators, userId);
/*  65 */           Chat.sendMessage(embedMessage("User defined as admin")).queue();
/*     */         } catch (Exception e) {
/*  67 */           Chat.sendMessage(embedMessage("Failed to add user", Color.RED)).queue();
/*  68 */           e.printStackTrace();
/*     */         }
/*  70 */       if ((args.length >= 2) && (args[0].equalsIgnoreCase("remove"))) {
/*  71 */         List<Object> admlist = YuzukuBot.getInstance().settingsData.getList(BCRItem.getItem(BCRItem.Type.Administrators));
/*     */         try {
/*  73 */           String userId = ((User)Message.getMentionedUsers().get(0)).getId();
/*  74 */           if (Guild.getOwner().getUser().getId().equals(userId)) {
/*  75 */             Chat.sendMessage(embedMessage("This user is owner the server", Color.YELLOW)).queue();
/*  76 */             return true;
/*     */           }
/*  78 */           if (!admlist.contains(userId)) {
/*  79 */             Chat.sendMessage(embedMessage("This user is not on the list", Color.YELLOW));
/*  80 */             return true;
/*     */           }
/*  82 */           settingsDataRemoveItem(BCRItem.Type.Administrators, userId);
/*  83 */           Chat.sendMessage(embedMessage("User removed from administrator post")).queue();
/*     */         } catch (Exception e) {
/*  85 */           Chat.sendMessage(embedMessage("Failed to remove user", Color.RED));
/*     */         }
/*  87 */       } else if ((args.length == 1) && (args[0].equalsIgnoreCase("myid"))) {
/*  88 */         Chat.sendMessage(embedMessage(sender(Sender) + "\nYour id is " + Sender.getId(), Color.YELLOW)).queue();
/*  89 */       } else if ((args.length == 1) && (args[0].equalsIgnoreCase("chid"))) {
/*  90 */         Chat.sendMessage(embedMessage(sender(Sender) + "\nThe Channel id is " + Chat.getId(), Color.YELLOW)).queue();
/*  91 */       } else if ((args.length == 1) && (args[0].equalsIgnoreCase("svid")))
/*     */       {
/*  93 */         Chat.sendMessage(embedMessage(sender(Sender) + "\nThe Server id is " + Guild.getId(), Color.YELLOW)).queue();
/*  94 */       } else if ((args.length == 3) && (args[0].equalsIgnoreCase("btxt"))) {
/*  95 */         YuzukuBot.getInstance().settingsData.reload();
/*  96 */         List<Object> btxt = YuzukuBot.getInstance().settingsData.getList(BCRItem.getItem(BCRItem.Type.BannedTexts));
/*  97 */         if (args[1].equalsIgnoreCase("add"))
/*     */           try {
/*  99 */             if (btxt.contains(args[2])) {
/* 100 */               Chat.sendMessage(embedMessage("This item has already been banned", Color.YELLOW)).queue();
/* 101 */               return true;
/*     */             }
/* 103 */             settingsDataAddItem(BCRItem.Type.BannedTexts, args[2]);
/* 104 */             Chat.sendMessage(embedMessage("Link/Word banned(a)", Color.YELLOW)).queue();
/*     */           } catch (Exception e) {
/* 106 */             Chat.sendMessage(embedMessage("Error banning link/word", Color.RED)).queue();
/*     */           }
/* 108 */         if (args[1].equalsIgnoreCase("remove")) {
/*     */           try {
/* 110 */             if (!btxt.contains(args[2])) {
/* 111 */               Chat.sendMessage(embedMessage("This item is not banned", Color.YELLOW)).queue();
/* 112 */               return true;
/*     */             }
/* 114 */             settingsDataRemoveItem(BCRItem.Type.BannedTexts, args[2]);
/* 115 */             Chat.sendMessage(embedMessage("Link/word unbanned")).queue();
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 119 */             Chat.sendMessage(embedMessage("Error trying unban link/word. Error: " + e.getMessage(), Color.RED)).queue();
/* 120 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/*     */       else {
/* 125 */         if ((args.length == 2) && (args[0].equalsIgnoreCase("btxt")) && (args[1].equalsIgnoreCase("list"))) {
/*     */           try {
/* 127 */             String itens = "";
/* 128 */             List<Object> btxt = YuzukuBot.getInstance().settingsData.getList(BCRItem.getItem(BCRItem.Type.BannedTexts));
/* 129 */             for (Object item : btxt) {
/* 130 */               itens = itens + item + "\n";
/*     */             }
/* 132 */             if (itens.isEmpty()) {
/* 133 */               Chat.sendMessage(embedMessage("No items are banned.")).queue();
/* 134 */               return true;
/*     */             }
/* 136 */             Chat.sendMessage(embedMessage("Banned Items\n" + itens)).queue();
/*     */           } catch (Exception e) {
/* 138 */             Chat.sendMessage(embedMessage("Failed to check ban list")).queue();
/*     */           }
/*     */         }
/* 141 */         HelpManager adml = new HelpManager();
/* 142 */         adml.setPrefix("{s}", YuzukuBot.guildManager.getPrefix(Guild) + "adm");
/* 143 */         adml.setTitle("**Admin Commands:**");
/* 144 */         adml.addItem("\t{s}list", "**Show the list of registered administrators**\n");
/* 145 */         adml.addItem("\t{s}add <User>", "**Add a user as administrator**\n");
/* 146 */         adml.addItem("\t{s}remove <User>", "**Remove admin user**\n");
/* 147 */         adml.addItem("\t{s}myid", "**Show your id**\n");
/* 148 */         adml.addItem("\t{s}chid", "**Show the Channel id**\n");
/* 149 */         adml.addItem("\t{s}svid", "**Show the Server id**\n");
/* 150 */         adml.addItem("\t{s}btxt <add/remove/list> <text> ", "**Ban a word** ");
/* 151 */         Chat.sendMessage(adml.getEmbedManager(net.sydrus.yuzuku.Managers.HelpManager.HelpType.UseComment).getMessage()).queue();
/*     */       } }
/* 153 */     return true;
/*     */   }
/*     */   
/*     */   public void settingsDataAddItem(BCRItem.Type type, String item) {
/* 157 */     ConfigReader config = YuzukuBot.getInstance().settingsData;
/* 158 */     config.reload();
/* 159 */     List<Object> itens = config.getList(BCRItem.getItem(type));
/* 160 */     if (!itens.contains(item)) {
/* 161 */       itens.add(item);
/*     */     }
/* 163 */     config.set(BCRItem.getItem(type), itens);
/* 164 */     config.save();
/*     */   }
/*     */   
/*     */   public void settingsDataRemoveItem(BCRItem.Type type, String item) {
/* 168 */     ConfigReader config = YuzukuBot.getInstance().settingsData;
/* 169 */     config.reload();
/* 170 */     List<Object> itens = config.getList(BCRItem.getItem(type));
/* 171 */     if (itens.size() <= 1) {
/* 172 */       config.set(BCRItem.getItem(type), null);
/*     */     } else {
/* 174 */       if (itens.contains(item)) {
/* 175 */         itens.remove(item);
/*     */       }
/* 177 */       config.set(BCRItem.getItem(type), itens);
/*     */     }
/* 179 */     config.save();
/* 180 */     config.reload();
/*     */   }
/*     */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Commands\AdmCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */