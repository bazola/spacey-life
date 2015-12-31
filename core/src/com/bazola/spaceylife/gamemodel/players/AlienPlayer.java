package com.bazola.spaceylife.gamemodel.players;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.bazola.spaceylife.gamemodel.MainGame;
import com.bazola.spaceylife.gamemodel.MapPoint;
import com.bazola.spaceylife.gamemodel.PlayerFlag;
import com.bazola.spaceylife.gamemodel.gamepieces.Alien;
import com.bazola.spaceylife.gamemodel.gamepieces.EnemyShip;
import com.bazola.spaceylife.gamemodel.gamepieces.Star;

public class AlienPlayer {
	
	private final MainGame game;
	
	private final PlayerType type = PlayerType.ALIEN;
	public final StateMachine<AlienPlayer, AlienPlayerState> stateMachine;
	
	private Star homeworld;

	private List<PlayerFlag> playerFlags = new ArrayList<PlayerFlag>();
	private int flagLimit = 3;
	private boolean hasPlayerFlagsChanged = false;
	
	private List<Alien> smallAliens = new ArrayList<Alien>();
	
	public AlienPlayer(MainGame game) {
		this.game = game;
		this.findHomeworld(game);
		
		this.stateMachine = new DefaultStateMachine<AlienPlayer, AlienPlayerState>(this);
		this.stateMachine.changeState(AlienPlayerState.FEEDING);
	}
	
	private void findHomeworld(MainGame game) {
		//player in bottom corner
		int searchSize = game.worldWidth / 10;
		MapPoint playerSearchLocation = new MapPoint(searchSize, searchSize);
		this.homeworld = game.findStarInRange(searchSize, playerSearchLocation);
		//increase the search size if a star is not found
		int count = 1;
		while(this.homeworld == null) {
			this.homeworld = game.findStarInRange(searchSize * count, playerSearchLocation);
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
	
	public List<Alien> getSmallAliens() {
		return this.smallAliens;
	}
	
	public void addSmallAlien(Alien alien) {
		this.smallAliens.add(alien);
	}
	
	public void removeSmallAlien(Alien alien) {
		this.smallAliens.remove(alien);
	}
	
	public void update(Map<MapPoint, Star>stars, 
			   		   List<EnemyShip> enemyShips) {
		
		this.stateMachine.update();
		
		for (Alien alien : this.smallAliens) {
			alien.update(playerFlags, this.hasPlayerFlagsChanged, stars, this.smallAliens, enemyShips);
		}
		
		//call this after updating the state machine of the aliens
		this.hasPlayerFlagsChanged = false;
	}
	
	public void setPlayerMarkedPoint(MapPoint point) {
		
		//create new flags up to the limit
		if (this.playerFlags.size() < this.flagLimit) {
			PlayerFlag flag = new PlayerFlag(point);
			this.playerFlags.add(flag);
			this.game.flagSpawned(flag);
			
		//else shuffle to last index
		} else {
			PlayerFlag flag = this.playerFlags.remove(0);
			this.playerFlags.add(flag);
			flag.setPosition(point);
			this.game.spawnRadarRingsAtFlagPlace(flag);
		}
		
		this.hasPlayerFlagsChanged = true;
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
