package net.sydrus.yuzuku.addon.Exception;

public class ExistAddon extends AddonException {
	private static final long serialVersionUID = 4547984874645364635L;

	public ExistAddon(String addonName) {
		super("There is already an addon called '" + addonName
				+ "' Please check your addon already exists or change his name");
	}

	public ExistAddon(String paramString, Throwable paramThrowable) {
		super(paramString, paramThrowable);
	}
}
