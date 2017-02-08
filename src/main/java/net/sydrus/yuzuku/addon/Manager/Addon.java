package net.sydrus.yuzuku.addon.Manager;

import java.io.File;

import net.dv8tion.jda.core.JDA;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.FileReader.ConfigReader;
import net.sydrus.yuzuku.addon.AddonCommand;

public abstract class Addon {
	private AddonsData adf;
	private ConfigReader reader;
	private File file;
	public static String name;
	private AddonCommand commands = new AddonCommand();

	public abstract void Enable();

	public abstract void Disable();

	public final AddonsData getAddonsDF() {
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

	public final AddonCommand commandManager() {
		return this.commands;
	}

	final void loadData(AddonsData addons, ConfigReader reader, File file) {
		this.adf = addons;
		this.reader = reader;
		this.file = file;
	}
}
