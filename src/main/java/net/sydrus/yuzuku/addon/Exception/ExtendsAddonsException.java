package net.sydrus.yuzuku.addon.Exception;

import net.sydrus.yuzuku.addon.Manager.AddonsData;

public class ExtendsAddonsException extends AddonException {

	private static final long serialVersionUID = -1152712133327898464L;

	public ExtendsAddonsException(AddonsData AddonsDF) {
		super("class `" + AddonsDF.getMain() + "' not extend Addons");
	}

	public ExtendsAddonsException(AddonsData AddonsDF, Throwable paramThrowable) {
		super("class `" + AddonsDF.getMain() + "' not extend Addons", paramThrowable);
	}
}
