package com.bazola.spaceylife.gamemodel;

public class Alien {

	private MapPoint position;
	
	private MapPointPair pointPair;
	
	private double angle;
	
	private int speed;
	
	public Alien(MapPoint position) {
		this.position = position;
		
		this.angle = 0;
		
		this.speed = 10;
	}
	
	public MapPoint getPosition() {
		return this.position;
	}
	
	public double getAngle() {
		return this.angle;
	}
	
	public void setDestination(MapPoint destination) {
		this.pointPair = new MapPointPair(this.position, destination);
	}
	
	public void move() {
		
		if (this.pointPair == null) {
			return;
		}
		
		if (this.position.equals(this.pointPair.secondPoint)) {
			return;
		}
		
		int deltaX = this.pointPair.secondPoint.x - this.position.x;
		int deltaY = this.pointPair.secondPoint.y - this.position.y;
		
		double goalDistance = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
		if (goalDistance > this.speed) {
			double ratio = this.speed / goalDistance;
			double xMove = ratio * deltaX;
			double yMove = ratio * deltaY;
			
			MapPoint previousPosition = this.position;
			this.position = new MapPoint((int)(xMove + this.position.x), (int)(yMove + this.position.y));
			this.angle = this.getAngle(previousPosition, this.position);
		} else {
			this.position = this.pointPair.secondPoint;
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
