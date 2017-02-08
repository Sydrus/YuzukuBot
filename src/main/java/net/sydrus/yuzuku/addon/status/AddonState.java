package net.sydrus.yuzuku.addon.status;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sydrus.yuzuku.addon.Manager.Addon;
import net.sydrus.yuzuku.addon.Manager.AddonsData;

public class AddonState {
	private Map<String, AddonStatus> state = new HashMap<String, AddonStatus>();

	public void setState(Addon addon, AddonStatus state) {
		this.state.put(addon.getAddonsDF().getName(), state);
	}

	public void setState(AddonsData df, AddonStatus state) {
		this.state.put(df.getName().toLowerCase(), state);
	}

	public void setState(String name, AddonStatus state) {
		this.state.put(name.toLowerCase(), state);
	}

	public AddonStatus getState(Addon addon) {
		return (AddonStatus) this.state.get(addon.getAddonsDF().getName().toLowerCase());
	}

	public AddonStatus getState(AddonsData df) {
		return (AddonStatus) this.state.get(df.getName().toLowerCase());
	}

	public AddonStatus getState(String name) {
		return (AddonStatus) this.state.get(name.toLowerCase());
	}

	public Set<String> getKeySet() {
		return this.state.keySet();
	}

	public List<String> getListState() {
		List<String> list = new ArrayList<String>();
		for (String names : this.state.keySet()) {
			String toset = names + ": " + this.state.get(names);
			list.add(toset);
		}
		return list;
	}

	public String getStringState() {
		String endItems = "";
		for (String itemslist : getListState()) {
			endItems = endItems + itemslist + "\n";
		}
		return endItems;
	}

	public boolean hasAddons() {
		return state.size() > 0;
	}

	public int runningAddons() {
		int toReturn = 0;
		for (AddonStatus status : this.state.values()) {
			if (status == AddonStatus.Working) {
				toReturn++;
			}
		}
		return toReturn;
	}

	public Collection<AddonStatus> getStatus() {
		return this.state.values();
	}

	public void setStateAll(AddonStatus stoped) {
		// TODO Auto-generated method stub

	}
}
