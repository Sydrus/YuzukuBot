package net.sydrus.yuzuku.Commands;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Constructors.Command;
import net.sydrus.yuzuku.FileReader.BCRItem;
import net.sydrus.yuzuku.Managers.LevelType;

public class infouser extends Command {

	@Override
	public boolean onCommand(User Sender, Message Message, Guild Guild, TextChannel Chat, List<LevelType> type,
			Boolean isEdited, String[] args) {

		if (args.length >= 1) {
			try {
				List<String> data = new ArrayList<String>();

				User user = Message.getMentionedUsers().get(0);
				OffsetDateTime dtime = Guild.getMember(user).getJoinDate();

				data.add("Name: " + user.getName());
				data.add("Id: " + user.getId());
				data.add("Avatar Id: " + user.getAvatarId());
				data.add("Avatar Url: " + user.getAvatarUrl());
				data.add("Default Avatar Id: " + user.getDefaultAvatarId());
				data.add("Default Avatar Url: " + user.getDefaultAvatarUrl());
				data.add("Discriminator: " + user.getDiscriminator());
				data.add("Creation Time: " + getDateAndHour(user.getCreationTime()));
				data.add("Join Date: " + getDateAndHour(dtime));
				data.add("Has Private Channel: " + user.hasPrivateChannel());
				if (user.hasPrivateChannel()) {
					data.add("Private Channel id: " + user.getPrivateChannel().getId());
				}
				data.add("Is Bot: " + user.isBot());
				data.add("Is Fake: " + user.isFake());
				data.add("As Mention: " + user.getAsMention());
				data.add("Owner of the server: " + isOwner(user, Guild));
				data.add("Administrator: " + isAdm(user));
				data.add("Yuzuku Bot Developer: " + YuzukuBot.getInstance().isDeveloper(user.getId()));
				String endItems = "";
				for (String itemslist : data) {
					endItems = endItems + itemslist + "\n";
				}

				Chat.sendMessage(Format("You data:\n\n" + endItems)).queue();
			} catch (Exception e) {
				Chat.sendMessage(Format("**Invalid user**")).queue();
			}
		} else {
			Chat.sendMessage(Format("Use: /uinfo <userMention>")).queue();
		}

		return true;
	}

	public String getDateAndHour(OffsetDateTime dtime) {
		return dtime.getYear() + "/" + dtime.getMonth().getValue() + "/" + dtime.getDayOfMonth() + " " + dtime.getHour() + ":"
				+ dtime.getMinute() + ":" + dtime.getSecond();
	}

	private boolean isOwner(User user, Guild guild) {
		if (user.getId().equals(guild.getOwner().getUser().getId())) {
			return true;
		}
		return false;
	}

	private boolean isAdm(User user) {
		if (YuzukuBot.getInstance().settingsData
				.getList(BCRItem.getItem(net.sydrus.yuzuku.FileReader.BCRItem.Type.Administrators))
				.contains(user.getId())) {

		}
		return false;
	}

	private String Format(String text) {
		return "```css\n\n" + text + "\n\n```";
	}
}
