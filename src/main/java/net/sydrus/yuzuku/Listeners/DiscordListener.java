package net.sydrus.yuzuku.Listeners;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.impl.MessageImpl;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.FileReader.BCRItem;
import net.sydrus.yuzuku.FileReader.BCRItem.Type;
import net.sydrus.yuzuku.Managers.Commands;
import net.sydrus.yuzuku.Managers.LevelType;
import net.sydrus.yuzuku.String.Administratives;

public class DiscordListener extends ListenerAdapter {
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		String cmdStr = "";
		if (e.getChannelType() == ChannelType.PRIVATE) {
		} else {
			if (e.getAuthor().isBot()) {
				return;
			}
			try {
				if (isPrefix(e.getGuild(), e.getMessage(), YuzukuBot.getInstance().getJDA())) {
					cmdStr = getCommand(e.getGuild(), e.getMessage(), YuzukuBot.getInstance().getJDA());
					Commands cmd = YuzukuBot.getInstance()
							.getCommandManager(/* this.getClass() */).getCommands();
					if (cmd.contains(cmdStr.split(" ")[0].toLowerCase())) {
						if (cmdStr.startsWith(cmdStr)) {
							if (hasLevel(e).contains(cmd.getCommandInfo(cmdStr.split(" ")[0]).level)) {
								cmd.get(cmdStr.split(" ")[0].toLowerCase()).onCommand(e.getAuthor(),
										setMessage(e.getMessage()), e.getGuild(), e.getTextChannel(), hasLevel(e),
										e.getMessage().isEdited(), argsToString(cmdStr, 1).split(" "));
							} else {
								e.getChannel().sendMessage(Administratives.badPermission).queue();
							}
						}
					}
				} else {
					try {
						if ((YuzukuBot.getInstance().isTextBanned(e.getMessage().getContent()))
								&& (!e.getAuthor().isBot())) {
							e.getMessage().deleteMessage().queue();
							e.getChannel()
									.sendMessage(sender(e.getAuthor())
											+ "```css\n\nOps, you used a banned link/word\n\n```")
									.queue();
						}
					} catch (IOException e1) {
						System.err.println(e1.getMessage());
					}
				}
			} catch (Exception e2) {
				YuzukuBot.getInstance().botErrors++;
				e2.printStackTrace();
			}
		}
	}

	@Override
	public void onGuildMessageUpdate(GuildMessageUpdateEvent e) {
		try {
			if ((YuzukuBot.getInstance().isTextBanned(e.getMessage().getContent())) && (!e.getAuthor().isBot())) {
				e.getMessage().deleteMessage();
				e.getChannel().sendMessage(sender(e.getAuthor())
						+ "```css\n\nOps, you used a banned link/word\n\n```");
			}
		} catch (Exception e1) {
			System.err.println(e1.getMessage());
		}
	}

	private String getCommand(Guild guild, Message message, JDA jda) {
		if (YuzukuBot.guildManager.getPrefix(guild).equals(jda.getSelfUser().getAsMention() + " ")) {
			if ((message.getContent().length() == jda.getSelfUser().getName().length())
					|| (message.getContent().length() == jda.getSelfUser().getName().length() + 1)) {
				return message.getContent().substring(jda.getSelfUser().getName().length() + 1) + "help";
			} else {
				return message.getContent().substring(jda.getSelfUser().getName().length() + 2);
			}
		} else {
			try {
				if ((message.getMentionedUsers().get(0).getAsMention().equals(jda.getSelfUser().getAsMention()))
						&& (message.getContent().split(" ")[0]
								.equals("@" + jda.getSelfUser().getName().split(" ")[0]))) {
					if (message.getContent()
							.substring(jda.getSelfUser().getName().length(), message.getContent().length())
							.split(" ").length > 0) {
						String itemFinal = message.getContent().substring(jda.getSelfUser().getName().length() + 1);
						if ((itemFinal.isEmpty()) || (itemFinal.equals(" "))) {
							return "help";
						} else {
							if (itemFinal.startsWith(" ")) {
								return itemFinal.substring(1);
							} else {
								return itemFinal;

							}
						}
					} else {
						return "help";
					}
				} else {
					return message.getContent().substring(1, message.getContent().length());
				}
			} catch (Exception e) {
				return message.getContent().substring(1, message.getContent().length());
			}
		}
	}

	public String sender(User user) {
		return "**[<@" + user.getId() + ">]** ";
	}

	private List<LevelType> hasLevel(MessageReceivedEvent e) {
		List<LevelType> type = new ArrayList<LevelType>();
		type.add(LevelType.User);
		if (e.getGuild().getOwner().getUser().getId() == e.getAuthor().getId()) {
			type.add(LevelType.ServerOwner);
		}
		if (YuzukuBot.getInstance().settingsData.getList(BCRItem.getItem(Type.Administrators))
				.contains(e.getAuthor().getId())) {
			type.add(LevelType.Administrator);
		}
		if (YuzukuBot.getInstance().isDeveloper(e.getAuthor().getId())) {
			type.add(LevelType.Developer);
			if (!type.contains(LevelType.Administrator)) {
				type.add(LevelType.Administrator);
			}
		}
		return type;
	}

	private String argsToString(String args, Integer index) {
		String myString = "";
		String[] argh = args.split(" ");
		for (int i = index; i < argh.length; i++) {
			String arg = argh[i] + " ";
			myString = myString + arg;
		}
		myString = myString.substring(0, myString.length());
		return myString;
	}

	private Message setMessage(Message message) {
		List<User> user = new ArrayList<User>();
		int quanty = 0;
		for (User usr : message.getMentionedUsers()) {
			if ((usr.getAsMention().equals(YuzukuBot.getInstance().getJDA().getSelfUser().getAsMention()))
					&& (quanty >= 0)) {
				user.add(usr);
			} else if (!usr.getAsMention().equals(YuzukuBot.getInstance().getJDA().getSelfUser().getAsMention())) {
				user.add(usr);
			}
		}
		return new MessageImpl(message.getId(), message.getTextChannel(), message.isWebhookMessage())
				.setContent(getCommand(message.getGuild(), message, YuzukuBot.getInstance().getJDA()))
				.setTTS(message.isTTS()).setAuthor(message.getAuthor())
				.setMentionedChannels(message.getMentionedChannels()).setMentionedRoles(message.getMentionedRoles())
				.setMentionedUsers(user).setMentionsEveryone(message.mentionsEveryone())
				.setEditedTime(message.getEditedTime()).setPinned(message.isPinned())
				.setReactions(message.getReactions()).setTime(message.getCreationTime());
	}

	private boolean isPrefix(Guild guild, Message message, JDA jda) {
		try {
			if (YuzukuBot.guildManager.getPrefix(guild).equals(jda.getSelfUser().getAsMention() + " ")) {
				try {
					if (message.getMentionedUsers().get(0).getId().equals(jda.getSelfUser().getId())) {
						return true;
					}
				} catch (Exception e) {
					return false;
				}
			} else {
				try {
					if (YuzukuBot.guildManager.getPrefix(guild).equals(message.getContent().substring(0, 1))) {
						return true;
					} else {
						try {
							if (message.getMentionedUsers().get(0).getAsMention()
									.equals(jda.getSelfUser().getAsMention())) {
								return true;
							}
						} catch (Exception e) {
						}
					}
				} catch (Exception e) {
					try {
						if (message.getMentionedUsers().get(0).getAsMention()
								.equals(jda.getSelfUser().getAsMention())) {
							return true;
						}
					} catch (Exception e1) {
					}
				}

			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
}
