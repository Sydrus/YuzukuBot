/*     */ package net.sydrus.yuzuku.Managers;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.sydrus.yuzuku.Constructors.Command;
/*     */ import net.sydrus.yuzuku.PLugin.CommandInfo;
/*     */ 
/*     */ public final class Commands
/*     */ {
/*  13 */   private Map<String, Command> cmd = new HashMap();
/*  14 */   private Map<String, CommandInfo> info = new HashMap();
/*     */   
/*     */   public void register(CommandInfo comInfo, Command path) {
/*  17 */     if (this.info.containsKey(comInfo.command)) {
/*     */       try {
/*  19 */         throw new BotCommand(comInfo.command);
/*     */       } catch (BotCommand e) {
/*  21 */         e.printStackTrace();
/*     */       }
/*     */     } else {
/*  24 */       this.info.put(comInfo.command.toLowerCase(), comInfo);
/*  25 */       this.cmd.put(comInfo.command.toLowerCase(), path);
/*     */     }
/*     */   }
/*     */   
/*     */   public Set<String> keySet() {
/*  30 */     return this.cmd.keySet();
/*     */   }
/*     */   
/*     */   public final CommandInfo getCommandInfo(String command) {
/*  34 */     return (CommandInfo)this.info.get(command);
/*     */   }
/*     */   
/*     */   public List<CommandInfo> getCommands(CommandType type) {
/*  38 */     List<CommandInfo> toReturn = new ArrayList();
/*  39 */     for (CommandInfo ifo : this.info.values()) {
/*  40 */       if ((!ifo.hideFromList) && 
/*  41 */         (ifo.getType() == type)) {
/*  42 */         toReturn.add(ifo);
/*     */       }
/*     */     }
/*     */     
/*  46 */     return toReturn;
/*     */   }
/*     */   
/*     */   public List<CommandInfo> getCommands(CommandType type, boolean getHide) {
/*  50 */     List<CommandInfo> toReturn = new ArrayList();
/*  51 */     for (CommandInfo ifo : this.info.values()) {
/*  52 */       if (ifo.getType() == type) {
/*  53 */         toReturn.add(ifo);
/*     */       }
/*     */     }
/*  56 */     return toReturn;
/*     */   }
/*     */   
/*     */   public void registerCommands(Commands command) {
/*  60 */     for (String commandname : command.keySet()) {
/*  61 */       if (this.info.containsKey(commandname)) {
/*     */         try {
/*  63 */           throw new BotCommand(commandname);
/*     */         } catch (BotCommand e) {
/*  65 */           e.printStackTrace();
/*     */         }
/*     */       } else {
/*  68 */         CommandInfo inf = command.getCommandInfo(commandname);
/*  69 */         register(inf, command.get(commandname));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean contains(String command)
/*     */   {
/*  76 */     return this.cmd.containsKey(command.toLowerCase());
/*     */   }
/*     */   
/*     */   public Command get(String command) {
/*  80 */     return (Command)this.cmd.get(command);
/*     */   }
/*     */   
/*     */   public int count() {
/*  84 */     return this.cmd.size();
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*  88 */     return this.cmd.isEmpty();
/*     */   }
/*     */   
/*     */   public List<Command> getCommands() {
/*  92 */     List<Command> cmdtr = new ArrayList();
/*  93 */     for (Command cmdtadd : this.cmd.values()) {
/*  94 */       cmdtr.add(cmdtadd);
/*     */     }
/*  96 */     return null;
/*     */   }
/*     */   
/*     */   public List<String> getNames() {
/* 100 */     List<String> itemtr = new ArrayList();
/* 101 */     for (String items : this.cmd.keySet()) {
/* 102 */       itemtr.add(items);
/*     */     }
/* 104 */     return null;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 108 */     this.cmd.clear();
/*     */   }
/*     */   
/*     */   public void replace(String key, Command newValue) {
/* 112 */     this.cmd.replace(key, newValue);
/*     */   }
/*     */   
/*     */   public final void unregisterCommands(Commands commands) {
/* 116 */     for (String commandname : commands.keySet()) {
/* 117 */       if (contains(commandname)) {
/* 118 */         this.cmd.remove(commandname);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\Commands.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */