package net.sydrus.yuzuku.Managers;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;


public class getBotListItems {
	public static List<User> getMembers(Guild guild, boolean ignoreCase, String name) {
		List<User> toReturn = new ArrayList<User>();
		for (Member mbr : guild.getMembers()) {
			if (ignoreCase) {
				if (mbr.getUser().getName().toLowerCase().contains(name.toLowerCase())) {
					toReturn.add(mbr.getUser());
				}
			} else {
				if (mbr.getUser().getName().contains(name)) {
					toReturn.add(mbr.getUser());
				}
			}
		}
		return toReturn;
	}

	public static List<VoiceChannel> getVoiceChannels(Guild guild, boolean ignoreCase, String name) {
		List<VoiceChannel> toReturn = new ArrayList<VoiceChannel>();
		for (VoiceChannel txt : guild.getVoiceChannels()) {
			if (ignoreCase) {
				if (txt.getName().toLowerCase().contains(name.toLowerCase())) {
					toReturn.add(txt);
				}
			} else {
				if (txt.getName().contains(name)) {
					toReturn.add(txt);
				}
			}
		}
		return toReturn;
	}

	public static List<TextChannel> getTextChannels(Guild guild, boolean ignoreCase, String name) {
		List<TextChannel> toReturn = new ArrayList<TextChannel>();
		for (TextChannel txt : guild.getTextChannels()) {
			if (ignoreCase) {
				if (txt.getName().toLowerCase().contains(name.toLowerCase())) {
					toReturn.add(txt);
				}
			} else {
				if (txt.getName().contains(name)) {
					toReturn.add(txt);
				}
			}
		}
		return toReturn;
	}

	public static List<Guild> getGuilds(JDA jda, boolean ignoreCase, String name) {
		List<Guild> toReturn = new ArrayList<Guild>();
		for (Guild gld : jda.getGuilds()) {
			if (ignoreCase) {
				if (gld.getName().toLowerCase().contains(name.toLowerCase())) {
					toReturn.add(gld);
				}
			} else {
				if (gld.getName().contains(name)) {
					toReturn.add(gld);
				}
			}
		}
		return toReturn;
	}
}
