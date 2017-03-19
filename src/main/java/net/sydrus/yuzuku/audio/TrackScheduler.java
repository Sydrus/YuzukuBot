/*     */ package net.sydrus.yuzuku.audio;
/*     */ 
/*     */ import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
/*     */ import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
/*     */ import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
/*     */ import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
/*     */ import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.dv8tion.jda.core.entities.TextChannel;
/*     */ import net.dv8tion.jda.core.requests.RestAction;
/*     */ import net.sydrus.yuzuku.Managers.EmbedManager;
/*     */ import net.sydrus.yuzuku.Managers.GuildManager;
/*     */ import net.sydrus.yuzuku.YuzukuBot;
/*     */ import net.sydrus.yuzuku.exceptions.OutOfRangeException;
/*     */ 
/*     */ public class TrackScheduler
/*     */   extends AudioEventAdapter
/*     */ {
/*     */   private AudioPlayer player;
/*  23 */   private List<AudioTrack> queue = new ArrayList();
/*  24 */   private List<String> trackId = new ArrayList();
/*  25 */   private TextChannel channel = null;
/*  26 */   private int AtualInQueue = 0;
/*  27 */   private boolean isPlaying = false;
/*  28 */   private int VOLUME = 100;
/*  29 */   private int NEXT_QUEUE = 0;
/*     */   
/*     */   public void reset()
/*     */   {
/*  33 */     this.player.stopTrack();
/*  34 */     this.queue = null;
/*  35 */     this.trackId = null;
/*  36 */     this.channel = null;
/*  37 */     this.isPlaying = false;
/*     */   }
/*     */   
/*     */   final void setVolume(int volume) {
/*  41 */     this.player.setVolume(volume);
/*  42 */     this.VOLUME = volume;
/*     */   }
/*     */   
/*     */   final int getVolume() {
/*  46 */     return this.VOLUME;
/*     */   }
/*     */   
/*     */   public boolean isPlaying() {
/*  50 */     return this.isPlaying;
/*     */   }
/*     */   
/*     */   public List<AudioTrack> getTrucks() {
/*  54 */     return this.queue;
/*     */   }
/*     */   
/*     */   public void clear()
/*     */   {
/*  59 */     this.queue.clear();
/*  60 */     this.trackId.clear();
/*     */   }
/*     */   
/*     */   public void shuffleTracks() {
/*  64 */     Collections.shuffle(this.queue);
/*     */   }
/*     */   
/*     */   public int getAtualQueue() {
/*  68 */     return this.AtualInQueue;
/*     */   }
/*     */   
/*     */   public TrackScheduler(AudioPlayer player) {
/*  72 */     this.player = player;
/*     */   }
/*     */   
/*     */   public void setNextQueue(int queue) throws OutOfRangeException
/*     */   {
/*  77 */     if ((queue <= 0) || (queue > this.queue.size())) {
/*  78 */       throw new OutOfRangeException(this.queue.size(), queue);
/*     */     }
/*  80 */     this.NEXT_QUEUE = queue;
/*     */   }
/*     */   
/*     */   public boolean hasNextTrack() {
/*  84 */     return (this.queue.size() > this.AtualInQueue) && (this.queue.size() > 0);
/*     */   }
/*     */   
/*     */   public int nextQueue() {
/*  88 */     return this.AtualInQueue + 1;
/*     */   }
/*     */   
/*     */   public int getAudioTrackQuanty() {
/*  92 */     return this.queue.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void registerTracks(final List<AudioTrack> tracks)
/*     */   {
/* 103 */     new Thread(new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/*  98 */         for (AudioTrack track : tracks) {
/*  99 */           TrackScheduler.this.queue.add(track);
/* 100 */           TrackScheduler.this.trackId.add(track.getIdentifier());
/*     */         }
/*     */       }
/*     */     })
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 103 */       .start();
/*     */   }
/*     */   
/*     */   public void registerTrack(AudioTrack track) {
/* 107 */     this.trackId.add(track.getIdentifier());
/* 108 */     this.queue.add(track);
/*     */   }
/*     */   
/*     */   public void setTextChannel(TextChannel tchannel)
/*     */   {
/* 113 */     this.channel = tchannel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void queue()
/*     */   {
/* 120 */     if (this.queue.size() >= 1) {
/* 121 */       this.player.startTrack((AudioTrack)this.queue.get(0), true);
/* 122 */       this.AtualInQueue = 0;
/* 123 */       this.isPlaying = true;
/*     */     }
/*     */   }
/*     */   
/*     */   public void queue(int number) throws Exception
/*     */   {
/* 129 */     int tq = number;
/* 130 */     if (number - 1 >= 0) {
/* 131 */       tq--;
/*     */     }
/* 133 */     if (this.queue.size() >= 1) {
/* 134 */       if (number <= this.queue.size()) {
/* 135 */         this.player.startTrack((AudioTrack)this.queue.get(tq), true);
/* 136 */         this.AtualInQueue = number;
/* 137 */         this.isPlaying = true;
/*     */       } else {
/* 139 */         throw new Exception("The specified number is greater than the number of songs in the playlist", new Throwable(number + ""));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public AudioPlayer getPlayer()
/*     */   {
/* 147 */     return this.player;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void nextTrack()
/*     */     throws AudioException
/*     */   {
/* 157 */     this.AtualInQueue += 1;
/* 158 */     if (this.queue.size() >= this.AtualInQueue) {
/* 159 */       this.player.stopTrack();
/*     */       try {
/* 161 */         this.player.stopTrack();
/* 162 */         queue(this.AtualInQueue);
/*     */       } catch (Exception e) {
/* 164 */         throw new AudioException("Playlist Finished", AudioException.ExceptionType.IsWarning);
/*     */       }
/*     */     } else {
/* 167 */       throw new AudioException("Playlist Finished", AudioException.ExceptionType.IsWarning);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void onTrackStart(AudioPlayer player, AudioTrack track)
/*     */   {
/* 174 */     if (YuzukuBot.guildManager.getVolume(this.channel.getGuild()) != this.VOLUME) {
/* 175 */       this.VOLUME = YuzukuBot.guildManager.getVolume(this.channel.getGuild());
/* 176 */       player.setVolume(this.VOLUME);
/*     */     }
/*     */   }
/*     */   
/*     */   public AudioTrack getNextTrack() {
/* 181 */     if ((hasNextTrack()) && 
/* 182 */       (nextQueue() - 1 <= this.queue.size())) {
/* 183 */       return (AudioTrack)this.queue.get(nextQueue() - 1);
/*     */     }
/*     */     
/* 186 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason)
/*     */   {
/* 194 */     this.isPlaying = false;
/* 195 */     if (endReason.mayStartNext) {
/*     */       try {
/* 197 */         if (this.NEXT_QUEUE == 0) {
/* 198 */           nextTrack();
/*     */         } else {
/* 200 */           queue(this.NEXT_QUEUE);
/* 201 */           this.NEXT_QUEUE = 0;
/*     */         }
/* 203 */         AudioTrackInfo info = track.getInfo();
/* 204 */         EmbedManager manager = new EmbedManager();
/* 205 */         manager.setColor(Color.GREEN);
/* 206 */         manager.addField("Playing Now", info.title, true);
/* 207 */         manager.addField("Durations", track.getDuration() + "", true);
/* 208 */         if (hasNextTrack()) {
/* 209 */           manager.addField("Next", getNextTrack().getInfo().title, false);
/*     */         }
/* 211 */         manager.addField("Position in queue", getAtualQueue() + "/" + getAudioTrackQuanty(), true);
/* 212 */         if (hasNextTrack()) {
/* 213 */           manager.addField("Next position in queue", nextQueue() + "/" + getAudioTrackQuanty(), true);
/*     */         }
/* 215 */         this.channel.sendMessage(manager.getMessage()).queue();
/*     */       } catch (AudioException e) {
/* 217 */         if (e.getExceptionType() == AudioException.ExceptionType.IsWarning) {
/* 218 */           EmbedManager emb = new EmbedManager();
/* 219 */           emb.setDescription("Playlist Finished");
/* 220 */           emb.setColor(Color.YELLOW);
/* 221 */           this.channel.sendMessage(emb.getMessage()).queue();
/* 222 */         } else if (e.getExceptionType() == AudioException.ExceptionType.isFatal) {
/* 223 */           EmbedManager emb = new EmbedManager();
/* 224 */           emb.setDescription(e.getMessage());
/* 225 */           emb.setColor(Color.RED);
/* 226 */           this.channel.sendMessage(emb.getMessage()).queue();
/* 227 */         } else if (e.getExceptionType() == AudioException.ExceptionType.isError) {
/* 228 */           EmbedManager emb = new EmbedManager();
/* 229 */           emb.setDescription("[Error] " + e.getMessage());
/* 230 */           emb.setColor(Color.RED);
/* 231 */           this.channel.sendMessage(emb.getMessage()).queue();
/*     */         }
/*     */       } catch (Exception e) {
/* 234 */         EmbedManager emb = new EmbedManager();
/* 235 */         emb.setDescription("[Error] " + e.getMessage());
/* 236 */         emb.setColor(Color.RED);
/* 237 */         this.channel.sendMessage(emb.getMessage()).queue();
/*     */       }
/*     */     } else {
/* 240 */       EmbedManager manager = new EmbedManager();
/* 241 */       manager.setColor(Color.YELLOW);
/* 242 */       manager.setDescription("Playlist ended");
/* 243 */       this.channel.sendMessage(manager.getMessage()).queue();
/* 244 */       YuzukuBot.MusicManager.removeChannel(this.channel);
/* 245 */       this.channel = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean containsAudioTrack(AudioTrack track) {
/* 250 */     return this.trackId.contains(track.getIdentifier());
/*     */   }
/*     */   
/*     */   public AudioTrack getSettedNextQueue() {
/* 254 */     int tq = this.NEXT_QUEUE;
/* 255 */     if (this.NEXT_QUEUE - 1 >= 0) {
/* 256 */       tq--;
/*     */     }
/* 258 */     return (AudioTrack)this.queue.get(tq);
/*     */   }
/*     */   
/*     */   public int getSettedNextQueueInt() {
/* 262 */     return this.NEXT_QUEUE;
/*     */   }
/*     */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\audio\TrackScheduler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */