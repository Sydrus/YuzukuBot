package net.sydrus.yuzuku.Managers.ConsoleCommand.Commands;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Constructors.ConsoleCommand;
import net.sydrus.yuzuku.Managers.ConsoleCommand.ConsoleDef;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;

public class ViewServerRoles extends ConsoleCommand {

	@Override
	public boolean onCommand(ConsoleDef console, String command, String time, String[] args) {
		if (args.length == 1) {
			Guild guild = null;
			try {
				guild = YuzukuBot.getInstance().getJDA().getGuildById(args[0]);
			} catch (Exception e) {
				Type.messageType(MessageType.Error, "Error on get server");
				return true;
			}
			try {
				console.sendText("Server Roles");
				for (Role rl : guild.getRoles()) {
					console.sendText("Role name: " + rl.getName() + ". id(" + rl.getId() + ")");
				}
			} catch (Exception e) {
				Type.messageType(MessageType.Error, "Error on get server roles");
			}
		} else {
			Type.messageType(MessageType.Error, "Use \"serverrole <serverId>\"");
		}
		return true;
	}

}
