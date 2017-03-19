/*    */ package net.sydrus.yuzuku.Managers;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.File;
/*    */ import java.io.FileReader;
/*    */ import java.net.URL;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ClassConfigManager
/*    */ {
/* 13 */   private HashMap<String, String> item = new HashMap();
/*    */   private File file;
/*    */   private FileReader filerd;
/*    */   private BufferedReader buff;
/*    */   
/*    */   public ClassConfigManager(String filename, Class<?> clas) {
/*    */     try {
/* 20 */       this.file = new File(clas.getResource(filename).getPath());
/*    */     } catch (Exception e) {
/* 22 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */   
/*    */   public void reload() throws java.io.IOException {
/* 27 */     this.item.clear();
/* 28 */     this.filerd = new FileReader(this.file);
/* 29 */     this.buff = new BufferedReader(this.filerd);
/* 30 */     String tf = "";
/* 31 */     String Textbfl = "";
/* 32 */     while ((Textbfl = this.buff.readLine()) != null) {
/* 33 */       if (!Textbfl.isEmpty()) {
/* 34 */         tf = Textbfl.substring(Textbfl.indexOf(":"));
/* 35 */         this.item.put(Textbfl.substring(0, Textbfl.indexOf(":")), tf.substring(tf.indexOf(":") + 2));
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean contains(String path) {
/* 41 */     return this.item.containsKey(path);
/*    */   }
/*    */   
/*    */   public boolean getBoolean(String path) {
/* 45 */     if (((String)this.item.get(path)).equalsIgnoreCase("true")) {
/* 46 */       return true;
/*    */     }
/* 48 */     return false;
/*    */   }
/*    */   
/*    */   public int getInt(String path) {
/* 52 */     return Integer.parseInt((String)this.item.get(path));
/*    */   }
/*    */   
/*    */   public String getString(String path) {
/* 56 */     return (String)this.item.get(path);
/*    */   }
/*    */   
/*    */   public List<Object> getList(String path) {
/* 60 */     String[] array = ((String)this.item.get(path)).split(", ");
/* 61 */     List<Object> finallist = new ArrayList();
/* 62 */     for (String finali : array) {
/* 63 */       finallist.add(finali);
/*    */     }
/* 65 */     return finallist;
/*    */   }
/*    */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Managers\ClassConfigManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */