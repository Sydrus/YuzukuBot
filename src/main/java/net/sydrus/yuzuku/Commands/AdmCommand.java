package net.sydrus.yuzuku.Commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Constructors.Command;
import net.sydrus.yuzuku.FileReader.BCRItem;
import net.sydrus.yuzuku.FileReader.ConfigReader;
import net.sydrus.yuzuku.FileReader.BCRItem.Type;
import net.sydrus.yuzuku.Managers.HelpManager;
import net.sydrus.yuzuku.Managers.LevelType;
import net.sydrus.yuzuku.Managers.HelpManager.HelpType;
import net.sydrus.yuzuku.String.Administratives;

public class AdmCommand extends Command {

	@Override
	public boolean onCommand(User Sender, Message Message, Guild Guild, TextChannel Chat, List<LevelType> type,
			Boolean isEdited, String[] args) {
		if (!isAdministrator(type)) {
			Chat.sendMessage(embedMessage(Administratives.AdminOnly, Color.RED)).queue();
			return true;
		}
		YuzukuBot.getInstance().settingsData.reload();
		if (!YuzukuBot.getInstance().settingsData.contains(BCRItem.getItem(Type.Administrators))) {
			List<String> item = new ArrayList<String>();
			item.add("");
			YuzukuBot.getInstance().settingsData.set(BCRItem.getItem(Type.Administrators), item);
			YuzukuBot.getInstance().settingsData.save();
		}

		if ((args.length == 1) && (args[0].equalsIgnoreCase("list"))) {
			try {
				List<Object> admlist = YuzukuBot.getInstance().settingsData.getList(BCRItem.getItem(Type.Administrators));
				String adm = "";
				for (Object user : admlist) {
					adm = adm + getAPI().getUserById(user.toString()).getName() + "\n\n";

				}
				Chat.sendMessage(embedMessage("**Registered Administrators:**\n" + adm)).queue();
			} catch (Exception e) {
				Chat.sendMessage(embedMessage("Failed to find user list", Color.YELLOW)).queue();
			}

		} else if ((args.length >= 2) && (args[0].equalsIgnoreCase("add"))) {
			try {
				YuzukuBot.getInstance().settingsData.reload();
				List<Object> admlist = YuzukuBot.getInstance().settingsData.getList(BCRItem.getItem(Type.Administrators));
				String userId = Message.getMentionedUsers().get(0).getId();
				if (Guild.getOwner().getUser().getId().equals(userId)) {
					Chat.sendMessage(embedMessage("This user is owner the server", Color.YELLOW));
					return true;
				}
				if (admlist.contains(userId)) {
					Chat.sendMessage(embedMessage("This user has already been added", Color.YELLOW)).queue();
					return true;
				}
				settingsDataAddItem(Type.Administrators, userId);
				Chat.sendMessage(embedMessage("User defined as admin")).queue();
			} catch (Exception e) {
				Chat.sendMessage(embedMessage("Failed to add user", Color.RED)).queue();
				e.printStackTrace();
			}
		} else if ((args.length >= 2) && (args[0].equalsIgnoreCase("remove"))) {
			List<Object> admlist = YuzukuBot.getInstance().settingsData.getList(BCRItem.getItem(Type.Administrators));
			try {
				String userId = Message.getMentionedUsers().get(0).getId();
				if (Guild.getOwner().getUser().getId().equals(userId)) {
					Chat.sendMessage(embedMessage("This user is owner the server", Color.YELLOW)).queue();
					return true;
				}
				if (!admlist.contains(userId)) {
					Chat.sendMessage(embedMessage("This user is not on the list", Color.YELLOW));
					return true;
				}
				settingsDataRemoveItem(Type.Administrators, userId);
				Chat.sendMessage(embedMessage("User removed from administrator post")).queue();
			} catch (Exception e) {
				Chat.sendMessage(embedMessage("Failed to remove user", Color.RED));
			}
		} else if ((args.length == 1) && (args[0].equalsIgnoreCase("myid"))) {
			Chat.sendMessage(embedMessage(sender(Sender) + "\nYour id is " + Sender.getId(), Color.YELLOW)).queue();
		} else if ((args.length == 1) && (args[0].equalsIgnoreCase("chid"))) {
			Chat.sendMessage(embedMessage(sender(Sender) + "\nThe Channel id is " + Chat.getId(), Color.YELLOW)).queue();
		} else if ((args.length == 1) && (args[0].equalsIgnoreCase("svid"))) {
			Chat.sendMessage(embedMessage(sender(Sender) + "\nThe Server id is " + Guild.getId(), Color.YELLOW))
					.queue();
		} else if ((args.length == 3) && (args[0].equalsIgnoreCase("btxt"))) {
			YuzukuBot.getInstance().settingsData.reload();
			List<Object> btxt = YuzukuBot.getInstance().settingsData.getList(BCRItem.getItem(Type.BannedTexts));
			if (args[1].equalsIgnoreCase("add")) {
				try {
					if (btxt.contains(args[2])) {
						Chat.sendMessage(embedMessage("This item has already been banned", Color.YELLOW)).queue();
						return true;
					}
					settingsDataAddItem(Type.BannedTexts, args[2]);
					Chat.sendMessage(embedMessage("Link/Word banned(a)", Color.YELLOW)).queue();
				} catch (Exception e) {
					Chat.sendMessage(embedMessage("Error banning link/word", Color.RED)).queue();
				}
			} else if (args[1].equalsIgnoreCase("remove")) {
				try {
					if (!btxt.contains(args[2])) {
						Chat.sendMessage(embedMessage("This item is not banned", Color.YELLOW)).queue();
						return true;
					}
					settingsDataRemoveItem(Type.BannedTexts, args[2]);
					Chat.sendMessage(embedMessage("Link/word unbanned")).queue();
				} catch (Exception e) {
					Chat.sendMessage(
							embedMessage("Error trying unban link/word. Error: " + e.getMessage(), Color.RED))
							.queue();
					e.printStackTrace();
				}
			} else {

			}
		} else if ((args.length == 2) && (args[0].equalsIgnoreCase("btxt") && (args[1].equalsIgnoreCase("list")))) {
			try {
				String itens = "";
				List<Object> btxt = YuzukuBot.getInstance().settingsData.getList(BCRItem.getItem(Type.BannedTexts));
				for (Object item : btxt) {
					itens = itens + item + "\n";
				}
				if (itens.isEmpty()) {
					Chat.sendMessage(embedMessage("No items are banned.")).queue();
					return true;
				}
				Chat.sendMessage(embedMessage("Banned Items\n" + itens)).queue();
			} catch (Exception e) {
				Chat.sendMessage(embedMessage("Failed to check ban list")).queue();
			}
		} else {
			HelpManager adml = new HelpManager();
			adml.setPrefix("{s}", YuzukuBot.guildManager.getPrefix(Guild) + "adm");
			adml.setTitle("**Admin Commands:**");
			adml.addItem("	{s}list", "**Show the list of registered administrators**\n");
			adml.addItem("	{s}add <User>", "**Add a user as administrator**\n");
			adml.addItem("	{s}remove <User>", "**Remove admin user**\n");
			adml.addItem("	{s}myid", "**Show your id**\n");
			adml.addItem("	{s}chid", "**Show the Channel id**\n");
			adml.addItem("	{s}svid", "**Show the Server id**\n");
			adml.addItem("	{s}btxt <add/remove/list> <text> ", "**Ban a word** ");
			Chat.sendMessage(adml.getEmbedManager(HelpType.UseComment).getMessage()).queue();
		}
		return true;
	}

	public void settingsDataAddItem(BCRItem.Type type, String item) {
		ConfigReader config = YuzukuBot.getInstance().settingsData;
		config.reload();
		List<Object> itens = config.getList(BCRItem.getItem(type));
		if (!itens.contains(item)) {
			itens.add(item);
		}
		config.set(BCRItem.getItem(type), itens);
		config.save();
	}

	public void settingsDataRemoveItem(BCRItem.Type type, String item) {
		ConfigReader config = YuzukuBot.getInstance().settingsData;
		config.reload();
		List<Object> itens = config.getList(BCRItem.getItem(type));
		if (itens.size() <= 1) {
			config.set(BCRItem.getItem(type), null);
		} else {
			if (itens.contains(item)) {
				itens.remove(item);
			}
			config.set(BCRItem.getItem(type), itens);
		}
		config.save();
		config.reload();
	}

}
