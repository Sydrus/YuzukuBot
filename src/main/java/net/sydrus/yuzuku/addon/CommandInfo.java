package net.sydrus.yuzuku.addon;

import net.sydrus.yuzuku.Managers.CommandType;
import net.sydrus.yuzuku.Managers.LevelType;

public class CommandInfo {
	public String command;
	public String comment;
	public LevelType level;
	public boolean hideFromList = false;
	private CommandType type = CommandType.Bot;

	public CommandInfo(String command, String comment, LevelType level) {
		this.command = command;
		this.comment = comment;
		this.level = level;
	}

	public CommandInfo(String command, String comment, LevelType level, boolean hideFromList) {
		this.command = command;
		this.comment = comment;
		this.level = level;
		this.hideFromList = hideFromList;
	}

	public final void setType(CommandType type) {
		this.type = type;
	}

	public CommandType getType() {
		return this.type;
	}
}
