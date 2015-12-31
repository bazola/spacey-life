package com.bazola.spaceylife.audio;

import com.badlogic.gdx.audio.Sound;
import com.bazola.spaceylife.LibGDXGame;

public class SoundPlayer {
	
	private final LibGDXGame libGDXGame;

	private boolean enabled = true;
	
	public SoundPlayer(LibGDXGame libGDXGame) {
		this.libGDXGame = libGDXGame;
	}
	
	public void disableSounds() {
		this.enabled = false;
	}
	
	public void enableSounds() {
		this.enabled = true;
	}
	
	public void playSound(SoundType soundType) {
		if (!this.enabled) {
			return;
		}
		Sound sound = this.libGDXGame.sounds.get(soundType);
		sound.play(0.6f); //play sounds at half volume
	}
}
