package net.sydrus.yuzuku.Managers.ConsoleCommand;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.FileReader.ConfigReader;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;

public class Console {

	private BufferedReader scan;
	//private boolean isLocked = true;
	private boolean autolock = true;
	private RegisterCommand cmds;
	private ConfigReader config = null;
	private Thread t1 = null;

	public void start() {
		scan = new BufferedReader(new InputStreamReader(System.in));
		t1 = new Thread(runCode());
		t1.start();
		cmds = new RegisterCommand();
		config = YuzukuBot.getInstance().settingsData;
	}

	private Console getConsole() {
		return this;
	}

	public final void updateState(boolean value) {

	}

	public final void updateState() {

	}

	private Runnable runCode() {
		return new Runnable() {
			public void run() {
				String scanned = "";
				while (true) {
					try {
						scanned = scan.readLine();
						if (/*enabled*/true) {
							config.reload();
							if (!scanned.isEmpty()) {
									if (cmds.getCmdManager().contains(getArray(scanned)[0])) {
										if (getArray(scanned).length == 1) {
											cmds.getCmdManager().get(getArray(scanned)[0].toLowerCase()).onCommand(
													new ConsoleDef(getConsole()), scanned, "null", new String[] {});
										} else {
											cmds.getCmdManager().get(getArray(scanned)[0].toLowerCase()).onCommand(
													new ConsoleDef(getConsole()), scanned, "null", getCommand(scanned));
										}
									} else {
										Type.messageType(MessageType.Error, "This command does not exist");
									}
								//}
							} else {
								Type.messageType(MessageType.Warning, "Type something");
							}
						}
					} catch (Exception e) {
						// System.out.println(e.getMessage());
						YuzukuBot.getInstance().botErrors++;
					}

				}
			}
		};
	}

	public void setLockStatus(Class<?> clas, boolean value) {
		if (clas.getName().equals("net.sydrus.yuzuku.Managers.ConsoleCommand.Commands.unlock")) {
			//this.isLocked = value;
			if ((value == false) && (autolock == true)) {
				//this.isLocked = value;

			} else if (value == true) {

			}
		} else {
			throw new RuntimeException("Invalid Class Permission");
		}
	}

	private String[] getArray(String args) {
		return args.split(" ");
	}

	private String argsToString(String args, Integer index) {
		String myString = "";
		String[] argh = args.split(" ");
		for (int i = index; i < argh.length; i++) {
			String arg = argh[i] + " ";
			myString = myString + arg;
		}
		myString = myString.substring(0, myString.length());
		if (myString.startsWith(" ")) {
			myString = myString.substring(1, myString.length());
		}
		return myString;
	}

	private String[] getCommand(String args) {
		return argsToString(args, 1).split(" ");
	}

}
