/*    */ package net.sydrus.yuzuku.Managers.ConsoleCommand.Commands;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.Collection;
/*    */ import net.dv8tion.jda.core.JDA;
/*    */ import net.dv8tion.jda.core.entities.TextChannel;
/*    */ import net.dv8tion.jda.core.requests.RestAction;
/*    */ import net.sydrus.yuzuku.Constructors.ConsoleCommand;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.ConsoleDef;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;
/*    */ import net.sydrus.yuzuku.Managers.EmbedManager;
/*    */ import net.sydrus.yuzuku.PLugin.Manager.Plugin;
/*    */ import net.sydrus.yuzuku.PLugin.Manager.PluginManager;
/*    */ import net.sydrus.yuzuku.YuzukuBot;
/*    */ import net.sydrus.yuzuku.audio.MusicManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class stop
/*    */   extends ConsoleCommand
/*    */ {
/*    */   public boolean onCommand(ConsoleDef console, String command, String time, String[] args)
/*    */   {
/* 37 */     new Thread(new Runnable()
/*    */     {
/*    */       public void run()
/*    */       {
/* 20 */         EmbedManager emb = new EmbedManager();
/* 21 */         emb.setColor(Color.RED);
/* 22 */         emb.setDescription("**I'm sorry but I'm hanging up**");
/* 23 */         for (Plugin plugin : YuzukuBot.getAddonsManager().addons()) {
/* 24 */           plugin.Disable();
/*    */         }
/* 26 */         for (TextChannel ch : YuzukuBot.MusicManager.getMusicChannels()) {
/*    */           try {
/* 28 */             ch.sendMessage(emb.getMessage()).queue();
/*    */           }
/*    */           catch (Exception localException) {}
/*    */         }
/* 32 */         Type.messageType(Type.MessageType.Warning, YuzukuBot.MusicManager.getMusicChannels().size() + " **Audio channels were turned off**");
/* 33 */         Type.messageType(Type.MessageType.Info, "Stopped");
/* 34 */         YuzukuBot.getInstance().getJDA().shutdown();
/* 35 */         System.exit(-1);
/*    */       }
/* 37 */     }).start();
/* 38 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\ConsoleCommand\Commands\stop.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */