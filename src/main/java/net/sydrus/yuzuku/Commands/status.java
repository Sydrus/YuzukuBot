package net.sydrus.yuzuku.Commands;

import java.awt.*;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.sun.management.OperatingSystemMXBean;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Constructors.Command;
import net.sydrus.yuzuku.Managers.LevelType;

public class status extends Command {

	private int getThreads() {
		return Thread.activeCount();
	}

	private long getTotalMemory() {
		return ((Runtime.getRuntime().totalMemory() / (1024 * 1024)));
	}

	private long getMemory() {
		return (((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024)));
	}

	long sec = System.currentTimeMillis();

	public String getUptime() {
		Long time = System.currentTimeMillis() - sec;
		long days = TimeUnit.MILLISECONDS.toDays(time);
		time -= TimeUnit.DAYS.toMillis(days);
		long hours = TimeUnit.MILLISECONDS.toHours(time);
		time -= TimeUnit.HOURS.toMillis(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
		time -= TimeUnit.MINUTES.toMillis(minutes);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(time);
		return days + " Days " + hours + " Hours " + minutes + " Minutes " + seconds + " Seconds";
	}

	private String getCPULoad() {
		OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		DecimalFormat df = new DecimalFormat("#.##");
		return (df.format(osBean.getProcessCpuLoad())) + "%";
	}

	@Override
	public boolean onCommand(User Sender, Message Message, Guild Guild, TextChannel Chat, List<LevelType> type,
			Boolean isEdited, String[] args) {
		List<String> statusItems = new ArrayList<String>();
		statusItems.add("#Bot Status:\n\n");
		statusItems.add("**Version:** " + YuzukuBot.getInstance().Version + "\n");
		statusItems.add("**Discord:**");
		statusItems.add("	**Status:** " + YuzukuBot.getInstance().Status);
		statusItems.add("	**Guilds:** " + YuzukuBot.getInstance().getJDA().getGuilds().size() + "\n");
		statusItems.add("	**Commands:** " + YuzukuBot.getInstance().getCommandManager().getCommands().count());
		statusItems.add("	**Sub Commands:** " + YuzukuBot.getInstance().subCommands + "\n");
		statusItems.add("**BOT Prefix:** " + YuzukuBot.guildManager.getPrefix(Guild) + "\n");
		statusItems.add("**Time Online:** " + getUptime() + "\n");
		statusItems.add("**Errors:** " + YuzukuBot.getInstance().botErrors + "\n");
		statusItems.add("**Bot Server Status:** \n");
		statusItems.add("**CPU:** " + getCPULoad() + "\n" + "	**Memory:** " + getMemory() + "/" + Runtime.getRuntime().totalMemory() / (1024 * 1024) + "MB" + " \n" + "**Threads:** " + getThreads());
		String endItems = "";
		for (String itemslist : statusItems) {
			endItems = endItems + itemslist + "\n";
		}

		Chat.sendMessage(embedMessage(endItems)).queue();
		return true;
	}

}
