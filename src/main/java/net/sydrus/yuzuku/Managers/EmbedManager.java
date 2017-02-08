package net.sydrus.yuzuku.Managers;

import java.awt.Color;
import java.time.temporal.TemporalAccessor;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.MessageEmbed.Field;

public class EmbedManager {

	private EmbedBuilder builder = null;

	public EmbedManager() {
		builder = new EmbedBuilder();
	}

	public EmbedManager(MessageEmbed embed) {
		builder = new EmbedBuilder(embed);
	}

	public EmbedBuilder addBlankField(boolean inline) {
		return builder.addBlankField(inline);
	}
	
	public EmbedBuilder addField(Field field) {
		return builder.addField(field);
	}

	public EmbedBuilder addField(String name, String value, boolean inline) {
		return builder.addField(name, value, inline);
	}

	public EmbedBuilder setAuthor(String name, String url, String iconUrl) {
		return builder.setAuthor(name, url, iconUrl);
	}

	public EmbedBuilder setColor(Color color) {
		return builder.setColor(color);
	}

	public EmbedBuilder setDescription(String description) {
		return builder.setDescription(description);
	}

	public EmbedBuilder setFooter(String text, String iconUrl) {
		return builder.setFooter(text, iconUrl);
	}

	public EmbedBuilder setImage(String url) {
		return builder.setImage(url);
	}

	public EmbedBuilder setProvider(String name, String url) {
		return builder.setProvider(name, url);
	}

	public EmbedBuilder setThumbail(String url) {
		return builder.setThumbnail(url);
	}

	public EmbedBuilder setTimeStamp(TemporalAccessor temporal) {
		return builder.setTimestamp(temporal);
	}

	public EmbedBuilder setTitle(String title) {
		return builder.setTitle(title);
	}

	public EmbedBuilder setUrl(String url) {
		return builder.setUrl(url);
	}

	public EmbedBuilder setVideo(String url) {
		return builder.setVideo(url);
	}

	public void baiscEmbed(Color color, String title, String description, String footer, String footerIcon) {
		setColor(color);
		setTitle(title);
		setDescription(description);
		setFooter(footer, footerIcon);
	}

	public MessageEmbed getMessageEmbed() {
		return builder.build();
	}

	public MessageBuilder getMessageBuilder() {
		MessageBuilder mbuilder = new MessageBuilder();
		mbuilder.setEmbed(getMessageEmbed());
		return mbuilder;
	}

	public Message getMessage() {
		return getMessageBuilder().build();
	}

}
