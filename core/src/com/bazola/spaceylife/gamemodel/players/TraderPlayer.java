package com.bazola.spaceylife.gamemodel.players;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.bazola.spaceylife.gamemodel.MainGame;
import com.bazola.spaceylife.gamemodel.MapPoint;
import com.bazola.spaceylife.gamemodel.Star;

public class TraderPlayer {
	
	public final StateMachine<TraderPlayer, TraderPlayerState> stateMachine;
	
	private Star homeworld;

	private final PlayerType type = PlayerType.TRADER;
	
	public TraderPlayer(MainGame game) {
		this.findHomeworld(game);
		
		this.stateMachine = new DefaultStateMachine<TraderPlayer, TraderPlayerState>(this);
		this.stateMachine.changeState(TraderPlayerState.EXPANDING);
	}
	
	private void findHomeworld(MainGame game) {
		//traders in top left corner
		int searchSize = game.worldWidth / 10;
		MapPoint aiSearchLocation = new MapPoint(0, game.worldHeight - searchSize);
		this.homeworld = game.findStarInRange(searchSize, aiSearchLocation);
		int count = 1;
		while (this.homeworld == null) {
			this.homeworld = game.findStarInRange(searchSize * count, aiSearchLocation);
			count++;
		}
		this.homeworld.setOwner(this.type);
	}
	
	public void setHomeworld(Star homeworld) {
		this.homeworld = homeworld;
		this.homeworld.setOwner(this.type);
	}
	
	public Star getHomeworld() {
		return this.homeworld;
	}
	
	public void update() {
		this.stateMachine.update();
	}
	
	/*
	public void update(List<PlayerFlag> playerFlags, 
			   boolean hasPlayerFlagsChanged,
			   Map<MapPoint, Star>stars, 
			   List<Alien> playerAliens, 
			   List<EnemyShip> enemyShips) {
	}
	*/


}
