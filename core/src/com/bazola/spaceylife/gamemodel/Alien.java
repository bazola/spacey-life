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
	
	private Rectangle rectangle;
	
	private int overlapMoveDistance = 30;
	
	/**
	 * These variables are used to allow aliens to move to avoid overlapping each
	 * other without getting another assignment to move to their destination.
	 */
	private int minDistanceFromFlag = 100;
	private int minDistanceFromPlanet = 25;
	
	private int sensorDistance = 100;
	
	private Comparator<MapPointDistanceTuple> distanceComparator = new Comparator<MapPointDistanceTuple>() {
	    public int compare(MapPointDistanceTuple a, MapPointDistanceTuple b) {
	        return Double.compare(a.distance, b.distance); //changed back to ascending
	    }
	};
	
	public final StateMachine<Alien, AlienState> stateMachine;
	
	private List<PlayerFlag> playerFlags;
	private List<Alien> playerAliens;
	private Map<MapPoint, Star> stars;
	
	private Star targetStar;
	
	public Alien(MapPoint position, Random random) {
		this.position = position;
		this.random = random;
		
		this.stateMachine = new DefaultStateMachine<Alien, AlienState>(this);
		this.stateMachine.changeState(AlienState.IDLE);
		
		this.angle = 0;
		
		this.speed = 10;
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
		
		this.playerFlags = playerFlags;
		this.stars = stars;
		this.playerAliens = playerAliens;
		
		this.stateMachine.update();
	}
	
	public boolean eatStar() {
		if (this.targetStar == null) {
			return true;
		}
		this.targetStar.addAlienEat();
		return this.targetStar.getState() == StarState.PLAYER_CONTROLLED;
	}
	
	public boolean isAtDestination() {
		return this.pointPair == null ||
			   this.position.equals(this.pointPair.secondPoint);
	}
	
	public boolean isOverlappingNeighbor() {
		for (Alien alien : this.playerAliens) {
			
			if (this.equals(alien)) {
				continue;
			}
			
			if (this.rectangle.overlaps(alien.getRectangle())) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isFlagAvailable() {
		return this.playerFlags.size() > 0;
	}
	
	public boolean isFlagNearEnough() {
		PlayerFlag targetFlag = playerFlags.get(playerFlags.size() - 1);
		return this.calculateDistance(targetFlag.getPosition(), this.position) < this.minDistanceFromFlag;
	}
	
	public boolean searchForEatableStar() {
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
						
						this.targetStar = star;
						
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void setDestinationForOverlapMove() {
		
		int randomX = this.random.nextInt(this.overlapMoveDistance);
		int randomY = this.random.nextInt(this.overlapMoveDistance);
		//50% chance to move negative instead of positive
		if (this.random.nextBoolean()) {
			randomX *= -1;
		}
		if (this.random.nextBoolean()) {
			randomY *= -1;
		}
		
		this.pointPair = new MapPointPair(this.position, new MapPoint(this.position.x + randomX, this.position.y + randomY));
		this.stateMachine.changeState(AlienState.MOVE);
	}
	
	public void setDestinationForFlagMove() {
		this.pointPair = new MapPointPair(this.position, this.playerFlags.get(this.playerFlags.size() - 1).getPosition());
		this.stateMachine.changeState(AlienState.MOVE);
	}
	
	public void setDestinationForStarMove() {
		if (this.targetStar == null ||
			this.targetStar.getState() == StarState.PLAYER_CONTROLLED) {
			this.stateMachine.changeState(AlienState.IDLE);
		} else {
			this.pointPair = new MapPointPair(this.position, this.targetStar.getPosition());
			this.stateMachine.changeState(AlienState.MOVE);
		}
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
		this.rectangle.setPosition(this.position.x, this.position.y);
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
