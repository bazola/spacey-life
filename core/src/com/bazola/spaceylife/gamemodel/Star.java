package com.bazola.spaceylife.gamemodel;

import java.util.Random;

import com.bazola.spaceylife.StarType;

public class Star {
	
	public final StarType type;
	
	private final MapPoint position;
	
	private StarState state;
	
	public Star(Random random, MapPoint position) {
		this.position = position;

		this.type = StarType.values()[random.nextInt(StarType.values().length)];
	
		this.state = StarState.NEUTRAL;
	}

	public MapPoint getPosition() {
		return this.position;
	}
	
	public StarState getState() {
		return this.state;
	}
	
	public void setPlayerControlled() {
		this.state = StarState.PLAYER_CONTROLLED;
	}
	
	public void setAIControlled() {
		this.state = StarState.AI_CONTROLLED;
	}
}
