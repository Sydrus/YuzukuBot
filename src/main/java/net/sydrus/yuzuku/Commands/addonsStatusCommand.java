
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
import net.sydrus.yuzuku.Managers.EmbedManager;
import net.sydrus.yuzuku.Managers.HelpManager;
import net.sydrus.yuzuku.Managers.LevelType;
import net.sydrus.yuzuku.Managers.HelpManager.HelpType;
import net.sydrus.yuzuku.String.AddonMessage;
import net.sydrus.yuzuku.String.Administratives;
import net.sydrus.yuzuku.addon.CommandInfo;
import net.sydrus.yuzuku.addon.Exception.AddonException;
import net.sydrus.yuzuku.addon.Manager.Addon;
import net.sydrus.yuzuku.addon.Manager.AddonsLoader;
import net.sydrus.yuzuku.addon.status.AddonStatus;

public class addonsStatusCommand extends Command {

	@Override
	public boolean onCommand(User Sender, Message Message, Guild Guild, TextChannel Chat, List<LevelType> type,
			Boolean isEdited, String[] args) {
		if (type.contains(LevelType.Developer)) {
			if ((args.length == 1) && (args[0].equalsIgnoreCase("commands"))) {
				String toretrn = "";
				for (String mng : YuzukuBot.getAddonCommand().keySet()) {
					if (!YuzukuBot.getAddonCommand().get(mng).isEmpty()) {
						toretrn = toretrn + mng + ":\n" + getItens(Chat, mng);
					}
				}
				if (!toretrn.isEmpty()) {
					if (toretrn.endsWith("\n")) {
						Chat.sendMessage(embedMessage(toretrn.substring(0, toretrn.length() - 1))).queue();
					} else {
						Chat.sendMessage(embedMessage(toretrn)).queue();
					}
				} else {
					Chat.sendMessage(embedMessage("**No command available right now**", Color.red)).queue();
				}

			} else if ((args.length == 1) && (args[0].equalsIgnoreCase("help"))) {
				HelpManager helpm = new HelpManager();
				helpm.setTitle("**Addons help command** \n");
				helpm.setPrefix("{s}", YuzukuBot.guildManager.getPrefix(Guild));
				helpm.addItem(" {s}commands", "**See the commands of Addons** \n");
				helpm.addItem(" {s}help", "**View list of commands** \n");
				helpm.addItem(" {s}aboult <Addon name>", "**View information from the Addon** \n");
				helpm.addItem(" {s}load <file name>", "**Load Addon (In development)** \n");
				helpm.addItem(" {s}stop <addon name>", "**Stop one addon (In development)** \n");

				Chat.sendMessage(helpm.getEmbedManager(HelpType.UseComment).getMessage()).queue();

			} else if ((args.length == 1) && (args[0].equalsIgnoreCase("about"))) {
				Chat.sendMessage(embedMessage("**Type a name for the Addon**", Color.RED)).queue();
			} else if ((args.length == 2) && (args[0].equalsIgnoreCase("about"))) {
				Addon addon = YuzukuBot.getAddonsManager().get(args[1]);
				if (addon == null) {
					Chat.sendMessage(embedMessage("**Invalid Addon**"));
				} else {
					List<String> items = new ArrayList<String>();
					items.add("Name: " + addon.getAddonsDF().getName());
					if (!addon.getAddonsDF().getDescription().isEmpty()) {
						items.add("Description: " + addon.getAddonsDF().getDescription());
					}
					items.add("**Version:** " + addon.getAddonsDF().getVersion());
					if (!addon.getAddonsDF().getWebsite().isEmpty()) {
						items.add("**Website:** " + addon.getAddonsDF().getWebsite());
					}
					if (!addon.getAddonsDF().getAuthors().isEmpty()) {
						items.add("**Authors:** " + ListToString(addon.getAddonsDF().getAuthors()));
					}
					if (!addon.getAddonsDF().getDep().isEmpty()) {
						items.add("**Dependencies:** " + ListToString(addon.getAddonsDF().getDep()));
					}
					Chat.sendMessage(embedMessage(ListToStringLine(items))).queue();
				}
			} else if ((args.length == 2) && (args[0].equalsIgnoreCase("load"))) {
				Addon addon = YuzukuBot.getAddonsManager().get(args[1]);
				if (addon == null) {
					try {
						AddonsLoader.getInstance().loadAddon(args[1]);
						Chat.sendMessage(embedMessage("Addon started")).queue();
					} catch (AddonException e) {
						Chat.sendMessage(embedMessage("**Error:** " + e.getMessage(), Color.RED)).queue();
					}
				} else {
					Chat.sendMessage(embedMessage("Invalid Addon", Color.RED)).queue();
				}
			} else if ((args.length == 2) && (args[0].equalsIgnoreCase("stop"))) {
				Addon addon = YuzukuBot.getAddonsManager().get(args[1]);
				if (addon == null) {
					Chat.sendMessage(embedMessage("Invalid Addon", Color.RED)).queue();
				} else {
					try {
						AddonsLoader.getInstance().remove(args[1]);
						Chat.sendMessage(embedMessage("Addon stopped", Color.YELLOW)).queue();
					} catch (AddonException e) {
						Chat.sendMessage(embedMessage("**Error:** " + e.getMessage(), Color.RED)).queue();
					}
				}
			} else {
				if (!AddonsLoader.getInstance().AddonsState.hasAddons()) {
					Chat.sendMessage(embedMessage(sender(Sender) + "\n" + AddonMessage.NoAddonsRunning, Color.YELLOW))
							.queue();
					return true;
				}
				lenght0Message(Chat, Guild, Sender);
			}
		} else {
			Chat.sendMessage(embedMessage(Administratives.DeveloperOnly, Color.RED)).queue();
		}
		return true;
	}

	private void lenght0Message(TextChannel chat, Guild guild, User sender) {
		EmbedManager emb = new EmbedManager();
		emb.setTitle("**Bot Addons:** ");
		emb.setColor(YuzukuBot.defaultMessageColor);
		String toReturn = "";
		for (String itemslist : AddonsLoader.getInstance().AddonsState.getKeySet()) {
			toReturn = itemslist.toUpperCase() + ": "
					+ getAddonState(AddonsLoader.getInstance().AddonsState.getState(itemslist)) + "\n";
		}
		emb.setDescription(toReturn);
		emb.setFooter("For more information " + YuzukuBot.guildManager.getPrefix(chat.getGuild()) + "addons help",
				sender.getAvatarUrl());
		chat.sendMessage(emb.getMessage()).queue();
	}

	private String ListToString(List<String> list) {
		String Finaly = "";
		for (String item : list) {
			Finaly = Finaly + item + ", ";
		}
		if (Finaly.endsWith(", ")) {
			Finaly = Finaly.substring(0, Finaly.length() - 2);
		}
		return Finaly;
	}

	private String ListToStringLine(List<String> list) {
		String Finaly = "";
		for (String item : list) {
			Finaly = Finaly + item + "\n";
		}
		if (Finaly.endsWith("\n ")) {
			Finaly = Finaly.substring(0, Finaly.length() - 2);
		}
		return Finaly;
	}

	private String getItens(TextChannel chat, String mng) {
		String toReturn = "";
		for (String ii : YuzukuBot.getAddonCommand().get(mng).getCmdInfoSet()) {
			CommandInfo tr = YuzukuBot.getAddonCommand().get(mng).getCmdInfo(ii);
			toReturn = toReturn + YuzukuBot.guildManager.getPrefix(chat.getGuild()) + tr.command + " [" + tr.comment
					+ "] " + getPerm(tr.level);
		}
		return toReturn;
	}

	private String getPerm(LevelType level) {
		String toReturn = "\n";
		if (level == LevelType.Administrator) {
			toReturn = "Usage(Administratrs)\n";
		} else if (level == LevelType.Developer) {
			toReturn = "Usage(Developers)\n";
		} else if (level == LevelType.ServerOwner) {
			toReturn = "Usage(Server Owner)\n";
		}
		return toReturn;
	}

	private String getAddonState(AddonStatus status) {
		if (status == AddonStatus.JarError) {
			return "**Stopped, reason:** error on load Jar file.";
		} else if (status == AddonStatus.MissingAddon) {
			return "**Stopped, reason:** one or more addons are missing.";
		} else if (status == AddonStatus.MPSError) {
			return "**Stopped, reason:** error on load File MPS.";
		} else if (status == AddonStatus.Starting) {
			return "Starting Addon.";
		} else if (status == AddonStatus.Stoped) {
			return "Stopped.";
		} else if (status == AddonStatus.Working) {
			return "Working.";
		} else if (status == AddonStatus.CommandError) {
			return "Working without commands, check the version of the api used in the addon";
		}
		return "Unknown.";
	}

}
