package net.sydrus.yuzuku.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;

import net.dv8tion.jda.core.audio.AudioSendHandler;

public class AudioPlayerSendHandlerw implements AudioSendHandler {
	private final AudioPlayer audioPlayer;
	private AudioFrame lastFrame;

	public AudioPlayerSendHandlerw(AudioPlayer audioPlayer) {
		this.audioPlayer = audioPlayer;
	}

	@Override
	public boolean canProvide() {
		if (lastFrame == null) {
			lastFrame = audioPlayer.provide();
		}
		return lastFrame != null;
	}

	@Override
	public byte[] provide20MsAudio() {
		if (lastFrame == null) {
			lastFrame = audioPlayer.provide();
		}
		byte[] data = lastFrame != null ? lastFrame.data : null;
		lastFrame = null;
		return data;
	}

	@Override
	public boolean isOpus() {
		return true;
	}
}