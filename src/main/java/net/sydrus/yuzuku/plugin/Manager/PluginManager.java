package net.sydrus.yuzuku.plugin.Manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PluginManager {
	public static Map<String, Plugin> itens = new HashMap<String, Plugin>();

	public Plugin get(String addn) {
		return itens.get(addn.toLowerCase());
	}

	final void remove(String name) {
		itens.remove(name.toLowerCase());
	}

	public Collection<Plugin> addons() {
		return itens.values();
	}

	public void register(String name, Plugin add) {
		itens.put(name.toLowerCase(), add);
	}

	public boolean contains(String name) {
		return itens.containsKey(name.toLowerCase());
	}

	public final void removeAll() {
		itens.clear();
	}
	
}
