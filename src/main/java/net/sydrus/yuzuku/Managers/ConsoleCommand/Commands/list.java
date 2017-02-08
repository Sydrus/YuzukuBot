package net.sydrus.yuzuku.Managers.ConsoleCommand.Commands;

import java.util.List;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Constructors.ConsoleCommand;
import net.sydrus.yuzuku.Managers.ConsoleCommand.ConsoleDef;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;

public class list extends ConsoleCommand {

	@Override
	public boolean onCommand(ConsoleDef console, String command, String time, String[] args) {
		if ((args.length == 1) && (args[0].equalsIgnoreCase("server"))) {
			if (YuzukuBot.getInstance().getJDA().getGuilds().isEmpty()) {
				Type.messageType(MessageType.Warning, "No server available");
				return true;
			}
			System.out.println("");
			Type.messageType(MessageType.Info, "===Servers===");
			for (Guild item : YuzukuBot.getInstance().getJDA().getGuilds()) {
				Type.messageType(MessageType.Info, "Name: " + item.getName() + " id(" + item.getId() + ") ");
			}
		} else if ((args.length == 2) && (args[0].equalsIgnoreCase("tchannel"))) {
			try {
				List<TextChannel> channels = YuzukuBot.getInstance().getJDA().getGuildById(args[1]).getTextChannels();
				System.out.println("");
				Type.messageType(MessageType.Info, "===Text Channels of " + YuzukuBot.getInstance().getJDA().getGuildById(args[1]).getName() + "===");
				for (TextChannel channel : channels) {
					Type.messageType(MessageType.Info,"Name: " + channel.getName() + " id(" + channel.getId() + ") ");
				}
			} catch (Exception e) {
				Type.messageType(MessageType.Error, "Uncknow server");
			}
		} else if ((args.length == 2) && (args[0].equalsIgnoreCase("vchannel"))) {
			try {
				List<VoiceChannel> channels = YuzukuBot.getInstance().getJDA().getGuildById(args[1]).getVoiceChannels();
				System.out.println("");
				Type.messageType(MessageType.Info, "=== Text Channels of " + YuzukuBot.getInstance().getJDA().getGuildById(args[1]).getName() + "===");
				for (VoiceChannel channel : channels) {
					Type.messageType(MessageType.Info, "Name: " + channel.getName() + " id(" + channel.getId() + ") ");
				}
			} catch (Exception e) {
				Type.messageType(MessageType.Error, "Uncknow server");
			}
		} else if ((args.length == 2) && (args[0].equalsIgnoreCase("users"))) {
			try {
				List<Member> users = YuzukuBot.getInstance().getJDA().getGuildById(args[1]).getMembers();
				System.out.println("");
				Type.messageType(MessageType.Info, "=== Text Channels of " + YuzukuBot.getInstance().getJDA().getGuildById(args[1]).getName() + "===");
				for (Member user : users) {
					Type.messageType(MessageType.Info, "Name: " + user.getUser().getName() + " id(" + user.getUser().getId() + ") ");
				}
			} catch (Exception e) {
				Type.messageType(MessageType.Error, "Uncknow server");
			}
		} else {
			Type.messageType(MessageType.Error, "Type \"server\", \"tchannel serverId\", \"vchannel serverId\", \"users serverId\"");
		}

		return true;
	}
}
