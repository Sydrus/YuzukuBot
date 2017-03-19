package net.sydrus.yuzuku.Managers.ConsoleCommand.Commands;

import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Constructors.ConsoleCommand;
import net.sydrus.yuzuku.Managers.ConsoleCommand.ConsoleDef;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;

public class unlock extends ConsoleCommand {

	@Override
	public boolean onCommand(ConsoleDef console, String command, String time, String[] args) {
		if (YuzukuBot.getInstance().getusers(/*this.getClass()*/).isEmpty()) {
			YuzukuBot.getInstance().console.setLockStatus(this.getClass(), false);
			Type.messageType(MessageType.Info,"Unlocked");
			Type.messageType(MessageType.Warning,"**No account is registered, type \"regc <mail> <pass>\" to register an account**");
			return true;
		}
		if (args.length == 2) {
			if (YuzukuBot.getInstance().getusers(/*this.getClass()*/).contains(args[0])) {
				if (YuzukuBot.getInstance().getusers(/*this.getClass()*/).getString(args[0]).equals(args[1])) {
					YuzukuBot.getInstance().console.setLockStatus(this.getClass(), false);
					Type.messageType(MessageType.Info,"Unlocked");
				} else {
					Type.messageType(MessageType.Error, "**Invalid Mail or Passworkd**");
				}
			} else {
				Type.messageType(MessageType.Error, "**Invalid Mail or Password**");
			}
		} else {
			Type.messageType(MessageType.Error, "**Use** \"unlock <mail> <pass>\"");
		}
		return true;
	}

}
