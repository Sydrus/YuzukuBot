package net.sydrus.yuzuku.plugin.Exception;

import java.io.File;

public class InvalidMPSFile extends PluginException {

	private static final long serialVersionUID = -4006524459692477910L;

	public InvalidMPSFile(File file) {
		super("The file '" + file.getName()
				+ "' this corrupt, with production errors or does not contain the correct name of the plugin in Plugins folder");
	}

	public InvalidMPSFile(File file, Throwable paramThrowable) {
		super("The file '" + file.getName()
				+ "' this corrupt, with production errors or does not contain the correct name of plugin in Plugins folder",
				paramThrowable);
	}
}
