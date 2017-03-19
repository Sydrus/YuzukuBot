/*    */ package net.sydrus.yuzuku.Managers;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.HashMap;
/*    */ import net.sydrus.yuzuku.YuzukuBot;
/*    */ 
/*    */ public class HelpManager
/*    */ {
/*  9 */   private HashMap<String, String> itr = new HashMap();
/*    */   
/* 11 */   private String prefix = "";
/* 12 */   private String toReplace = "";
/* 13 */   private Color color = YuzukuBot.defaultMessageColor;
/* 14 */   private String titlee = "";
/* 15 */   private String footer = "";
/* 16 */   private String foUrl = "";
/*    */   
/*    */   public void setPrefix(String prefix, String toreplace) {
/* 19 */     if ((prefix == null) || (prefix.isEmpty())) {
/* 20 */       throw new NullPointerException("**String 'prefix' can not be empty or null**");
/*    */     }
/* 22 */     if ((toreplace == null) || (toreplace.isEmpty())) {
/* 23 */       throw new NullPointerException("**String 'toreplace' can not be empty or null**");
/*    */     }
/* 25 */     this.prefix = prefix;
/* 26 */     this.toReplace = (toreplace + " ");
/*    */   }
/*    */   
/*    */   public void setTitle(String title) {
/* 30 */     this.titlee = title;
/*    */   }
/*    */   
/*    */   public void setFooter(String title, String Url) {
/* 34 */     this.titlee = title;
/* 35 */     this.foUrl = Url;
/*    */   }
/*    */   
/*    */   public void setColor(Color color) {
/* 39 */     this.color = color;
/*    */   }
/*    */   
/*    */   public void addItem(String command, String coment) {
/* 43 */     if (!this.itr.containsKey(command)) {
/* 44 */       this.itr.put(command, coment);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean containsCommand(String Command) {
/* 49 */     return this.itr.containsKey(Command);
/*    */   }
/*    */   
/*    */   public EmbedManager getEmbedManager(HelpType type) {
/* 53 */     String AddField = "";
/* 54 */     EmbedManager embmanager = new EmbedManager();
/* 55 */     embmanager.setTitle(this.titlee);
/* 56 */     if ((!this.footer.isEmpty()) && (!this.foUrl.isEmpty())) {
/* 57 */       embmanager.setFooter(this.footer, this.foUrl);
/*    */     }
/* 59 */     if (type == HelpType.UseNameAndCommend) {
/* 60 */       for (String item : this.itr.keySet()) {
/* 61 */         embmanager.addField(item.replace(this.prefix, this.toReplace), (String)this.itr.get(item), false);
/*    */       }
/* 63 */     } else if (type == HelpType.UseComment) {
/* 64 */       for (String item : this.itr.keySet()) {
/* 65 */         AddField = AddField + " " + item.replace(this.prefix, this.toReplace) + "  [" + (String)this.itr.get(item) + "]\n";
/*    */       }
/* 67 */       embmanager.setDescription(AddField);
/* 68 */     } else if (type == HelpType.UseCommentWhiteOutBrackets) {
/* 69 */       for (String item : this.itr.keySet()) {
/* 70 */         AddField = AddField + " " + item.replace(this.prefix, this.toReplace) + " " + (String)this.itr.get(item) + "\n";
/*    */       }
/* 72 */       embmanager.setDescription(AddField);
/*    */     }
/* 74 */     embmanager.setColor(this.color);
/* 75 */     return embmanager;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 79 */     String Finaly = this.titlee;
/* 80 */     for (String item : this.itr.keySet()) {
/* 81 */       Finaly = Finaly + item.replace(this.prefix, this.toReplace) + " [" + (String)this.itr.get(item) + "]\n";
/*    */     }
/* 83 */     Finaly = Finaly + "\n" + this.footer;
/* 84 */     if (Finaly.endsWith("\n")) {
/* 85 */       return Finaly.substring(0, Finaly.length() - 1);
/*    */     }
/* 87 */     return Finaly;
/*    */   }
/*    */   
/*    */   public static enum HelpType
/*    */   {
/* 92 */     UseNameAndCommend, 
/* 93 */     UseComment,  UseCommentWhiteOutBrackets;
/*    */     
/*    */     private HelpType() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\HelpManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */