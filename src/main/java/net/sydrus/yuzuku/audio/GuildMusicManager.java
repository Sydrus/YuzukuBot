package net.sydrus.yuzuku.audio;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.core.entities.TextChannel;
import net.sydrus.yuzuku.exceptions.OutOfRangeException;

public class GuildMusicManager {

	public TrackScheduler scheduler;
	private AudioPlayerManager man = null;

	public GuildMusicManager(AudioPlayerManager manager) {
		this.man = manager;
		scheduler = new TrackScheduler(manager.createPlayer());
		getPlayer().addListener(scheduler);
	}

	final int containsTracks(List<AudioTrack> tracks) {
		int ToReturn = 0;
		for (AudioTrack track : tracks) {
			if (containsAudioTrack(track)) {
				ToReturn++;
			}
		}
		return ToReturn;
	}

	final void setVolume(int volume) {
		this.scheduler.setVolume(volume);
	}

	final int getVolume() {
		return this.scheduler.getVolume();
	}

	public boolean isPlaying() {
		return this.scheduler.isPlaying();
	}

	public void clear() {
		this.scheduler.clear();
	}

	public void shuffleTracks() {
		this.scheduler.shuffleTracks();
	}

	public int getAtualQueue() {
		return this.scheduler.getAtualQueue();
	}

	public boolean hasNextTrack() {
		return this.scheduler.hasNextTrack();
	}

	public int nextQueue() {
		return this.scheduler.nextQueue();
	}

	public boolean containsAudioTrack(AudioTrack track) {
		return this.scheduler.containsAudioTrack(track);
	}

	public void registerTracks(List<AudioTrack> tracks) {
		this.scheduler.registerTracks(tracks);
	}

	public void registerTrack(AudioTrack track) {
		this.scheduler.registerTrack(track);
	}

	public void queue() {
		this.scheduler.queue();
	}

	public void queue(int number) throws Exception {
		this.scheduler.queue(number);
	}

	public AudioPlayer getPlayer() {
		return scheduler.getPlayer();
	}

	public void nextTrack() throws AudioException {
		this.scheduler.nextTrack();
	}

	public AudioPlayerSendHandlerw getSendHandler() {
		return new AudioPlayerSendHandlerw(scheduler.getPlayer());

	}

	public AudioTrack getPlayingTrack() {
		return scheduler.getPlayer().getPlayingTrack();
	}

	public void setTextChannel(TextChannel channel) {
		this.scheduler.setTextChannel(channel);
	}

	public void reset() {
		this.scheduler.reset();
		getPlayer().removeListener(scheduler);
		scheduler = new TrackScheduler(man.createPlayer());
		getPlayer().addListener(scheduler);
	}

	public AudioTrack getNextTrack() {
		return scheduler.getNextTrack();
	}

	public int musicSize() {
		return this.scheduler.getTrucks().size();
	}

	public boolean hasMusic() {
		return !scheduler.getTrucks().isEmpty();
	}

	public void setNextQueue(int queue) throws OutOfRangeException {
		this.scheduler.setNextQueue(queue);
	}

	public AudioTrack getSettedNextQueue() {
		return this.scheduler.getSettedNextQueue();
	}

	public int getSettedNextQueueInt() {
		return this.scheduler.getSettedNextQueueInt();
	}

	public final void stopTrack() {
	//	this.scheduler.resetSetted();
	}

}