package com.bazola.spaceylife.gamemodel;

public class Alien {

	private MapPoint position;
	
	public Alien(MapPoint position) {
		this.position = position;
	}
	
	public MapPoint getPosition() {
		return this.position;
	}
}
