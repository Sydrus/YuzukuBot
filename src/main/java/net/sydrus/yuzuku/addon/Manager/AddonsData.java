package net.sydrus.yuzuku.addon.Manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import net.sydrus.yuzuku.Managers.Cipher3DES;
import net.sydrus.yuzuku.addon.Exception.InvalidMPSFile;

public class AddonsData {

	private String main = "";
	private String version = "";
	private String addName = "";
	private String website = "";
	private List<String> authors = new ArrayList<String>();
	private String description = "";
	private List<String> depend = new ArrayList<String>();
	private FileReader filerd;
	private BufferedReader buff;
	private Cipher3DES Key;

	public AddonsData(String Name, String Version, String mainClass) {
		this.addName = Name;
		this.version = Version;
		this.main = mainClass;
	}

	public AddonsData(File file) throws InvalidMPSFile {
		try {
			Key = new Cipher3DES("178826827547465761262824", 29818486);
		} catch (Exception e1) {
		}
		String crypted = "";
		String finalText = "";
		try {
			filerd = new FileReader(file);
			buff = new BufferedReader(filerd);
			String content = "";
			while ((content = buff.readLine()) != null) {
				crypted = crypted + content;
			}
			try {
				finalText = Key.decryptText(crypted);
			} catch (Exception e) {
				throw new InvalidMPSFile(file, e);
			}
			for (String finalt : finalText.split("\n")) {
				if (finalt.startsWith("Main: ")) {
					this.main = finalt.substring(6);
				}
				if (finalt.startsWith("Version: ")) {
					this.version = finalt.substring(9);
				}
				if (finalt.startsWith("AddonName: ")) {
					this.addName = finalt.substring(11);
				}
				if (finalt.startsWith("WebSite: ")) {
					this.website = finalt.substring(9);
				}
				if (finalt.startsWith("Authors: ")) {
					String[] vr = finalt.substring(9).split(", ");
					for (String itm : vr) {
						this.authors.add(itm);
					}
				}
				if (finalt.startsWith("Description: ")) {
					this.description = finalt.substring(13);
				}
				if (finalt.startsWith("Depend: ")) {
					String[] vr = finalt.substring(8).split(", ");
					for (String itm : vr) {
						this.depend.add(itm);
					}
				}
			}
		} catch (Exception e) {
			throw new InvalidMPSFile(file, e);
		}
		if (this.main.isEmpty()) {
			throw new InvalidMPSFile(file, new Throwable("The field 'Main' is empty, check the MPS file"));
		}
		if (this.version.isEmpty()) {
			throw new InvalidMPSFile(file, new Throwable("The field 'Version' is empty, check the MPS file"));
		}
		if (this.addName.isEmpty()) {
			throw new InvalidMPSFile(file, new Throwable("The field 'AddonName' is empty, check the MPS file"));
		}
		/*
		 * this.main = "linkbot.main.clas"; this.version = "0.4 (Beta)";
		 * this.addName = "BetaPlugin";
		 */
	}

	public final void EndConnections() {
		try {
			filerd.close();
			buff.close();
		} catch (Exception e) {
		}
	}

	public String getName() {

		return this.addName;
	}

	public String getVersion() {
		return this.version;
	}

	public String getMain() {
		return this.main;
	}

	public String getDescription() {
		return this.description;
	}

	public List<String> getAuthors() {
		return this.authors;
	}

	public String getWebsite() {
		return this.website;
	}

	public List<String> getDep() {
		return this.depend;
	}

}
