package net.sydrus.yuzuku.plugin.Manager;

import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.plugin.Exception.ExistPlugin;
import net.sydrus.yuzuku.plugin.Exception.InvalidMPSFile;
import net.sydrus.yuzuku.plugin.Exception.InvalidPlugin;
import net.sydrus.yuzuku.plugin.Exception.PluginException;
import net.sydrus.yuzuku.plugin.PluginCommand;
import net.sydrus.yuzuku.plugin.status.AddonState;
import net.sydrus.yuzuku.plugin.status.AddonStatus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginsLoader {
	public Map<String, ADDCloder> loaders = new HashMap<String, ADDCloder>();
	public PluginManager addonsmanager = new PluginManager();
	public AddonState AddonsState = new AddonState();
	public Map<String, PluginCommand> addoncommand = new HashMap<String, PluginCommand>();
	public List<String> mainClass = new ArrayList<String>();
	private String pseparator = System.getProperty("file.separator");
	private List<Plugin> torem = new ArrayList<Plugin>();
	private String mps = "MPSFiles";
	public static String addonsFolder = "Addons";
	private static PluginsLoader tclass;

	public PluginsLoader() throws Throwable {
		tclass = this;
		createFolder(addonsFolder);
		createFolder(this.mps);
		Plugin add = null;
		PluginsData addf = null;
		File[] mpsFiles = getFolderFileList(this.mps);
		if (mpsFiles == null) {
			return;
		}
		File[] arrayOfFile1;
		int j = (arrayOfFile1 = mpsFiles).length;
		for (int i = 0; i < j; i++) {
			File fl = arrayOfFile1[i];
			if (fl.getName().endsWith(".mps")) {
				try {
					addf = new PluginsData(fl);
					if (addf.getMain().startsWith("net.sydrus.yuzuku")) {
						addf.EndConnections();
						throw new InvalidPlugin(
								"the plugin can not have a package with 'start net.yukuzu.bot' please modify the main class of your plugin. Cause of problem: "
										+ addf.getName());
					}
					add = loadAddon(new File(addonsFolder + this.pseparator
							+ fl.getName().substring(0, fl.getName().length() - 3) + "jar"), addf);
					AddonsState.setState(addf, AddonStatus.Starting);
					if (!addonsmanager.contains(add.getAddonsDF().getName())) {
						addonsmanager.register(addf.getName().toLowerCase(), add);
						AddonsState.setState(addf, AddonStatus.Working);
						if (mainClass.contains(addf.getMain())) {
							this.torem.add(add);
							throw new InvalidPlugin("Class '" + addf.getMain()
									+ "' already exists, please modify it and then try again. Cause of error "
									+ addf.getName());
						}
						mainClass.add(addf.getMain());
						continue;
					}
					throw new ExistPlugin(add.getAddonsDF().getMain());
				} catch (InvalidMPSFile e) {
					try {
						AddonsState.setState(fl.getName().substring(0, fl.getName().length() - 4),
								AddonStatus.MPSError);
					} catch (Exception localException) {
					}
					System.err.println(e.getMessage());
				} catch (InvalidPlugin e) {
					AddonsState.setState(addf, AddonStatus.JarError);
					addf.EndConnections();
					System.err.println(e.getMessage());
				}
			} else {
				try {
					throw new InvalidPlugin(fl);
				} catch (InvalidPlugin e) {
					System.err.println(e.getMessage());
				}
			}
		}
		for (Plugin plugin : addonsmanager.addons()) {
			try {
				giveDependencies(plugin);
			} catch (InvalidPlugin e) {
				System.err.println(e.getMessage());
			}
		}
		toRemove(this.torem);
	}

	public static PluginsLoader getInstance() {
		return tclass;
	}

	public void reload() throws Throwable {
		loaders.clear();
		addonsmanager.removeAll();
		AddonsState.setStateAll(AddonStatus.Stoped);
		addoncommand.clear();
		mainClass.clear();
		createFolder(addonsFolder);
		createFolder(this.mps);
		Plugin add = null;
		PluginsData addf = null;
		File[] mpsFiles = getFolderFileList(this.mps);
		if (mpsFiles == null) {
			return;
		}
		File[] arrayOfFile1;
		int j = (arrayOfFile1 = mpsFiles).length;
		for (int i = 0; i < j; i++) {
			File fl = arrayOfFile1[i];
			if (fl.getName().endsWith(".mps")) {
				try {
					addf = new PluginsData(fl);
					if (addf.getMain().startsWith("net.yuzuku.bot")) {
						addf.EndConnections();
						throw new InvalidPlugin(
								"the plugin can not have a package with 'start net.yuzuku.bot' please modify the main class of your plugin. Cause of problem: "
										+ addf.getName());
					}
					add = loadAddon(new File(addonsFolder + this.pseparator
							+ fl.getName().substring(0, fl.getName().length() - 3) + "jar"), addf);
					AddonsState.setState(addf, AddonStatus.Starting);
					if (!addonsmanager.contains(add.getAddonsDF().getName())) {
						addonsmanager.register(addf.getName().toLowerCase(), add);
						AddonsState.setState(addf, AddonStatus.Working);
						if (mainClass.contains(addf.getMain())) {
							this.torem.add(add);
							throw new InvalidPlugin("Class '" + addf.getMain()
									+ "' already exists, please modify it and then try again. Cause of error "
									+ addf.getName());
						}
						mainClass.add(addf.getMain());
						continue;
					}
					throw new ExistPlugin(add.getAddonsDF().getMain());
				} catch (InvalidMPSFile e) {
					try {
						AddonsState.setState(fl.getName().substring(0, fl.getName().length() - 4),
								AddonStatus.MPSError);
					} catch (Exception localException) {
					}
					System.err.println(e.getMessage());
				} catch (InvalidPlugin e) {
					AddonsState.setState(addf, AddonStatus.JarError);
					addf.EndConnections();
					System.err.println(e.getMessage());
				}
			} else {
				try {
					throw new InvalidPlugin(fl);
				} catch (InvalidPlugin e) {
					System.err.println(e.getMessage());
				}
			}
		}
		for (Plugin plugin : addonsmanager.addons()) {
			try {
				giveDependencies(plugin);
			} catch (InvalidPlugin e) {
				System.err.println(e.getMessage());
			}
		}
		toRemove(this.torem);
	}

	public void loadAddon(String fileName) throws PluginException {
		createFolder(addonsFolder);
		createFolder(mps);
		Plugin add = null;
		PluginsData addf = null;
		File fl = new File(cFolder() + File.separatorChar + "MPSFiles", fileName + ".mps");
		if (fl.exists()) {
			if (fl.getName().endsWith(".mps")) {
				try {
					addf = new PluginsData(fl);
					if (addf.getMain().startsWith("net.yusuku.bot")) {
						addf.EndConnections();
						throw new InvalidPlugin(
								"the plugin can not have a package with 'start net.yukuzu.bot' please modify the main class of your plugin. Cause of problem: "
										+ addf.getName());
					}
					add = loadAddon(new File(addonsFolder + this.pseparator
							+ fl.getName().substring(0, fl.getName().length() - 3) + "jar"), addf);
					AddonsState.setState(addf, AddonStatus.Starting);
					if (!addonsmanager.contains(add.getAddonsDF().getName())) {
						addonsmanager.register(addf.getName().toLowerCase(), add);
						AddonsState.setState(addf, AddonStatus.Working);
						if (mainClass.contains(addf.getMain())) {
							this.torem.add(add);
							throw new InvalidPlugin("Class '" + addf.getMain()
									+ "' already exists, please modify it and then try again. Cause of error "
									+ addf.getName());
						}
						mainClass.add(addf.getMain());
					}
					throw new ExistPlugin(add.getAddonsDF().getMain());
				} catch (InvalidMPSFile e) {
					AddonsState.setState(fl.getName().substring(0, fl.getName().length() - 4), AddonStatus.MPSError);
					throw new PluginException(e.getMessage());
				} catch (InvalidPlugin e) {
					AddonsState.setState(addf, AddonStatus.JarError);
					addf.EndConnections();
					throw new PluginException(e.getMessage());
				}
			} else {
				throw new InvalidPlugin(fl);
			}
		} else {
			throw new InvalidPlugin("The file '" + fl.getName() + "' not found");
		}
		/*
		 * for (Plugin plugin : addonsmanager.addons()) { try {
		 * giveDependencies(plugin); } catch (InvalidPlugin e) {
		 * System.err.println(e.getMessage()); } } toRemove(this.torem);
		 */
	}

	public final void remove(String name) throws InvalidPlugin {
		if (!addonsmanager.contains(name)) {
			throw new InvalidPlugin("This plugin was not added or it was deactivated");
		}
		Plugin add = addonsmanager.get(name);
		addonsmanager.remove(name);
		AddonsState.setState(name, AddonStatus.Stoped);
		mainClass.remove(add.getAddonsDF().getMain());
		loaders.remove(name);
		addoncommand.remove(name);
	}

	private String cFolder() {
		try {
			return new File("").getCanonicalPath();
		} catch (IOException e) {
			return System.getProperty("user.dir");
		}
	}

	private void toRemove(List<Plugin> list) {
		for (Plugin adde : list) {
			try {
				adde.getAddonsDF().EndConnections();
				adde.getConfigReader().closeAllConn();
			} catch (Exception localException) {
			}
			try {
				((ADDCloder) loaders.get(adde.getAddonsDF().getName().toLowerCase())).close();
			} catch (Exception localException1) {
			}
			addonsmanager.remove(adde.getAddonsDF().getName());
		}
		this.torem.clear();
	}

	private void giveDependencies(Plugin plugin) throws InvalidPlugin {
		List<String> addDep = plugin.getAddonsDF().getDep();
		for (String addn : addDep) {
			if (addonsmanager == null) {
				AddonsState.setState(addn, AddonStatus.Unknown);
				throw new InvalidPlugin("Invalid Plugin: " + addn);
			}
			Plugin cr = addonsmanager.get(addn.toLowerCase());
			if (cr == null) {
				this.torem.add(plugin);
				AddonsState.setState(plugin, AddonStatus.MissingAddon);
				throw new InvalidPlugin("Plugin not found: " + addn);
			}
		}
		if (this.torem.contains(plugin)) {
			return;
		}
		plugin.Enable();
		Plugin.name = plugin.getAddonsDF().getName().toLowerCase();
		try {
			addoncommand.put(Plugin.name, plugin.commandManager());
			YuzukuBot.getInstance()._CommandManager.getCommands().registerCommands(plugin.commandManager().getCommands());
		} catch (Exception e) {
			AddonsState.setState(plugin, AddonStatus.CommandError);
			try {
				addoncommand.remove(Plugin.name);
				YuzukuBot.getInstance()._CommandManager.getCommands()
						.unregisterCommands(plugin.commandManager().getCommands());
			} catch (Exception localException2) {
			}
		}
	}

	private File[] getFolderFileList(String folder) {
		File diretorio = null;
		try {
			diretorio = new File(new File("").getCanonicalPath(),folder);
		} catch (IOException e) {
			diretorio = new File(folder);
		}
		File[] fList = diretorio.listFiles();
		return fList;
	}

	public Plugin loadAddon(File file, PluginsData df) throws InvalidPlugin {
		ADDCloder loader = null;
		try {
			if (!file.exists()) {
				throw new InvalidPlugin(file);
			}
		} catch (Exception e) {
			throw new InvalidPlugin(df.getName(), e);
		}
		try {
			loader = new ADDCloder(getClass().getClassLoader(), this, df, file);
			loaders.put(df.getName().toLowerCase(), loader);
		} catch (Throwable ex) {
			throw new InvalidPlugin(file);
		}
		return loader.plugin;
	}

	private void createFolder(String folder) {
		String path = cFolder();
		String pseparator = System.getProperty("file.separator");
		File file = null;
		if ((path.endsWith(pseparator)) || (folder.startsWith(pseparator))) {
			file = new File(path + folder);
		} else {
			file = new File(path + pseparator + folder);
		}
		file.mkdirs();
	}

}
