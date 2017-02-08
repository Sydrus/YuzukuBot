package net.sydrus.yuzuku.addon.Manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.addon.AddonCommand;
import net.sydrus.yuzuku.addon.Exception.AddonException;
import net.sydrus.yuzuku.addon.Exception.ExistAddon;
import net.sydrus.yuzuku.addon.Exception.InvalidAddon;
import net.sydrus.yuzuku.addon.Exception.InvalidMPSFile;
import net.sydrus.yuzuku.addon.status.AddonState;
import net.sydrus.yuzuku.addon.status.AddonStatus;

public class AddonsLoader {
	public Map<String, ADDCloder> loaders = new HashMap<String, ADDCloder>();
	public AddonsManager addonsmanager = new AddonsManager();
	public AddonState AddonsState = new AddonState();
	public Map<String, AddonCommand> addoncommand = new HashMap<String, AddonCommand>();
	public List<String> mainClass = new ArrayList<String>();
	private String pseparator = System.getProperty("file.separator");
	private List<Addon> torem = new ArrayList<Addon>();
	private String mps = "MPSFiles";
	public static String addonsFolder = "Addons";
	private static AddonsLoader tclass;

	public AddonsLoader() throws Throwable {
		tclass = this;
		createFolder(addonsFolder);
		createFolder(this.mps);
		Addon add = null;
		AddonsData addf = null;
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
					addf = new AddonsData(fl);
					if (addf.getMain().startsWith("net.sydrus.yuzuku")) {
						addf.EndConnections();
						throw new InvalidAddon(
								"the addon can not have a package with 'start net.yukuzu.bot' please modify the main class of your addon. Cause of problem: "
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
							throw new InvalidAddon("Class '" + addf.getMain()
									+ "' already exists, please modify it and then try again. Cause of error "
									+ addf.getName());
						}
						mainClass.add(addf.getMain());
						continue;
					}
					throw new ExistAddon(add.getAddonsDF().getMain());
				} catch (InvalidMPSFile e) {
					try {
						AddonsState.setState(fl.getName().substring(0, fl.getName().length() - 4),
								AddonStatus.MPSError);
					} catch (Exception localException) {
					}
					System.err.println(e.getMessage());
				} catch (InvalidAddon e) {
					AddonsState.setState(addf, AddonStatus.JarError);
					addf.EndConnections();
					System.err.println(e.getMessage());
				}
			} else {
				try {
					throw new InvalidAddon(fl);
				} catch (InvalidAddon e) {
					System.err.println(e.getMessage());
				}
			}
		}
		for (Addon addon : addonsmanager.addons()) {
			try {
				giveDependencies(addon);
			} catch (InvalidAddon e) {
				System.err.println(e.getMessage());
			}
		}
		toRemove(this.torem);
	}

	public static AddonsLoader getInstance() {
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
		Addon add = null;
		AddonsData addf = null;
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
					addf = new AddonsData(fl);
					if (addf.getMain().startsWith("net.yuzuku.bot")) {
						addf.EndConnections();
						throw new InvalidAddon(
								"the addon can not have a package with 'start net.yuzuku.bot' please modify the main class of your addon. Cause of problem: "
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
							throw new InvalidAddon("Class '" + addf.getMain()
									+ "' already exists, please modify it and then try again. Cause of error "
									+ addf.getName());
						}
						mainClass.add(addf.getMain());
						continue;
					}
					throw new ExistAddon(add.getAddonsDF().getMain());
				} catch (InvalidMPSFile e) {
					try {
						AddonsState.setState(fl.getName().substring(0, fl.getName().length() - 4),
								AddonStatus.MPSError);
					} catch (Exception localException) {
					}
					System.err.println(e.getMessage());
				} catch (InvalidAddon e) {
					AddonsState.setState(addf, AddonStatus.JarError);
					addf.EndConnections();
					System.err.println(e.getMessage());
				}
			} else {
				try {
					throw new InvalidAddon(fl);
				} catch (InvalidAddon e) {
					System.err.println(e.getMessage());
				}
			}
		}
		for (Addon addon : addonsmanager.addons()) {
			try {
				giveDependencies(addon);
			} catch (InvalidAddon e) {
				System.err.println(e.getMessage());
			}
		}
		toRemove(this.torem);
	}

	public void loadAddon(String fileName) throws AddonException {
		createFolder(addonsFolder);
		createFolder(mps);
		Addon add = null;
		AddonsData addf = null;
		File fl = new File(cFolder() + File.separatorChar + "MPSFiles", fileName + ".mps");
		if (fl.exists()) {
			if (fl.getName().endsWith(".mps")) {
				try {
					addf = new AddonsData(fl);
					if (addf.getMain().startsWith("net.yusuku.bot")) {
						addf.EndConnections();
						throw new InvalidAddon(
								"the addon can not have a package with 'start net.yukuzu.bot' please modify the main class of your addon. Cause of problem: "
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
							throw new InvalidAddon("Class '" + addf.getMain()
									+ "' already exists, please modify it and then try again. Cause of error "
									+ addf.getName());
						}
						mainClass.add(addf.getMain());
					}
					throw new ExistAddon(add.getAddonsDF().getMain());
				} catch (InvalidMPSFile e) {
					AddonsState.setState(fl.getName().substring(0, fl.getName().length() - 4), AddonStatus.MPSError);
					throw new AddonException(e.getMessage());
				} catch (InvalidAddon e) {
					AddonsState.setState(addf, AddonStatus.JarError);
					addf.EndConnections();
					throw new AddonException(e.getMessage());
				}
			} else {
				throw new InvalidAddon(fl);
			}
		} else {
			throw new InvalidAddon("The file '" + fl.getName() + "' not found");
		}
		/*
		 * for (Addon addon : addonsmanager.addons()) { try {
		 * giveDependencies(addon); } catch (InvalidAddon e) {
		 * System.err.println(e.getMessage()); } } toRemove(this.torem);
		 */
	}

	public final void remove(String name) throws InvalidAddon {
		if (!addonsmanager.contains(name)) {
			throw new InvalidAddon("This addon was not added or it was deactivated");
		}
		Addon add = addonsmanager.get(name);
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

	private void toRemove(List<Addon> list) {
		for (Addon adde : list) {
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

	private void giveDependencies(Addon addon) throws InvalidAddon {
		List<String> addDep = addon.getAddonsDF().getDep();
		for (String addn : addDep) {
			if (addonsmanager == null) {
				AddonsState.setState(addn, AddonStatus.Unknown);
				throw new InvalidAddon("Invalid Addon: " + addn);
			}
			Addon cr = addonsmanager.get(addn.toLowerCase());
			if (cr == null) {
				this.torem.add(addon);
				AddonsState.setState(addon, AddonStatus.MissingAddon);
				throw new InvalidAddon("Addon not found: " + addn);
			}
		}
		if (this.torem.contains(addon)) {
			return;
		}
		addon.Enable();
		Addon.name = addon.getAddonsDF().getName().toLowerCase();
		try {
			addoncommand.put(Addon.name, addon.commandManager());
			YuzukuBot.getInstance()._CommandManager.getCommands().registerCommands(addon.commandManager().getCommands());
		} catch (Exception e) {
			AddonsState.setState(addon, AddonStatus.CommandError);
			try {
				addoncommand.remove(Addon.name);
				YuzukuBot.getInstance()._CommandManager.getCommands()
						.unregisterCommands(addon.commandManager().getCommands());
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

	public Addon loadAddon(File file, AddonsData df) throws InvalidAddon {
		ADDCloder loader = null;
		try {
			if (!file.exists()) {
				throw new InvalidAddon(file);
			}
		} catch (Exception e) {
			throw new InvalidAddon(df.getName(), e);
		}
		try {
			loader = new ADDCloder(getClass().getClassLoader(), this, df, file);
			loaders.put(df.getName().toLowerCase(), loader);
		} catch (Throwable ex) {
			throw new InvalidAddon(file);
		}
		return loader.addon;
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
