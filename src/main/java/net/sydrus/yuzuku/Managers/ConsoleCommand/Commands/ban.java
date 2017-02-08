package net.sydrus.yuzuku.Managers.ConsoleCommand.Commands;


import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Constructors.ConsoleCommand;
import net.sydrus.yuzuku.Managers.ConsoleCommand.ConsoleDef;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;

public class ban extends ConsoleCommand {

	@Override
	public boolean onCommand(ConsoleDef console, String command, String time, String[] args) {
		if (args.length == 2) {
			console.sendText("Antes do jda");
			Guild gd = YuzukuBot.getInstance().getJDA().getGuildById(args[0]);
			try {
				console.sendText("No try");
				gd.getController().ban(args[1], 0).block();
				console.sendText("Foi");
			} catch (RateLimitedException e) {
				console.sendText("Erro: " + e.getMessage());
			}
			
		} else {
			Type.messageType(MessageType.Error, "Use \"ban <Server id> <user id>\"");
		}
		return true;
	}

}
