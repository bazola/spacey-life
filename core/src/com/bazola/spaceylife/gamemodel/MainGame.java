package com.bazola.spaceylife.gamemodel;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.bazola.spaceylife.GameScreen;
import com.bazola.spaceylife.gamemodel.gamepieces.Alien;
import com.bazola.spaceylife.gamemodel.gamepieces.EnemyShip;
import com.bazola.spaceylife.gamemodel.gamepieces.Star;
import com.bazola.spaceylife.gamemodel.players.AlienPlayer;
import com.bazola.spaceylife.gamemodel.players.FighterPlayer;
import com.bazola.spaceylife.gamemodel.players.PlayerType;
import com.bazola.spaceylife.gamemodel.players.TraderPlayer;

public class MainGame {
	
	private final GameScreen gameScreen;
	private final Random random;
	public final UniverseGenerator universe;
	public final int worldWidth;
	public final int worldHeight;
	
	private AlienPlayer alienPlayer;
	private TraderPlayer traderPlayer;
	private FighterPlayer fighterPlayer;
	
	private int playerResources = 0;
	private int smallAlienCost = 100;
	private int largeAlienCost = 500;
	private int maxEnemies = 20;
	private int startingAliens = 5;
	
	public MainGame(int width, int height, GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		
		this.random = this.gameScreen.random;
		
		this.worldWidth = width;
		this.worldHeight = height;
		
		this.universe = new UniverseGenerator(width, height, this.random, this);
		
		this.setupGame();
	}
	
	private void setupGame() {
		this.createPlayers();
	}
	
	/**
	 * If you add these aliens inside the setup game method
	 * they do not properly spawn.
	 */
	public void startGame() {
		for (int i = 0; i < this.startingAliens; i++) {
			this.spawnAlien();
		}
	}
	
	private void createPlayers() {
		this.alienPlayer = new AlienPlayer(this);
		this.fighterPlayer = new FighterPlayer(this);
		this.traderPlayer = new TraderPlayer(this);
	}
	
	public int getPlayerResources() {
		return this.playerResources;
	}
	
	public Star findStarInRange(int searchSize, MapPoint playerSearchLocation) {
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
		this.alienPlayer.setPlayerMarkedPoint(point);
	}
	
	public void flagSpawned(PlayerFlag flag) {
		this.gameScreen.flagSpawned(flag);
	}
	
	public void spawnRadarRingsAtFlagPlace(PlayerFlag flag) {
		this.gameScreen.spawnRadarRingsAtFlagPlace(flag);
	}
	
	public void update() {
		this.alienPlayer.update(this.universe.getStars(), this.fighterPlayer.getShips());
		this.fighterPlayer.update(this.universe.getStars(), this.alienPlayer.getSmallAliens());
		this.traderPlayer.update();
		
		//check for player stars and allocate resources for them
		for (Star star : this.universe.getStars().values()) {
			if (star.getOwner() == PlayerType.ALIEN) {
				this.playerResources++;
			}
		}
		
		//manually spawning ships for fighter player
    	if (this.fighterPlayer.getShips().size() < this.maxEnemies) {
    		this.spawnEnemyShip();
    	}
	}
	
	public Star getAlienHomeworld() {
		return this.alienPlayer.getHomeworld();
	}
	
	public Star getFighterHomeworld() {
		return this.fighterPlayer.getHomeworld();
	}
	
	public Star getTraderHomeworld() {
		return this.traderPlayer.getHomeworld();
	}
	
	public List<Alien> getPlayerAliens() {
		return this.alienPlayer.getSmallAliens();
	}
	
	public void spawnAlien() {
		Alien alien = new Alien(this.alienPlayer.getHomeworld().getPosition(), this.random, this);
		this.alienPlayer.addSmallAlien(alien);
		this.gameScreen.alienSpawned(alien);
	}	
	
	public void spawnEnemyShip() {
		EnemyShip ship = new EnemyShip(this.random, this.fighterPlayer.getHomeworld().getPosition(), this);
		this.fighterPlayer.addShip(ship);
		this.gameScreen.enemyShipSpawned(ship);
	}
	
	public void enemyFiredWeaponAtAlien(EnemyShip enemyShip, Alien alien) {
		this.alienPlayer.removeSmallAlien(alien);
		this.gameScreen.enemyFiredWeaponAtAlien(enemyShip, alien);
	}
	
	public void alienEatingEnemy(Alien alien, EnemyShip enemyShip) {
		this.fighterPlayer.removeShip(enemyShip);
		this.gameScreen.enemyKilled(enemyShip);
	}
	
	public boolean isWithinBounds(MapPoint point) {
		return point.x >= 0 &&
			   point.x <= this.worldWidth &&
			   point.y >= 0 &&
			   point.y <= this.worldHeight;
	}
	
	public boolean positionIsDarkZone(MapPoint point) {
		UniverseFeature targetFeature = null;
		for (UniverseFeature feature : this.universe.getUniverseFeatures()) {
			if (feature.circle.contains(new Vector2(point.x, point.y))) {
				targetFeature = feature;
				break;
			}
		}
		
		if (targetFeature == null) {
			return false;
		}
		
		return targetFeature.type == UniverseFeatureType.DARK_ZONE;
	}
	
	public int getSmallAlienCost() {
		int maintenance = this.alienPlayer.getSmallAliens().size();
		return this.smallAlienCost + maintenance;
	}
	
	public int getLargeAlienCost() {
		return this.largeAlienCost;
	}
	
	public void playerClickedSmallAlienButton() {
		int maintenance = this.alienPlayer.getSmallAliens().size();
		if (this.playerResources >= this.smallAlienCost + maintenance) {
			this.playerResources -= this.smallAlienCost + maintenance;
			this.spawnAlien();
		}
	}
	
	public void playerClickedLargeAlienButton() {
		/*
		if (this.playerResources >= this.largeAlienCost) {
			this.playerResources -= this.largeAlienCost;
		}
		*/
	}
	
	public void playerClickedFlagButtonAtIndex(int index) {
		//TODO: fix this
	}
	
	public void aliensCapturedStar() {
		this.gameScreen.alienCapturedStar();
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
