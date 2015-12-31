package com.bazola.spaceylife.gamemodel;

import com.bazola.spaceylife.gamemodel.gamepieces.EnemyShip;

public class EnemyDistanceTuple {
	
	public final EnemyShip enemy;
	public final double distance;
	
	public EnemyDistanceTuple(EnemyShip enemy, double distance) {
		this.enemy = enemy;
		this.distance = distance;
	}
}
