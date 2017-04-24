package net.sydrus.yuzuku.Managers;

import net.sydrus.yuzuku.Commands.*;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.plugin.CommandInfo;

public class CommandManager {

	private Commands cmd = new Commands();

	public CommandManager() {
		cmd.register(new CommandInfo("adm", " - **View the administrative commands**", LevelType.Administrator),
				new AdmCommand());
		// cmd.register("yt", new YtCommand()); Closed, Error Class

		cmd.register(new CommandInfo("stop", " - **Disconnect bot**", LevelType.Developer), new StopCommand());
		cmd.register(new CommandInfo("help", " - **See bot commands**", LevelType.User), new Help());
		cmd.register(new CommandInfo("clear", " - **Clear chat**", LevelType.Administrator), new ClearChat());
		cmd.register(new CommandInfo("setgame", " - **Set the bot game**", LevelType.Developer), new setgame());
		cmd.register(new CommandInfo("status", " - **Show the bot status**", LevelType.User), new status());
		// cmd.register("vote", new vote());
		cmd.register(new CommandInfo("myin", " - **Show your user's data**", LevelType.User), new mydata());
		cmd.register(new CommandInfo("uinfo", " - **Show a user's information**", LevelType.Administrator),
				new infouser());
		/*
		cmd.register(new CommandInfo("oserver", " - **Official server**", LevelType.User), new OficialServer());
		*/
		cmd.register(new CommandInfo("plugins", " - **Show the Plugins running now**", LevelType.Developer),
				new addonsStatusCommand());
		cmd.register(new CommandInfo("plhelp", " - **Show plugins commands**", LevelType.User), new HelpAddons());
		cmd.register(new CommandInfo("music", " - **Play music**", LevelType.User), new music());
		cmd.register(new CommandInfo("setprefix", " - **Set the prefix to use the bot**", LevelType.ServerOwner),
				new setPrefix());
		cmd.register(new CommandInfo("add", " - **Get the link to add to " + YuzukuBot.botName + " user discord guild**",
				LevelType.User), new add());
		cmd.register(new CommandInfo("pgif", " - **Random porn gif [+18] [NSFW]**",
				LevelType.User), new NSFW());

	}

	public Commands getCommands() {
		return this.cmd;
	}

}
