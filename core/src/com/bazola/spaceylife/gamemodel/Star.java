package com.bazola.spaceylife.gamemodel;

import java.util.Random;

import com.bazola.spaceylife.StarType;
import com.bazola.spaceylife.gamemodel.players.PlayerType;

public class Star {
	
	public final StarType type;
	
	private final MapPoint position;
	
	private PlayerType owner;
	
	private int numEatsToConsume = 1000; //these will be incremented 1 per tick per alien
	private int currentEats = 0;
	
	public Star(Random random, MapPoint position) {
		this.position = position;

		this.type = StarType.values()[random.nextInt(StarType.values().length)];
	
		this.owner = PlayerType.NONE;
	}

	public MapPoint getPosition() {
		return this.position;
	}
	
	public PlayerType getOwner() {
		return this.owner;
	}
	
	/**
	 * Used when setting the homeworld
	 */
	public void setOwner(PlayerType type) {
		this.owner = type;
	}
	
	public void addAlienEat() {
		this.currentEats ++;
		if (this.currentEats > this.numEatsToConsume) {
			this.owner = PlayerType.ALIEN;
		}
	}
}
