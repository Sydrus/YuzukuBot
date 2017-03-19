/*     */ package net.sydrus.yuzuku.Managers.ConsoleCommand;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStreamReader;
/*     */ import net.sydrus.yuzuku.Constructors.ConsoleCommand;
/*     */ import net.sydrus.yuzuku.FileReader.ConfigReader;
/*     */ import net.sydrus.yuzuku.YuzukuBot;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Console
/*     */ {
/*     */   private BufferedReader scan;
/*  14 */   private boolean autolock = true;
/*     */   private RegisterCommand cmds;
/*  16 */   private ConfigReader config = null;
/*  17 */   private Thread t1 = null;
/*     */   
/*     */   public void start() {
/*  20 */     this.scan = new BufferedReader(new InputStreamReader(System.in));
/*  21 */     this.t1 = new Thread(runCode());
/*  22 */     this.t1.start();
/*  23 */     this.cmds = new RegisterCommand();
/*  24 */     this.config = YuzukuBot.getInstance().settingsData;
/*     */   }
/*     */   
/*     */   private Console getConsole() {
/*  28 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */   public final void updateState(boolean value) {}
/*     */   
/*     */ 
/*     */   public final void updateState() {}
/*     */   
/*     */ 
/*     */   private Runnable runCode()
/*     */   {
/*  40 */     new Runnable() {
/*     */       public void run() {
/*  42 */         String scanned = "";
/*     */         try {
/*     */           for (;;) {
/*  45 */             scanned = Console.this.scan.readLine();
/*     */             
/*  47 */             Console.this.config.reload();
/*  48 */             if (!scanned.isEmpty()) {
/*  49 */               if (Console.this.cmds.getCmdManager().contains(Console.this.getArray(scanned)[0])) {
/*  50 */                 if (Console.this.getArray(scanned).length == 1) {
/*  51 */                   Console.this.cmds.getCmdManager().get(Console.this.getArray(scanned)[0].toLowerCase()).onCommand(new ConsoleDef(Console.this
/*  52 */                     .getConsole()), scanned, "null", new String[0]);
/*     */                 } else {
/*  54 */                   Console.this.cmds.getCmdManager().get(Console.this.getArray(scanned)[0].toLowerCase()).onCommand(new ConsoleDef(Console.this
/*  55 */                     .getConsole()), scanned, "null", Console.this.getCommand(scanned));
/*     */                 }
/*     */               } else {
/*  58 */                 Type.messageType(Type.MessageType.Error, "This command does not exist");
/*     */               }
/*     */             }
/*     */             else {
/*  62 */               Type.messageType(Type.MessageType.Warning, "Type something");
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (Exception e) {
/*  67 */           YuzukuBot.getInstance().botErrors += 1;
/*     */         }
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */ 
/*     */   public void setLockStatus(Class<?> clas, boolean value)
/*     */   {
/*  76 */     if (clas.getName().equals("net.sydrus.yuzuku.Managers.ConsoleCommand.Commands.unlock"))
/*     */     {
/*  78 */       if ((value) || (this.autolock != true))
/*     */       {
/*  80 */         if (value != true) {}
/*     */       }
/*     */     } else {
/*  83 */       throw new RuntimeException("Invalid Class Permission");
/*     */     }
/*     */   }
/*     */   
/*     */   private String[] getArray(String args) {
/*  88 */     return args.split(" ");
/*     */   }
/*     */   
/*     */   private String argsToString(String args, Integer index) {
/*  92 */     String myString = "";
/*  93 */     String[] argh = args.split(" ");
/*  94 */     for (int i = index.intValue(); i < argh.length; i++) {
/*  95 */       String arg = argh[i] + " ";
/*  96 */       myString = myString + arg;
/*     */     }
/*  98 */     myString = myString.substring(0, myString.length());
/*  99 */     if (myString.startsWith(" ")) {
/* 100 */       myString = myString.substring(1, myString.length());
/*     */     }
/* 102 */     return myString;
/*     */   }
/*     */   
/*     */   private String[] getCommand(String args) {
/* 106 */     return argsToString(args, Integer.valueOf(1)).split(" ");
/*     */   }
/*     */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\ConsoleCommand\Console.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */