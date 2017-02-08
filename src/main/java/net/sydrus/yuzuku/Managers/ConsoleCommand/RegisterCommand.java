package net.sydrus.yuzuku.Managers.ConsoleCommand;

import net.sydrus.yuzuku.Managers.ConsoleCommand.Commands.ViewRolesPermissions;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Commands.ViewServerRoles;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Commands.addev;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Commands.ban;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Commands.disableConsole;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Commands.info;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Commands.list;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Commands.message;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Commands.stop;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Commands.unban;

public class RegisterCommand {
	private ConsoleCmdManager command = new ConsoleCmdManager();

	public RegisterCommand() {
		//command.register("unlock", new unlock());
		command.register("stop", new stop());
		command.register("disableconsole", new disableConsole());
		command.register("list", new list());
		command.register("addeveloper", new addev());
		command.register("info", new info());
		command.register("message", new message());
		command.register("serverrole", new ViewServerRoles());
		command.register("proles", new ViewRolesPermissions());
		command.register("ban", new ban());
		command.register("unban", new unban());
	}

	public ConsoleCmdManager getCmdManager() {
		return command;
	}
}
