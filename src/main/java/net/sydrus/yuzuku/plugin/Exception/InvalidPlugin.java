package net.sydrus.yuzuku.plugin.Exception;

import java.io.File;

public class InvalidPlugin extends PluginException {

	private static final long serialVersionUID = 6203887395494223932L;

	public InvalidPlugin(String text) {
		super(text);
	}
	
	public InvalidPlugin(File file) {
		super("Error on load Plugin '" + file.getName() + "' Please check your plugin project");
	}

	public InvalidPlugin(File file, Throwable paramThrowable) {
		super("Error on load Plugin '" + file.getName() + "' Please check your plugin project", paramThrowable);
	}
	public InvalidPlugin(String file, Throwable paramThrowable) {
		super("Error on load Plugin '" + file + "' Please check Plugins Folder Or  MPSFiles Folder", paramThrowable);
	}
}