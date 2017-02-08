package net.sydrus.yuzuku.audio;

public class AudioException extends Exception {

	private static final long serialVersionUID = 8172527932742632531L;
	private static ExceptionType type = ExceptionType.none;

	public ExceptionType getExceptionType() {
		return type;
	}

	private static String getExceptionType(String error, ExceptionType bool) {
		if (bool == ExceptionType.isFatal) {
			type = ExceptionType.isFatal;
			return "[FATAL] " + error;
		} else {
			if (bool == ExceptionType.isError) {
				type = ExceptionType.isError;
			} else if (bool == ExceptionType.IsWarning) {
				type = ExceptionType.IsWarning;
			}
			return error;
		}
	}

	public AudioException(String error, Throwable reason) {
		super(error, reason);
	}

	public AudioException(String error, Throwable reason, ExceptionType type) {
		super(getExceptionType(error, type), reason);
	}

	public AudioException(String error, ExceptionType type) {
		super(error);
	}

	public static enum ExceptionType {
		isFatal, IsWarning, isError, none
	}
}
