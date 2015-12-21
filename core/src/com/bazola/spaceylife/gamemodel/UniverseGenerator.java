package com.bazola.spaceylife.gamemodel;

import java.util.HashMap;
import java.util.Random;

public class UniverseGenerator {
	
	private final int ATTEPMTS = 250;
	private final int SPACING = 200;

	private final Random random;
	
	private final HashMap<MapPoint, Star> stars = new HashMap<MapPoint, Star>();

	public UniverseGenerator(int width, int height, Random random) {
		
		this.random = random;
		
		for (int i = 0; i < this.ATTEPMTS; i++) {
			int randomX = this.random.nextInt(width);
			int randomY = this.random.nextInt(height);
			MapPoint point = new MapPoint(randomX, randomY);
			
			//dont put stars too close together
			if (!regionContainsStar(this.SPACING, point)) {
				this.stars.put(point, new Star(this.random, point));
			}
		}
	}
	
	private boolean regionContainsStar(int spacing, MapPoint point) {
		for (MapPoint starPoint : this.stars.keySet()) {
			if (starPoint.x >= point.x - spacing &&
				starPoint.x <= point.x + spacing &&
				starPoint.y >= point.y - spacing &&
				starPoint.y <= point.y + spacing) {
				return true;
			}
		}
		return false;
	}
	
	public HashMap<MapPoint, Star> getStars() {
		return this.stars;
	}
}
