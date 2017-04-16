package net.sydrus.yuzuku.plugin.Exception;

public class PluginException extends Exception {
	private static final long serialVersionUID = 4547984874645364635L;

	public PluginException(String addonName) {
		super("There is already an PLugin called '" + addonName
				+ "' Please check if your plugin already exists or change his name");
	}

	public PluginException(String paramString, Throwable paramThrowable) {
		super(paramString, paramThrowable);
	}
}
