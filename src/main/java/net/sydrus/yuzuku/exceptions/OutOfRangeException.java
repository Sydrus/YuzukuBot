package net.sydrus.yuzuku.exceptions;

public class OutOfRangeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OutOfRangeException() {
		super("**You have exceeded the character limit**");
	}

	public OutOfRangeException(Throwable throlable) {
		super("**You have exceeded the character limit**", throlable);
	}

	public OutOfRangeException(int number) {
		super("**You have exceeded the character limit:** " + number);
	}

	public OutOfRangeException(int maxNumber, int number) {
		super("**You have exceeded the limit of " + maxNumber + ", current** " + number);
	}

	public OutOfRangeException(String text, Throwable throlable) {
		super(text, throlable);
	}
}
