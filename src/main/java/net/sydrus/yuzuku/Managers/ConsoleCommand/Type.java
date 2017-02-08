package net.sydrus.yuzuku.Managers.ConsoleCommand;

import java.util.Calendar;
import java.util.TimeZone;

public class Type {

	public enum MessageType {
		Info, Warning, Error, Uncknow, God
	}

	public static void messageType(MessageType type, String text) {
		String toReturn = text;
		if (type == MessageType.Error) {
			toReturn = timeToConsole() + "[Error] " + text;
			System.err.println(toReturn);
		}
		if (type == MessageType.Info) {
			toReturn = timeToConsole() + "[Info] " + text;
			System.out.println(toReturn);
		}

		if (type == MessageType.Warning) {
			toReturn = timeToConsole() + "[Warning] " + text;
			System.out.println(toReturn);
		}
		if (type == MessageType.God) {
			toReturn = timeToConsole() + "[God] " + text;
			System.out.println(toReturn);
		}
		if (type == MessageType.Uncknow) {
			toReturn = timeToConsole() + "[Uncknow] " + text;
			System.out.println(toReturn);
		}
	}

	public static String timeToConsole() {
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		return "[" + hour + ":" + minute + ":" + second + "] ";
	}

}