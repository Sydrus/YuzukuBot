package net.sydrus.yuzuku.Commands;

import java.awt.Color;
import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Constructors.Command;
import net.sydrus.yuzuku.Managers.HelpManager;
import net.sydrus.yuzuku.Managers.LevelType;
import net.sydrus.yuzuku.Managers.HelpManager.HelpType;
import net.sydrus.yuzuku.String.Administratives;

public class ClearChat extends Command {

	@Override
	public boolean onCommand(User Sender, Message Message, Guild Guild, TextChannel Chat, List<LevelType> type,
			Boolean isEdited, String[] args) {
		if (isAdministrator(type)) {
			if (args.length == 1) {
				new Thread(new Runnable() {
					public void run() {
						List<String> Messages = new ArrayList<String>();
						int qnt = 0;
						int count = 0;
						if (args[0].equalsIgnoreCase("help")) {
							argsCommand(Sender, Message, Guild, Chat, type, isEdited, args);
							return;
						}
						try {
							qnt = Integer.valueOf(args[0]).intValue();
						} catch (Exception e) {
							Chat.sendMessage(embedMessage(sender(Sender) + "Enter a number between 1 and 100")).queue();
							return;
						}
						if ((qnt == 0) || (qnt > 100) || (qnt < 1)) {
							Chat.sendMessage(embedMessage(sender(Sender) + "Enter a number between 1 and 100")).queue();
							return;
						}
						try {
							for (Message msgs : Chat.getHistory().retrievePast(qnt).block()) {
								Messages.add(msgs.getId());
								count++;
							}
						} catch (RateLimitedException e1) {
						}
						for (String str : Messages) {
							try {
								Chat.getMessageById(str).block().deleteMessage().block();
							} catch (Exception e) {
							}
						}
						Chat.sendMessage(embedMessage("**Was removed** " + count + " **Messages**")).queue();
					}
				}).start();
			} else if (args.length >= 2) {
				argsCommand(Sender, Message, Guild, Chat, type, isEdited, args);
			} else if (args.length == 0) {
				Chat.sendMessage(embedMessage("To know the available commands enter \""
						+ YuzukuBot.guildManager.getPrefix(Guild) + "rm help\"")).queue();
			} else {
				Chat.sendMessage(embedMessage(sender(Sender) + "Enter a number between 1 and 100")).queue();
			}
		} else {
			Chat.sendMessage(embedMessage(sender(Sender) + Administratives.AdminOnly)).queue();
		}
		return true;
	}

	private boolean argsCommand(User Sender, Message Message, Guild Guild, TextChannel Chat, List<LevelType> type,
			Boolean isEdited, String[] args) {
		if ((args.length == 1) && args[0].equalsIgnoreCase("help")) {
			HelpManager hlp = new HelpManager();
			hlp.setPrefix("{s}", YuzukuBot.guildManager.getPrefix(Guild));
			hlp.addItem(" {s}rm <number>", "**Clear the chat according to the defined number** \n");
			hlp.addItem("{s}rm <number> igp <Usuario>", "**Clears chat but ignores entered user's messages** \n");
			hlp.addItem("{s}rm <number> igm <numero>", "**Clears the chat but ignores a number of defined messages** \n");
			hlp.addItem(" {s}rm <number> rmu <usuario>", "**Removes the message from a specific user** \n");
			hlp.addItem("{s}rm <number> jump",
					"Clears the chat but skips messages as defined in the last option. Ex: {s}rm 5 jump 1: Result: delete, jump, delete, jump, delete");
			Chat.sendMessage(hlp.getEmbedManager(HelpType.UseComment).getMessage()).queue();
		} else if ((args.length == 3) && args[1].equalsIgnoreCase("igp")) {
			new Thread(new Runnable() {
				public void run() {
			List<String> Messages = new ArrayList<String>();
			int qnt = 0;
			int count = 0;
			User ignore = null;
			try {
				qnt = Integer.valueOf(args[0]).intValue();
			} catch (Exception e) {
				Chat.sendMessage(embedMessage(sender(Sender) + "Enter a number between 1 and 100", Color.RED)).queue();
				return;
			}
			if ((qnt == 0) || (qnt > 100) || (qnt < 1)) {
				Chat.sendMessage(embedMessage(sender(Sender) + "Enter a number between 1 and 100", Color.RED)).queue();
				return;
			}

			try {
				ignore = Message.getMentionedUsers().get(0);
			} catch (Exception e) {
				Chat.sendMessage(embedMessage(sender(Sender) + "**Select a valid user**", Color.RED)).queue();
				return;
			}

			try {
				for (Message msgs : Chat.getHistory().retrievePast(100).block()) {
					if (msgs.getAuthor() != ignore) {
						Messages.add(msgs.getId());
						count++;
					}
					if (count == qnt) {
						break;
					}
				}
			} catch (RateLimitedException e1) {
			}
			for (String str : Messages) {
				try {
					Chat.getMessageById(str).block().deleteMessage().block();
				} catch (Exception e) {
				}
			}
			Chat.sendMessage(embedMessage("**Was removed**" + count + " **Messages**", Color.RED)).queue();

				}
			}).start();
		} else if ((args.length == 3) && args[1].equalsIgnoreCase("igm")) {
			new Thread(new Runnable() {
				public void run() {
			List<String> Messages = new ArrayList<String>();
			int qnt = 0;
			int count = 0;
			int ignore = 0;
			int ignored = 0;
			try {
				qnt = Integer.valueOf(args[0]).intValue();
			} catch (Exception e) {
				Chat.sendMessage(embedMessage(sender(Sender) + "Enter a number between 1 and 100", Color.RED)).queue();
				return;
			}
			if ((qnt == 0) || (qnt > 100) || (qnt < 1)) {
				Chat.sendMessage(embedMessage(sender(Sender) + "Enter a number between 1 and 100", Color.RED)).queue();
				return;
			}
			try {
				ignore = Integer.valueOf(args[2]).intValue();
			} catch (Exception e) {
				Chat.sendMessage(sender(Sender) + "```css\n\nPut on the argument 3 a number between 1 and 50\n\n```")
						.queue();
				return;
			}
			if ((ignore == 0) || (ignore > 50) || (ignore < 1)) {
				Chat.sendMessage(sender(Sender) + "```css\n\nPut on the argument 3 a number between 1 and 50\n\n```")
						.queue();
				return;
			}
			try {
				for (Message msgs : Chat.getHistory().retrievePast(100).block()) {
					if (ignored == ignore) {
						Messages.add(msgs.getId());
						count++;
					} else {
						ignored++;
					}
				}
			} catch (RateLimitedException e1) {
			}
			for (String str : Messages) {
				try {
					Chat.getMessageById(str).block().deleteMessage().block();
				} catch (Exception e) {
				}
			}
			Chat.sendMessage(embedMessage("**Messages Removeds** "+ count +", **skipped:**" + ignored))
					.queue();

				}
			}).start();
		} else if ((args.length == 2) && args[1].equalsIgnoreCase("jump")) {
			new Thread(new Runnable() {
				public void run() {
					List<String> Messages = new ArrayList<String>();
					int qnt = 0;
					int count = 0;
					boolean ignore = false;
					int ignoreds = 0;
					try {
						qnt = Integer.valueOf(args[0]).intValue();
					} catch (Exception e) {
						Chat.sendMessage(embedMessage(sender(Sender) + "Enter a number between 1 and 100", Color.RED))
								.queue();
						return;
					}
					if ((qnt == 0) || (qnt > 100) || (qnt < 1)) {
						Chat.sendMessage(embedMessage(sender(Sender) + "Enter a number between 1 and 100", Color.RED))
								.queue();
						return;
					}

					try {
						for (Message msgs : Chat.getHistory().retrievePast(100).block()) {
							if (ignore) {
								ignoreds++;
								ignore = false;
							} else {
								Messages.add(msgs.getId());
								count++;
								ignore = true;
							}
						}
					} catch (RateLimitedException e1) {
					}
					for (String str : Messages) {
						try {
							Chat.getMessageById(str).block().deleteMessage().block();
						} catch (Exception e) {
						}
					}
					Chat.sendMessage(embedMessage(sender(Sender) + count + "**Messages were removed and " + ignoreds
							+ " messages were ignored**")).queue();
				}
			}).start();
		} else if ((args.length == 3) && args[1].equalsIgnoreCase("rmu")) {
			new Thread(new Runnable() {
				public void run() {
					List<String> Messages = new ArrayList<String>();
					int qnt = 0;
					int count = 0;
					User ignore = null;
					try {
						qnt = Integer.valueOf(args[0]).intValue();
					} catch (Exception e) {
						Chat.sendMessage(embedMessage(sender(Sender) + "Enter a number between 1 and 100", Color.RED))
								.queue();
						return;
					}
					if ((qnt == 0) || (qnt > 100) || (qnt < 1)) {
						Chat.sendMessage(embedMessage(sender(Sender) + "Enter a number between 1 and 100", Color.RED))
								.queue();
						return;
					}

					try {
						ignore = Message.getMentionedUsers().get(0);
					} catch (Exception e) {
						Chat.sendMessage(embedMessage(sender(Sender) + "**Put a valid user**", Color.RED)).queue();
						return;
					}

					try {
						for (Message msgs : Chat.getHistory().retrievePast(100).block()) {
							if (msgs.getAuthor() == ignore) {
								Messages.add(msgs.getId());
								count++;
							}
							if (count == qnt) {
								break;
							}
						}
					} catch (RateLimitedException e1) {
					}
					for (String str : Messages) {
						try {
							Chat.getMessageById(str).block().deleteMessage().block();
						} catch (Exception e) {
						}
					}
					Chat.sendMessage(embedMessage("**Removed** " + count + " **Message**")).queue();
				}
			}).start();
		} else {
			Chat.sendMessage(embedMessage(sender(Sender) + "**Error, invalid arguments**", Color.RED)).queue();
		}
		return false;
	}

}
