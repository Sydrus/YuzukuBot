package net.sydrus.yuzuku.Managers.ConsoleCommand.Commands;

import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Constructors.ConsoleCommand;
import net.sydrus.yuzuku.Managers.ConsoleCommand.ConsoleDef;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;

public class disableConsole extends ConsoleCommand {

	@Override
	public boolean onCommand(ConsoleDef console, String command, String time, String[] args) {
		if (args.length == 2) {
			YuzukuBot.getInstance().settingsData.set("ConsoleCommands", false);
			YuzukuBot.getInstance().settingsData.save();
			System.out.println("Console disabled");
			YuzukuBot.getInstance().console.updateState();
		} else {
			Type.messageType(MessageType.Error, "Use \"disableconsole <mail> <pass>\"");
		}
		return true;
	}

}
