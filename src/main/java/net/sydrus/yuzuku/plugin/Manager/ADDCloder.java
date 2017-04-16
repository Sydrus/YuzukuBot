package net.sydrus.yuzuku.plugin.Manager;

import net.sydrus.yuzuku.FileReader.ConfigReader;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.plugin.Exception.ExtendsPluginException;
import net.sydrus.yuzuku.plugin.Exception.InvalidPlugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ADDCloder extends URLClassLoader {
	Plugin plugin = null;

	private String pseparator = System.getProperty("file.separator");
	
	public ADDCloder(ClassLoader classLoader, PluginsLoader pluginsLoader, PluginsData df, File file)
			throws Throwable, MalformedURLException {
		super(new URL[] { file.toURI().toURL() }, classLoader);
		try {
			Class<?> jC;
			try {
				jC = Class.forName(df.getMain(), true, this);
			} catch (ClassNotFoundException ex) {
				throw new ClassNotFoundException("Cannot find main class `" + df.getMain() + "'", ex);
			} catch (Exception ex) {
				throw new InvalidPlugin(file, ex);
			}
			Class<? extends Plugin> addc;
			try {
				addc = jC.asSubclass(Plugin.class);
			} catch (ClassCastException ex) {
				throw new ExtendsPluginException(df, ex);
			} catch (Exception ex) {
				throw new InvalidPlugin(file, ex);
			}
			this.plugin = ((Plugin) addc.newInstance());
			plugin.loadData(df, new ConfigReader(YuzukuBot.getInstance().settingsFolder + pseparator + df.getName(), df.getName()), file);
		} catch (Exception ex) {
			throw new InvalidPlugin(file, ex);
		}

	}
}
