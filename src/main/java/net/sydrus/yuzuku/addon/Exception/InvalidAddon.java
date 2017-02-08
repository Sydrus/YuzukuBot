package net.sydrus.yuzuku.addon.Exception;

import java.io.File;

public class InvalidAddon extends AddonException {

	private static final long serialVersionUID = 6203887395494223932L;

	public InvalidAddon(String text) {
		super(text);
	}
	
	public InvalidAddon(File file) {
		super("Error on load Addon '" + file.getName() + "' Please check your addon project");
	}

	public InvalidAddon(File file, Throwable paramThrowable) {
		super("Error on load Addon '" + file.getName() + "' Please check your addon project", paramThrowable);
	}
	public InvalidAddon(String file, Throwable paramThrowable) {
		super("Error on load Addon '" + file + "' Please check Addons Folder Or  MPSFiles Folder", paramThrowable);
	}
}