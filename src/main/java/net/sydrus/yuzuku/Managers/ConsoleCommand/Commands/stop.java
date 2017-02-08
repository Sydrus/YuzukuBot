package net.sydrus.yuzuku.Managers.ConsoleCommand.Commands;

import java.awt.Color;

import net.dv8tion.jda.core.entities.TextChannel;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Constructors.ConsoleCommand;
import net.sydrus.yuzuku.Managers.EmbedManager;
import net.sydrus.yuzuku.Managers.ConsoleCommand.ConsoleDef;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;
import net.sydrus.yuzuku.addon.Manager.Addon;

public class stop extends ConsoleCommand {

	@Override
	public boolean onCommand(ConsoleDef console, String command, String time, String[] args) {
		new Thread(new Runnable() {
			public void run() {
				EmbedManager emb = new EmbedManager();
				emb.setColor(Color.RED);
				emb.setDescription("**I'm sorry but I'm hanging up**");
				for (Addon addon : YuzukuBot.getAddonsManager().addons()) {
					addon.Disable();
				}
				for (TextChannel ch : YuzukuBot.MusicManager.getMusicChannels()) {
					try {
						ch.sendMessage(emb.getMessage()).queue();
					} catch (Exception e) {
					}
				}
				Type.messageType(MessageType.Warning, YuzukuBot.MusicManager.getMusicChannels().size() + " **Audio channels were turned off**");
				Type.messageType(MessageType.Info, "Stopped");
				YuzukuBot.getInstance().getJDA().shutdown();
				System.exit(-1);
			}
		}).start();
		return true;
	}

}
