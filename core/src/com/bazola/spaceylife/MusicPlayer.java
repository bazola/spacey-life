package com.bazola.spaceylife;

import java.util.Random;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;

public class MusicPlayer {
	
	private final LibGDXGame libGDXGame;
	private final Random random;
	
	private boolean enabled = true;
	
	private Music currentTrack;
	private MusicType nextTrack;
	
	public MusicPlayer(LibGDXGame libGDXGame, Random random) {
		this.libGDXGame = libGDXGame;
		this.random = random;
	}
	
	public void stopMusic() {
		if (this.currentTrack != null) {
			this.currentTrack.stop();
		}
	}
	
	public void startMusic() {
		if (this.currentTrack == null ||
			!this.currentTrack.isPlaying()) {
				this.selectRandomTrack(false);
		}
	}
	
	public void disableMusic() {
		this.enabled = false;
		if (this.currentTrack != null) {
			this.currentTrack.stop();
		}
	}
	
	public void enableMusic() {
		this.enabled = true;
	}

	public void playTitle() {
		if (!this.enabled) {
			return;
		}
		
		this.currentTrack = this.libGDXGame.music.get(MusicType.getTitleTrack());
		this.currentTrack.play();
		this.currentTrack.setLooping(true);
	}
	
	private void selectRandomTrack(boolean playSilence) {
		if (!this.enabled) {
			return;
		}

		this.nextTrack = MusicType.getSongs().get(this.random.nextInt(MusicType.getSongs().size()));
		
		if (playSilence) {
			this.currentTrack = this.libGDXGame.music.get(MusicType.getSilence());
			this.currentTrack.play();
			this.currentTrack.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(Music music) {
					MusicPlayer.this.playNextTrack();
				}
	    	});
		} else {
			this.playNextTrack();
		}

	}
	
	private void playNextTrack() {
		if (!this.enabled) {
			return;
		}
		this.currentTrack = this.libGDXGame.music.get(this.nextTrack);
		this.currentTrack.play();
		this.currentTrack.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(Music music) {
				MusicPlayer.this.selectRandomTrack(true);
			}
    	});
	}
	
	private void playRandomTrack() {
		if (!this.enabled) {
			return;
		}
		MusicType type = MusicType.values()[this.random.nextInt(MusicType.values().length)];
		this.playTrack(type);
	}
	
	private void playTrack(MusicType track) {
		if (!this.enabled) {
			return;
		}
		this.currentTrack = this.libGDXGame.music.get(track);
		this.currentTrack.play();
		this.currentTrack.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(Music music) {
				MusicPlayer.this.playRandomTrack();
			}
    	});
	}

}
