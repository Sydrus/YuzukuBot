package net.sydrus.yuzuku.Commands;

import java.time.OffsetDateTime;
import java.util.List;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.sydrus.yuzuku.Constructors.Command;
import net.sydrus.yuzuku.Managers.HelpManager;
import net.sydrus.yuzuku.Managers.LevelType;
import net.sydrus.yuzuku.Managers.HelpManager.HelpType;

public class mydata extends Command {

	@Override
	public boolean onCommand(User Sender, Message Message, Guild Guild, TextChannel Chat, List<LevelType> type,
			Boolean isEdited, String[] args) {
		boolean isVoice = Guild.getMember(Sender).getVoiceState().getChannel() != null;
		String Game = "";
		try {
			if (Guild.getMember(Sender).getGame().getName() == null) {
				Game = "None";
			} else {
				Game = Guild.getMember(Sender).getGame().getName();
			}
		} catch (Exception e) {
			Game = "None";
		}
		HelpManager manager = new HelpManager();
		manager.setTitle("**#Your Data:**");
		manager.addItem("**Your Id:** ", Sender.getId());
		manager.addItem("**Owner of the server:** ", type.contains(LevelType.ServerOwner) + "");
		manager.addItem("**Administrator:** ", type.contains(LevelType.Administrator) + "");
		manager.addItem("**Yuzuku bot Developer:** ", type.contains(LevelType.Developer) + "");
		manager.addItem("**Join Date**", getDateAndHour(Guild.getMember(Sender).getJoinDate()));
		manager.addItem("**Current game:**", Game);
		manager.addItem("**Online status:**", Guild.getMember(Sender).getOnlineStatus().toString());
		manager.addItem("**You are on a voice channel:**", isVoice + "");
		Chat.sendMessage(manager.getEmbedManager(HelpType.UseCommentWhiteOutBrackets).getMessage()).queue();
		return true;
	}

	public String getDateAndHour(OffsetDateTime dtime) {
		return dtime.getYear() + "/" + dtime.getMonth().getValue() + "/" + dtime.getDayOfMonth() + " " + dtime.getHour()
				+ ":" + dtime.getMinute() + ":" + dtime.getSecond();
	}
}
