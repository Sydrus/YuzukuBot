package net.sydrus.yuzuku.Commands;

import java.awt.Color;
import java.util.List;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Constructors.Command;
import net.sydrus.yuzuku.Managers.LevelType;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;
import net.sydrus.yuzuku.String.Administratives;
import net.sydrus.yuzuku.addon.Manager.Addon;

public class StopCommand extends Command {

	@Override
	public boolean onCommand(User Sender, Message Message, Guild Guild, TextChannel Chat, List<LevelType> type,
			Boolean isEdited, String[] args) {

		if (type.contains(LevelType.Developer)) {
			new Thread(new Runnable() {
				public void run() {
					for (Addon addon : YuzukuBot.getAddonsManager().addons()) {
						addon.Disable();
					}
					for (TextChannel ch : YuzukuBot.MusicManager.getMusicChannels()) {
						try {
							ch.sendMessage(embedMessage("**I'm sorry but I'm hanging up**", Color.RED)).queue();
						} catch (Exception e) {
						}
					}
					Type.messageType(MessageType.Warning, YuzukuBot.MusicManager.getMusicChannels().size() + " **Audio channels were turned off**");
					Type.messageType(MessageType.Info, "Stopped");
					Chat.sendMessage(embedMessage("Stopping...", Color.YELLOW)).queue();
					YuzukuBot.getInstance().getJDA().shutdown();
					System.exit(-1);
				}
			}).start();
		} else {
			Chat.sendMessage(embedMessage(Administratives.DeveloperOnly)).queue();
		}
		return true;
	}
}
