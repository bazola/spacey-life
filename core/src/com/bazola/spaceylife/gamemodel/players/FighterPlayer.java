package com.bazola.spaceylife.gamemodel.players;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.bazola.spaceylife.gamemodel.MainGame;
import com.bazola.spaceylife.gamemodel.MapPoint;
import com.bazola.spaceylife.gamemodel.gamepieces.Alien;
import com.bazola.spaceylife.gamemodel.gamepieces.EnemyShip;
import com.bazola.spaceylife.gamemodel.gamepieces.Star;

public class FighterPlayer {
	
	private final PlayerType type = PlayerType.FIGHTER;
	public final StateMachine<FighterPlayer, FighterPlayerState> stateMachine;
	
	private Star homeworld;
	
	private List<EnemyShip> enemyShips = new ArrayList<EnemyShip>();
	
	public List<EnemyShip> getShips() {
		return this.enemyShips;
	}
	
	public void addShip(EnemyShip ship) {
		this.enemyShips.add(ship);
	}
	
	public void removeShip(EnemyShip ship) {
		this.enemyShips.remove(ship);
	}
	
	public FighterPlayer(MainGame game) {
		this.findHomeworld(game);
		
		this.stateMachine = new DefaultStateMachine<FighterPlayer, FighterPlayerState>(this);
		this.stateMachine.changeState(FighterPlayerState.PATROLLING);
	}
	
	private void findHomeworld(MainGame game) {
		//ai in top opposite corner
		int searchSize = game.worldWidth / 10;
		MapPoint aiSearchLocation = new MapPoint(game.worldWidth - searchSize, game.worldHeight - searchSize);
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
	
	public void update(Map<MapPoint, Star> stars,
					   List<Alien> smallAliens) {
		
		this.stateMachine.update();
		
		for (EnemyShip enemyShip : this.enemyShips) {
			enemyShip.update(smallAliens);
		}
	}
}
