/*    */ package net.sydrus.yuzuku.Commands;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.List;
/*    */ import net.dv8tion.jda.core.JDA;
/*    */ import net.dv8tion.jda.core.entities.Game;
/*    */ import net.dv8tion.jda.core.entities.Guild;
/*    */ import net.dv8tion.jda.core.entities.Message;
/*    */ import net.dv8tion.jda.core.entities.TextChannel;
/*    */ import net.dv8tion.jda.core.entities.User;
/*    */ import net.dv8tion.jda.core.requests.RestAction;
/*    */ import net.sydrus.yuzuku.Constructors.Command;
/*    */ import net.sydrus.yuzuku.Managers.LevelType;
/*    */ import net.sydrus.yuzuku.String.Administratives;
/*    */ import net.sydrus.yuzuku.YuzukuBot;
/*    */ 
/*    */ public class setgame extends Command
/*    */ {
/*    */   public boolean onCommand(User Sender, Message Message, Guild Guild, TextChannel Chat, List<LevelType> type, Boolean isEdited, String[] args)
/*    */   {
/* 21 */     if (!type.contains(LevelType.Developer)) {
/* 22 */       Chat.sendMessage(embedMessage(Administratives.AdminOnly, Color.RED)).queue();
/*    */       
/* 24 */       return true;
/*    */     }
/* 26 */     if (args.length == 0) {
/* 27 */       Chat.sendMessage(embedMessage("Game", Color.YELLOW)).queue();
/* 28 */     } else if (args.length > 0) {
/* 29 */       YuzukuBot.getInstance().getJDA().getPresence().setGame(Game.of(argsToString(args, Integer.valueOf(0))));
/* 30 */       Chat.sendMessage(embedMessage("**Now Playing:** " + argsToString(args, Integer.valueOf(0)), Color.CYAN)).queue();
/*    */     }
/* 32 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Commands\setgame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */