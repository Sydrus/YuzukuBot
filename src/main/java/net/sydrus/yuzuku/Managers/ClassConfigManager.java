package net.sydrus.yuzuku.Managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClassConfigManager {

	private HashMap<String, String> item = new HashMap<String, String>();
	private File file;
	private FileReader filerd;
	private BufferedReader buff;
	
	public ClassConfigManager(String filename, Class<?> clas) {
		try {
			file = new File(clas.getResource(filename).getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reload() throws IOException {
		item.clear();
		filerd = new FileReader(file);
		buff = new BufferedReader(filerd);
		String tf = "";
		String Textbfl = "";
		while ((Textbfl = buff.readLine()) != null) {
			if(!Textbfl.isEmpty()) {
				tf = Textbfl.substring(Textbfl.indexOf(":"));
				 item.put(Textbfl.substring(0, Textbfl.indexOf(":")),tf.substring(tf.indexOf(":") + 2));	
			}
		}
	}

	public boolean contains(String path) {
		return item.containsKey(path);
	}

	public boolean getBoolean(String path) {
		if(item.get(path).equalsIgnoreCase("true")) {
			return true;
		}
		return false;
	}

	public int getInt(String path) {
		return Integer.parseInt(item.get(path));
	}
	
	public String getString(String path) {
		return item.get(path);
	}

	public List<Object> getList(String path) {
		String[] array = item.get(path).split(", ");
		List<Object> finallist = new ArrayList<Object>();
		for(String finali : array) {
			finallist.add(finali);
		}
		return finallist;
	}
}
