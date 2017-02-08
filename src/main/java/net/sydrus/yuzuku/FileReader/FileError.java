package net.sydrus.yuzuku.FileReader;

public class FileError extends RuntimeException {

	private static final long serialVersionUID = 4547984874645364635L;

	public FileError() {
	}

	public FileError(String paramString) {
		super(paramString);
	}

	public FileError(String paramString, Throwable paramThrowable) {
		super("Load the file before trying to use it: " + paramString, paramThrowable);
	}

	public FileError(Throwable paramThrowable) {
		super(paramThrowable);
	}

	protected FileError(String paramString, Throwable paramThrowable, boolean paramBoolean1, boolean paramBoolean2) {
		super(paramString, paramThrowable, paramBoolean1, paramBoolean2);
	}
}