package net.sydrus.yuzuku.plugin.Exception;

import net.sydrus.yuzuku.plugin.Manager.PluginsData;

public class ExtendsPluginException extends PluginException {

	private static final long serialVersionUID = -1152712133327898464L;

	public ExtendsPluginException(PluginsData AddonsDF) {
		super("class `" + AddonsDF.getMain() + "' not extend Plugins");
	}

	public ExtendsPluginException(PluginsData AddonsDF, Throwable paramThrowable) {
		super("class `" + AddonsDF.getMain() + "' not extend Plugins", paramThrowable);
	}
}
