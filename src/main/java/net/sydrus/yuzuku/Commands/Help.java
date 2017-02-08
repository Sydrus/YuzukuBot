package net.sydrus.yuzuku.Commands;

import java.util.List;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Constructors.Command;
import net.sydrus.yuzuku.Managers.CommandType;
import net.sydrus.yuzuku.Managers.HelpManager;
import net.sydrus.yuzuku.Managers.LevelType;
import net.sydrus.yuzuku.Managers.HelpManager.HelpType;
import net.sydrus.yuzuku.addon.CommandInfo;

public class Help extends Command {

	@Override
	public boolean onCommand(User Sender, Message Message, Guild Guild, TextChannel Chat, List<LevelType> type,
			Boolean isEdited, String[] args) {
		HelpManager adml = new HelpManager();
		adml.setPrefix("{s}", YuzukuBot.guildManager.getPrefix(Guild));
		List<CommandInfo> info = YuzukuBot.getInstance()._CommandManager.getCommands().getCommands(CommandType.Bot);

		for (CommandInfo ifo : info) {
			String command = ifo.command;
			String comment = ifo.comment;
			String level = "";
			if (ifo.level == LevelType.Administrator) {
				level = " (Administrator Only)";
			} else if (ifo.level == LevelType.Developer) {
				level = " (Developer Only)";
			} else if (ifo.level == LevelType.ServerOwner) {
				level = " (Owner Only)";
			}
			adml.addItem("   {s}" + command, comment + level);
		}

		Chat.sendMessage(adml.getEmbedManager(HelpType.UseComment).getMessage()).queue();

		return true;
	}
}
