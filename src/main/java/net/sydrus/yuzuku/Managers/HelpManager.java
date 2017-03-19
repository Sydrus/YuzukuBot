package net.sydrus.yuzuku.Managers;

import java.awt.Color;
import java.util.HashMap;

import net.sydrus.yuzuku.YuzukuBot;

public class HelpManager {
	private HashMap<String, String> itr = new HashMap<String, String>();

	private String prefix = "";
	private String toReplace = "";
	private Color color = YuzukuBot.defaultMessageColor;
	private String titlee = "";
	private String footer = "";
	private String foUrl = "";

	public void setPrefix(String prefix, String toreplace) {
		if ((prefix == null) || (prefix.isEmpty())) {
			throw new NullPointerException("**String 'prefix' can not be empty or null**");
		}
		if ((toreplace == null) || (toreplace.isEmpty())) {
			throw new NullPointerException("**String 'toreplace' can not be empty or null**");
		}
		this.prefix = prefix;
		this.toReplace = toreplace + " ";
	}

	public void setTitle(String title) {
		this.titlee = title;
	}

	public void setFooter(String title, String Url) {
		this.titlee = title;
		this.foUrl = Url;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void addItem(String command, String coment) {
		if (!itr.containsKey(command)) {
			itr.put(command, coment);
		}
	}

	public boolean containsCommand(String Command) {
		return itr.containsKey(Command);
	}

	public EmbedManager getEmbedManager(HelpType type) {
		String AddField = "";
		EmbedManager embmanager = new EmbedManager();
		embmanager.setTitle(titlee);
		if ((!footer.isEmpty()) && (!foUrl.isEmpty())) {
			embmanager.setFooter(footer, foUrl);
		}
		if (type == HelpType.UseNameAndCommend) {
			for (String item : itr.keySet()) {
				embmanager.addField(item.replace(prefix, toReplace), itr.get(item), false);
			}
		} else if (type == HelpType.UseComment) {
			for (String item : itr.keySet()) {
				AddField = AddField + " " + item.replace(prefix, toReplace) + "  [" + itr.get(item) + "]\n";
			}
			embmanager.setDescription(AddField);
		}else if (type == HelpType.UseCommentWhiteOutBrackets) {
			for (String item : itr.keySet()) {
				AddField = AddField + " " + item.replace(prefix, toReplace) + " " + itr.get(item) + "\n";
			}
			embmanager.setDescription(AddField);
		}
		embmanager.setColor(color);
		return embmanager;
	}

	public String toString() {
		String Finaly = titlee;
		for (String item : itr.keySet()) {
			Finaly = Finaly + item.replace(prefix, toReplace) + " [" + itr.get(item) + "]\n";
		}
		Finaly = Finaly + "\n" + footer;
		if (Finaly.endsWith("\n")) {
			return Finaly.substring(0, Finaly.length() - 1);
		} else {
			return Finaly;
		}
	}

	public static enum HelpType {
		@Deprecated
		UseNameAndCommend, UseComment, UseCommentWhiteOutBrackets
	}

}
