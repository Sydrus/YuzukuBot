/*    */ package net.sydrus.yuzuku.audio;
/*    */ 
/*    */ import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
/*    */ 
/*    */ public class AudioData
/*    */ {
/*  7 */   private String AUDIOSOURCE = "";
/*  8 */   private String AUDIOIDENTIFIER = "";
/*    */   
/*    */   public void ofString(String audio) {
/* 11 */     if ((audio.startsWith("AudioData(Source{")) && (audio.endsWith("})"))) {
/* 12 */       this.AUDIOSOURCE = audio.substring(17, audio.lastIndexOf("},Identifier{"));
/* 13 */       this.AUDIOIDENTIFIER = audio.substring(audio.lastIndexOf("},Identifier{") + 13, audio.lastIndexOf("})"));
/* 14 */       System.out.println(this.AUDIOSOURCE);
/* 15 */       System.out.println(this.AUDIOIDENTIFIER);
/*    */     } else {
/* 17 */       throw new RuntimeException("Invalid Audio");
/*    */     }
/*    */   }
/*    */   
/*    */   public void ofAudioTrack(AudioTrack track) {
/* 22 */     this.AUDIOSOURCE = track.getSourceManager().getSourceName();
/* 23 */     this.AUDIOIDENTIFIER = track.getIdentifier();
/*    */   }
/*    */   
/*    */   public String getAudioSource() {
/* 27 */     return this.AUDIOSOURCE;
/*    */   }
/*    */   
/*    */   public String getAudioIdentifier() {
/* 31 */     return this.AUDIOIDENTIFIER;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 36 */     return "AudioData(Source{" + this.AUDIOSOURCE + "},Identifier{" + this.AUDIOIDENTIFIER + "})";
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\audio\AudioData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */