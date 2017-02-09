package net.sydrus.yuzuku.Commands;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Constructors.Command;
import net.sydrus.yuzuku.Managers.LevelType;

public class status extends Command {

	@Override
	public boolean onCommand(User Sender, Message Message, Guild Guild, TextChannel Chat, List<LevelType> type,
			Boolean isEdited, String[] args) {
		List<String> statusItems = new ArrayList<String>();
		statusItems.add("#Bot Status:\n\n");
		statusItems.add("**Version:** " + YuzukuBot.getInstance().Version + "\n");
		statusItems.add("**Discord:**");
		statusItems.add("	**Status:** " + YuzukuBot.getInstance().Status);
		statusItems.add("	**Guilds:** " + YuzukuBot.getInstance().getJDA().getGuilds().size() + "\n");
		statusItems.add("	**Commands:** " + YuzukuBot.getInstance().getCommandManager().getCommands().count());
		statusItems.add("	**Sub Commands:** " + YuzukuBot.getInstance().subCommands + "\n");
		statusItems.add("**BOT Prefix:** " + YuzukuBot.guildManager.getPrefix(Guild) + "\n");
		statusItems.add("**Time Online:** " + YuzukuBot.getInstance().tmanager.toString() + "\n");
		statusItems.add("**Errors:** " + YuzukuBot.getInstance().botErrors + "\n");
		String endItems = "";
		for (String itemslist : statusItems) {
			endItems = endItems + itemslist + "\n";
		}

		Chat.sendMessage(embedMessage(endItems)).queue();
		return true;
	}

}
