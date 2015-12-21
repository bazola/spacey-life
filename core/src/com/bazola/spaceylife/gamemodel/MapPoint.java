package com.bazola.spaceylife.gamemodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapPoint {
	public int x;
	public int y;
	
	public MapPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public MapPoint(MapPoint point) {
		this.x = point.x;
		this.y = point.y;
	}
	
	public void setPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "{" + String.valueOf(this.x) + " , " + String.valueOf(this.y) + "}";
	}
	
	//used for drawing on map
	public void move(MapDirection direction) {
	    this.x += direction.getX();
	    this.y += direction.getY();
	}
	public void reverseMove(MapDirection direction) {
		this.x -= direction.getX();
		this.y -= direction.getY();
	}
	public void move(MapDirection direction, int bounds) {
	    this.x += direction.getX();
	    this.y += direction.getY();
	    
	    if (this.x < 0) {
	    	this.x = 0;
	    } else if (this.x > bounds) {
	    	this.x = bounds;
	    }
	    if (this.y < 0) {
	    	this.y = 0;
 	    } else if (this.y > bounds) {
 	    	this.y = bounds;
 	    }
	}
	public void randomMoveTowards(MapDirection direction, int bounds, Random random) {
		boolean randomBool = random.nextBoolean();
		switch (direction) {
		case LEFT:
		case RIGHT:
			if (randomBool) {
				this.move(MapDirection.UP, bounds);
			} else {
				this.move(MapDirection.DOWN, bounds);
			}
			break;
		case UP:
		case DOWN:
			if (randomBool) {
				this.move(MapDirection.LEFT, bounds);
			} else {
				this.move(MapDirection.RIGHT, bounds);
			}
			break;
		}
	}
	
	public int distanceSq(MapPoint point) {
		point.x -= this.x;
		point.y -= this.y;
		return point.x * point.x + point.y * point.y;
	}
	
	@Override
	public int hashCode() {
	    int hash = 7;
	    hash = 71 * hash + this.x;
	    hash = 71 * hash + this.y;
	    return hash;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (o == null) {
	        return false;
	    }
	    if (!(o instanceof MapPoint)) {
	        return false;
	    }
	    return (x == ((MapPoint) o).x && y == ((MapPoint) o).y);
	}
	
	public boolean isAdjacent(MapPoint point) {
		int distanceX = Math.abs(this.x - point.x);
		int distanceY = Math.abs(this.y - point.y);
		return distanceX <= 1 &&
			   distanceY <= 1;
	}
	
	public List<MapPoint> getAdjacentPoints() {
		List<MapPoint> adjacentPoints = new ArrayList<MapPoint>();
		for (int i = this.x - 1; i <= this.x + 1; i++) {
			for (int j = this.y - 1; j <= this.y + 1; j++) {
				MapPoint newPoint = new MapPoint(i, j);
				if (!newPoint.equals(this)) {
					adjacentPoints.add(newPoint);
				}
			}
		}
		return adjacentPoints;
	}
}