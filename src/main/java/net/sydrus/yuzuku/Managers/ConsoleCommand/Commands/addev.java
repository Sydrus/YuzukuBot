package net.sydrus.yuzuku.Managers.ConsoleCommand.Commands;

import java.util.List;

import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Constructors.ConsoleCommand;
import net.sydrus.yuzuku.FileReader.BCRItem;
import net.sydrus.yuzuku.Managers.ConsoleCommand.ConsoleDef;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;

public class addev extends ConsoleCommand {

	@Override
	public boolean onCommand(ConsoleDef console, String command, String time, String[] args) {
		if (args.length == 2) {
			YuzukuBot.getInstance().settingsData.reload();
			List<Object> dev = YuzukuBot.getInstance().settingsData.getList(BCRItem.getItem(BCRItem.Type.Developers));
			if(dev.contains(args[1])) {
				Type.messageType(MessageType.Error, "**Has already been added**");
				return true;
			}
			dev.add(args[1]);
			YuzukuBot.getInstance().settingsData.set(BCRItem.getItem(BCRItem.Type.Developers), dev);
			YuzukuBot.getInstance().settingsData.save();
			Type.messageType(MessageType.Info, "Added");
		} else {
			 Type.messageType(MessageType.Error, "Use \"adDeveloper <encode> <user id>\"");
		}
		return true;
	}

}
