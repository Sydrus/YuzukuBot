package net.sydrus.yuzuku.addon.Exception;

import java.io.File;

public class InvalidMPSFile extends AddonException {

	private static final long serialVersionUID = -4006524459692477910L;

	public InvalidMPSFile(File file) {
		super("The file '" + file.getName()
				+ "' this corrupt, with production errors or does not contain the correct name of addon in Addons folder");
	}

	public InvalidMPSFile(File file, Throwable paramThrowable) {
		super("The file '" + file.getName()
				+ "' this corrupt, with production errors or does not contain the correct name of addon in Addons folder",
				paramThrowable);
	}
}
