package net.sydrus.yuzuku.Managers.ConsoleCommand.Commands;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Constructors.ConsoleCommand;
import net.sydrus.yuzuku.Managers.ConsoleCommand.ConsoleDef;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type;
import net.sydrus.yuzuku.Managers.ConsoleCommand.Type.MessageType;

public class ViewRolesPermissions extends ConsoleCommand {

	@Override
	public boolean onCommand(ConsoleDef console, String command, String time, String[] args) {
		if (args.length == 2) {
			Guild guild = null;
			Role role = null;
			try {
				guild = YuzukuBot.getInstance().getJDA().getGuildById(args[0]);
				Type.messageType(MessageType.Info, "**Server name:** " + guild.getName());
			} catch (Exception e) {
				Type.messageType(MessageType.Error, "**Error on get server**");
				return true;
			}
			try {
				role = guild.getRoleById(args[1]);
				Type.messageType(MessageType.Info, "**Role name:** " + role.getName());
			} catch (Exception e) {
				Type.messageType(MessageType.Error, "**Error on get role**");
			}
			try {
				console.sendText("Permissions:");
				for (Permission rl : role.getPermissions()) {
					console.sendText("Permission name: " + rl.name());
				}
				console.sendText(" ");
				console.sendText("Role Mention: " + role.getAsMention());
				console.sendText("Role Id: " + role.getId());
				console.sendText("Role Name: " + role.getName());
				console.sendText("Role PermissionsRaw: " + role.getPermissionsRaw());
				console.sendText("Role Position: " + role.getPosition());
				console.sendText("Role Color: " + role.getColor());
				console.sendText("Role CreationTime: " + role.getCreationTime());
				console.sendText("Role Guild: " + role.getGuild());
				console.sendText("Role is Managed: " + role.isManaged());
				console.sendText("Role is Hoisted: " + role.isHoisted());
				console.sendText("Role is Mentionable :" + role.isMentionable());
				console.sendText("Role Permission: " + role.getPermissions().size());
			} catch (Exception e) {
				Type.messageType(MessageType.Error, "Error on get role permissions");
			}
		} else {
			Type.messageType(MessageType.Error, "Use: proles <serverId> <roleId>");
		}
		return true;
	}

}
