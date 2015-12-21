package com.bazola.spaceylife;

public enum ShipType {
	AI_COLONY("aiColony01"),
	AI_FIGHTER("aiFighter01"),
	AI_FLAGSHIP("aiFlagship01"),
	AI_FRIGATE("aiFrigate01"),
	AI_SCOUT("aiScout01"),
	AI_TRANSPORT("aiTransport01"),
	
	ENEMY_COLONY("enemyColony01"),
	ENEMY_FIGHTER("enemyFighter01"),
	ENEMY_FLAGSHIP("enemyFlagship01"),
	ENEMY_FRIGATE("enemyFrigate01"),
	ENEMY_SCOUT("enemyScout01"),
	ENEMY_TRANSPORT("enemyTransport01"),
	
	PLAYER_COLONY("playerColony01"),
	PLAYER_FIGHTER("playerFighter01"),
	PLAYER_FLAGSHIP("playerFlagship01"),
	PLAYER_FRIGATE("playerFrigate01"),
	PLAYER_SCOUT("playerScout01"),
	PLAYER_TRANSPORT("playerTransport01");
	
	public final String fileName;
	
	private ShipType(String fileName) {
		this.fileName = fileName;
	}
}
