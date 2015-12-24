package com.bazola.spaceylife.gamemodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.math.Rectangle;

public class Alien {

	private final Random random;
	
	private MapPoint position;
	
	private MapPointPair pointPair;
	
	private double angle;
	
	private int speed;
	
	public AlienState state;
	
	private Rectangle rectangle;
	
	private int overlapMoveDistance = 30;
	
	/**
	 * These variables are used to allow aliens to move to avoid overlapping each
	 * other without getting another assignment to move to their destination.
	 */
	private int minDistanceFromFlag = 100;
	private int minDistanceFromPlanet = 25;
	
	private int sensorDistance = 100;
	
	private int numTicksToEat = 50;
	private int eatCount = 0;
	
	private Comparator<MapPointDistanceTuple> distanceComparator = new Comparator<MapPointDistanceTuple>() {
	    public int compare(MapPointDistanceTuple a, MapPointDistanceTuple b) {
	        return Double.compare(a.distance, b.distance); //changed back to ascending
	    }
	};
	
	
	
	
	
	
	private final StateMachine<Alien, AlienStateS> stateMachine;
	
	public Alien(MapPoint position, Random random) {
		this.position = position;
		this.random = random;
		
		this.stateMachine = new DefaultStateMachine<Alien, AlienStateS>(this);
		this.stateMachine.changeState(AlienStateS.IDLE);
		
		this.angle = 0;
		
		this.speed = 10;
		
		this.state = AlienState.RESTING;
	}
	
	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
	
	public Rectangle getRectangle() {
		return this.rectangle;
	}
	
	public MapPoint getPosition() {
		return this.position;
	}
	
	public double getAngle() {
		return this.angle;
	}

	public void update(List<PlayerFlag> playerFlags, Map<MapPoint, Star>stars, List<Alien> playerAliens) {
		
		this.stateMachine.update();
		
		if (this.state == AlienState.EATING_PLANET) {
			
			//VERY TEMPORARY
			this.eatCount++;
			if (this.eatCount > this.numTicksToEat) {
				this.eatCount = 0;
				this.state = AlienState.RESTING;
			}
			
		} else {
			this.moveToPlayerFlag(playerFlags);
			
			this.moveToEmptyPlanet(stars);
			
			this.moveForOverlap(playerAliens);
			
			this.move();
			this.rectangle.setPosition(this.position.x, this.position.y);
		}
	}
	
	private void moveToPlayerFlag(List<PlayerFlag> playerFlags) {
		//move to player flag first
		if (playerFlags.size() > 0) {
			PlayerFlag targetFlag = playerFlags.get(playerFlags.size() - 1);
			//move to the last flag
			if (this.calculateDistance(targetFlag.getPosition(), this.position) > this.minDistanceFromFlag) {
				this.setFlagDestination(targetFlag.getPosition());
			}
		}
	}
	
	private void moveToEmptyPlanet(Map<MapPoint, Star> stars) {
		List<MapPointDistanceTuple> closebyPointsForSort = new ArrayList<MapPointDistanceTuple>();
		for (MapPoint point : stars.keySet()) {
			double distance = this.calculateDistance(point, this.position);
			if (distance <= this.sensorDistance) {
				closebyPointsForSort.add(new MapPointDistanceTuple(point, distance));
			}
		}
		Collections.sort(closebyPointsForSort, this.distanceComparator);
		if (closebyPointsForSort.size() > 0) {
			for (MapPointDistanceTuple tuple : closebyPointsForSort) {
				Star star = stars.get(tuple.point);
				if (star.getState() != StarState.PLAYER_CONTROLLED) {
					if (this.calculateDistance(star.getPosition(), this.position) > this.minDistanceFromPlanet) {
						this.setPlanetDestination(star.getPosition());
						return;
					}
				}
			}
		}
	}
	
	private void moveForOverlap(List<Alien> playerAliens) {
		//move away if overlapping other alien
		for (Alien alien : playerAliens) {
			
			if (this.equals(alien)) {
				continue;
			}
			
			if (this.rectangle.overlaps(alien.getRectangle())) {

				int randomX = this.random.nextInt(this.overlapMoveDistance);
				int randomY = this.random.nextInt(this.overlapMoveDistance);
				//50% chance to move negative instead of positive
				if (this.random.nextBoolean()) {
					randomX *= -1;
				}
				if (this.random.nextBoolean()) {
					randomY *= -1;
				}
				
				this.setOverlapDestination(new MapPoint(this.position.x + randomX,
														this.position.y + randomY));
			}
		}
	}
	
	private double calculateDistance(MapPoint destination, MapPoint origin) {
		return Math.hypot(destination.x - origin.x, destination.y - origin.y);
	}
	
	public void setFlagDestination(MapPoint destination) {
		this.pointPair = new MapPointPair(this.position, destination);
		this.state = AlienState.MOVING_TO_FLAG;
	}
	
	public void setPlanetDestination(MapPoint destination) {
		if (this.state == AlienState.MOVING_TO_FLAG) {
			return;
		}
		this.pointPair = new MapPointPair(this.position, destination);
		this.state = AlienState.MOVING_TO_PLANET;
	}
	
	public void setOverlapDestination(MapPoint destination) {
		if (this.state != AlienState.RESTING) {
			return;
		}
		this.pointPair = new MapPointPair(this.position, destination);
		this.state = AlienState.MOVING_FOR_OVERLAP;
	}
	
	private void enterRestingState() {
		if (this.state != AlienState.MOVING_TO_PLANET) {
			this.state = AlienState.RESTING;
		} else {
			this.state = AlienState.EATING_PLANET;
		}
	}
	
	public void move() {
		
		if (this.pointPair == null ||
			this.position.equals(this.pointPair.secondPoint)) {
			this.enterRestingState();
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
			this.angle = this.getAngle(previousPosition, this.position);
		} else {
			this.position = this.pointPair.secondPoint;
			this.enterRestingState();
		}
	}
	
	private double getAngle(MapPoint origin, MapPoint destination) {
		double degree = Math.toDegrees(Math.atan2(destination.y - origin.y, 
                								  destination.x - origin.x));
		if (degree < 0) {
			degree += 360;
		}
		return degree;
	}
}
