package com.bazola.spaceylife.gamemodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class UniverseGenerator {
	
	private final MainGame game;
	
	private final int worldWidth;
	private final int worldHeight;
	private final Random random;
	
	private final int FEATURE_ATTEPMTS = 1000;
	private final int STAR_ATTEMPTS = 500;
	
	private final int EMPTY_SPACE_DENSITY = 400;
	
	private final List<UniverseFeature> universeRegions;
	private final HashMap<MapPoint, Star> stars = new HashMap<MapPoint, Star>();

	public UniverseGenerator(int width, int height, Random random, MainGame game) {
		this.game = game;
		
		this.random = random;
		this.worldWidth = width;
		this.worldHeight = height;
		
		this.universeRegions = this.createUniverseRegions();

		this.createStars();
	}

	public List<UniverseFeature> getUniverseFeatures() {
		return this.universeRegions;
	}
	
	private List<UniverseFeature> createUniverseRegions() {
		List<UniverseFeature> universeRegions = new ArrayList<UniverseFeature>();
		
		for (int i = 0; i < this.FEATURE_ATTEPMTS; i++) {
			
			int randomX = this.random.nextInt(this.worldWidth);
			int randomY = this.random.nextInt(this.worldHeight);
			
			int maxSize = this.worldWidth / 5;
			int minSize = this.worldWidth / 10;
			int randomSize = this.random.nextInt(maxSize - minSize) + minSize;
			
			Circle newCircle = new Circle(randomX, randomY, randomSize);

			boolean overlapsOrOutside = false;
			for (UniverseFeature feature : universeRegions) {
				if (feature.circle.overlaps(newCircle) ||
					!this.worldContainsCircle(newCircle)) {
					overlapsOrOutside = true;
				}
			}
			if (!overlapsOrOutside) {
				universeRegions.add(new UniverseFeature(newCircle, this.random));
			}
		}
		
		return universeRegions;
	}
	
	private void createStars() {
		for (int i = 0; i < this.STAR_ATTEMPTS; i++) {
			
			int randomX = this.random.nextInt(this.worldWidth);
			int randomY = this.random.nextInt(this.worldHeight);
			MapPoint point = new MapPoint(randomX, randomY);

			//try to find a feature at the random point
			UniverseFeature featureFound = null;
			for (UniverseFeature feature : this.universeRegions) {
				if (feature.circle.contains(new Vector2(point.x, point.y))) {
					featureFound = feature;
				}
			}
			
			// if it exists, use its star density
			//otherwise use the empty space star density
			int starDensity = featureFound != null ? featureFound.type.starDensity : this.EMPTY_SPACE_DENSITY;
			if (!this.regionContainsStar(starDensity, point)) {
				this.stars.put(point, new Star(this.random, point, this.game));
			}
		}
		
		//create stars for galaxy arms
		for (UniverseFeature feature : this.universeRegions) {
			if (feature.type == UniverseFeatureType.SPIRAL_ARM) {
				int fullCircle = 360;
				int halfCircle = 180;
				int randomAngle = this.random.nextInt(fullCircle);
				int oppositeAngle = randomAngle + halfCircle;
				if (oppositeAngle > 360) {
					oppositeAngle -= 360;
				}
				
				System.out.println("first = " + randomAngle);
				System.out.println("opp = " + oppositeAngle);
				
				int randomX = (int)(feature.circle.x + Math.cos(Math.toRadians(randomAngle)) * feature.circle.radius);
				int randomY = (int)(feature.circle.y + Math.sin(Math.toRadians(randomAngle)) * feature.circle.radius);
				System.out.println("x = " + randomX + " y = " + randomY);
				
				int randomOppX = (int)(feature.circle.x + Math.cos(Math.toRadians(oppositeAngle)) * feature.circle.radius);
				int randomOppY = (int)(feature.circle.y + Math.sin(Math.toRadians(oppositeAngle)) * feature.circle.radius);
				System.out.println("oppX = " + randomOppX + " oppY = " + randomOppY);
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
	
	private boolean worldContainsCircle(Circle circle) {
		float maxX = circle.x + circle.radius;
		float minX = circle.x - circle.radius;
		float maxY = circle.y + circle.radius;
		float minY = circle.y - circle.radius;
		if (minX < 0 ||
			minY < 0) {
			return false;
		}
		if (maxX > this.worldWidth ||
			maxY > this.worldHeight) {
			return false;
		}
		return true;
	}
	
	public HashMap<MapPoint, Star> getStars() {
		return this.stars;
	}
}
