package net.sydrus.yuzuku.Managers.ConsoleCommand;

import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;

public class ConsoleDef {

	private Console console;

	public ConsoleDef(Console console) {
		this.console = console;
	}

	public void sendText(String text) {
		System.out.println(Type.timeToConsole() + "[Info] " + text);
	}
	
	public void sendText(MessageType type, String text) {
		String toReturn = text;
		if (type == MessageType.Error) {
			toReturn = Type.timeToConsole() + "[Error] " + text;
			System.err.println(toReturn);
		}
		if (type == MessageType.Info) {
			toReturn = Type.timeToConsole() + "[Info] " + text;
			System.out.println(toReturn);
		}

		if (type == MessageType.Warning) {
			toReturn = Type.timeToConsole() + "[Warning] " + text;
			System.out.println(toReturn);
		}
		if (type == MessageType.Uncknow) {
			toReturn = Type.timeToConsole() + "[Uncknow] " + text;
			System.out.println(toReturn);
		}
	}
	
	public Console getConsole() {
		return this.console;
	}

}
