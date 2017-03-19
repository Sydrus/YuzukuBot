/*     */ package net.sydrus.yuzuku.audio;
/*     */ 
/*     */ import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
/*     */ import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
/*     */ import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
/*     */ import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
/*     */ import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
/*     */ import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
/*     */ import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
/*     */ import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
/*     */ import java.awt.Color;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.dv8tion.jda.core.entities.Guild;
/*     */ import net.dv8tion.jda.core.entities.Message;
/*     */ import net.dv8tion.jda.core.entities.TextChannel;
/*     */ import net.dv8tion.jda.core.entities.VoiceChannel;
/*     */ import net.dv8tion.jda.core.managers.AudioManager;
/*     */ import net.dv8tion.jda.core.requests.RestAction;
/*     */ import net.sydrus.yuzuku.Managers.EmbedManager;
/*     */ import net.sydrus.yuzuku.Managers.GuildManager;
/*     */ import net.sydrus.yuzuku.YuzukuBot;
/*     */ import net.sydrus.yuzuku.exceptions.OutOfRangeException;
/*     */ 
/*     */ public class MusicManager
/*     */ {
/*     */   private AudioPlayerManager playerManager;
/*  29 */   private Map<Long, TextChannel> mtChannel = new HashMap();
/*  30 */   private Map<Long, GuildMusicManager> mmusicManager = new HashMap();
/*     */   
/*     */   public java.util.Collection<TextChannel> getMusicChannels() {
/*  33 */     return this.mtChannel.values();
/*     */   }
/*     */   
/*     */   public MusicManager() {
/*  37 */     this.playerManager = new com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager();
/*  38 */     this.playerManager.registerSourceManager(new com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager(true));
/*  39 */     AudioSourceManagers.registerLocalSource(this.playerManager);
/*     */   }
/*     */   
/*     */   public void setTextChannel(Guild guild, TextChannel textchannel) {
/*  43 */     long gid = Long.parseLong(guild.getId());
/*  44 */     this.mtChannel.put(Long.valueOf(gid), textchannel);
/*     */   }
/*     */   
/*     */   public void setGuildManager(Guild guild, GuildMusicManager manager) {
/*  48 */     long gid = Long.parseLong(guild.getId());
/*  49 */     this.mmusicManager.put(Long.valueOf(gid), manager);
/*     */   }
/*     */   
/*     */   public void setNextQueue(Guild guild, int queue) throws OutOfRangeException {
/*  53 */     long gid = Long.parseLong(guild.getId());
/*  54 */     ((GuildMusicManager)this.mmusicManager.get(Long.valueOf(gid))).setNextQueue(queue);
/*     */   }
/*     */   
/*     */   public AudioTrack getSettedNextQueue(Guild guild) {
/*  58 */     long gid = Long.parseLong(guild.getId());
/*  59 */     return ((GuildMusicManager)this.mmusicManager.get(Long.valueOf(gid))).getSettedNextQueue();
/*     */   }
/*     */   
/*     */   public int getSettedNextQueueInt(Guild guild) {
/*  63 */     long gid = Long.parseLong(guild.getId());
/*  64 */     return ((GuildMusicManager)this.mmusicManager.get(Long.valueOf(gid))).getSettedNextQueueInt();
/*     */   }
/*     */   
/*     */   public void clearTracks(Guild guild) {
/*  68 */     long gid = Long.parseLong(guild.getId());
/*  69 */     ((GuildMusicManager)this.mmusicManager.get(Long.valueOf(gid))).clear();
/*     */   }
/*     */   
/*     */   public void addTrack(Guild guild, AudioTrack track) {
/*  73 */     GuildMusicManager manager = getGuildMusicManager(guild);
/*  74 */     manager.registerTrack(track);
/*     */   }
/*     */   
/*     */   public void addTracks(Guild guild, List<AudioTrack> trackslist) {
/*  78 */     GuildMusicManager manager = getGuildMusicManager(guild);
/*  79 */     manager.registerTracks(trackslist);
/*     */   }
/*     */   
/*     */   public TextChannel getChannel(Guild guild) {
/*  83 */     return (TextChannel)this.mtChannel.get(Long.valueOf(Long.parseLong(guild.getId())));
/*     */   }
/*     */   
/*     */   public boolean exist(Guild guild) {
/*  87 */     long gid = Long.parseLong(guild.getId());
/*  88 */     return this.mtChannel.containsKey(Long.valueOf(gid));
/*     */   }
/*     */   
/*     */   public void setVolume(Guild guild, int volume) {
/*  92 */     long gid = Long.parseLong(guild.getId());
/*  93 */     GuildMusicManager gman = (GuildMusicManager)this.mmusicManager.get(Long.valueOf(gid));
/*  94 */     if (gman.getVolume() != volume) {
/*  95 */       gman.setVolume(volume);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getVolume(Guild guild) {
/* 100 */     long gid = Long.parseLong(guild.getId());
/* 101 */     return ((GuildMusicManager)this.mmusicManager.get(Long.valueOf(gid))).getVolume();
/*     */   }
/*     */   
/*     */   public void SkipMusic(Guild guild) throws AudioException {
/* 105 */     GuildMusicManager musicManager = getGuildAudioPlayer(guild);
/* 106 */     musicManager.nextTrack();
/*     */   }
/*     */   
/*     */   public GuildMusicManager getGuildMusicManager(Guild guild) {
/* 110 */     return getGuildAudioPlayer(guild);
/*     */   }
/*     */   
/*     */   public void Pause(Guild guild, boolean value) {
/* 114 */     long gid = Long.parseLong(guild.getId());
/* 115 */     ((GuildMusicManager)this.mmusicManager.get(Long.valueOf(gid))).getPlayer().setPaused(value);
/*     */   }
/*     */   
/*     */   public void Start(Guild guild, TextChannel channel) {
/* 119 */     Start(guild, channel, 1);
/*     */   }
/*     */   
/*     */   public boolean hasMusic(Guild guild) {
/* 123 */     long gid = Long.parseLong(guild.getId());
/* 124 */     return ((GuildMusicManager)this.mmusicManager.get(Long.valueOf(gid))).hasMusic();
/*     */   }
/*     */   
/*     */   public void Start(Guild guild, TextChannel channel, int request) {
/* 128 */     long gid = Long.parseLong(guild.getId());
/* 129 */     GuildMusicManager manager = getGuildMusicManager(guild);
/* 130 */     if (manager != null) {
/* 131 */       if (!((GuildMusicManager)this.mmusicManager.get(Long.valueOf(gid))).hasMusic()) {
/* 132 */         channel.sendMessage(embedMessage("No music found", Color.RED)).queue();
/*     */       } else {
/*     */         try {
/* 135 */           int quanty = manager.musicSize();
/* 136 */           if (quanty >= request) {
/* 137 */             this.mtChannel.put(Long.valueOf(gid), channel);
/* 138 */             manager.setTextChannel(channel);
/* 139 */             manager.queue(request);
/*     */           } else {
/* 141 */             channel.sendMessage("Music (" + request + ") not found").queue();
/*     */           }
/*     */         } catch (Exception e) {
/* 144 */           channel.sendMessage(embedMessage(":x: Error: " + e.getMessage(), Color.RED));
/*     */         }
/*     */       }
/*     */     } else {
/* 148 */       channel.sendMessage(embedMessage(":x: Error on load manager", Color.RED)).queue();
/*     */     }
/*     */   }
/*     */   
/*     */   public void Stop(Guild guild) {
/* 153 */     getGuildAudioPlayer(guild).stopTrack();
/*     */   }
/*     */   
/*     */   public void leaveChannel(Guild guild) {
/* 157 */     AudioManager audioManager = guild.getAudioManager();
/* 158 */     if (audioManager.isConnected()) {
/* 159 */       audioManager.closeAudioConnection();
/*     */     }
/*     */   }
/*     */   
/*     */   public void joinVoiceChannel(Guild guild, VoiceChannel voiceChannel) {
/* 164 */     AudioManager audioManager = guild.getAudioManager();
/* 165 */     if ((!audioManager.isConnected()) && (!audioManager.isAttemptingToConnect())) {
/* 166 */       audioManager.openAudioConnection(voiceChannel);
/*     */     }
/*     */   }
/*     */   
/*     */   public AudioTrack nowPlaying(Guild guild) {
/* 171 */     return getGuildMusicManager(guild).getPlayingTrack();
/*     */   }
/*     */   
/*     */   public void addItems(final Guild guild, String trackUrl) {
/* 175 */     GuildMusicManager musicManager = getGuildAudioPlayer(guild);
/*     */     
/* 177 */     this.playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
/*     */       public void trackLoaded(AudioTrack track) {
/* 179 */         MusicManager.this.addTrack(guild, track);
/*     */       }
/*     */       
/*     */       public void playlistLoaded(AudioPlaylist playlist) {
/* 183 */         AudioTrack firstTrack = playlist.getSelectedTrack();
/* 184 */         if (firstTrack == null) {
/* 185 */           firstTrack = (AudioTrack)playlist.getTracks().get(0);
/*     */         }
/* 187 */         List<AudioTrack> track = playlist.getTracks();
/* 188 */         MusicManager.this.addTracks(guild, track);
/*     */       }
/*     */       
/*     */ 
/*     */       public void noMatches() {}
/*     */       
/*     */       public void loadFailed(FriendlyException exception) {}
/*     */     });
/*     */   }
/*     */   
/*     */   public void addItems(final Guild guild, final TextChannel channel, final String trackUrl)
/*     */   {
/* 200 */     final GuildMusicManager musicManager = getGuildAudioPlayer(guild);
/*     */     
/* 202 */     this.playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
/*     */       public void trackLoaded(AudioTrack track) {
/* 204 */         if (YuzukuBot.guildManager.autoSaveMusic(guild)) {
/* 205 */           YuzukuBot.guildManager.addMusic(guild, track);
/*     */         }
/* 207 */         if (!musicManager.containsAudioTrack(track)) {
/* 208 */           MusicManager.this.addTrack(guild, track);
/* 209 */           channel.sendMessage(MusicManager.this.embedMessage("Added music (" + track.getInfo().title + ") musics to the list)", Color.RED)).queue();
/*     */         } else {
/* 211 */           channel.sendMessage(MusicManager.this.embedMessage("This music has already been added", Color.RED)).queue();
/*     */         }
/*     */       }
/*     */       
/*     */       public void playlistLoaded(AudioPlaylist playlist) {
/* 216 */         List<AudioTrack> track = playlist.getTracks();
/* 217 */         if (YuzukuBot.guildManager.autoSaveMusic(guild)) {
/* 218 */           YuzukuBot.guildManager.addList(guild, playlist);
/*     */         }
/* 220 */         MusicManager.this.addTracks(guild, track);
/* 221 */         channel.sendMessage(MusicManager.this.embedMessage("added (" + playlist
/* 222 */           .getTracks().size() + ") musics From playlist (" + playlist.getName() + ")", Color.YELLOW))
/* 223 */           .queue();
/*     */       }
/*     */       
/*     */       public void noMatches() {
/* 227 */         channel.sendMessage("Nothing found by " + trackUrl).queue();
/*     */       }
/*     */       
/*     */       public void loadFailed(FriendlyException exception) {
/* 231 */         channel.sendMessage("Could not add: " + exception.getMessage()).queue();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public boolean isPaused(Guild guild) {
/* 237 */     return getGuildMusicManager(guild).getPlayer().isPaused();
/*     */   }
/*     */   
/*     */   public boolean hasConnection(Guild guild) {
/* 241 */     return guild.getAudioManager().getConnectedChannel() != null;
/*     */   }
/*     */   
/*     */   public void startCode(Guild guild) {
/* 245 */     getGuildAudioPlayer(guild);
/*     */   }
/*     */   
/*     */   public boolean isStarted(Guild guild) {
/* 249 */     return exist(guild);
/*     */   }
/*     */   
/*     */   private synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
/* 253 */     long guildId = Long.parseLong(guild.getId());
/* 254 */     GuildMusicManager musicManager = (GuildMusicManager)this.mmusicManager.get(Long.valueOf(guildId));
/*     */     
/* 256 */     if (musicManager == null) {
/* 257 */       musicManager = new GuildMusicManager(this.playerManager);
/* 258 */       this.mmusicManager.put(Long.valueOf(guildId), musicManager);
/*     */     }
/*     */     
/* 261 */     guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
/*     */     
/* 263 */     return musicManager;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void closeConnection(Guild guild) {
/* 268 */     long guildId = Long.parseLong(guild.getId());
/* 269 */     guild.getAudioManager().closeAudioConnection();
/* 270 */     this.mtChannel.remove(Long.valueOf(guildId));
/* 271 */     getGuildAudioPlayer(guild).reset();
/* 272 */     YuzukuBot.guildManager.registerTracks();
/*     */   }
/*     */   
/*     */   private Message embedMessage(String text, Color color) {
/* 276 */     EmbedManager emb = new EmbedManager();
/* 277 */     emb.setDescription(text);
/* 278 */     emb.setColor(color);
/* 279 */     return emb.getMessage();
/*     */   }
/*     */   
/*     */   public final void removeChannel(TextChannel channel) {
/* 283 */     long guildId = Long.parseLong(channel.getGuild().getId());
/* 284 */     this.mtChannel.remove(Long.valueOf(guildId));
/*     */   }
/*     */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\audio\MusicManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */