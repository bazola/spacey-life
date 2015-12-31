package com.bazola.spaceylife.gamemodel;

import com.bazola.spaceylife.gamemodel.gamepieces.Alien;

public class AlienDistanceTuple {
	
	public final Alien alien;
	public final double distance;

	public AlienDistanceTuple(Alien alien, double distance) {
		this.alien = alien;
		this.distance = distance;
	}
}
