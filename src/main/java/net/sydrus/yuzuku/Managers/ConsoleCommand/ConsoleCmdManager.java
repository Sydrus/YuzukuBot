/*    */ package net.sydrus.yuzuku.Managers.ConsoleCommand;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.sydrus.yuzuku.Constructors.ConsoleCommand;
/*    */ 
/*    */ public class ConsoleCmdManager
/*    */ {
/* 12 */   private Map<String, ConsoleCommand> cmd = new HashMap();
/*    */   
/*    */   public void register(String command, ConsoleCommand path) {
/* 15 */     this.cmd.put(command.toLowerCase(), path);
/*    */   }
/*    */   
/*    */   public Set<String> keySet() {
/* 19 */     return this.cmd.keySet();
/*    */   }
/*    */   
/*    */   public boolean contains(String command) {
/* 23 */     return this.cmd.containsKey(command.toLowerCase());
/*    */   }
/*    */   
/*    */   public ConsoleCommand get(String command) {
/* 27 */     return (ConsoleCommand)this.cmd.get(command);
/*    */   }
/*    */   
/*    */   public int count() {
/* 31 */     return this.cmd.size();
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 35 */     return this.cmd.isEmpty();
/*    */   }
/*    */   
/*    */   public List<ConsoleCommand> getCommands() {
/* 39 */     List<ConsoleCommand> cmdtr = new ArrayList();
/* 40 */     for (ConsoleCommand cmdtadd : this.cmd.values()) {
/* 41 */       cmdtr.add(cmdtadd);
/*    */     }
/* 43 */     return null;
/*    */   }
/*    */   
/*    */   public List<String> getNames() {
/* 47 */     List<String> itemtr = new ArrayList();
/* 48 */     for (String items : this.cmd.keySet()) {
/* 49 */       itemtr.add(items);
/*    */     }
/* 51 */     return null;
/*    */   }
/*    */   
/*    */   public void clear() {
/* 55 */     this.cmd.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\ConsoleCommand\ConsoleCmdManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */