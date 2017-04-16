package net.sydrus.yuzuku.plugin.Exception;

public class ExistPlugin extends PluginException {
	private static final long serialVersionUID = 4547984874645364635L;

	public ExistPlugin(String addonName) {
		super("There is already an plugin called '" + addonName
				+ "' Please check if the plugin already exists or change his name");
	}

	public ExistPlugin(String paramString, Throwable paramThrowable) {
		super(paramString, paramThrowable);
	}
}
