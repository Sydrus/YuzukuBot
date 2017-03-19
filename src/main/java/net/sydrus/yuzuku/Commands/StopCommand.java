/*    */ package net.sydrus.yuzuku.Commands;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import net.dv8tion.jda.core.JDA;
/*    */ import net.dv8tion.jda.core.entities.Guild;
/*    */ import net.dv8tion.jda.core.entities.Message;
/*    */ import net.dv8tion.jda.core.entities.TextChannel;
/*    */ import net.dv8tion.jda.core.entities.User;
/*    */ import net.dv8tion.jda.core.requests.RestAction;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;
/*    */ import net.sydrus.yuzuku.Managers.LevelType;
/*    */ import net.sydrus.yuzuku.PLugin.Manager.Plugin;
/*    */ import net.sydrus.yuzuku.String.Administratives;
/*    */ import net.sydrus.yuzuku.YuzukuBot;
/*    */ import net.sydrus.yuzuku.audio.MusicManager;
/*    */ 
/*    */ public class StopCommand extends net.sydrus.yuzuku.Constructors.Command
/*    */ {
/*    */   public boolean onCommand(User Sender, Message Message, Guild Guild, final TextChannel Chat, List<LevelType> type, Boolean isEdited, String[] args)
/*    */   {
/* 24 */     if (type.contains(LevelType.Developer))
/*    */     {
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
/*    */ 
/* 42 */       new Thread(new Runnable()
/*    */       {
/*    */         public void run()
/*    */         {
/* 27 */           for (Plugin plugin : YuzukuBot.getAddonsManager().addons()) {
/* 28 */             plugin.Disable();
/*    */           }
/* 30 */           for (TextChannel ch : YuzukuBot.MusicManager.getMusicChannels()) {
/*    */             try {
/* 32 */               ch.sendMessage(StopCommand.this.embedMessage("**I'm sorry but I'm hanging up**", Color.RED)).queue();
/*    */             }
/*    */             catch (Exception localException) {}
/*    */           }
/* 36 */           Type.messageType(Type.MessageType.Warning, YuzukuBot.MusicManager.getMusicChannels().size() + " **Audio channels were turned off**");
/* 37 */           Type.messageType(Type.MessageType.Info, "Stopped");
/* 38 */           Chat.sendMessage(StopCommand.this.embedMessage("Stopping...", Color.YELLOW)).queue();
/* 39 */           YuzukuBot.getInstance().getJDA().shutdown();
/* 40 */           System.exit(-1);
/*    */         }
/*    */       })
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
/*    */ 
/* 42 */         .start();
/*    */     } else {
/* 44 */       Chat.sendMessage(embedMessage(Administratives.DeveloperOnly)).queue();
/*    */     }
/* 46 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Commands\StopCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */