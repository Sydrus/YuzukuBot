/*     */ package net.sydrus.yuzuku;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.dv8tion.jda.core.AccountType;
/*     */ import net.dv8tion.jda.core.JDA;
/*     */ import net.dv8tion.jda.core.JDABuilder;
/*     */ import net.dv8tion.jda.core.entities.Game;
/*     */ import net.sydrus.yuzuku.FileReader.BCRItem;
/*     */ import net.sydrus.yuzuku.FileReader.BCRItem.Type;
/*     */ import net.sydrus.yuzuku.FileReader.ConfigReader;
/*     */ import net.sydrus.yuzuku.Listeners.DiscordListener;
/*     */ import net.sydrus.yuzuku.Managers.CommandManager;
/*     */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Console;
/*     */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
/*     */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;
/*     */ import net.sydrus.yuzuku.Managers.GuildManager;
/*     */ import net.sydrus.yuzuku.PLugin.Manager.PluginLoader;
/*     */ import net.sydrus.yuzuku.PLugin.PluginCommand;
/*     */ import net.sydrus.yuzuku.audio.MusicManager;
/*     */ 
/*     */ public class YuzukuBot
/*     */ {
/*  30 */   public static final MusicManager MusicManager = new MusicManager();
/*     */   private JDA _JDA;
/*     */   private static YuzukuBot _Instance;
/*  33 */   public static Color defaultMessageColor = Color.GREEN;
/*     */   public final CommandManager _CommandManager;
/*     */   private static PluginLoader admanager;
/*  36 */   public String Version = "2.3.7/ALPHA_DC_1.0";
/*  37 */   public String Status = "Running";
/*  38 */   public int subCommands = 12;
/*  39 */   public String settingsFolder = "Settings";
/*  40 */   private String botDataOfUsersAndText = "Data";
/*     */   public ConfigReader settingsData;
/*     */   private ConfigReader users;
/*  43 */   public int botsvErros = 0;
/*  44 */   public int botErrors = 0;
/*  45 */   public Console console = new Console();
/*  46 */   public static GuildManager guildManager = null;
/*  47 */   public static String botName = "Yuzuku Bot";
/*     */   
/*     */   public YuzukuBot()
/*     */   {
/*  51 */     _Instance = this;
/*     */     
/*     */     try
/*     */     {
/*  55 */       this._JDA = new JDABuilder(AccountType.BOT).setToken("Mjc4NjkzNzkyMjk3Nzc5MjAw.C39sBA.YTCqwvzbHU-lhenApAF0M4iHL-s").addListener(new Object[] { new DiscordListener() }).setGame(Game.of("Please wait! Updating!")).buildBlocking();
/*  56 */       this.settingsData = new ConfigReader(this.settingsFolder, this.botDataOfUsersAndText);
/*  57 */       this.settingsData.createFile();
/*     */     } catch (Exception e) {
/*  59 */       e.printStackTrace();
/*  60 */       System.exit(-1);
/*     */     }
/*  62 */     this._CommandManager = new CommandManager();
/*     */     try {
/*  64 */       this.users = new ConfigReader("Settings", "users.data", "57894561857282254862552u", 84678942);
/*     */     } catch (Exception e1) {
/*  66 */       Type.messageType(Type.MessageType.Error, "the file 'users.data' is corrupted");
/*     */       
/*  68 */       this.botErrors += 1;
/*     */     }
/*     */     try
/*     */     {
/*  72 */       this.settingsData.reload();
/*  73 */       if (!this.settingsData.contains(BCRItem.getItem(BCRItem.Type.Developers))) {
/*  74 */         List<String> item = new ArrayList();
/*  75 */         item.add("");
/*  76 */         this.settingsData.set(BCRItem.getItem(BCRItem.Type.Developers), item);
/*     */       }
/*     */       
/*  79 */       if (!this.settingsData.contains(BCRItem.getItem(BCRItem.Type.Administrators))) {
/*  80 */         List<String> item = new ArrayList();
/*     */         
/*     */ 
/*  83 */         this.settingsData.set(BCRItem.getItem(BCRItem.Type.Administrators), item);
/*     */       }
/*  85 */       this.settingsData.save();
/*     */     } catch (Exception e) {
/*  87 */       System.out.println(e.getMessage());
/*  88 */       this.botErrors += 1;
/*     */     }
/*  90 */     Type.messageType(Type.MessageType.Info, "Loading plugins...");
/*     */     try {
/*  92 */       admanager = new PluginLoader();
/*     */     } catch (Throwable e) {
/*  94 */       this.botErrors += 1;
/*  95 */       e.printStackTrace();
/*     */     }
/*  97 */     createEmpty();
/*  98 */     this.console.start();
/*  99 */     guildManager = new GuildManager(this._JDA);
/* 100 */     guildManager.CheckAllConfigs();
/* 101 */     guildManager.registerTracks();
/*     */   }
/*     */   
/*     */ 
/*     */   private void createEmpty()
/*     */   {
/* 107 */     if (!this.settingsData.contains("ServerPort")) {
/* 108 */       this.settingsData.set("ServerPort", Integer.valueOf(4343));
/*     */     }
/* 110 */     if (!this.settingsData.contains("UseServer")) {
/* 111 */       this.settingsData.set("UseServer", Boolean.valueOf(false));
/*     */     }
/* 113 */     if (!this.settingsData.contains("MaxServerConnections")) {
/* 114 */       this.settingsData.set("MaxServerConnections", "infinity");
/*     */     }
/* 116 */     if (!this.settingsData.contains("UserServerPass")) {
/* 117 */       this.settingsData.set("UserServerPass", Boolean.valueOf(false));
/*     */     }
/* 119 */     if (!this.settingsData.contains("ServerPass")) {
/* 120 */       this.settingsData.set("ServerPass", "000");
/*     */     }
/* 122 */     if (!this.settingsData.contains("ConsoleCommands")) {
/* 123 */       this.settingsData.set("ConsoleCommands", Boolean.valueOf(false));
/*     */     }
/* 125 */     if (!this.settingsData.contains("UseServerCommandInConsole")) {
/* 126 */       this.settingsData.set("UseServerCommandInConsole", Boolean.valueOf(false));
/*     */     }
/* 128 */     if (!this.settingsData.contains("ServerJoinClient")) {
/* 129 */       this.settingsData.set("ServerJoinClient", "[{clientHostName}/{ClientIP}] Connect");
/*     */     }
/* 131 */     if (!this.settingsData.contains("TimeToLock")) {
/* 132 */       this.settingsData.set("TimeToLock", "1 m");
/*     */     }
/* 134 */     this.settingsData.save();
/*     */   }
/*     */   
/*     */   public int getErrors() {
/* 138 */     return this.botsvErros;
/*     */   }
/*     */   
/*     */   public static java.util.Map<String, PluginCommand> getAddonCommand() {
/* 142 */     return PluginLoader.getInstance().addoncommand;
/*     */   }
/*     */   
/*     */   public static net.sydrus.yuzuku.PLugin.Manager.PluginManager getAddonsManager() {
/* 146 */     return admanager.addonsmanager;
/*     */   }
/*     */   
/*     */   public static YuzukuBot getInstance() {
/* 150 */     return _Instance;
/*     */   }
/*     */   
/*     */   public Boolean isDeveloper(String id) {
/*     */     try {
/* 155 */       this.settingsData.reload();
/* 156 */       if (this.settingsData.getList(BCRItem.getItem(BCRItem.Type.Developers)).contains(id)) {
/* 157 */         return Boolean.valueOf(true);
/*     */       }
/*     */     }
/*     */     catch (Exception localException) {}
/* 161 */     return Boolean.valueOf(false);
/*     */   }
/*     */   
/*     */   public final ConfigReader getusers() {
/* 165 */     return this.users;
/*     */   }
/*     */   
/*     */   public CommandManager getCommandManager()
/*     */   {
/* 170 */     return this._CommandManager;
/*     */   }
/*     */   
/*     */   public boolean isTextBanned(String message) throws IOException {
/* 174 */     this.settingsData.reload();
/* 175 */     List<Object> bannedText = this.settingsData.getList(BCRItem.getItem(BCRItem.Type.BannedTexts));
/* 176 */     for (Object banned : bannedText) {
/* 177 */       if (message.toLowerCase().contains(banned.toString().toLowerCase())) {
/* 178 */         return true;
/*     */       }
/*     */     }
/* 181 */     return false;
/*     */   }
/*     */   
/*     */   public List<String> readFile(File file) throws IOException
/*     */   {
/* 186 */     List<String> text = new ArrayList();
/* 187 */     String Textbfl = "";
/*     */     
/*     */ 
/* 190 */     FileReader filerd = new FileReader(file);
/* 191 */     BufferedReader buff = new BufferedReader(filerd);
/* 192 */     while ((Textbfl = buff.readLine()) != null) {
/* 193 */       text.add(Textbfl);
/*     */     }
/* 195 */     return text;
/*     */   }
/*     */   
/*     */   public JDA getJDA() {
/* 199 */     return this._JDA;
/*     */   }
/*     */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\YuzukuBot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */