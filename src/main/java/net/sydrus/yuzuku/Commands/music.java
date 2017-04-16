package net.sydrus.yuzuku.Commands;

import java.awt.Color;
import java.util.List;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Constructors.Command;
import net.sydrus.yuzuku.FileReader.ConfigReader;
import net.sydrus.yuzuku.Managers.EmbedManager;
import net.sydrus.yuzuku.Managers.GuildManager;
import net.sydrus.yuzuku.Managers.HelpManager;
import net.sydrus.yuzuku.Managers.LevelType;
import net.sydrus.yuzuku.Managers.HelpManager.HelpType;
import net.sydrus.yuzuku.audio.AudioException;
import net.sydrus.yuzuku.audio.GuildMusicManager;
import net.sydrus.yuzuku.audio.MusicManager;
import net.sydrus.yuzuku.audio.AudioException.ExceptionType;
import net.sydrus.yuzuku.exceptions.OutOfRangeException;

public class music extends Command {

	MusicManager manager = YuzukuBot.MusicManager;

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(User Sender, Message Message, Guild Guild, TextChannel Chat, List<LevelType> type,
			Boolean isEdited, String[] args) {
		if (!manager.isStarted(Guild)) {
			manager.startCode(Guild);
		}
		if ((YuzukuBot.guildManager.hasMusicInConfig(Guild)) && (manager.getGuildMusicManager(Guild).musicSize() == 0)
				&& (YuzukuBot.guildManager.playlistIsLoaded())) {
			Chat.sendMessage(embedMessage("Please wait, I'm loading the music", Color.YELLOW)).queue();
			return true;
		}
		if (args.length == 0) {
			lenght0Message(Chat, Guild);
		} else if ((args.length == 2) && (args[0].equalsIgnoreCase("add"))) {
			manager.addItems(Guild, Chat, args[1]);
		} else if ((args.length == 1) && (args[0].equalsIgnoreCase("play"))) {
			if (manager.hasConnection(Guild)) {
				if (manager.hasMusic(Guild)) {
					if (manager.isPaused(Guild)) {
						manager.Pause(Guild, false);
					} else {
						if (!manager.getGuildMusicManager(Guild).isPlaying()) {
							manager.Start(Guild, Chat);
							manager.setVolume(Guild, YuzukuBot.guildManager.getVolume(Guild));
							Chat.sendMessage(nowPlaying(Guild,true)).queue();
						} else {
							Chat.sendMessage(embedMessage("I'm already playing a music", Color.RED)).queue();
						}
					}
				} else {
					Chat.sendMessage(embedMessage("**:x: No music found**", Color.RED)).queue();
				}
			} else {
				Chat.sendMessage(embedMessage("**:x: I'm not connected to a channel**", Color.RED)).queue();
			}

		} else if ((args.length == 2) && (args[0].equalsIgnoreCase("play"))) {
			if (manager.hasConnection(Guild)) {
				if (manager.hasMusic(Guild)) {
					try {
						int music_queue = Integer.valueOf(args[1]).intValue();
						if ((music_queue <= manager.getGuildMusicManager(Guild).musicSize()) && (music_queue > 0)) {
							if (manager.getGuildMusicManager(Guild).getAtualQueue() != music_queue) {
								if (manager.getGuildMusicManager(Guild).isPlaying()) {
									manager.Stop(Guild);
								}
								manager.Start(Guild, Chat, music_queue);
								manager.setVolume(Guild, YuzukuBot.guildManager.getVolume(Guild));
								Chat.sendMessage(nowPlaying(Guild, true)).queue();
							} else {
								Chat.sendMessage(embedMessage("I'm already playing this song", Color.RED)).queue();
							}
						} else {
							Chat.sendMessage(embedMessage("Invalid music " + music_queue, Color.RED)).queue();
						}
					} catch (Exception e) {
						e.printStackTrace();
						Chat.sendMessage(embedMessage("Error: " + e.getMessage(), Color.RED)).queue();
					}
				} else {
					Chat.sendMessage(embedMessage("**:x: No music found**", Color.RED)).queue();
				}
			} else {
				Chat.sendMessage(embedMessage("**:x: I'm not connected on any channel**", Color.RED)).queue();
			}

		} else if ((args.length == 1) && (args[0].equalsIgnoreCase("pause"))) {
			if (manager.hasConnection(Guild)) {
				if (manager.getGuildMusicManager(Guild).isPlaying()) {
					manager.Pause(Guild, true);
					Chat.sendMessage(embedMessage("Paused", Color.YELLOW)).queue();
				}
			} else {
				Chat.sendMessage(embedMessage("**:x: I'm not connected on any channel**", Color.RED)).queue();
			}
		} else if ((args.length == 1) && (args[0].equalsIgnoreCase("stop"))) {
			try {
				if (manager.hasConnection(Guild)) {
					if ((manager.getGuildMusicManager(Guild).isPlaying())
							|| (manager.getGuildMusicManager(Guild).getPlayer().isPaused())) {
						manager.Stop(Guild);
						manager.closeConnection(Guild);
						Chat.sendMessage(embedMessage("** Stopped By (" + Sender.getName() + ")**")).queue();
					} else {
						Chat.sendMessage(embedMessage("I'm not playing or I'm paused", Color.RED)).queue();
					}
				} else {
					Chat.sendMessage(embedMessage("**:x: I'm not connected**", Color.RED)).queue();
				}
			} catch (Exception e) {
				Chat.sendMessage(embedMessage("**Error on stop**", Color.RED)).queue();
			}
		} else if ((args.length == 1) && (args[0].equalsIgnoreCase("pn"))) {
			if (manager.getGuildMusicManager(Guild).isPlaying()) {
				try {
					Chat.sendMessage(nowPlaying(Guild, true)).queue();
				} catch (Exception e) {
					Chat.sendMessage(embedMessage("**Error on get nowplaying**", Color.RED)).queue();
				}
			} else {
				Chat.sendMessage(embedMessage("**I'm not playing anything now**", Color.RED)).queue();
			}
		} else if ((args.length == 1) && (args[0].equalsIgnoreCase("join"))) {
			if (!manager.hasConnection(Guild)) {
				if (Guild.getMember(Sender).getVoiceState().inVoiceChannel()) {
					VoiceChannel voice = Guild.getMember(Sender).getVoiceState().getChannel();
					manager.joinVoiceChannel(Guild, voice);
					Chat.sendMessage(embedMessage(
							"**I'm connected in to the channel :musical_note: (" + voice.getName() + ")** by (" + Sender.getName() + ")"))
							.queue();
				} else {
					Chat.sendMessage(embedMessage("**You need to be on a channel to do this**", Color.RED)).queue();
				}
			} else {
				Chat.sendMessage(embedMessage(":x: **I'm already on a channel**", Color.RED)).queue();
			}
		} else if ((args.length == 2) && (args[0].equalsIgnoreCase("join"))) {
			if (!manager.hasConnection(Guild)) {
				VoiceChannel voice = getVoiceChannel(Guild, args[1]);
				if (voice == null) {
					Chat.sendMessage("**" + args[1] + ":x: Channel does not exist**").queue();
				} else {
					manager.joinVoiceChannel(Guild, voice);
					Chat.sendMessage(embedMessage("**I'm connected in :musical_note: (" + voice.getName() + ") by ("
							+ Sender.getName() + ")**")).queue();
				}
			} else {
				Chat.sendMessage(embedMessage(":x: **I'm already on a channel**", Color.RED)).queue();
			}
		} else if ((args.length == 1) && (args[0].equalsIgnoreCase("leave"))) {
			if (manager.hasConnection(Guild)) {
				manager.Stop(Guild);
				manager.leaveChannel(Guild);
				Chat.sendMessage(embedMessage("**Disconnected**")).queue();
			} else {
				Chat.sendMessage(embedMessage(":x: I'm not connected on any channel", Color.RED)).queue();
			}
		} else if ((args.length == 2) && (args[0].equalsIgnoreCase("setv"))) {
			if (manager.hasConnection(Guild)) {
				int volume = Integer.valueOf(args[1]).intValue();
				if (volume <= 100) {
					manager.setVolume(Guild, volume);
					GuildManager gm = YuzukuBot.guildManager;
					gm.setVolume(Guild, volume);
					Chat.sendMessage(embedMessage("**Volume set to (" + volume + "%)**")).queue();
				} else {
					Chat.sendMessage(embedMessage("**The volume can not be greater than 100%**", Color.RED)).queue();
				}
			} else {
				Chat.sendMessage(embedMessage(":x: **I'm not connected on any channel**", Color.RED)).queue();
			}
		} else if ((args.length == 1) && (args[0].equalsIgnoreCase("skip"))) {
			if (manager.getGuildMusicManager(Guild).isPlaying()) {
				try {
					if (manager.getGuildMusicManager(Guild).hasNextTrack()) {
						manager.SkipMusic(Guild);
						Chat.sendMessage(nowPlaying(Guild, true)).queue();
					} else {
						Chat.sendMessage(embedMessage("**There's no other music to play.**", Color.YELLOW));
					}
				} catch (AudioException e) {
					if (e.getExceptionType() == ExceptionType.isFatal) {
						Chat.sendMessage(embedMessage(e.getMessage(), Color.RED)).queue();
					} else if (e.getExceptionType() == ExceptionType.isFatal) {
						Chat.sendMessage(embedMessage(":x: **Error on skip music:** " + e.getMessage(), Color.RED)).queue();
					} else if (e.getExceptionType() == ExceptionType.IsWarning) {
						Chat.sendMessage(embedMessage(e.getMessage(), Color.YELLOW)).queue();
					}
				} catch (IndexOutOfBoundsException e) {
					Chat.sendMessage(embedMessage(e.getMessage(), Color.YELLOW)).queue();
				}
			} else {
				Chat.sendMessage(embedMessage("**I'm not playing anything now!**", Color.RED)).queue();
			}
		} else if ((args.length == 1) && (args[0].equalsIgnoreCase("list"))) {
			GuildMusicManager musicman = manager.getGuildMusicManager(Guild);
			Chat.sendMessage(embedMessage("**Music list: " + musicman.musicSize() + "**")).queue();

			  if

			  (manager.hasMusic(Guild)) { String toReturn = ""; int position =
			  0; for (AudioTrack track :
			  manager.getGuildMusicManager(Guild).scheduler.getTrucks()) {
			  toReturn = toReturn + "(" + position + ") " +
			  track.getInfo().title; position++; } } else {
			  Chat.sendMessage(embedMessage("No musics")).queue(); }

		} else if ((args.length == 1) && (args[0].equalsIgnoreCase("shuffle"))) {

		} else if ((args.length == 1) && (args[0].equalsIgnoreCase("asp"))) {
			GuildManager gm = YuzukuBot.guildManager;
			if (gm.autoSaveMusic(Guild)) {
				gm.setAutosave(Guild, false);
				Chat.sendMessage(embedMessage("**Auto save**  __**off**__")).queue();
			} else {
				gm.setAutosave(Guild, true);
				Chat.sendMessage(embedMessage("**Auto save** __**on**__")).queue();
			}
		} else if ((args.length == 1) && (args[0].equalsIgnoreCase("clear"))) {
			ConfigReader rdr = new ConfigReader("Guilds", Guild.getId());
			if (manager.hasMusic(Guild)) {
				manager.clearTracks(Guild);
				if (rdr.contains("PlayList")) {
					rdr.set("PlayList", null);
					rdr.save();
					rdr.reload();
				}
				manager.Stop(Guild);
				manager.closeConnection(Guild);
				Chat.sendMessage(embedMessage("**Songs removed**")).queue();
			} else {
				Chat.sendMessage(embedMessage("**No songs to remove**")).queue();
			}
		} else if ((args.length == 1) && (args[0].equalsIgnoreCase("reset"))) {
			try {
				manager.closeConnection(Guild);
				Chat.sendMessage(embedMessage(
						"**I've tried resetting your connection, if this not work, contact the developer, It may be necessary to wait between 1 and 5 minutes**",
						Color.YELLOW)).queue();
			} catch (Exception e) {
				e.printStackTrace();
				Chat.sendMessage("__**Error**__ **trying to reset AudioPlayer. Send this error to the author of** "
						+ YuzukuBot.botName + " :" + e).queue();
			}
		} else if ((args.length == 2) && (args[0].equalsIgnoreCase("snm"))) {
			try {
				int music_queue = Integer.valueOf(args[1]).intValue();
				try {
					manager.setNextQueue(Guild, music_queue);
					EmbedManager embManager = new EmbedManager();
					AudioTrack track = this.manager.getSettedNextQueue(Guild);
					embManager.setColor(Color.GREEN);
					embManager.addField("Playing Now", track.getInfo().title, true);
					embManager.addField("Durations", track.getDuration() + "", true);
					embManager.addField("Position in queue", manager.getSettedNextQueueInt(Guild) + "/"
							+ manager.getGuildMusicManager(Guild).musicSize(), true);
					Chat.sendMessage(embManager.getMessage()).queue();
				} catch (OutOfRangeException e) {
					Chat.sendMessage(embedMessage("Error: " + e.getMessage(), Color.RED)).queue();
				}
			} catch (Exception e) {
				Chat.sendMessage(embedMessage("Error: " + e.getMessage(), Color.RED)).queue();
			}
		} else if ((args.length == 1) && (args[0].equalsIgnoreCase("nm"))) {
			if (this.manager.getSettedNextQueueInt(Guild) > 0) {
				EmbedManager embManager = new EmbedManager();
				AudioTrack track = this.manager.getSettedNextQueue(Guild);
				embManager.setColor(Color.GREEN);
				embManager.addField("Playing Now", track.getInfo().title, true);
				embManager.addField("Durations", track.getDuration() + "", true);
				embManager.addField("Position in queue",
						manager.getSettedNextQueueInt(Guild) + "/" + manager.getGuildMusicManager(Guild).musicSize(),
						true);
				Chat.sendMessage(embManager.getMessage()).queue();
			} else {
				Chat.sendMessage(embedMessage("**No music was defined as next**")).queue();
			}
		} else {
			lenght0Message(Chat, Guild);
		}
		return true;
	}

	public boolean isConnected(Member member) {
		return member.getVoiceState().getChannel() != null;
	}

	private void lenght0Message(TextChannel channel, Guild guild) {
		HelpManager help = new HelpManager();
		help.setPrefix("{s}", YuzukuBot.guildManager.getPrefix(guild) + "music");
		help.addItem("	{s}add <Playlist/YoutubeUrl> - ", "**Add music**");
		help.addItem("	{s}play - ", "**Play a song**" );
		help.addItem("	{s}pause - ", "**Pause the music**");
		help.addItem("	{s}stop - ", "**Stop the music**");
		help.addItem("	{s}skip - ", "**Skip the music**");
		help.addItem("	{s}snm - ", "**Sets the next music**");
		help.addItem("	{s}nm - ", "**See the next music**");
		help.addItem("	{s}pn - ", "**See what's playing now**");
		help.addItem("	{s}setv - ", "**Set the volume of the music**");
		help.addItem("	{s}join <channel> - ",
				"**Enter the defined channel, if no channel is defined the bot will enter the channel you are**");
		help.addItem("	{s}leave - ", "**Exits the voice channel if it is connected** " );
		help.addItem("	{s}asp - ", "**For songs to be saved in the setting when adding. Use <True/False>**");
		help.addItem("	{s}shuffle - ", "**Shuffle the musics**");
		help.addItem("	{s}reset - ",
				"**Enter this command if your audio player is not responding / working so that it is reset**");
		help.addItem("	{s}clear - ",
				"**Removes all songs, including songs from the settings, even if the auto save option is turned off**");

		channel.sendMessage(help.getEmbedManager(HelpType.UseComment).getMessage()).queue();

	}

	private VoiceChannel getVoiceChannel(Guild guild, String name) {
		for (VoiceChannel channel : guild.getVoiceChannels()) {
			if (channel.getName().equalsIgnoreCase(name)) {
				return channel;
			}
		}
		return null;
	}

	private Message nowPlaying(Guild guild, boolean getNextTrack) {
		GuildMusicManager musicman = manager.getGuildMusicManager(guild);
		AudioTrackInfo info = musicman.getPlayer().getPlayingTrack().getInfo();
		EmbedManager manager = new EmbedManager();
		manager.setColor(Color.GREEN);
		manager.addField("Playing Now", info.title, true);
		manager.addField("Durations", musicman.getPlayingTrack().getDuration() + "", true);
		manager.addField("Volume", this.manager.getVolume(guild) + "", true);
		if (getNextTrack) {
			if (musicman.hasNextTrack()) {
				try {
					manager.addField("**Next Track**", musicman.getNextTrack().getInfo().title, false);
				} catch (Exception e) {
					e.printStackTrace();
					manager.addField("**Next Track**", "**Erro on give**", false);
				}
			}
		}
		manager.addField("**Position in queue**", musicman.getAtualQueue() + "/" + musicman.musicSize(), true);
		if (getNextTrack) {
			if (musicman.hasNextTrack()) {
				manager.addField("**Next position in queue**", musicman.nextQueue() + "/" + musicman.musicSize(), true);
			}
		}
		return manager.getMessage();
	}

}