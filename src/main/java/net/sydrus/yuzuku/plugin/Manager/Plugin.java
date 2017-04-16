package net.sydrus.yuzuku.plugin.Manager;

import net.dv8tion.jda.core.JDA;
import net.sydrus.yuzuku.FileReader.ConfigReader;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.plugin.PluginCommand;

import java.io.File;

public abstract class Plugin {
	private PluginsData adf;
	private ConfigReader reader;
	private File file;
	public static String name;
	private PluginCommand commands = new PluginCommand();

	public abstract void Enable();

	public abstract void Disable();

	public final PluginsData getAddonsDF() {
		return adf;
	}

	public final JDA getJda() {
		return YuzukuBot.getInstance().getJDA();
	}

	public final ConfigReader getConfigReader() {
		return this.reader;
	}

	public final File getFile() {
		return this.file;
	}

	public final PluginCommand commandManager() {
		return this.commands;
	}

	final void loadData(PluginsData addons, ConfigReader reader, File file) {
		this.adf = addons;
		this.reader = reader;
		this.file = file;
	}
}
