package com.bazola.spaceylife.gamemodel.players;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.bazola.spaceylife.gamemodel.Star;

public class TraderPlayer {
	
	public final StateMachine<TraderPlayer, TraderPlayerState> stateMachine;
	
	private Star homeworld;

	private final PlayerType type = PlayerType.TRADER;
	
	public TraderPlayer() {
		this.stateMachine = new DefaultStateMachine<TraderPlayer, TraderPlayerState>(this);
		this.stateMachine.changeState(TraderPlayerState.EXPANDING);
	}
	
	public void setHomeworld(Star homeworld) {
		this.homeworld = homeworld;
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
