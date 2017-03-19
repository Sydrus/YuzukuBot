package net.sydrus.yuzuku.Managers.ConsoleCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sydrus.yuzuku.Constructors.ConsoleCommand;

public class ConsoleCmdManager {
	private Map<String, ConsoleCommand> cmd = new HashMap<String, ConsoleCommand>();

	public void register(String command, ConsoleCommand path) {
		cmd.put(command.toLowerCase(), path);
	}

	public Set<String> keySet() {
		return this.cmd.keySet();
	}

	public boolean contains(String command) {
		return cmd.containsKey(command.toLowerCase());
	}

	public ConsoleCommand get(String command) {
		return cmd.get(command);
	}

	public int count() {
		return cmd.size();
	}

	public boolean isEmpty() {
		return cmd.isEmpty();
	}

	public List<ConsoleCommand> getCommands() {
		List<ConsoleCommand> cmdtr = new ArrayList<ConsoleCommand>();
		for (ConsoleCommand cmdtadd : cmd.values()) {
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
}
