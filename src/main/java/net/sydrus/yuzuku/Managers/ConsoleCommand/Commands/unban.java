package net.sydrus.yuzuku.Managers.ConsoleCommand.Commands;


import net.dv8tion.jda.core.entities.Guild;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Constructors.ConsoleCommand;
import net.sydrus.yuzuku.Managers.ConsoleCommand.ConsoleDef;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;

public class unban extends ConsoleCommand {

	@Override
	public boolean onCommand(ConsoleDef console, String command, String time, String[] args) {
		if (args.length == 2) {
			Guild gd = YuzukuBot.getInstance().getJDA().getGuildById(args[0]);
			gd.getController().unban(args[1]);
		} else {
			Type.messageType(MessageType.Error, "Use \"unban  <serverId> <user id>\"");
		}
		return true;
	}

}
