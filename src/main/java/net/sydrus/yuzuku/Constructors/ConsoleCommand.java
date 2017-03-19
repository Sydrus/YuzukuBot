package net.sydrus.yuzuku.Constructors;

import net.sydrus.yuzuku.Managers.ConsoleCommand.ConsoleDef;

public abstract class ConsoleCommand {

	public abstract boolean onCommand(ConsoleDef console, String command, String time, String[] args);

}
