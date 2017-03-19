/*    */ package net.sydrus.yuzuku.Managers.ConsoleCommand.Commands;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import net.dv8tion.jda.core.JDA;
/*    */ import net.dv8tion.jda.core.entities.Guild;
/*    */ import net.dv8tion.jda.core.entities.TextChannel;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
/*    */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;
/*    */ import net.sydrus.yuzuku.YuzukuBot;
/*    */ 
/*    */ public class list extends net.sydrus.yuzuku.Constructors.ConsoleCommand
/*    */ {
/*    */   public boolean onCommand(net.sydrus.yuzuku.Managers.ConsoleCommand.ConsoleDef console, String command, String time, String[] args)
/*    */   {
/*    */     Iterator localIterator;
/*    */     Guild item;
/* 19 */     if ((args.length == 1) && (args[0].equalsIgnoreCase("server"))) {
/* 20 */       if (YuzukuBot.getInstance().getJDA().getGuilds().isEmpty()) {
/* 21 */         Type.messageType(Type.MessageType.Warning, "No server available");
/* 22 */         return true;
/*    */       }
/* 24 */       System.out.println("");
/* 25 */       Type.messageType(Type.MessageType.Info, "===Servers===");
/* 26 */       for (localIterator = YuzukuBot.getInstance().getJDA().getGuilds().iterator(); localIterator.hasNext();) { item = (Guild)localIterator.next();
/* 27 */         Type.messageType(Type.MessageType.Info, "Name: " + item.getName() + " id(" + item.getId() + ") ");
/*    */       }
/* 29 */     } else if ((args.length == 2) && (args[0].equalsIgnoreCase("tchannel"))) {
/*    */       try {
/* 31 */         Object channels = YuzukuBot.getInstance().getJDA().getGuildById(args[1]).getTextChannels();
/* 32 */         System.out.println("");
/* 33 */         Type.messageType(Type.MessageType.Info, "===Text Channels of " + YuzukuBot.getInstance().getJDA().getGuildById(args[1]).getName() + "===");
/* 34 */         for (TextChannel channel : (List)channels) {
/* 35 */           Type.messageType(Type.MessageType.Info, "Name: " + channel.getName() + " id(" + channel.getId() + ") ");
/*    */         }
/*    */       } catch (Exception e) {
/* 38 */         Type.messageType(Type.MessageType.Error, "Uncknow server");
/*    */       }
/* 40 */     } else if ((args.length == 2) && (args[0].equalsIgnoreCase("vchannel"))) {
/*    */       try {
/* 42 */         Object channels = YuzukuBot.getInstance().getJDA().getGuildById(args[1]).getVoiceChannels();
/* 43 */         System.out.println("");
/* 44 */         Type.messageType(Type.MessageType.Info, "=== Text Channels of " + YuzukuBot.getInstance().getJDA().getGuildById(args[1]).getName() + "===");
/* 45 */         for (net.dv8tion.jda.core.entities.VoiceChannel channel : (List)channels) {
/* 46 */           Type.messageType(Type.MessageType.Info, "Name: " + channel.getName() + " id(" + channel.getId() + ") ");
/*    */         }
/*    */       } catch (Exception e) {
/* 49 */         Type.messageType(Type.MessageType.Error, "Uncknow server");
/*    */       }
/* 51 */     } else if ((args.length == 2) && (args[0].equalsIgnoreCase("users"))) {
/*    */       try {
/* 53 */         Object users = YuzukuBot.getInstance().getJDA().getGuildById(args[1]).getMembers();
/* 54 */         System.out.println("");
/* 55 */         Type.messageType(Type.MessageType.Info, "=== Text Channels of " + YuzukuBot.getInstance().getJDA().getGuildById(args[1]).getName() + "===");
/* 56 */         for (net.dv8tion.jda.core.entities.Member user : (List)users) {
/* 57 */           Type.messageType(Type.MessageType.Info, "Name: " + user.getUser().getName() + " id(" + user.getUser().getId() + ") ");
/*    */         }
/*    */       } catch (Exception e) {
/* 60 */         Type.messageType(Type.MessageType.Error, "Uncknow server");
/*    */       }
/*    */     } else {
/* 63 */       Type.messageType(Type.MessageType.Error, "Type \"server\", \"tchannel serverId\", \"vchannel serverId\", \"users serverId\"");
/*    */     }
/*    */     
/* 66 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\ConsoleCommand\Commands\list.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */