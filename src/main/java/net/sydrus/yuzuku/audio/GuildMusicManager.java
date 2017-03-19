/*     */ package net.sydrus.yuzuku.audio;
/*     */ 
/*     */ import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
/*     */ import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
/*     */ import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
/*     */ import java.util.List;
/*     */ import net.dv8tion.jda.core.entities.TextChannel;
/*     */ import net.sydrus.yuzuku.exceptions.OutOfRangeException;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuildMusicManager
/*     */ {
/*     */   public TrackScheduler scheduler;
/*  15 */   private AudioPlayerManager man = null;
/*     */   
/*     */   public GuildMusicManager(AudioPlayerManager manager) {
/*  18 */     this.man = manager;
/*  19 */     this.scheduler = new TrackScheduler(manager.createPlayer());
/*  20 */     getPlayer().addListener(this.scheduler);
/*     */   }
/*     */   
/*     */   final int containsTracks(List<AudioTrack> tracks) {
/*  24 */     int ToReturn = 0;
/*  25 */     for (AudioTrack track : tracks) {
/*  26 */       if (containsAudioTrack(track)) {
/*  27 */         ToReturn++;
/*     */       }
/*     */     }
/*  30 */     return ToReturn;
/*     */   }
/*     */   
/*     */   final void setVolume(int volume) {
/*  34 */     this.scheduler.setVolume(volume);
/*     */   }
/*     */   
/*     */   final int getVolume() {
/*  38 */     return this.scheduler.getVolume();
/*     */   }
/*     */   
/*     */   public boolean isPlaying() {
/*  42 */     return this.scheduler.isPlaying();
/*     */   }
/*     */   
/*     */   public void clear() {
/*  46 */     this.scheduler.clear();
/*     */   }
/*     */   
/*     */   public void shuffleTracks() {
/*  50 */     this.scheduler.shuffleTracks();
/*     */   }
/*     */   
/*     */   public int getAtualQueue() {
/*  54 */     return this.scheduler.getAtualQueue();
/*     */   }
/*     */   
/*     */   public boolean hasNextTrack() {
/*  58 */     return this.scheduler.hasNextTrack();
/*     */   }
/*     */   
/*     */   public int nextQueue() {
/*  62 */     return this.scheduler.nextQueue();
/*     */   }
/*     */   
/*     */   public boolean containsAudioTrack(AudioTrack track) {
/*  66 */     return this.scheduler.containsAudioTrack(track);
/*     */   }
/*     */   
/*     */   public void registerTracks(List<AudioTrack> tracks) {
/*  70 */     this.scheduler.registerTracks(tracks);
/*     */   }
/*     */   
/*     */   public void registerTrack(AudioTrack track) {
/*  74 */     this.scheduler.registerTrack(track);
/*     */   }
/*     */   
/*     */   public void queue() {
/*  78 */     this.scheduler.queue();
/*     */   }
/*     */   
/*     */   public void queue(int number) throws Exception {
/*  82 */     this.scheduler.queue(number);
/*     */   }
/*     */   
/*     */   public AudioPlayer getPlayer() {
/*  86 */     return this.scheduler.getPlayer();
/*     */   }
/*     */   
/*     */   public void nextTrack() throws AudioException {
/*  90 */     this.scheduler.nextTrack();
/*     */   }
/*     */   
/*     */   public AudioPlayerSendHandlerw getSendHandler() {
/*  94 */     return new AudioPlayerSendHandlerw(this.scheduler.getPlayer());
/*     */   }
/*     */   
/*     */   public AudioTrack getPlayingTrack()
/*     */   {
/*  99 */     return this.scheduler.getPlayer().getPlayingTrack();
/*     */   }
/*     */   
/*     */   public void setTextChannel(TextChannel channel) {
/* 103 */     this.scheduler.setTextChannel(channel);
/*     */   }
/*     */   
/*     */   public void reset() {
/* 107 */     this.scheduler.reset();
/* 108 */     getPlayer().removeListener(this.scheduler);
/* 109 */     this.scheduler = new TrackScheduler(this.man.createPlayer());
/* 110 */     getPlayer().addListener(this.scheduler);
/*     */   }
/*     */   
/*     */   public AudioTrack getNextTrack() {
/* 114 */     return this.scheduler.getNextTrack();
/*     */   }
/*     */   
/*     */   public int musicSize() {
/* 118 */     return this.scheduler.getTrucks().size();
/*     */   }
/*     */   
/*     */   public boolean hasMusic() {
/* 122 */     return !this.scheduler.getTrucks().isEmpty();
/*     */   }
/*     */   
/*     */   public void setNextQueue(int queue) throws OutOfRangeException {
/* 126 */     this.scheduler.setNextQueue(queue);
/*     */   }
/*     */   
/*     */   public AudioTrack getSettedNextQueue() {
/* 130 */     return this.scheduler.getSettedNextQueue();
/*     */   }
/*     */   
/*     */   public int getSettedNextQueueInt() {
/* 134 */     return this.scheduler.getSettedNextQueueInt();
/*     */   }
/*     */   
/*     */   public final void stopTrack() {
/* 138 */     this.scheduler.reset();
/*     */   }
/*     */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\audio\GuildMusicManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */