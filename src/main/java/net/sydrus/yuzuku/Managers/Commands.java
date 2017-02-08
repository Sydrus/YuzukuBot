package net.sydrus.yuzuku.Managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sydrus.yuzuku.Constructors.Command;
import net.sydrus.yuzuku.addon.CommandInfo;

public final class Commands {
	private Map<String, Command> cmd = new HashMap<String, Command>();
	private Map<String, CommandInfo> info = new HashMap<String, CommandInfo>();

	public void register(CommandInfo comInfo, Command path) {
		if (info.containsKey(comInfo.command)) {
			try {
				throw new BotCommand(comInfo.command);
			} catch (BotCommand e) {
				e.printStackTrace();
			}
		} else {
			this.info.put(comInfo.command.toLowerCase(), comInfo);
			cmd.put(comInfo.command.toLowerCase(), path);
		}
	}

	/*
	 * public void unRegister(String command) {
	 * cmd.remove(command.toLowerCase()); }
	 * 
	 * public void unRegister(Command command) { cmd.remove(command); }
	 */

	public Set<String> keySet() {
		return this.cmd.keySet();
	}

	public final CommandInfo getCommandInfo(String command) {
		return this.info.get(command);
	}

	public List<CommandInfo> getCommands(CommandType type) {
		List<CommandInfo> toReturn = new ArrayList<CommandInfo>();
		for (CommandInfo ifo : info.values()) {
			if (!ifo.hideFromList) {
				if (ifo.getType() == type) {
					toReturn.add(ifo);
				}
			}
		}
		return toReturn;
	}

	public List<CommandInfo> getCommands(CommandType type, boolean getHide) {
		List<CommandInfo> toReturn = new ArrayList<CommandInfo>();
		for (CommandInfo ifo : info.values()) {
			if (ifo.getType() == type) {
				toReturn.add(ifo);
			}
		}
		return toReturn;
	}

	public void registerCommands(Commands command) {
		for (String commandname : command.keySet()) {
			if (info.containsKey(commandname)) {
				try {
					throw new BotCommand(commandname);
				} catch (BotCommand e) {
					e.printStackTrace();
				}
			} else {
				CommandInfo inf = command.getCommandInfo(commandname);
				this.register(inf, command.get(commandname));
			}

		}
	}

	public boolean contains(String command) {
		return cmd.containsKey(command.toLowerCase());
	}

	public Command get(String command) {
		return cmd.get(command);
	}

	public int count() {
		return cmd.size();
	}

	public boolean isEmpty() {
		return cmd.isEmpty();
	}

	public List<Command> getCommands() {
		List<Command> cmdtr = new ArrayList<Command>();
		for (Command cmdtadd : cmd.values()) {
			cmdtr.add(cmdtadd);
		}
		return null;
	}

	public List<String> getNames() {
		List<String> itemtr = new ArrayList<String>();
		for (String items : cmd.keySet()) {
			itemtr.add(items);
		}
		return null;
	}

	public void clear() {
		cmd.clear();
	}

	public void replace(String key, Command newValue) {
		cmd.replace(key, newValue);
	}

	public final void unregisterCommands(Commands commands) {
		for (String commandname : commands.keySet()) {
			if (this.contains(commandname)) {
				this.cmd.remove(commandname);
			}
		}
	}
}
