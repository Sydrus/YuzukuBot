/*     */ package net.sydrus.yuzuku.FileReader;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.crypto.BadPaddingException;
/*     */ import javax.crypto.IllegalBlockSizeException;
/*     */ import net.sydrus.yuzuku.Managers.Cipher3DES;
/*     */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
/*     */ import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;
/*     */ 
/*     */ public class ConfigReader
/*     */ {
/*  22 */   private List<String> rbox = new ArrayList();
/*  23 */   private String pseparator = System.getProperty("file.separator");
/*     */   private File file;
/*     */   private String folder;
/*     */   private FileReader filerd;
/*     */   private BufferedReader buff;
/*  28 */   private Cipher3DES cp = null;
/*     */   
/*     */   public ConfigReader(String folderName, String file) {
/*  31 */     if ((file.contains(this.pseparator)) || (file.contains("."))) {
/*  32 */       throw new FileError("Put here only the filename");
/*     */     }
/*     */     try {
/*  35 */       this.cp = new Cipher3DES("62091292172489127628589y", 95987562);
/*     */     }
/*     */     catch (Exception localException) {}
/*  38 */     this.folder = folderName;
/*  39 */     this.file = new File(folderName + this.pseparator + file + ".dcnf");
/*     */     try {
/*  41 */       createFile();
/*     */     }
/*     */     catch (IOException localIOException) {}
/*  44 */     reload();
/*     */   }
/*     */   
/*     */   public ConfigReader(String folderName, String file, String encryptKey, int EncryptVector) throws Exception
/*     */   {
/*  49 */     if (file.contains(this.pseparator)) {
/*  50 */       throw new FileError("Put here only the filename");
/*     */     }
/*  52 */     this.cp = new Cipher3DES(encryptKey, EncryptVector);
/*  53 */     this.folder = folderName;
/*  54 */     this.file = new File(folderName + this.pseparator + file);
/*     */     try {
/*  56 */       createFile();
/*     */     }
/*     */     catch (IOException localIOException) {}
/*  59 */     reload();
/*     */   }
/*     */   
/*     */   public void createFile() throws IOException {
/*  63 */     if (!this.file.exists()) {
/*  64 */       createFolder();
/*  65 */       if ((cFolder().endsWith(this.pseparator)) || (this.folder.startsWith(this.pseparator))) {
/*  66 */         this.file = new File(cFolder() + this.pseparator + this.file);
/*     */       } else {
/*  68 */         this.file = new File(cFolder() + this.pseparator + this.pseparator + this.file);
/*     */       }
/*  70 */       this.file.createNewFile();
/*     */     }
/*     */   }
/*     */   
/*     */   private String cFolder() {
/*     */     try {
/*  76 */       return new File("").getCanonicalPath();
/*     */     } catch (IOException e) {}
/*  78 */     return System.getProperty("user.dir");
/*     */   }
/*     */   
/*     */   private void createFolder()
/*     */   {
/*  83 */     File file = null;
/*     */     
/*  85 */     if ((cFolder().endsWith(this.pseparator)) || (this.folder.startsWith(this.pseparator))) {
/*  86 */       file = new File(cFolder() + this.folder);
/*     */     } else {
/*  88 */       file = new File(cFolder() + this.pseparator + this.folder);
/*     */     }
/*  90 */     file.mkdirs();
/*     */   }
/*     */   
/*     */   public Boolean existFile() {
/*  94 */     return Boolean.valueOf(this.file.exists());
/*     */   }
/*     */   
/*     */   public void save() {
/*  98 */     String text = "";
/*  99 */     for (String ii : this.rbox) {
/* 100 */       if (text.isEmpty()) {
/* 101 */         text = ii;
/*     */       } else {
/* 103 */         text = text + "\n" + ii;
/*     */       }
/*     */     }
/*     */     try {
/* 107 */       this.file.createNewFile();
/* 108 */       FileWriter fileR = new FileWriter(this.file);
/* 109 */       BufferedWriter buffR = new BufferedWriter(fileR);
/* 110 */       buffR.write(Encrypt(text));
/* 111 */       buffR.close();
/* 112 */       fileR.close();
/*     */     }
/*     */     catch (IOException e) {
/* 115 */       e.printStackTrace();
/*     */     }
/*     */     catch (InvalidKeyException e) {
/* 118 */       e.printStackTrace();
/*     */     }
/*     */     catch (InvalidAlgorithmParameterException e) {
/* 121 */       e.printStackTrace();
/*     */     }
/*     */     catch (IllegalBlockSizeException e) {
/* 124 */       e.printStackTrace();
/*     */     }
/*     */     catch (BadPaddingException e) {
/* 127 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public void set(String path, Object value) {
/* 132 */     List<String> qnt = this.rbox;
/* 133 */     String line = "";
/* 134 */     int tp = 0;
/* 135 */     if (this.rbox.size() == 0) {
/* 136 */       if ((value.getClass().getName().equalsIgnoreCase("java.util.ArrayList")) && 
/* 137 */         (value.toString().startsWith("[, "))) {
/* 138 */         qnt.add(path + ": ([" + value.toString().substring(3) + ")");
/* 139 */       } else if (value.getClass().getName().equalsIgnoreCase("java.lang.String")) {
/* 140 */         qnt.add(path + ": (\"" + value + "\")");
/*     */       } else {
/* 142 */         qnt.add(path + ": (" + value + ")");
/*     */       }
/*     */     }
/* 145 */     for (int i = 0; i < qnt.size(); i++) {
/* 146 */       line = (String)qnt.get(i);
/* 147 */       if (line.startsWith(path)) {
/* 148 */         tp = i;
/* 149 */         i = qnt.size() + 1;
/* 150 */         qnt.remove(tp);
/*     */         try {
/* 152 */           if ((value.getClass().getName().equalsIgnoreCase("java.util.ArrayList")) && 
/* 153 */             (value.toString().startsWith("[, "))) {
/* 154 */             qnt.add(path + ": ([" + value.toString().substring(3) + ")");
/* 155 */           } else if (value.getClass().getName().equalsIgnoreCase("java.lang.String")) {
/* 156 */             qnt.add(path + ": (\"" + value + "\")");
/*     */           } else {
/* 158 */             qnt.add(path + ": (" + value + ")");
/*     */           }
/*     */         } catch (Exception e) {
/* 161 */           return;
/*     */         }
/*     */       }
/* 164 */       else if (i == qnt.size() - 1) {
/* 165 */         if ((value.getClass().getName().equalsIgnoreCase("java.util.ArrayList")) && 
/* 166 */           (value.toString().startsWith("[, "))) {
/* 167 */           qnt.add(path + ": ([" + value.toString().substring(3) + ")");
/* 168 */         } else if (value.getClass().getName().equalsIgnoreCase("java.lang.String")) {
/* 169 */           qnt.add(path + ": (\"" + value + "\")");
/*     */         } else {
/* 171 */           qnt.add(path + ": (" + value + ")");
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void reload()
/*     */   {
/* 179 */     String Textbfl = "";
/* 180 */     String toDecrypt = "";
/*     */     try {
/* 182 */       this.rbox.clear();
/* 183 */       this.filerd = new FileReader(this.file);
/* 184 */       this.buff = new BufferedReader(this.filerd);
/* 185 */       while ((Textbfl = this.buff.readLine()) != null) {
/* 186 */         toDecrypt = toDecrypt + Textbfl;
/*     */       }
/* 188 */       String[] vr = Decrypt(toDecrypt).split("\n");
/* 189 */       for (String itm : vr) {
/* 190 */         if (!itm.isEmpty()) {
/* 191 */           this.rbox.add(itm);
/*     */         }
/*     */       }
/* 194 */       this.filerd.close();
/* 195 */       this.buff.close();
/*     */     } catch (Exception e) {
/* 197 */       Type.messageType(Type.MessageType.Error, "Error on config: " + e.getMessage());
/*     */     }
/*     */   }
/*     */   
/*     */   private Object get(String path, String s1, String s2) {
/* 202 */     s1 = "(" + s1;
/* 203 */     s2 = s2 + ")";
/* 204 */     int s1c = s1.length();
/* 205 */     String itemstring = "";
/* 206 */     for (String item : this.rbox) {
/* 207 */       if (item.startsWith(path)) {
/* 208 */         itemstring = item;
/*     */       }
/*     */     }
/* 211 */     if (itemstring.isEmpty()) {
/* 212 */       throw new FileError("Session of configuration not found");
/*     */     }
/* 214 */     String itemc = itemstring.substring(itemstring.indexOf(s1) + s1c);
/* 215 */     return itemc.substring(0, itemc.lastIndexOf(s2));
/*     */   }
/*     */   
/*     */   public String getString(String path) {
/*     */     try {
/* 220 */       return (String)get(path, "\"", "\"");
/*     */     } catch (Exception ex) {}
/* 222 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isEmpty()
/*     */   {
/* 227 */     String text = "";
/* 228 */     for (String ii : this.rbox) {
/* 229 */       if (text.isEmpty()) {
/* 230 */         text = text + ii;
/*     */       } else {
/* 232 */         text = text + "\n" + ii;
/*     */       }
/*     */     }
/*     */     
/* 236 */     return text.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean contains(String path) {
/* 240 */     for (String item : this.rbox) {
/* 241 */       if (item.startsWith(path + ":")) {
/* 242 */         return true;
/*     */       }
/*     */     }
/* 245 */     return false;
/*     */   }
/*     */   
/*     */   public boolean getBoolean(String path) {
/*     */     try {
/* 250 */       return Boolean.parseBoolean((String)get(path, "", ""));
/*     */     } catch (Exception ex) {}
/* 252 */     return false;
/*     */   }
/*     */   
/*     */   public int getInt(String path)
/*     */   {
/*     */     try {
/* 258 */       return Integer.parseInt((String)get(path, "", ""));
/*     */     } catch (Exception ex) {}
/* 260 */     return 0;
/*     */   }
/*     */   
/*     */   public double getDouble(String path)
/*     */   {
/*     */     try {
/* 266 */       return Double.parseDouble((String)get(path, "", ""));
/*     */     } catch (Exception ex) {}
/* 268 */     return 0.0D;
/*     */   }
/*     */   
/*     */   public List<Object> getList(String path)
/*     */   {
/* 273 */     List<Object> ob = new ArrayList();
/*     */     try {
/* 275 */       String item = (String)get(path, "[", "]");
/* 276 */       String[] vr = item.split(", ");
/* 277 */       for (String itm : vr) {
/* 278 */         ob.add(itm);
/*     */       }
/*     */     }
/*     */     catch (Exception localException) {}
/* 282 */     return ob;
/*     */   }
/*     */   
/*     */   private String Decrypt(String text) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
/*     */   {
/* 287 */     return this.cp.decryptText(text);
/*     */   }
/*     */   
/*     */   private String Encrypt(String text) throws InvalidKeyException, UnsupportedEncodingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
/*     */   {
/* 292 */     return this.cp.encryptText(text);
/*     */   }
/*     */   
/*     */   public void closeAllConn() throws IOException {
/* 296 */     this.filerd.close();
/* 297 */     this.buff.close();
/*     */   }
/*     */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\FileReader\ConfigReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */