package net.sydrus.yuzuku.audio;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class AudioData {

	private String AUDIOSOURCE = "";
	private String AUDIOIDENTIFIER = "";

	public void ofString(String audio) {
		if ((audio.startsWith("AudioData(Source{")) && (audio.endsWith("})"))) {
			AUDIOSOURCE = audio.substring(17, audio.lastIndexOf("},Identifier{"));
			AUDIOIDENTIFIER = audio.substring(audio.lastIndexOf("},Identifier{") + 13, audio.lastIndexOf("})"));
			System.out.println(AUDIOSOURCE);
			System.out.println(AUDIOIDENTIFIER);
		} else {
			throw new RuntimeException("Invalid Audio");
		}
	}

	public void ofAudioTrack(AudioTrack track) {
		AUDIOSOURCE = track.getSourceManager().getSourceName();
		AUDIOIDENTIFIER = track.getIdentifier();
	}

	public String getAudioSource() {
		return this.AUDIOSOURCE;
	}

	public String getAudioIdentifier() {
		return this.AUDIOIDENTIFIER;
	}

	public String toString() {

		return "AudioData(Source{" + AUDIOSOURCE + "},Identifier{" + AUDIOIDENTIFIER + "})";
	}
}
