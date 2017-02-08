package net.sydrus.yuzuku.FileReader;

public class BCRItem {
	public enum Type {
		Developers, Administrators, BannedTexts
	}

	public static String getItem(Type type) {
		if (type == Type.Administrators) {
			return "administrators";
		} else if (type == Type.BannedTexts) {
			return "txtbnd";
		} else if (type == Type.Developers) {
			return "developers";
		}
		return null;
	}
}
