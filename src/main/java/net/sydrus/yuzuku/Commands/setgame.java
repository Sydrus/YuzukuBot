package net.sydrus.yuzuku.Commands;

import java.awt.Color;
import java.util.List;

import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Constructors.Command;
import net.sydrus.yuzuku.Managers.LevelType;
import net.sydrus.yuzuku.String.Administratives;

public class setgame extends Command {

	@Override
	public boolean onCommand(User Sender, Message Message, Guild Guild, TextChannel Chat, List<LevelType> type,
			Boolean isEdited, String[] args) {
		if (!type.contains(LevelType.Developer)) {
			Chat.sendMessage(embedMessage(Administratives.AdminOnly, Color.RED)).queue();
			;
			return true;
		}
		if (args.length == 0) {
			Chat.sendMessage(embedMessage("Game", Color.YELLOW)).queue();
		} else if (args.length > 0) {
			YuzukuBot.getInstance().getJDA().getPresence().setGame(Game.of(argsToString(args, 0)));
			Chat.sendMessage(embedMessage("**Now Playing:** " + argsToString(args, 0), Color.CYAN)).queue();
		}
		return true;
	}

}
