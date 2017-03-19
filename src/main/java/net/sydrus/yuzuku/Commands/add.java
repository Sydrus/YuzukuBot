/*    */ package net.sydrus.yuzuku.Commands;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.dv8tion.jda.core.entities.Guild;
/*    */ import net.dv8tion.jda.core.entities.Message;
/*    */ import net.dv8tion.jda.core.entities.TextChannel;
/*    */ import net.dv8tion.jda.core.entities.User;
/*    */ import net.dv8tion.jda.core.requests.RestAction;
/*    */ import net.sydrus.yuzuku.Constructors.Command;
/*    */ import net.sydrus.yuzuku.Managers.LevelType;
/*    */ import net.sydrus.yuzuku.YuzukuBot;
/*    */ 
/*    */ public class add
/*    */   extends Command
/*    */ {
/*    */   public boolean onCommand(User Sender, Message Message, Guild Guild, TextChannel Chat, List<LevelType> type, Boolean isEdited, String[] args)
/*    */   {
/* 18 */     Chat.sendMessage(embedMessage("Hi, I'm " + YuzukuBot.botName + ". Add me to your discord server, for do that use the link: https://discordapp.com/oauth2/authorize?&client_id=278693792297779200&scope=bot&permissions=66321471")).queue();
/* 19 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Commands\add.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */