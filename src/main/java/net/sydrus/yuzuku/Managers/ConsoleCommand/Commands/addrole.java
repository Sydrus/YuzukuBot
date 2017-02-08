package net.sydrus.yuzuku.Managers.ConsoleCommand.Commands;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Constructors.ConsoleCommand;
import net.sydrus.yuzuku.Managers.ConsoleCommand.ConsoleDef;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;

public class addrole extends ConsoleCommand {

	@Override
	public boolean onCommand(ConsoleDef console, String command, String time, String[] args) {
		if (args.length == 3) {
			Guild gd =YuzukuBot.getInstance().getJDA().getGuildById(args[0]);
			gd.getController().addRolesToMember(gd.getMemberById(args[1]), gd.getRoleById(args[2])).queue();
			try {
				gd.getController().addRolesToMember(gd.getMemberById(args[1]), gd.getRoleById(args[2])).block();
			} catch (RateLimitedException e) {
				e.printStackTrace();
			}
		} else {
			 Type.messageType(MessageType.Error, "Use \"adrole <encode> <user id>\"");
		}
		return true;
	}

}
