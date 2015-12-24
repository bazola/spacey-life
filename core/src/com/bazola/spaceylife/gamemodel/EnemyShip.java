package com.bazola.spaceylife.gamemodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;

public class EnemyShip {
	
	private final Random random;
	private final MainGame game;
	
	private MapPoint position;
	
	private MapPointPair pointPair;
	
	private double angle;
	
	private int speed;
	
	//private Rectangle rectangle;
	
	private int patrolMoveDistance = 200;
	
	private int sensorDistance = 100;
	
	private int weaponsRange = 40;
	
	private List<Alien> playerAliens;
	
	private Alien targetAlien;
	
	private int weaponCooldownTime = 25;
	private int currentWeaponCooldown = 0;
	
	private Comparator<AlienDistanceTuple> distanceComparator = new Comparator<AlienDistanceTuple>() {
	    public int compare(AlienDistanceTuple a, AlienDistanceTuple b) {
	        return Double.compare(a.distance, b.distance); //changed back to ascending
	    }
	};
	
	public final StateMachine<EnemyShip, EnemyState> stateMachine;
	
	public EnemyShip(Random random, MapPoint position, MainGame game) {
		this.random = random;
		this.position = position;
		this.game = game;
		
		this.stateMachine = new DefaultStateMachine<EnemyShip, EnemyState>(this);
		this.stateMachine.changeState(EnemyState.IDLE);

		this.angle = 0;
		
		this.speed = 5;
	}
	
	public MapPoint getPosition() {
		return this.position;
	}
	
	public double getAngle() {
		return this.angle;
	}
	
	public void fireWeapon() {
		if (this.currentWeaponCooldown < this.weaponCooldownTime) {
			this.currentWeaponCooldown += this.weaponCooldownTime;
			this.game.enemyFiredWeaponAtAlien(this, this.targetAlien);
		}
	}
	
	public void update(List<Alien> playerAliens) {
		
		this.currentWeaponCooldown--;
		
		this.playerAliens = playerAliens;
		
		this.stateMachine.update();
	}
	
	public boolean isAtDestination() {
		
		if (this.stateMachine.getCurrentState() == EnemyState.FOLLOW_ALIEN) {
			return this.calculateDistance(this.targetAlien.getPosition(), this.position) <= this.weaponsRange;
		
		} else {
	 		return this.pointPair == null ||
	 			   this.position.equals(this.pointPair.secondPoint);
		}
	}
	
	public boolean searchForNearbyAlien() {
		List<AlienDistanceTuple> closebyPointsForSort = new ArrayList<AlienDistanceTuple>();
		for (Alien alien : this.playerAliens) {
			double distance = this.calculateDistance(alien.getPosition(), this.position);
			if (distance <= this.sensorDistance) {
				closebyPointsForSort.add(new AlienDistanceTuple(alien, distance));
			}
		}
		Collections.sort(closebyPointsForSort, this.distanceComparator);
		if (closebyPointsForSort.size() > 0) {
			
			this.targetAlien = closebyPointsForSort.get(0).alien;
			
			return true;
		}
		
		return false;
	}
	
	public void startFollowingAlien() {
		this.pointPair = new MapPointPair(this.position, this.targetAlien.getPosition());
		this.stateMachine.changeState(EnemyState.MOVE);
	}
	
	public void startNewPatrol() {
	
		MapPoint patrolPoint = new MapPoint(-100, -100); //make sure it will fail the first time
		//make sure to generate a point in bounds
		while (!this.game.isWithinBounds(patrolPoint)) {
			int randomX = this.random.nextInt(this.patrolMoveDistance);
			int randomY = this.random.nextInt(this.patrolMoveDistance);
			//50% chance to move negative instead of positive
			if (this.random.nextBoolean()) {
				randomX *= -1;
			}
			if (this.random.nextBoolean()) {
				randomY *= -1;
			}
			patrolPoint = new MapPoint(randomX + this.position.x, randomY + this.position.y);
		}
		
		this.pointPair = new MapPointPair(this.position, patrolPoint);
		
		this.stateMachine.changeState(EnemyState.MOVE);
	}

	public void move() {
		
		if (this.pointPair == null ||
			this.position.equals(this.pointPair.secondPoint)) {
			return;
		}
		
		int deltaX = this.pointPair.secondPoint.x - this.position.x;
		int deltaY = this.pointPair.secondPoint.y - this.position.y;
		
		double goalDistance = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
		if (goalDistance > this.speed) {
			
			//System.out.println("goal distance greater");
			
			double ratio = this.speed / goalDistance;
			double xMove = ratio * deltaX;
			double yMove = ratio * deltaY;
			
			MapPoint previousPosition = this.position;
			this.position = new MapPoint((int)(xMove + this.position.x), (int)(yMove + this.position.y));
			this.angle = this.calculateAngle(previousPosition, this.position);
		} else {
			this.position = this.pointPair.secondPoint;
		}
		
		//only update rectangle after position changes
		//this.rectangle.setPosition(this.position.x, this.position.y);
	}
	
	private double calculateAngle(MapPoint origin, MapPoint destination) {
		double degree = Math.toDegrees(Math.atan2(destination.y - origin.y, 
                								  destination.x - origin.x));
		if (degree < 0) {
			degree += 360;
		}
		return degree;
	}
	
	private double calculateDistance(MapPoint destination, MapPoint origin) {
		return Math.hypot(destination.x - origin.x, destination.y - origin.y);
	}
}
