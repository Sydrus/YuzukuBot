package net.sydrus.yuzuku.Managers.ConsoleCommand.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Constructors.ConsoleCommand;
import net.sydrus.yuzuku.Managers.getBotListItems;
import net.sydrus.yuzuku.Managers.ConsoleCommand.ConsoleDef;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;

public class message extends ConsoleCommand {

	TextChannel channel = null;

	@Override
	public boolean onCommand(ConsoleDef console, String command, String time, String[] args) {
		if (args.length == 0) {
			Type.messageType(MessageType.Error, "Args for use this command");
			Type.messageType(MessageType.Error, "message set <server id> <channel id>");
			Type.messageType(MessageType.Error, "message send <text>");
			Type.messageType(MessageType.Error, "message clear");
		} else if ((args.length == 1) && (args[0].equalsIgnoreCase("info"))) {
			try {
				if (channel == null) {
					console.sendText("No channels defined");
				} else {
					console.sendText("Channel info");
					console.sendText("Source server: " + channel.getGuild().getName());
					console.sendText("Source server id: " + channel.getGuild().getId());
					console.sendText("Channel name: " + channel.getName());
					console.sendText("Channel id: " + channel.getId());
				}
			} catch (Exception e) {
				console.sendText(MessageType.Error, "Error fetching channel");
			}

		} else if ((args.length == 3) && (args[0].equalsIgnoreCase("set"))) {
			Guild guild = null;
			String[] setDef = argsToString(args, 1).split(" ");
			try {
				guild = YuzukuBot.getInstance().getJDA().getGuildById(setDef[0]);
			} catch (Exception e) {
				console.sendText(MessageType.Error, "Error on get server");
				return true;
			}
			try {
				channel = guild.getTextChannelById(setDef[1]);
				console.sendText("Channel set to");
				console.sendText("Channel Name: " + channel.getName());
				console.sendText("Chnnel id: " + channel.getId());
			} catch (Exception e) {
				console.sendText(MessageType.Error, "Error on get Channel");
				return true;
			}
		} else if ((args.length > 1) && (args[0].equalsIgnoreCase("send"))) {
			if (channel == null) {
				console.sendText(MessageType.Error, "Channel not set");
				return true;
			}
			try {
				channel.sendMessage(getMention(argsToString(args, 1))).queue();
				Type.messageType(MessageType.Info,
						"To Channel [" + channel.getName() + "] -> " + argsToString(args, 1));
			} catch (NullPointerException e) {
				console.sendText(MessageType.Error, "Error on send message, verify if channel exist");
			}
		} else if ((args.length == 1) && (args[0].equalsIgnoreCase("clear"))) {
			try {
				console.sendText("Channel removed");
			} catch (Exception e) {
				console.sendText("Error on remove channel");
			}
		} else {
			Type.messageType(MessageType.Error, "Args for use this command");
			Type.messageType(MessageType.Error, "message set <server id> <channel id>");
			Type.messageType(MessageType.Error, "message send <text>");
			Type.messageType(MessageType.Error, "message clear");
		}
		return true;
	}

	private String getMention(String Text) {
		String toReturn = Text;
		List<String> mention = new ArrayList<String>();
		if (Text.contains("@")) {
			for (String itens : Text.split(" ")) {
				if (itens.startsWith("@")) {
					mention.add(itens);
				}
			}
		}
		HashMap<String, String> userNamement = new HashMap<String, String>();
		if (!mention.isEmpty()) {
			for (String ment : mention) {
				try {
					userNamement.put(ment, getBotListItems.getMembers(channel.getGuild(), true, ment.replace("@", ""))
							.get(0).getAsMention());
				} catch (Exception e) {
				}
			}
			for (String txt : mention) {
				if (userNamement.containsKey(txt)) {
					try {
						toReturn = toReturn.replace(txt, userNamement.get(txt));
					} catch (Exception e) {
					}
					
				}
			}
		}

		return toReturn;
	}

	private String argsToString(String[] args, Integer index) {
		String myString = "";
		for (int i = index; i < args.length; i++) {
			String arg = args[i] + " ";
			myString = myString + arg;
		}
		myString = myString.substring(0, myString.length());
		if (myString.startsWith(" ")) {
			myString = myString.substring(1, myString.length());
		}
		return myString;
	}
}
