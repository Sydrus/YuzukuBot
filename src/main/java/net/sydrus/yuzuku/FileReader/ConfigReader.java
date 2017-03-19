package net.sydrus.yuzuku.FileReader;

import net.sydrus.yuzuku.Managers.Cipher3DES;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class ConfigReader {
	private List<String> rbox = new ArrayList<String>();
	private String pseparator = System.getProperty("file.separator");
	private File file;
	private String folder;
	private FileReader filerd;
	private BufferedReader buff;
	private Cipher3DES cp = null;

	public ConfigReader(String folderName, String file) {
		if ((file.contains(pseparator)) || (file.contains("."))) {
			throw new FileError("Put here only the filename");
		}
		try {
			cp = new Cipher3DES("62091292172489127628589y", 95987562);
		} catch (Exception e1) {
		}
		this.folder = folderName;
		this.file = new File(folderName + pseparator + file + ".dcnf");
		try {
			createFile();
		} catch (IOException e) {
		}
		reload();

	}

	public ConfigReader(String folderName, String file, String encryptKey, int EncryptVector) throws Exception {
		if (file.contains(pseparator)) {
			throw new FileError("Put here only the filename");
		}
		cp = new Cipher3DES(encryptKey, EncryptVector);
		this.folder = folderName;
		this.file = new File(folderName + pseparator + file);
		try {
			createFile();
		} catch (IOException e) {
		}
		reload();
	}

	public void createFile() throws IOException {
		if (!file.exists()) {
			createFolder();
			if ((cFolder().endsWith(pseparator)) || (folder.startsWith(pseparator))) {
				this.file = new File(cFolder() + pseparator + file);
			} else {
				this.file = new File(cFolder() + pseparator + pseparator + file);
			}
			file.createNewFile();
		}
	}

	private String cFolder() {
		try {
			return new File("").getCanonicalPath();
		} catch (IOException e) {
			return System.getProperty("user.dir");
		}
	}

	private void createFolder() {
		File file = null;

		if ((cFolder().endsWith(pseparator)) || (folder.startsWith(pseparator))) {
			file = new File(cFolder() + folder);
		} else {
			file = new File(cFolder() + pseparator + folder);
		}
		file.mkdirs();
	}

	public Boolean existFile() {
		return file.exists();
	}

	public void save() {
		String text = "";
		for (String ii : rbox) {
			if (text.isEmpty()) {
				text = ii;
			} else {
				text = text + "\n" + ii;
			}
		}
		try {
			file.createNewFile();
			FileWriter fileR = new FileWriter(file);
			BufferedWriter buffR = new BufferedWriter(fileR);
			buffR.write(Encrypt(text));
			buffR.close();
			fileR.close();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void set(String path, Object value) {
		List<String> qnt = rbox;
		String line = "";
		int tp = 0;
		if (rbox.size() == 0) {
			if ((value.getClass().getName().equalsIgnoreCase("java.util.ArrayList"))
					&& (value.toString().startsWith("[, "))) {
				qnt.add(path + ": ([" + value.toString().substring(3) + ")");
			} else if (value.getClass().getName().equalsIgnoreCase("java.lang.String")) {
				qnt.add(path + ": (\"" + value + "\")");
			} else {
				qnt.add(path + ": (" + value + ")");
			}
		}
		for (int i = 0; i < qnt.size(); i++) {
			line = qnt.get(i);
			if (line.startsWith(path)) {
				tp = i;
				i = qnt.size() + 1;
				qnt.remove(tp);
				try {
					if (value.getClass().getName().equalsIgnoreCase("java.util.ArrayList")
							&& (value.toString().startsWith("[, "))) {
						qnt.add(path + ": ([" + value.toString().substring(3) + ")");
					} else if (value.getClass().getName().equalsIgnoreCase("java.lang.String")) {
						qnt.add(path + ": (\"" + value + "\")");
					} else {
						qnt.add(path + ": (" + value + ")");
					}
				} catch (Exception e) {
					return;
				}
			} else {
				if (i == qnt.size() - 1) {
					if ((value.getClass().getName().equalsIgnoreCase("java.util.ArrayList"))
							&& (value.toString().startsWith("[, "))) {
						qnt.add(path + ": ([" + value.toString().substring(3) + ")");
					} else if (value.getClass().getName().equalsIgnoreCase("java.lang.String")) {
						qnt.add(path + ": (\"" + value + "\")");
					} else {
						qnt.add(path + ": (" + value + ")");
					}
				}
			}
		}
	}

	public void reload() {
		String Textbfl = "";
		String toDecrypt = "";
		try {
			rbox.clear();
			filerd = new FileReader(file);
			buff = new BufferedReader(filerd);
			while ((Textbfl = buff.readLine()) != null) {
				toDecrypt = toDecrypt + Textbfl;
			}
			String[] vr = Decrypt(toDecrypt).split("\n");
			for (String itm : vr) {
				if (!itm.isEmpty()) {
					rbox.add(itm);
				}
			}
			filerd.close();
			buff.close();
		} catch (Exception e) {
			Type.messageType(Type.MessageType.Error, "Error on config: " + e.getMessage());
		}
	}

	private Object get(String path, String s1, String s2) {
		s1 = "(" + s1;
		s2 = s2 + ")";
		int s1c = s1.length();
		String itemstring = "";
		for (String item : rbox) {
			if (item.startsWith(path)) {
				itemstring = item;
			}
		}
		if (itemstring.isEmpty()) {
			throw new FileError("Session of configuration not found");
		}
		String itemc = itemstring.substring(itemstring.indexOf(s1) + s1c);
		return itemc.substring(0, itemc.lastIndexOf(s2));
	}

	public String getString(String path) {
		try {
			return (String) get(path, "\"", "\"");
		} catch (Exception ex) {
			return null;
		}
	}

	public boolean isEmpty() {
		String text = "";
		for (String ii : rbox) {
			if (text.isEmpty()) {
				text = text + ii;
			} else {
				text = text + "\n" + ii;
			}

		}
		return text.isEmpty();
	}

	public boolean contains(String path) {
		for (String item : rbox) {
			if (item.startsWith(path + ":")) {
				return true;
			}
		}
		return false;
	}

	public boolean getBoolean(String path) {
		try {
			return Boolean.parseBoolean((String) get(path, "", ""));
		} catch (Exception ex) {
			return false;
		}
	}

	public int getInt(String path) {
		try {
			return Integer.parseInt((String) get(path, "", ""));
		} catch (Exception ex) {
			return 0;
		}
	}

	public double getDouble(String path) {
		try {
			return Double.parseDouble((String) get(path, "", ""));
		} catch (Exception ex) {
			return 0;
		}
	}

	public List<Object> getList(String path) {
		List<Object> ob = new ArrayList<Object>();
		try {
			String item = (String) get(path, "[", "]");
			String[] vr = item.split(", ");
			for (String itm : vr) {
				ob.add(itm);
			}
		} catch (Exception e) {
		}
		return ob;
	}

	private String Decrypt(String text) throws InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException {
		return cp.decryptText(text);
	}

	private String Encrypt(String text) throws InvalidKeyException, UnsupportedEncodingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		return cp.encryptText(text);
	}

	public void closeAllConn() throws IOException {
		filerd.close();
		buff.close();
	}

}
