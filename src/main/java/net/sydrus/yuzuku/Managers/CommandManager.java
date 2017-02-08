package net.sydrus.yuzuku.Managers;

import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Commands.AdmCommand;
import net.sydrus.yuzuku.Commands.ClearChat;
import net.sydrus.yuzuku.Commands.Help;
import net.sydrus.yuzuku.Commands.HelpAddons;
import net.sydrus.yuzuku.Commands.StopCommand;
import net.sydrus.yuzuku.Commands.add;
import net.sydrus.yuzuku.Commands.addonsStatusCommand;
import net.sydrus.yuzuku.Commands.infouser;
import net.sydrus.yuzuku.Commands.music;
import net.sydrus.yuzuku.Commands.mydata;
import net.sydrus.yuzuku.Commands.setPrefix;
import net.sydrus.yuzuku.Commands.setgame;
import net.sydrus.yuzuku.Commands.status;
import net.sydrus.yuzuku.addon.CommandInfo;

public class CommandManager {

	private Commands cmd = new Commands();

	public CommandManager() {
		cmd.register(new CommandInfo("adm", " - **View the administrative commands**", LevelType.Administrator),
				new AdmCommand());
		// cmd.register("yt", new YtCommand()); Closed, Error Class

		cmd.register(new CommandInfo("stop", " - **Disconnect bot**", LevelType.Developer), new StopCommand());
		cmd.register(new CommandInfo("help", " - **See bot commands**", LevelType.User), new Help());
		cmd.register(new CommandInfo("rm", " - **Clear chat**", LevelType.Administrator), new ClearChat());
		cmd.register(new CommandInfo("setgame", " - **Set the bot game**", LevelType.Developer), new setgame());
		cmd.register(new CommandInfo("status", " - **View bot status**", LevelType.User), new status());
		// cmd.register("vote", new vote());
		cmd.register(new CommandInfo("mydata", " - **View your data**", LevelType.User), new mydata());
		cmd.register(new CommandInfo("addons", " - **View the addons by running the bot**", LevelType.Developer),
				new addonsStatusCommand());
		cmd.register(new CommandInfo("uinfo", " - **View a user's information**", LevelType.Administrator),
				new infouser());
		cmd.register(new CommandInfo("ahelp", " - **View addons commands**", LevelType.User), new HelpAddons());
		cmd.register(new CommandInfo("music", " - **Play music**", LevelType.User), new music());
		cmd.register(new CommandInfo("setprefix", " - **Set the prefix to use the bot**", LevelType.ServerOwner),
				new setPrefix());
		cmd.register(new CommandInfo("add", " - **Get the link to add to " + YuzukuBot.botName + " user discord guild**",
				LevelType.User), new add());
	}

	public Commands getCommands() {
		return this.cmd;
	}

}
