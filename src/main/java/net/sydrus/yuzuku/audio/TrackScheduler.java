package net.sydrus.yuzuku.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import net.dv8tion.jda.core.entities.TextChannel;
import net.sydrus.yuzuku.YuzukuBot;
import net.sydrus.yuzuku.Managers.EmbedManager;
import net.sydrus.yuzuku.audio.AudioException.ExceptionType;
import net.sydrus.yuzuku.exceptions.OutOfRangeException;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrackScheduler extends AudioEventAdapter {

	private AudioPlayer player;
	private List<AudioTrack> queue = new ArrayList<AudioTrack>();
	private List<String> trackId = new ArrayList<String>();
	private TextChannel channel = null;
	private int AtualInQueue = 0;
	private boolean isPlaying = false;
	private int VOLUME = 100;
	private int NEXT_QUEUE = 0;


	public void reset() {
		player.stopTrack();
		queue = null;
		trackId = null;
		channel = null;
		isPlaying = false;
	}

	final void setVolume(int volume) {
		player.setVolume(volume);
		this.VOLUME = volume;
	}

	final int getVolume() {
		return this.VOLUME;
	}

	public boolean isPlaying() {
		return this.isPlaying;
	}

	public List<AudioTrack> getTrucks() {
		return this.queue;
	}


	public void clear() {
		queue.clear();
		trackId.clear();
	}

	public void shuffleTracks() {
		Collections.shuffle(queue);
	}

	public int getAtualQueue() {
		return this.AtualInQueue;
	}

	public TrackScheduler(AudioPlayer player) {
		this.player = player;
	}


	public void setNextQueue(int queue) throws OutOfRangeException {
		if ((queue <= 0) || (queue > this.queue.size())) {
			throw new OutOfRangeException(this.queue.size(), queue);
		}
		NEXT_QUEUE = queue;
	}

	public boolean hasNextTrack() {
		return (queue.size() > AtualInQueue) && (queue.size() > 0);
	}

	public int nextQueue() {
		return AtualInQueue + 1;
	}

	public int getAudioTrackQuanty() {
		return queue.size();
	}

	public void registerTracks(final List<AudioTrack> tracks) {
		new Thread(new Runnable() {
			public void run() {
				for (AudioTrack track : tracks) {
					queue.add(track);
					trackId.add(track.getIdentifier());
				}
			}
		}).start();
	}

	public void registerTrack(AudioTrack track) {
		trackId.add(track.getIdentifier());
		queue.add(track);
	}


	public void setTextChannel(TextChannel tchannel) {
		channel = tchannel;
	}


	public void queue() {


		if (queue.size() >= 1) {
			player.startTrack(queue.get(0), true);
			AtualInQueue = 0;
			this.isPlaying = true;
		}

	}

	public void queue(int number) throws Exception {
		int tq = number;
		if (number - 1 >= 0) {
			tq--;
		}
		if (queue.size() >= 1) {
			if (number <= queue.size()) {
				player.startTrack(queue.get(tq), true);
				this.AtualInQueue = number;
				this.isPlaying = true;
			} else {
				throw new Exception("The specified number is greater than the number of songs in the playlist",
						new Throwable(number + ""));
			}
		}

	}

	public AudioPlayer getPlayer() {
		return player;
	}

	public void nextTrack() throws AudioException {

		// Start the next track, regardless of if something is already playing
		// or not. In case queue was empty, we are

		// giving null to startTrack, which is a valid argument and will simply
		// stop the player.
		AtualInQueue++;
		if (queue.size() >= AtualInQueue) {
			// player.stopTrack();
			try {
				player.stopTrack();
				queue(AtualInQueue);
			} catch (Exception e) {
				throw new AudioException("Playlist Finished", ExceptionType.IsWarning);
			}
		} else {
			throw new AudioException("Playlist Finished", ExceptionType.IsWarning);
		}
	}

	@Override
	public void onTrackStart(AudioPlayer player, AudioTrack track) {
		if (YuzukuBot.guildManager.getVolume(channel.getGuild()) != VOLUME) {
			VOLUME = YuzukuBot.guildManager.getVolume(channel.getGuild());
			player.setVolume(VOLUME);
		}
	}

	public AudioTrack getNextTrack() {
		if (hasNextTrack()) {
			if ((nextQueue() - 1 <= queue.size())) {
				return queue.get(nextQueue() - 1);
			}
		}
		return null;
	}
	
	@Override
	public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {

		// Only start the next track if the end reason is suitable for it
		// (FINISHED or LOAD_FAILED)
		isPlaying = false;
		if (endReason.mayStartNext) {
			try {
				if (NEXT_QUEUE == 0) {
					nextTrack();
				} else {
					queue(NEXT_QUEUE);
					NEXT_QUEUE = 0;
				}
				AudioTrackInfo info = track.getInfo();
				EmbedManager manager = new EmbedManager();
				manager.setColor(Color.GREEN);
				manager.addField("Playing Now", info.title, true);
				manager.addField("Durations", track.getDuration() + "", true);
				if (hasNextTrack()) {
					manager.addField("Next", getNextTrack().getInfo().title, false);
				}
				manager.addField("Position in queue", getAtualQueue() + "/" + getAudioTrackQuanty(), true);
				if (hasNextTrack()) {
					manager.addField("Next position in queue", nextQueue() + "/" + getAudioTrackQuanty(), true);
				}
				channel.sendMessage(manager.getMessage()).queue();
			} catch (AudioException e) {
				if (e.getExceptionType() == ExceptionType.IsWarning) {
					EmbedManager emb = new EmbedManager();
					emb.setDescription("Playlist Finished");
					emb.setColor(Color.YELLOW);
					channel.sendMessage(emb.getMessage()).queue();
				} else if (e.getExceptionType() == ExceptionType.isFatal) {
					EmbedManager emb = new EmbedManager();
					emb.setDescription(e.getMessage());
					emb.setColor(Color.RED);
					channel.sendMessage(emb.getMessage()).queue();
				} else if (e.getExceptionType() == ExceptionType.isError) {
					EmbedManager emb = new EmbedManager();
					emb.setDescription("[Error] " + e.getMessage());
					emb.setColor(Color.RED);
					channel.sendMessage(emb.getMessage()).queue();
				}
			} catch (Exception e) {
				EmbedManager emb = new EmbedManager();
				emb.setDescription("[Error] " + e.getMessage());
				emb.setColor(Color.RED);
				channel.sendMessage(emb.getMessage()).queue();
			}
		} else {
			EmbedManager manager = new EmbedManager();
			manager.setColor(Color.YELLOW);
			manager.setDescription("Playlist ended");
			channel.sendMessage(manager.getMessage()).queue();
			YuzukuBot.MusicManager.removeChannel(channel);
			channel = null;
		}
	}

	public boolean containsAudioTrack(AudioTrack track) {
		return trackId.contains(track.getIdentifier());
	}

	public AudioTrack getSettedNextQueue() {
		int tq = NEXT_QUEUE;
		if (NEXT_QUEUE - 1 >= 0) {
			tq--;
		}
		return queue.get(tq);
	}

	public int getSettedNextQueueInt() {
		return NEXT_QUEUE;
	}
}