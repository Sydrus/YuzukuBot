package net.sydrus.yuzuku.addon.Exception;

public class AddonException extends Exception {
	private static final long serialVersionUID = 4547984874645364635L;

	public AddonException(String addonName) {
		super("There is already an addon called '" + addonName
				+ "' Please check your addon already exists or change his name");
	}

	public AddonException(String paramString, Throwable paramThrowable) {
		super(paramString, paramThrowable);
	}
}
