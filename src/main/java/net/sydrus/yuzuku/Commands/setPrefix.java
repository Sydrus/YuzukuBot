package net.sydrus.yuzuku.Commands;

import java.awt.Color;
import java.util.List;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Constructors.Command;
import net.sydrus.yuzuku.FileReader.ConfigReader;
import net.sydrus.yuzuku.Managers.LevelType;

public class setPrefix extends Command {

	@Override
	public boolean onCommand(User Sender, Message Message, Guild Guild, TextChannel Chat, List<LevelType> type,
			Boolean isEdited, String[] args) {
		String prefix = "!, $, %, &, *, (, ), _, -, =, +, /, ?, <, >, ;, :, \",\", \".\"";
		if ((args.length == 1) && (args[0].equalsIgnoreCase("reset"))) {
			try {
				ConfigReader reader = YuzukuBot.guildManager.getServerConfig(Guild);
				if (reader.contains("guildPrefix")) {
					reader.set("guildPrefix", null);
					reader.save();
					Chat.sendMessage(embedMessage("**Character has ben reseted**")).queue();
				} else {
					Chat.sendMessage(embedMessage("**No prefix setted**", Color.RED)).queue();
				}
			} catch (Exception e) {
				Chat.sendMessage(embedMessage("**Error on choose prefix!**", Color.RED)).queue();
			}

		} else if (args.length == 1) {
			if (args[0].length() == 1) {
				if (prefix.contains(args[0])) {
					Chat.sendMessage(embedMessage("**Character changed to:** (" + args[0] + ")")).queue();
					YuzukuBot.guildManager.setPrefix(Guild, args[0].charAt(0));
				} else {
					Chat.sendMessage(embedMessage("**Enter a prefix that contains in the list** \n " + prefix)).queue();
				}
			} else {
				Chat.sendMessage(embedMessage("**Enter an argument that contains only one character**")).queue();
			}
		} else {
			Chat.sendMessage(
					embedMessage("**Enter a prefix that contains only one character and is in the list** \n " + prefix+"\n **Or type setprefix reset**"))
					.queue();
		}
		return true;
	}

}
