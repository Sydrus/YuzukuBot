package net.sydrus.yuzuku.Commands;

import java.util.List;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Constructors.Command;
import net.sydrus.yuzuku.Managers.LevelType;

public class add extends Command {

	@Override
	public boolean onCommand(User Sender, Message Message, Guild Guild, TextChannel Chat, List<LevelType> type,
			Boolean isEdited, String[] args) {
		Chat.sendMessage(embedMessage("Hi, I'm " + YuzukuBot.botName + ". Add me to your discord server, for do that use the link: https://discordapp.com/oauth2/authorize?&client_id=278693792297779200&scope=bot&permissions=66321471")).queue();;
		return true;
	}

}
