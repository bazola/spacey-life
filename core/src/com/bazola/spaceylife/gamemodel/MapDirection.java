package com.bazola.spaceylife.gamemodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum MapDirection {
	RIGHT(1, 0), LEFT(-1, 0), UP(0, 1), DOWN(0, -1);
	
	private int dx;
	private int dy;
	
	private MapDirection(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
    public int getX() {return dx;}
    public int getY() {return dy;}
    
    public static MapDirection opposite(MapDirection direction) {
    	switch (direction) {
    	case LEFT:
    		return RIGHT;
    	case RIGHT:
    		return LEFT;
    	case UP:
    		return DOWN;
    	case DOWN:
    		return UP;
    	}
    	return null;
    }
    
    /**
     * The idea here is to not oscillate back and forth between directions,
     * so the exact opposite direction is not valid.
     */
    public static List<MapDirection> validDirections(MapDirection direction) {
    	List<MapDirection> validDirections = new ArrayList<MapDirection>();
    	validDirections.addAll(Arrays.asList(MapDirection.values()));
    	validDirections.remove(MapDirection.opposite(direction)); 
    	return validDirections;
    }
    
	public MapPoint getPointForDirection(MapPoint point) {
		int newX = point.x + this.dx;
		int newY = point.y + this.dy;
		return new MapPoint(newX, newY);
	}
	
	/**
	 * Will return null if the points are not directly adjacent
	 */
	public static MapDirection directionForPoints(MapPoint p1, MapPoint p2) {
		int xDiff = p1.x - p2.x;
		int yDiff = p1.y - p2.y;
		
		if (xDiff == 0) {
			if (yDiff > 0) {
				return MapDirection.UP;
 			} else {
 				return MapDirection.DOWN;
 			}
		}
		
		if (yDiff == 0) {
			if (xDiff > 0) {
				return MapDirection.RIGHT;
			} else {
				return MapDirection.LEFT;
			}
		}
		
		return null;
	}
}
