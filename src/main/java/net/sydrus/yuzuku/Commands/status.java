/*    */ package net.sydrus.yuzuku.Commands;
/*    */ 
/*    */ import com.sun.management.OperatingSystemMXBean;
/*    */ import java.lang.management.ManagementFactory;
/*    */ import java.text.DecimalFormat;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import net.dv8tion.jda.core.entities.Guild;
/*    */ import net.dv8tion.jda.core.entities.Message;
/*    */ import net.dv8tion.jda.core.entities.TextChannel;
/*    */ import net.dv8tion.jda.core.entities.User;
/*    */ import net.sydrus.yuzuku.Managers.GuildManager;
/*    */ import net.sydrus.yuzuku.Managers.LevelType;
/*    */ import net.sydrus.yuzuku.YuzukuBot;
/*    */ 
/*    */ public class status extends net.sydrus.yuzuku.Constructors.Command
/*    */ {
/*    */   private int getThreads()
/*    */   {
/* 21 */     return Thread.activeCount();
/*    */   }
/*    */   
/*    */   private long getTotalMemory() {
/* 25 */     return Runtime.getRuntime().totalMemory() / 1048576L;
/*    */   }
/*    */   
/*    */   private long getMemory() {
/* 29 */     return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576L;
/*    */   }
/*    */   
/*    */   private String getCPULoad() {
/* 33 */     OperatingSystemMXBean osBean = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
/* 34 */     DecimalFormat df = new DecimalFormat("#.##");
/* 35 */     return df.format(osBean.getProcessCpuLoad()) + "%";
/*    */   }
/*    */   
/* 38 */   long sec = System.currentTimeMillis();
/*    */   
/*    */   public String getUptime() {
/* 41 */     Long time = Long.valueOf(System.currentTimeMillis() - this.sec);
/* 42 */     long days = TimeUnit.MILLISECONDS.toDays(time.longValue());
/* 43 */     time = Long.valueOf(time.longValue() - TimeUnit.DAYS.toMillis(days));
/* 44 */     long hours = TimeUnit.MILLISECONDS.toHours(time.longValue());
/* 45 */     time = Long.valueOf(time.longValue() - TimeUnit.HOURS.toMillis(hours));
/* 46 */     long minutes = TimeUnit.MILLISECONDS.toMinutes(time.longValue());
/* 47 */     time = Long.valueOf(time.longValue() - TimeUnit.MINUTES.toMillis(minutes));
/* 48 */     long seconds = TimeUnit.MILLISECONDS.toSeconds(time.longValue());
/* 49 */     return days + " Days " + hours + " Hours " + minutes + " Minutes " + seconds + " Seconds";
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean onCommand(User Sender, Message Message, Guild Guild, TextChannel Chat, List<LevelType> type, Boolean isEdited, String[] args)
/*    */   {
/* 56 */     List<String> statusItems = new ArrayList();
/* 57 */     statusItems.add("#Bot Status:\n\n");
/* 58 */     statusItems.add("**Version:** " + YuzukuBot.getInstance().Version + "\n");
/* 59 */     statusItems.add("**Discord:**");
/* 60 */     statusItems.add("\t**Status:** " + YuzukuBot.getInstance().Status);
/* 61 */     statusItems.add("\t**Guilds:** " + YuzukuBot.getInstance().getJDA().getGuilds().size() + "\n");
/* 62 */     statusItems.add("\t**Commands:** " + YuzukuBot.getInstance().getCommandManager().getCommands().count());
/* 63 */     statusItems.add("\t**Sub Commands:** " + YuzukuBot.getInstance().subCommands + "\n");
/* 64 */     statusItems.add("**BOT Prefix:** " + YuzukuBot.guildManager.getPrefix(Guild) + "\n");
/* 65 */     statusItems.add("**Time Online:** " + getUptime() + "\n");
/* 66 */     statusItems.add("**Errors:** " + YuzukuBot.getInstance().botErrors + "\n");
/* 67 */     statusItems.add("**Bot Server Status:** \n");
/* 68 */     statusItems.add("**CPU:** " + getCPULoad() + "\n\t**Memory:** " + getMemory() + "/" + Runtime.getRuntime().totalMemory() / 1048576L + "MB \n**Threads:** " + getThreads() + "\n");
/* 69 */     statusItems.add("**Redis:**");
/* 70 */     statusItems.add("\t**Status: **in development!");
/* 71 */     String endItems = "";
/* 72 */     for (String itemslist : statusItems) {
/* 73 */       endItems = endItems + itemslist + "\n";
/*    */     }
/*    */     
/* 76 */     Chat.sendMessage(embedMessage(endItems)).queue();
/* 77 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Commands\status.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */