package com.bazola.spaceylife.gamemodel;

import com.bazola.spaceylife.GameScreen;

public class MainGame {
	
	private final GameScreen gameScreen;
	
	public final UniverseGenerator universe;
	
	public MainGame(int width, int height, GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		
		this.universe = new UniverseGenerator(width, height, this.gameScreen.random);
	}
	
	public void update() {
		
	}
	
	/*
	private Star findClosestStar(Collection<Star> stars, Star originStar) {
		double smallestDistance = 1000000; //make sure that any distance will be less
		Star closestStar = null;
		for (Star star : stars) {
			if (!star.equals(originStar)) {
				double distance = this.calculateDistance(star.getPosition(), originStar.getPosition());
				if (distance < smallestDistance) {
					smallestDistance = distance;
					closestStar = star;
				}
			}
		}
		return closestStar;
	}
	
	private double calculateDistance(MapPoint destination, MapPoint origin) {
		return Math.hypot(destination.x - origin.x, destination.y - origin.y);
	}
	*/
}
