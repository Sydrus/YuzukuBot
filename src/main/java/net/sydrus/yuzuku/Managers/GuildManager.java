package net.sydrus.yuzuku.Managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.FileReader.ConfigReader;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;
import net.sydrus.yuzuku.audio.MusicManager;

public class GuildManager {

	private JDA jda = null;

	private boolean isLoaded = false;

	public GuildManager(JDA jda) {
		this.jda = jda;
	}

	public static MusicManager getMusicManager() {
		return YuzukuBot.MusicManager;
	}

	public ConfigReader getServerConfig(Guild guild) {
		return new ConfigReader("Guilds", guild.getId());
	}

	public boolean autoSaveMusic(Guild guild) {
		ConfigReader bol = getServerConfig(guild);
		return bol.contains("autoSaveMusic") == false ? true : bol.getBoolean("autoSaveMusic");
	}

	public boolean playlistIsLoaded() {
		return this.isLoaded;
	}

	public int getVolume(Guild guild) {
		ConfigReader vall = getServerConfig(guild);
		return vall.contains("playerVolume") == false ? 50 : vall.getInt("playerVolume");
	}

	public void setVolume(Guild guild, int volume) {
		ConfigReader vall = getServerConfig(guild);
		vall.set("playerVolume", volume);
		vall.save();
	}

	public void setAutosave(Guild guild, boolean value) {
		try {
			ConfigReader bol = getServerConfig(guild);
			bol.set("autoSaveMusic", value);
			bol.save();
		} catch (Exception e) {
		}
	}

	public boolean containsTrack(Guild guild, AudioTrack track) {
		ConfigReader config = getServerConfig(guild);
		if (config.contains("PlayList")) {
			return config.getList("PlayList").contains(track.getIdentifier());
		}
		return false;
	}

	public void addMusic(Guild guild, AudioTrack track) {
		try {
			ConfigReader conf = getServerConfig(guild);
			List<Object> links = new ArrayList<Object>();
			if (conf.contains("PlayList")) {
				links = conf.getList("PlayList");
			} else {
				links = new ArrayList<Object>();
			}
			if (!links.contains(track.getIdentifier())) {
				links.add(track.getIdentifier());
			}
			conf.set("PlayList", links);
			conf.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addList(Guild guild, AudioPlaylist playlist) {
		new Thread(new Runnable() {
			public void run() {
				for (AudioTrack track : playlist.getTracks()) {
						addMusic(guild, track);
				}
			}
		}).start();
	}

	public String getPrefix(Guild guild) {
		String prefix = jda.getSelfUser().getAsMention() + " ";
		try {
			ConfigReader reader = getServerConfig(guild);
			if (reader.contains("guildPrefix")) {
				prefix = reader.getString("guildPrefix");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return prefix;
	}

	public void setPrefix(Guild guild, char charAt) {
		try {
			ConfigReader reader = getServerConfig(guild);
			reader.set("guildPrefix", charAt + "");
			reader.save();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public boolean hasMusicInConfig(Guild guild) {
		ConfigReader read = getServerConfig(guild);
		return read.contains("PlayList") && !read.getList("PlayList").isEmpty();
	}

	@Deprecated
	public void registerTracks() {
		new Thread(new Runnable() {
			public void run() {
				ConfigReader reader = null;
				for (Guild gd : jda.getGuilds()) {
					try {
						reader = new ConfigReader("Guilds", gd.getId());
						if (reader.contains("PlayList")) {
							List<Object> links = reader.getList("PlayList");
							for (Object ob : links) {
								String toAdd = "";
								if (ob.toString().contains("youtube.com/watch?v=")) {
									toAdd = ob.toString();
								} else {
									toAdd = "www.youtube.com/watch?v=" + ob;
								}
								
								getMusicManager().addItems(YuzukuBot.getInstance().getJDA().getGuildById(gd.getId()),
										toAdd);
							}
						}
					} catch (Exception e) {
					}
				}
				isLoaded = true;
				Type.messageType(MessageType.God, "Server playlist is loaded");
			}
		}).start();
	}

	@Deprecated
	public void CheckAllConfigs() {
		new Thread(new Runnable() {
			public void run() {
				List<String> toRemove = new ArrayList<String>();
				File[] files = getFolderFileList("Guilds");
				List<String> guilds = guidsId();
				String guildId = "";
				for (File file : files) {
					guildId = file.getName().substring(0, file.getName().lastIndexOf("."));
					if (!guilds.contains(guildId)) {
						toRemove.add(guildId);
					}
				}
				for (String tr : toRemove) {
					try {
						guilds.remove(guildId);
						new File(cFolder("Guilds"), tr + ".dcnf").delete();
					} catch (Exception e) {
					}
				}
			}
		}).start();
	}

	private List<String> guidsId() {
		List<String> toReturn = new ArrayList<String>();
		for (Guild guild : jda.getGuilds()) {
			toReturn.add(guild.getId());
		}
		return toReturn;
	}

	private String cFolder(String folder) {
		File locFolder = null;
		try {
			locFolder = new File(new File("").getCanonicalPath(), folder);
		} catch (IOException e) {
			locFolder = new File(folder);
		}
		return locFolder.toString();
	}

	private File[] getFolderFileList(String folder) {
		File locFolder = null;
		try {
			locFolder = new File(new File("").getCanonicalPath(), folder);
		} catch (IOException e) {
			locFolder = new File(folder);
		}
		locFolder.mkdirs();
		File[] fList = locFolder.listFiles();
		return fList;
	}

}