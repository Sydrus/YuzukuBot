package net.sydrus.yuzuku.addon.Manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AddonsManager {
	public static Map<String, Addon> itens = new HashMap<String, Addon>();

	public Addon get(String addn) {
		return itens.get(addn.toLowerCase());
	}

	final void remove(String name) {
		itens.remove(name.toLowerCase());
	}

	public Collection<Addon> addons() {
		return itens.values();
	}

	public void register(String name, Addon add) {
		itens.put(name.toLowerCase(), add);
	}

	public boolean contains(String name) {
		return itens.containsKey(name.toLowerCase());
	}

	public final void removeAll() {
		itens.clear();
	}
	
}
