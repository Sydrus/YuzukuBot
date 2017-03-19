/*    */ package net.sydrus.yuzuku.Managers;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.time.temporal.TemporalAccessor;
/*    */ import net.dv8tion.jda.core.EmbedBuilder;
/*    */ import net.dv8tion.jda.core.MessageBuilder;
/*    */ import net.dv8tion.jda.core.entities.Message;
/*    */ import net.dv8tion.jda.core.entities.MessageEmbed;
/*    */ import net.dv8tion.jda.core.entities.MessageEmbed.Field;
/*    */ 
/*    */ 
/*    */ public class EmbedManager
/*    */ {
/* 14 */   private EmbedBuilder builder = null;
/*    */   
/*    */   public EmbedManager() {
/* 17 */     this.builder = new EmbedBuilder();
/*    */   }
/*    */   
/*    */   public EmbedManager(MessageEmbed embed) {
/* 21 */     this.builder = new EmbedBuilder(embed);
/*    */   }
/*    */   
/*    */   public EmbedBuilder addBlankField(boolean inline) {
/* 25 */     return this.builder.addBlankField(inline);
/*    */   }
/*    */   
/*    */   public EmbedBuilder addField(MessageEmbed.Field field) {
/* 29 */     return this.builder.addField(field);
/*    */   }
/*    */   
/*    */   public EmbedBuilder addField(String name, String value, boolean inline) {
/* 33 */     return this.builder.addField(name, value, inline);
/*    */   }
/*    */   
/*    */   public EmbedBuilder setAuthor(String name, String url, String iconUrl) {
/* 37 */     return this.builder.setAuthor(name, url, iconUrl);
/*    */   }
/*    */   
/*    */   public EmbedBuilder setColor(Color color) {
/* 41 */     return this.builder.setColor(color);
/*    */   }
/*    */   
/*    */   public EmbedBuilder setDescription(String description) {
/* 45 */     return this.builder.setDescription(description);
/*    */   }
/*    */   
/*    */   public EmbedBuilder setFooter(String text, String iconUrl) {
/* 49 */     return this.builder.setFooter(text, iconUrl);
/*    */   }
/*    */   
/*    */   public EmbedBuilder setImage(String url) {
/* 53 */     return this.builder.setImage(url);
/*    */   }
/*    */   
/*    */   public EmbedBuilder setProvider(String name, String url) {
/* 57 */     return this.builder.setProvider(name, url);
/*    */   }
/*    */   
/*    */   public EmbedBuilder setThumbail(String url) {
/* 61 */     return this.builder.setThumbnail(url);
/*    */   }
/*    */   
/*    */   public EmbedBuilder setTimeStamp(TemporalAccessor temporal) {
/* 65 */     return this.builder.setTimestamp(temporal);
/*    */   }
/*    */   
/*    */   public EmbedBuilder setTitle(String title) {
/* 69 */     return this.builder.setTitle(title);
/*    */   }
/*    */   
/*    */   public EmbedBuilder setUrl(String url) {
/* 73 */     return this.builder.setUrl(url);
/*    */   }
/*    */   
/*    */   public EmbedBuilder setVideo(String url) {
/* 77 */     return this.builder.setVideo(url);
/*    */   }
/*    */   
/*    */   public void baiscEmbed(Color color, String title, String description, String footer, String footerIcon) {
/* 81 */     setColor(color);
/* 82 */     setTitle(title);
/* 83 */     setDescription(description);
/* 84 */     setFooter(footer, footerIcon);
/*    */   }
/*    */   
/*    */   public MessageEmbed getMessageEmbed() {
/* 88 */     return this.builder.build();
/*    */   }
/*    */   
/*    */   public MessageBuilder getMessageBuilder() {
/* 92 */     MessageBuilder mbuilder = new MessageBuilder();
/* 93 */     mbuilder.setEmbed(getMessageEmbed());
/* 94 */     return mbuilder;
/*    */   }
/*    */   
/*    */   public Message getMessage() {
/* 98 */     return getMessageBuilder().build();
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\EmbedManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */