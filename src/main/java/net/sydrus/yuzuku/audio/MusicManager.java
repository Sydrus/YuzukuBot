package net.sydrus.yuzuku.audio;

import java.awt.Color;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Managers.EmbedManager;
import net.sydrus.yuzuku.exceptions.OutOfRangeException;

public class MusicManager {
	private AudioPlayerManager playerManager;
	private Map<Long, TextChannel> mtChannel = new HashMap<Long, TextChannel>();
	private Map<Long, GuildMusicManager> mmusicManager = new HashMap<Long, GuildMusicManager>();

	public Collection<TextChannel> getMusicChannels() {
		return mtChannel.values();
	}

	public MusicManager() {
		this.playerManager = new DefaultAudioPlayerManager();
		playerManager.registerSourceManager(new YoutubeAudioSourceManager(true));
		AudioSourceManagers.registerLocalSource(playerManager);
	}

	public void setTextChannel(Guild guild, TextChannel textchannel) {
		long gid = Long.parseLong(guild.getId());
		mtChannel.put(gid, textchannel);
	}

	public void setGuildManager(Guild guild, GuildMusicManager manager) {
		long gid = Long.parseLong(guild.getId());
		mmusicManager.put(gid, manager);
	}

	public void setNextQueue(Guild guild, int queue) throws OutOfRangeException {
		long gid = Long.parseLong(guild.getId());
		mmusicManager.get(gid).setNextQueue(queue);
	}

	public AudioTrack getSettedNextQueue(Guild guild) {
		long gid = Long.parseLong(guild.getId());
		return mmusicManager.get(gid).getSettedNextQueue();
	}
	
	public int getSettedNextQueueInt(Guild guild) {
		long gid = Long.parseLong(guild.getId());
		return mmusicManager.get(gid).getSettedNextQueueInt();
	}
	
	public void clearTracks(Guild guild) {
		long gid = Long.parseLong(guild.getId());
		mmusicManager.get(gid).clear();
	}

	public void addTrack(Guild guild, AudioTrack track) {
		GuildMusicManager manager = getGuildMusicManager(guild);
		manager.registerTrack(track);
	}

	public void addTracks(Guild guild, List<AudioTrack> trackslist) {
		GuildMusicManager manager = getGuildMusicManager(guild);
		manager.registerTracks(trackslist);
	}

	public TextChannel getChannel(Guild guild) {
		return mtChannel.get(Long.parseLong(guild.getId()));
	}

	public boolean exist(Guild guild) {
		long gid = Long.parseLong(guild.getId());
		return mtChannel.containsKey(gid);
	}

	public void setVolume(Guild guild, int volume) {
		long gid = Long.parseLong(guild.getId());
		GuildMusicManager gman = mmusicManager.get(gid);
		if (gman.getVolume() != volume) {
			gman.setVolume(volume);
		}
	}

	public int getVolume(Guild guild) {
		long gid = Long.parseLong(guild.getId());
		return mmusicManager.get(gid).getVolume();
	}

	public void SkipMusic(Guild guild) throws AudioException {
		GuildMusicManager musicManager = getGuildAudioPlayer(guild);
		musicManager.nextTrack();
	}

	public GuildMusicManager getGuildMusicManager(Guild guild) {
		return getGuildAudioPlayer(guild);
	}

	public void Pause(Guild guild, boolean value) {
		long gid = Long.parseLong(guild.getId());
		mmusicManager.get(gid).getPlayer().setPaused(value);
	}

	public void Start(Guild guild, TextChannel channel) {
		Start(guild, channel, 1);
	}

	public boolean hasMusic(Guild guild) {
		long gid = Long.parseLong(guild.getId());
		return mmusicManager.get(gid).hasMusic();
	}

	public void Start(Guild guild, TextChannel channel, int request) {
		long gid = Long.parseLong(guild.getId());
		GuildMusicManager manager = getGuildMusicManager(guild);
		if (manager != null) {
			if (!mmusicManager.get(gid).hasMusic()) {
				channel.sendMessage(embedMessage("No music found", Color.RED)).queue();
			} else {
				try {
					int quanty = manager.musicSize();
					if (quanty >= request) {
						mtChannel.put(gid, channel);
						manager.setTextChannel(channel);
						manager.queue(request);
					} else {
						channel.sendMessage("Music (" + request + ") not found").queue();
					}
				} catch (Exception e) {
					channel.sendMessage(embedMessage(":x: Error: " + e.getMessage(), Color.RED));
				}
			}
		} else {
			channel.sendMessage(embedMessage(":x: Error on load manager", Color.RED)).queue();
		}
	}

	public void Stop(Guild guild) {
		getGuildAudioPlayer(guild).stopTrack();
	}

	public void leaveChannel(Guild guild) {
		AudioManager audioManager = guild.getAudioManager();
		if (audioManager.isConnected()) {
			audioManager.closeAudioConnection();
		}
	}

	public void joinVoiceChannel(Guild guild, VoiceChannel voiceChannel) {
		AudioManager audioManager = guild.getAudioManager();
		if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
			audioManager.openAudioConnection(voiceChannel);
		}
	}

	public AudioTrack nowPlaying(Guild guild) {
		return getGuildMusicManager(guild).getPlayingTrack();
	}

	public void addItems(final Guild guild, String trackUrl) {
		GuildMusicManager musicManager = getGuildAudioPlayer(guild);

		playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
			public void trackLoaded(AudioTrack track) {
				addTrack(guild, track);
			}

			public void playlistLoaded(AudioPlaylist playlist) {
				AudioTrack firstTrack = playlist.getSelectedTrack();
				if (firstTrack == null) {
					firstTrack = playlist.getTracks().get(0);
				}
				List<AudioTrack> track = playlist.getTracks();
				addTracks(guild, track);
			}

			public void noMatches() {
			}

			public void loadFailed(FriendlyException exception) {
			}
		});
	}

	public void addItems(final Guild guild, final TextChannel channel, final String trackUrl) {
		final GuildMusicManager musicManager = getGuildAudioPlayer(guild);

		playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
			public void trackLoaded(AudioTrack track) {
				if (YuzukuBot.guildManager.autoSaveMusic(guild)) {
					YuzukuBot.guildManager.addMusic(guild, track);
				}
				if (!musicManager.containsAudioTrack(track)) {
					addTrack(guild, track);
					channel.sendMessage(
							embedMessage("Added music (" + track.getInfo().title + ") musics to the list)", Color.RED))
							.queue();
				} else {
					channel.sendMessage(embedMessage("This music has already been added", Color.RED)).queue();
				}
			}

			public void playlistLoaded(AudioPlaylist playlist) {
				List<AudioTrack> track = playlist.getTracks();
				if (YuzukuBot.guildManager.autoSaveMusic(guild)) {
					YuzukuBot.guildManager.addList(guild, playlist);
				}
				addTracks(guild, track);
				channel.sendMessage(embedMessage(
						"added (" + playlist.getTracks().size() + ") musics From playlist (" + playlist.getName() + ")",
						Color.YELLOW)).queue();
			}

			public void noMatches() {
				channel.sendMessage("Nothing found by " + trackUrl).queue();
			}

			public void loadFailed(FriendlyException exception) {
				channel.sendMessage("Could not add: " + exception.getMessage()).queue();
			}
		});
	}

	public boolean isPaused(Guild guild) {
		return getGuildMusicManager(guild).getPlayer().isPaused();
	}

	public boolean hasConnection(Guild guild) {
		return guild.getAudioManager().getConnectedChannel() != null;
	}

	public void startCode(Guild guild) {
		getGuildAudioPlayer(guild);
	}

	public boolean isStarted(Guild guild) {
		return exist(guild);
	}

	private synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
		long guildId = Long.parseLong(guild.getId());
		GuildMusicManager musicManager = mmusicManager.get(guildId);

		if (musicManager == null) {
			musicManager = new GuildMusicManager(playerManager);
			mmusicManager.put(guildId, musicManager);
		}

		guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

		return musicManager;
	}

	@Deprecated
	public void closeConnection(Guild guild) {
		long guildId = Long.parseLong(guild.getId());
		guild.getAudioManager().closeAudioConnection();
		mtChannel.remove(guildId);
		getGuildAudioPlayer(guild).reset();
		YuzukuBot.guildManager.registerTracks();
	}

	private Message embedMessage(String text, Color color) {
		EmbedManager emb = new EmbedManager();
		emb.setDescription(text);
		emb.setColor(color);
		return emb.getMessage();
	}

	public final void removeChannel(TextChannel channel) {
		long guildId = Long.parseLong(channel.getGuild().getId());
		mtChannel.remove(guildId);
	}
}