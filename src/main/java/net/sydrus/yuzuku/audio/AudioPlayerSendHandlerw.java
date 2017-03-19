/*    */ package net.sydrus.yuzuku.audio;
/*    */ 
/*    */ import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
/*    */ import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
/*    */ import net.dv8tion.jda.core.audio.AudioSendHandler;
/*    */ 
/*    */ public class AudioPlayerSendHandlerw implements AudioSendHandler
/*    */ {
/*    */   private final AudioPlayer audioPlayer;
/*    */   private AudioFrame lastFrame;
/*    */   
/*    */   public AudioPlayerSendHandlerw(AudioPlayer audioPlayer)
/*    */   {
/* 14 */     this.audioPlayer = audioPlayer;
/*    */   }
/*    */   
/*    */   public boolean canProvide()
/*    */   {
/* 19 */     if (this.lastFrame == null) {
/* 20 */       this.lastFrame = this.audioPlayer.provide();
/*    */     }
/* 22 */     return this.lastFrame != null;
/*    */   }
/*    */   
/*    */   public byte[] provide20MsAudio()
/*    */   {
/* 27 */     if (this.lastFrame == null) {
/* 28 */       this.lastFrame = this.audioPlayer.provide();
/*    */     }
/* 30 */     byte[] data = this.lastFrame != null ? this.lastFrame.data : null;
/* 31 */     this.lastFrame = null;
/* 32 */     return data;
/*    */   }
/*    */   
/*    */   public boolean isOpus()
/*    */   {
/* 37 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\audio\AudioPlayerSendHandlerw.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */