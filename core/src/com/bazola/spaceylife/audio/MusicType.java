package com.bazola.spaceylife.audio;

import java.util.ArrayList;
import java.util.List;

public enum MusicType {
	
	CHASE_PULSE_FASTER("music/chasePulseFaster.mp3"),
	DRAGON_AND_TOAST("music/dragonAndToast.mp3"),
	OSSUARY_AIR("music/ossuaryAir.mp3"),
	OSSUARY_ANIMATE("music/ossuaryAnimate.mp3"),
	OSSUARY_RESOLVE("music/ossuaryResolve.mp3"),
	RISING_GAME("music/risingGameTitle.mp3"),
	SHORES_OF_AVALON("music/shoresOfAvalon.mp3"),
	SILENCE("music/silence.mp3");

	public final String path;
	
	private MusicType(String path) {
		this.path = path;
	}
	
	public static MusicType getTitleTrack() {
		return RISING_GAME;
	}
	
	public static  MusicType getSilence() {
		return SILENCE;
	}
	
	public static List<MusicType> getSongs() {
		List<MusicType> songs = new ArrayList<MusicType>();
		songs.add(CHASE_PULSE_FASTER);
		songs.add(DRAGON_AND_TOAST);
		songs.add(OSSUARY_AIR);
		songs.add(OSSUARY_ANIMATE);
		songs.add(OSSUARY_RESOLVE);
		songs.add(SHORES_OF_AVALON);
		return songs;
	}
}
