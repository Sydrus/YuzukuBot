package net.sydrus.yuzuku.Managers;

public class BotCommand extends Throwable {

	private static final long serialVersionUID = -5129054224373554452L;

	public BotCommand(String addonName) {
		super("**The command " + addonName + " is already registered**");
	}

	public BotCommand(String paramString, Throwable paramThrowable) {
		super(paramString, paramThrowable);
	}
}
