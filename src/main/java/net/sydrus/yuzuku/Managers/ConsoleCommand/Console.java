package net.sydrus.yuzuku.Managers.ConsoleCommand;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.FileReader.ConfigReader;
import net.sydrus.yuzuku.Managers.timeManager;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;

public class Console {

	private BufferedReader scan;
	//private boolean isLocked = true;
	private boolean autolock = true;
	private RegisterCommand cmds;
	private timeManager tmanager = new timeManager();
	private ConfigReader config = null;
	private Thread t1 = null;
	//private Thread t2 = null;
	//private boolean enabled = false;

	public void start() {
		scan = new BufferedReader(new InputStreamReader(System.in));
		t1 = new Thread(runCode());
		t1.start();
		/*t2 = new Thread(lockTime());
		t2.start();*/
		cmds = new RegisterCommand();
		config = YuzukuBot.getInstance().settingsData;
		//enabled = config.getBoolean("ConsoleCommands");
		tmanager.clear();
	}

	private Console getConsole() {
		return this;
	}

	public final void updateState(boolean value) {
		//enabled = value;
	}

	public final void updateState() {
		//enabled = config.getBoolean("ConsoleCommands");
	}

	/*private Runnable lockTime() {
		return new Runnable() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
					if ((autolock == true) && (isLocked == false)) {
						if (isTime()) {
							isLocked = true;
							tmanager.stopCount();
							tmanager.clear();
							Type.messageType(MessageType.Warning, "Console locked");
						}
					}
				}
			}
		};
	}*/

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
								/*if (isLocked) {
									if (getArray(scanned)[0].equalsIgnoreCase("unlock")) {
										if (cmds.getCmdManager().contains("unlock")) {
											cmds.getCmdManager().get("unlock").onCommand(new ConsoleDef(getConsole()),
													scanned, "null", getCommand(scanned));
										} else {
											Type.messageType(MessageType.Warning,
													"Error initializing the command \"unlock\" contact with the developer, because this command is essential to be able to use the console as it is the only command that the unlock");
										}

									} else {
										Type.messageType(MessageType.Error,
												"the use of the console this blocked, please use the \"unlock\" command");
									}
								} else {*/
									if (cmds.getCmdManager().contains(getArray(scanned)[0])) {
										if (getArray(scanned).length == 1) {
											cmds.getCmdManager().get(getArray(scanned)[0].toLowerCase()).onCommand(
													new ConsoleDef(getConsole()), scanned, "null", new String[] {});
										} else {
											cmds.getCmdManager().get(getArray(scanned)[0].toLowerCase()).onCommand(
													new ConsoleDef(getConsole()), scanned, "null", getCommand(scanned));
										}
										tmanager.clear();
									} else {
										Type.messageType(MessageType.Error, "This command does not exist");
									}
								//}
							} else {
								Type.messageType(MessageType.Warning, "Type something");
							}
						} /*else {

							if (scanned.equalsIgnoreCase("enable")) {
								if (config.contains("consolePass")) {
									if (config.getString("consolePass").equalsIgnoreCase(argsToString(scanned, 1))) {
										config.set("ConsoleCommands", true);
										config.save();
										enabled = true;
										Type.messageType(MessageType.God, "the Console has been enabled");
									} else {
										Type.messageType(MessageType.Error, "Invalid pass");
									}
								} else {
									config.set("ConsoleCommands", true);
									config.save();
									enabled = true;
									Type.messageType(MessageType.God, "the Console has been enabled");
								}
							} else {
								Type.messageType(MessageType.Error,
										"the use of the console this blocked, please use the \"enable <password>\" command");
							}
						}*/
					} catch (Exception e) {
						// System.out.println(e.getMessage());
						YuzukuBot.getInstance().botErrors++;
					}

				}
			}
		};
	}

	/*private boolean isTime() {
		LinkBot.getInstance().settingsData.reload();
		String givetype = LinkBot.getInstance().settingsData.getString("TimeToLock");
		try {
			if (getArray(givetype).length != 2) {
				Type.messageType(MessageType.Warning, "invalid time: \"" + givetype + "\", Choosing time for \"1 m\"");
			}
			int intvalue = 0;
			try {
				intvalue = Integer.parseInt(getArray(givetype)[0]);
			} catch (Exception e) {
				Type.messageType(MessageType.Warning,
						"Error on convert time: \"" + givetype + "\", Choosing time for \"1 m\"");
				LinkBot.getInstance().settingsData.set("TimeToLock", "10 s");
				LinkBot.getInstance().settingsData.save();
			}
			if (getArray(givetype)[1].equals("m")) {
				if ((intvalue > 59) || (intvalue < 1)) {
					Type.messageType(MessageType.Warning,
							"invalid time: \"" + givetype + "\", Choosing time for \"1 m\"");
					LinkBot.getInstance().settingsData.set("TimeToLock", "10 s");
					LinkBot.getInstance().settingsData.save();
				}
				if (tmanager.minute == intvalue) {
					return true;
				}
			} else if (getArray(givetype)[1].equals("s")) {
				if ((intvalue > 59) || (intvalue < 10)) {
					Type.messageType(MessageType.Warning,
							"invalid time: \"" + givetype + "\", Choosing time for \"1 m\"");
					LinkBot.getInstance().settingsData.set("TimeToLock", "10 s");
					LinkBot.getInstance().settingsData.save();
				}
				if (tmanager.second == intvalue) {
					return true;
				}
			} else {
				Type.messageType(MessageType.Warning, "invalid time: \"" + givetype + "\", Choosing time for \"1 m\"");
				LinkBot.getInstance().settingsData.set("TimeToLock", "10 s");
				LinkBot.getInstance().settingsData.save();
			}
		} catch (Exception e) {
			Type.messageType(MessageType.Warning, "invalid time: \"" + givetype + "\", Choosing time for \"1 m\"");
			LinkBot.getInstance().getJDA().shutdown();
			LinkBot.getInstance().settingsData.set("TimeToLock", "1 m");
			LinkBot.getInstance().settingsData.save();
			LinkBot.getInstance().settingsData.reload();
			LinkBot.getInstance().botErrors++;
		}

		return false;
	}*/

	public void setLockStatus(Class<?> clas, boolean value) {
		if (clas.getName().equals("net.sydrus.yuzuku.Managers.ConsoleCommand.Commands.unlock")) {
			//this.isLocked = value;
			if ((value == false) && (autolock == true)) {
				//this.isLocked = value;
				tmanager.startCount();
			} else if (value == true) {
				tmanager.stopCount();
				tmanager.clear();
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
