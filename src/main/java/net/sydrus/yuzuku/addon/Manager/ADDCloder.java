package net.sydrus.yuzuku.addon.Manager;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.FileReader.ConfigReader;
import net.sydrus.yuzuku.addon.Exception.ExtendsAddonsException;
import net.sydrus.yuzuku.addon.Exception.InvalidAddon;

public class ADDCloder extends URLClassLoader {
	Addon addon = null;

	private String pseparator = System.getProperty("file.separator");
	
	public ADDCloder(ClassLoader classLoader, AddonsLoader addonsLoader, AddonsData df, File file)
			throws Throwable, MalformedURLException {
		super(new URL[] { file.toURI().toURL() }, classLoader);
		try {
			Class<?> jC;
			try {
				jC = Class.forName(df.getMain(), true, this);
			} catch (ClassNotFoundException ex) {
				throw new ClassNotFoundException("Cannot find main class `" + df.getMain() + "'", ex);
			} catch (Exception ex) {
				throw new InvalidAddon(file, ex);
			}
			Class<? extends Addon> addc;
			try {
				addc = jC.asSubclass(Addon.class);
			} catch (ClassCastException ex) {
				throw new ExtendsAddonsException(df, ex);
			} catch (Exception ex) {
				throw new InvalidAddon(file, ex);
			}
			this.addon = ((Addon) addc.newInstance());
			addon.loadData(df, new ConfigReader(YuzukuBot.getInstance().settingsFolder + pseparator + df.getName(), df.getName()), file);
		} catch (Exception ex) {
			throw new InvalidAddon(file, ex);
		}

	}
}
