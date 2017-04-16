package net.sydrus.yuzuku.plugin;

import net.sydrus.yuzuku.Constructors.Command;
import net.sydrus.yuzuku.Managers.CommandType;
import net.sydrus.yuzuku.Managers.Commands;

import java.util.*;

public class PluginCommand {
	private Commands cmds = new Commands();
	private Map<String, CommandInfo> commandInfo = new HashMap<String, CommandInfo>();

	public Commands getCommands() {
		return this.cmds;
	}

	public void register(CommandInfo commandInfo, Command path) {
		commandInfo.setType(CommandType.Addon);
		this.commandInfo.put(commandInfo.command, commandInfo);
		cmds.register(commandInfo, path);
	}

	public Set<String> getCmdInfoSet() {
		return commandInfo.keySet();
	}

	public Collection<CommandInfo> getCmdInfoCollection() {
		return commandInfo.values();
	}

	public CommandInfo getCmdInfo(String command) {
		return this.commandInfo.get(command);
	}

	public Set<String> keySet() {
		return this.cmds.keySet();
	}

	public boolean contains(String command) {
		return this.cmds.contains(command);
	}

	public Command get(String command) {
		return this.cmds.get(command);
	}

	public int count() {
		return this.cmds.count();
	}

	public boolean isEmpty() {
		return this.cmds.isEmpty();
	}

	/*
	 * public List<Command> getRegistredCommands() { return
	 * this.cmds.getCommands(); }
	 */

	public List<String> getNames() {
		return this.cmds.getNames();
	}

	/*
	 * public void replace(String key, Command newValue) {
	 * this.cmds.replace(key, newValue); }
	 */
}
