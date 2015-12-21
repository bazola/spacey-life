package com.bazola.spaceylife.gamemodel;

import java.util.Random;

import com.bazola.spaceylife.StarType;

public class Star {
	
	public final StarType type;
	
	private final MapPoint position;
	
	//private final Random random;
	
	public Star(Random random, MapPoint position) {
		this.position = position;
		
		//this.random = random;
		this.type = StarType.values()[random.nextInt(StarType.values().length)];
	}

	public MapPoint getPosition() {
		return this.position;
	}
}
