package net.sydrus.yuzuku.Constructors;

import java.awt.Color;
import java.util.List;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Managers.EmbedManager;
import net.sydrus.yuzuku.Managers.LevelType;

public abstract class Command {

	public abstract boolean onCommand(User Sender, Message Message, Guild Guild, TextChannel Chat, List<LevelType> type,
			Boolean isEdited, String[] args);

	public JDA getAPI() {
		return YuzukuBot.getInstance().getJDA();
	}

	public boolean isAdministrator(List<LevelType> type) {
		if (type.contains(LevelType.Administrator)) {
			return true;
		}
		if (type.contains(LevelType.Developer)) {
			return true;
		}
		return false;
	}

	public String sender(User user) {
		return "**[<@" + user.getId() + ">]** ";
	}

	public String usage(String msg) {
		return "**Usage**: " + msg + ".\n";
	}

	public String description(String msg) {
		return "**Description**: " + msg + ".\n";
	}

	public Message embedMessage(String text) {
		return embedMessage(text, YuzukuBot.defaultMessageColor);
	}
	
	public Message embedMessage(String text, Color color) {
		EmbedManager emb = new EmbedManager();
		emb.setDescription(text);
		emb.setColor(color);
		return emb.getMessage();
	}

	public String argsToString(String[] args, Integer index) {
		String myString = "";
		for (int i = index; i < args.length; i++) {
			String arg = args[i] + " ";
			myString = myString + arg;
		}
		myString = myString.substring(0, myString.length() - 1);
		return myString;
	}

}
