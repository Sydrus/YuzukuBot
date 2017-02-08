package net.sydrus.yuzuku.Managers.ConsoleCommand.Commands;

import java.time.OffsetDateTime;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Constructors.ConsoleCommand;
import net.sydrus.yuzuku.FileReader.BCRItem;
import net.sydrus.yuzuku.Managers.ConsoleCommand.ConsoleDef;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;

public class info extends ConsoleCommand {

	@Override
	public boolean onCommand(ConsoleDef console, String command, String time, String[] args) {
		System.out.println(args[0]);
		System.out.println(args[1]);
		if ((args.length == 2) && (args[0].equalsIgnoreCase("server"))) {
			try {
				Guild guild = YuzukuBot.getInstance().getJDA().getGuildById(args[1]);

				System.out.println("");
				Type.messageType(MessageType.Info, "**Name:** " + guild.getName());
				Type.messageType(MessageType.Info, "**Id:** " + guild.getId());
				Type.messageType(MessageType.Info, "**Creation Time:** " + guild.getCreationTime().toString());
				Type.messageType(MessageType.Info, "**Icon Id:** " + guild.getIconId());
				Type.messageType(MessageType.Info, "**Icon Url:** " + guild.getIconUrl());
				Type.messageType(MessageType.Info, "**Splash Id:** " + guild.getSplashId());
				Type.messageType(MessageType.Info, "**Splash Url:** " + guild.getSplashUrl());
				Type.messageType(MessageType.Info, "**Check Verification:** " + guild.checkVerification());
				Type.messageType(MessageType.Info, "**Afk Channel Name:** " + guild.getAfkChannel().getName());
				Type.messageType(MessageType.Info, "**Afk Channel Id:** " + guild.getAfkChannel().getId());
				Type.messageType(MessageType.Info, "**Afk	Timeout:** " + guild.getAfkTimeout().getSeconds());
				Type.messageType(MessageType.Info,
						"**Default Notification Level:** " + guild.getDefaultNotificationLevel().toString());
				Type.messageType(MessageType.Info, "**Emotes:** " + guild.getEmotes().size());
				Type.messageType(MessageType.Info, "**Members:** " + guild.getMembers().size());
				Type.messageType(MessageType.Info, "**Owner Name:** " + guild.getOwner().getUser().getName());
				Type.messageType(MessageType.Info, "**Owner Id:** " + guild.getOwner().getUser().getId());
				Type.messageType(MessageType.Info, "**Public Channel Name:** " + guild.getPublicChannel().getName());
				Type.messageType(MessageType.Info, "**Public Channel Id:** " + guild.getPublicChannel().getId());
				Type.messageType(MessageType.Info, "**Region Name:** " + guild.getRegion().name());
				Type.messageType(MessageType.Info, "**Roles:** " + guild.getRoles().size());
				Type.messageType(MessageType.Info, "**Text Channel:** " + guild.getTextChannels().size());
				Type.messageType(MessageType.Info, "**Voice Channels:** " + guild.getVoiceChannels().size());
				Type.messageType(MessageType.Info, "**Voice States:** " + guild.getVoiceStates().size());
				Type.messageType(MessageType.Info, "**Is Available:** " + guild.isAvailable());

			} catch (Exception e) {
				Type.messageType(MessageType.Error, "**This server not exist**");
			}
		} else if ((args.length == 3) && (args[0].equalsIgnoreCase("tchannel"))) {
			try {
				TextChannel textChannel = YuzukuBot.getInstance().getJDA().getGuildById(args[1])
						.getTextChannelById(args[2]);
				System.out.println("");
				Type.messageType(MessageType.Info, "**Name:** " + textChannel.getName());
				Type.messageType(MessageType.Info, "**Id:** " + textChannel.getId());
				Type.messageType(MessageType.Info, "**Position:** " + textChannel.getPosition());
				Type.messageType(MessageType.Info, "**Position Raw:** " + textChannel.getPositionRaw());
				Type.messageType(MessageType.Info, "**Topic:** " + textChannel.getTopic());
				Type.messageType(MessageType.Info, "**Creation Time:** " + getDateAndHour(textChannel.getCreationTime()));
				Type.messageType(MessageType.Info,
						"**Cached History:** " + textChannel.getHistory().getCachedHistory().size());
				Type.messageType(MessageType.Info, "**Members:** " + textChannel.getMembers().size());
				Type.messageType(MessageType.Info, "**Guild:** " + textChannel.getGuild().getName());
				Type.messageType(MessageType.Info,
						"**Permission Overrides:** " + textChannel.getPermissionOverrides().size());
				Type.messageType(MessageType.Info,
						"**Pinned Messages:** " + textChannel.getPinnedMessages().block().size());
				Type.messageType(MessageType.Info,
						"**Role Permission Overrides:** " + textChannel.getRolePermissionOverrides().size());

			} catch (Exception e) {
				Type.messageType(MessageType.Error, "**This Text Channel not exist**");
			}
		} else if ((args.length == 3) && (args[0].equalsIgnoreCase("vchannel"))) {
			try {
				VoiceChannel voiceChannel = YuzukuBot.getInstance().getJDA().getGuildById(args[1])
						.getVoiceChannelById(args[2]);
				System.out.println("");
				Type.messageType(MessageType.Info, "**Name:** " + voiceChannel.getName());
				Type.messageType(MessageType.Info, "**Id:** " + voiceChannel.getId());
				Type.messageType(MessageType.Info, "**Position:** " + voiceChannel.getPosition());
				Type.messageType(MessageType.Info, "**Position Raw:** " + voiceChannel.getPositionRaw());
				Type.messageType(MessageType.Info, "**User Limit:** " + voiceChannel.getUserLimit());
				Type.messageType(MessageType.Info, "**Creation Time:** " + getDateAndHour(voiceChannel.getCreationTime()));
				Type.messageType(MessageType.Info, "**Bitrate:** " + voiceChannel.getBitrate());
				Type.messageType(MessageType.Info, "**Members:** " + voiceChannel.getMembers().size());
				Type.messageType(MessageType.Info, "**Guild:** " + voiceChannel.getGuild().getName());
				Type.messageType(MessageType.Info,
						"**Permission Overrides:** " + voiceChannel.getPermissionOverrides().size());
				Type.messageType(MessageType.Info,
						"**Role Permission Overrides:** " + voiceChannel.getRolePermissionOverrides().size());

			} catch (Exception e) {
				Type.messageType(MessageType.Error, "**This Voice Channel not exist**");
			}
		} else if ((args.length == 3) && (args[0].equalsIgnoreCase("users"))) {
			try {
				Guild gd = YuzukuBot.getInstance().getJDA().getGuildById(args[1]);
				User user = gd.getMemberById(args[2]).getUser();
				Type.messageType(MessageType.Info, "**Name:** " + user.getName());
				Type.messageType(MessageType.Info, "**Id:** " + user.getId());
				Type.messageType(MessageType.Info, "**Avatar Id:** " + user.getAvatarId());
				Type.messageType(MessageType.Info, "**Avatar Url:** " + user.getAvatarUrl());
				Type.messageType(MessageType.Info, "**Default Avatar Id:** " + user.getDefaultAvatarId());
				Type.messageType(MessageType.Info, "**Default Avatar Url:** " + user.getDefaultAvatarUrl());
				Type.messageType(MessageType.Info, "**Discriminator:** " + user.getDiscriminator());
				Type.messageType(MessageType.Info, "**Join Date:** " + getDateAndHour(gd.getMember(user).getJoinDate()));
				Type.messageType(MessageType.Info, "**Creation Time:** " + getDateAndHour(user.getCreationTime()));
				Type.messageType(MessageType.Info, "**Has Private Channel:** " + user.hasPrivateChannel());
				if (user.hasPrivateChannel()) {
					Type.messageType(MessageType.Info, "Private Channel id:** " + user.getPrivateChannel().getId());
				}
				Type.messageType(MessageType.Info, "**Is Bot:** " + user.isBot());
				Type.messageType(MessageType.Info, "**Is Fake:** " + user.isFake());
				Type.messageType(MessageType.Info, "**As Mention:** " + user.getAsMention());
				Type.messageType(MessageType.Info, "**Owner of the server:** " + isOwner(user, gd));
				Type.messageType(MessageType.Info, "**Administrator:** " + isAdm(user));
				Type.messageType(MessageType.Info,
						"**Yumi Bot Developer:** " + YuzukuBot.getInstance().isDeveloper(user.getId()));

			} catch (Exception e) {
				Type.messageType(MessageType.Error, "**This user not exist**");
			}
		} else {
			Type.messageType(MessageType.Error,
					"Type \"server serverId\", \"tchannel serverId channelId\", \"vchannel serverId channelId\", \"users serverId userId\"");
		}

		return true;
	}

	private boolean isOwner(User user, Guild guild) {
		if (user.getId().equals(guild.getOwner().getUser().getId())) {
			return true;
		}
		return false;
	}

	public String getDateAndHour(OffsetDateTime dtime) {
		return dtime.getYear() + "/" + dtime.getMonth().getValue() + "/" + dtime.getDayOfMonth() + " " + dtime.getHour() + ":"
				+ dtime.getMinute() + ":" + dtime.getSecond();
	}

	private boolean isAdm(User user) {
		if (YuzukuBot.getInstance().settingsData
				.getList(BCRItem.getItem(net.sydrus.yuzuku.FileReader.BCRItem.Type.Administrators))
				.contains(user.getId())) {

		}
		return false;
	}
}
