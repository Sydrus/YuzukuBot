/*     */ package net.sydrus.yuzuku.Managers;
/*     */ 
/*     */ import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
/*     */ import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.dv8tion.jda.core.JDA;
/*     */ import net.dv8tion.jda.core.entities.Guild;
/*     */ import net.dv8tion.jda.core.entities.SelfUser;
/*     */ import net.sydrus.yuzuku.FileReader.ConfigReader;
/*     */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
/*     */ import net.sydrus.yuzuku.YuzukuBot;
/*     */ import net.sydrus.yuzuku.audio.MusicManager;
/*     */ 
/*     */ public class GuildManager
/*     */ {
/*  21 */   private JDA jda = null;
/*     */   
/*  23 */   private boolean isLoaded = false;
/*     */   
/*     */   public GuildManager(JDA jda) {
/*  26 */     this.jda = jda;
/*     */   }
/*     */   
/*     */   public static MusicManager getMusicManager() {
/*  30 */     return YuzukuBot.MusicManager;
/*     */   }
/*     */   
/*     */   public ConfigReader getServerConfig(Guild guild) {
/*  34 */     return new ConfigReader("Guilds", guild.getId());
/*     */   }
/*     */   
/*     */   public boolean autoSaveMusic(Guild guild) {
/*  38 */     ConfigReader bol = getServerConfig(guild);
/*  39 */     return !bol.contains("autoSaveMusic") ? true : bol.getBoolean("autoSaveMusic");
/*     */   }
/*     */   
/*     */   public boolean playlistIsLoaded() {
/*  43 */     return this.isLoaded;
/*     */   }
/*     */   
/*     */   public int getVolume(Guild guild) {
/*  47 */     ConfigReader vall = getServerConfig(guild);
/*  48 */     return !vall.contains("playerVolume") ? 50 : vall.getInt("playerVolume");
/*     */   }
/*     */   
/*     */   public void setVolume(Guild guild, int volume) {
/*  52 */     ConfigReader vall = getServerConfig(guild);
/*  53 */     vall.set("playerVolume", Integer.valueOf(volume));
/*  54 */     vall.save();
/*     */   }
/*     */   
/*     */   public void setAutosave(Guild guild, boolean value) {
/*     */     try {
/*  59 */       ConfigReader bol = getServerConfig(guild);
/*  60 */       bol.set("autoSaveMusic", Boolean.valueOf(value));
/*  61 */       bol.save();
/*     */     }
/*     */     catch (Exception localException) {}
/*     */   }
/*     */   
/*     */   public boolean containsTrack(Guild guild, AudioTrack track) {
/*  67 */     ConfigReader config = getServerConfig(guild);
/*  68 */     if (config.contains("PlayList")) {
/*  69 */       return config.getList("PlayList").contains(track.getIdentifier());
/*     */     }
/*  71 */     return false;
/*     */   }
/*     */   
/*     */   public void addMusic(Guild guild, AudioTrack track) {
/*     */     try {
/*  76 */       ConfigReader conf = getServerConfig(guild);
/*  77 */       List<Object> links = new ArrayList();
/*  78 */       if (conf.contains("PlayList")) {
/*  79 */         links = conf.getList("PlayList");
/*     */       } else {
/*  81 */         links = new ArrayList();
/*     */       }
/*  83 */       if (!links.contains(track.getIdentifier())) {
/*  84 */         links.add(track.getIdentifier());
/*     */       }
/*  86 */       conf.set("PlayList", links);
/*  87 */       conf.save();
/*     */     } catch (Exception e) {
/*  89 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addList(final Guild guild, final AudioPlaylist playlist)
/*     */   {
/* 100 */     new Thread(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/*  96 */         for (AudioTrack track : playlist.getTracks()) {
/*  97 */           GuildManager.this.addMusic(guild, track);
/*     */         }
/*     */       }
/*     */     })
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 100 */       .start();
/*     */   }
/*     */   
/*     */   public String getPrefix(Guild guild) {
/* 104 */     String prefix = this.jda.getSelfUser().getAsMention() + " ";
/*     */     try {
/* 106 */       ConfigReader reader = getServerConfig(guild);
/* 107 */       if (reader.contains("guildPrefix")) {
/* 108 */         prefix = reader.getString("guildPrefix");
/*     */       }
/*     */     } catch (Exception e) {
/* 111 */       System.err.println(e.getMessage());
/*     */     }
/* 113 */     return prefix;
/*     */   }
/*     */   
/*     */   public void setPrefix(Guild guild, char charAt) {
/*     */     try {
/* 118 */       ConfigReader reader = getServerConfig(guild);
/* 119 */       reader.set("guildPrefix", charAt + "");
/* 120 */       reader.save();
/*     */     } catch (Exception e) {
/* 122 */       System.err.println(e.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean hasMusicInConfig(Guild guild) {
/* 127 */     ConfigReader read = getServerConfig(guild);
/* 128 */     return (read.contains("PlayList")) && (!read.getList("PlayList").isEmpty());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public void registerTracks()
/*     */   {
/* 159 */     new Thread(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 135 */         ConfigReader reader = null;
/* 136 */         for (Guild gd : GuildManager.this.jda.getGuilds()) {
/*     */           try {
/* 138 */             reader = new ConfigReader("Guilds", gd.getId());
/* 139 */             if (reader.contains("PlayList")) {
/* 140 */               List<Object> links = reader.getList("PlayList");
/* 141 */               for (Object ob : links) {
/* 142 */                 String toAdd = "";
/* 143 */                 if (ob.toString().contains("youtube.com/watch?v=")) {
/* 144 */                   toAdd = ob.toString();
/*     */                 } else {
/* 146 */                   toAdd = "www.youtube.com/watch?v=" + ob;
/*     */                 }
/*     */                 
/* 149 */                 GuildManager.getMusicManager().addItems(YuzukuBot.getInstance().getJDA().getGuildById(gd.getId()), toAdd);
/*     */               }
/*     */             }
/*     */           }
/*     */           catch (Exception localException) {}
/*     */         }
/*     */         
/* 156 */         GuildManager.this.isLoaded = true;
/* 157 */         Type.messageType(net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType.God, "Server playlist is loaded");
/*     */       }
/*     */     })
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 159 */       .start();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public void CheckAllConfigs()
/*     */   {
/* 184 */     new Thread(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 166 */         List<String> toRemove = new ArrayList();
/* 167 */         File[] files = GuildManager.this.getFolderFileList("Guilds");
/* 168 */         List<String> guilds = GuildManager.this.guidsId();
/* 169 */         String guildId = "";
/* 170 */         for (File file : files) {
/* 171 */           guildId = file.getName().substring(0, file.getName().lastIndexOf("."));
/* 172 */           if (!guilds.contains(guildId)) {
/* 173 */             toRemove.add(guildId);
/*     */           }
/*     */         }
/* 176 */         for (??? = toRemove.iterator(); ((Iterator)???).hasNext();) { String tr = (String)((Iterator)???).next();
/*     */           try {
/* 178 */             guilds.remove(guildId);
/* 179 */             new File(GuildManager.this.cFolder("Guilds"), tr + ".dcnf").delete();
/*     */           }
/*     */           catch (Exception localException1) {}
/*     */         }
/*     */       }
/*     */     })
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 184 */       .start();
/*     */   }
/*     */   
/*     */   private List<String> guidsId() {
/* 188 */     List<String> toReturn = new ArrayList();
/* 189 */     for (Guild guild : this.jda.getGuilds()) {
/* 190 */       toReturn.add(guild.getId());
/*     */     }
/* 192 */     return toReturn;
/*     */   }
/*     */   
/*     */   private String cFolder(String folder) {
/* 196 */     File locFolder = null;
/*     */     try {
/* 198 */       locFolder = new File(new File("").getCanonicalPath(), folder);
/*     */     } catch (IOException e) {
/* 200 */       locFolder = new File(folder);
/*     */     }
/* 202 */     return locFolder.toString();
/*     */   }
/*     */   
/*     */   private File[] getFolderFileList(String folder) {
/* 206 */     File locFolder = null;
/*     */     try {
/* 208 */       locFolder = new File(new File("").getCanonicalPath(), folder);
/*     */     } catch (IOException e) {
/* 210 */       locFolder = new File(folder);
/*     */     }
/* 212 */     locFolder.mkdirs();
/* 213 */     File[] fList = locFolder.listFiles();
/* 214 */     return fList;
/*     */   }
/*     */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\GuildManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */