package com.bazola.spaceylife.gamemodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.bazola.spaceylife.GameScreen;

public class MainGame {
	
	private final GameScreen gameScreen;
	
	private final Random random;
	
	public final UniverseGenerator universe;
	
	private final int worldWidth;
	private final int worldHeight;
	
	private Star playerHomeworld;
	private Star aiHomeworld;
	
	private List<Alien> playerAliens = new ArrayList<Alien>();
	
	private List<EnemyShip> enemyShips = new ArrayList<EnemyShip>();
	
	private List<PlayerFlag> playerFlags = new ArrayList<PlayerFlag>();
	private int flagLimit = 3;
	
	public MainGame(int width, int height, GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		
		this.random = this.gameScreen.random;
		
		this.worldWidth = width;
		this.worldHeight = height;
		
		this.universe = new UniverseGenerator(width, height, this.random);
	
		this.setPlayerAndAIHomeworlds();
	}
	
	private void setPlayerAndAIHomeworlds() {
		//player in bottom corner
		int searchSize = this.worldWidth / 10;
		MapPoint playerSearchLocation = new MapPoint(searchSize, searchSize);
		this.playerHomeworld = this.findStarInRange(searchSize, playerSearchLocation);
		//increase the search size if a star is not found
		int count = 1;
		while(this.playerHomeworld == null) {
			this.playerHomeworld = this.findStarInRange(searchSize * count, playerSearchLocation);
			count++;
		}
		this.playerHomeworld.setPlayerControlled();
		
		//ai in top opposite corner
		MapPoint aiSearchLocation = new MapPoint(this.worldWidth - searchSize, this.worldHeight - searchSize);
		this.aiHomeworld = this.findStarInRange(searchSize, aiSearchLocation);
		count = 1;
		while (this.aiHomeworld == null) {
			this.aiHomeworld = this.findStarInRange(searchSize * count, aiSearchLocation);
			count++;
		}
		this.aiHomeworld.setAIControlled();
	}
	
	private Star findStarInRange(int searchSize, MapPoint playerSearchLocation) {
		for (MapPoint starPoint : this.universe.getStars().keySet()) {
			if (starPoint.x <= playerSearchLocation.x + searchSize &&
				starPoint.x >= playerSearchLocation.x - searchSize &&
				starPoint.y <= playerSearchLocation.y + searchSize &&
				starPoint.y >= playerSearchLocation.y - searchSize) {
				return this.universe.getStars().get(starPoint);
			}
		}
		return null;
	}
	
	public void setPlayerMarkedPoint(MapPoint point) {
		//create new flags up to the limit
		if (this.playerFlags.size() < this.flagLimit) {
			PlayerFlag flag = new PlayerFlag(point);
			this.playerFlags.add(flag);
			this.gameScreen.flagSpawned(flag);
		} else {
			//shuffle to last index
			PlayerFlag flag = this.playerFlags.remove(0);
			this.playerFlags.add(flag);
			flag.setPosition(point);
			this.gameScreen.spawnRadarRingsAtFlagPlace(flag);
		}
	}
	
	public void update() {
		for (Alien alien : this.playerAliens) {
			alien.update(this.playerFlags, this.universe.getStars(), this.playerAliens, this.enemyShips);
		}
		for (EnemyShip enemyShip : this.enemyShips) {
			enemyShip.update(this.playerAliens);
		}
	}
	
	public Star getPlayerHomeworld() {
		return this.playerHomeworld;
	}
	
	public Star getAIHomeworld() {
		return this.aiHomeworld;
	}
	
	public List<Alien> getPlayerAliens() {
		return this.playerAliens;
	}
	
	public void spawnAlien() {
		Alien alien = new Alien(this.playerHomeworld.getPosition(), this.random, this);
		this.playerAliens.add(alien);
		this.gameScreen.alienSpawned(alien);
	}	
	
	public void spawnEnemyShip() {
		EnemyShip ship = new EnemyShip(this.random, this.aiHomeworld.getPosition(), this);
		this.enemyShips.add(ship);
		this.gameScreen.enemyShipSpawned(ship);
	}
	
	public void enemyFiredWeaponAtAlien(EnemyShip enemyShip, Alien alien) {
		this.gameScreen.enemyFiredWeaponAtAlien(enemyShip.getPosition(), alien.getPosition());
	}
	
	public void alienEatingEnemy(Alien alien, EnemyShip enemyShip) {
		this.enemyShips.remove(enemyShip);
		this.gameScreen.enemyKilled(enemyShip);
	}
	
	public boolean isWithinBounds(MapPoint point) {
		return point.x >= 0 &&
			   point.x <= this.worldWidth &&
			   point.y >= 0 &&
			   point.y <= this.worldHeight;
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
