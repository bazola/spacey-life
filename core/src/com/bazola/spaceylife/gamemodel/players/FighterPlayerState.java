package com.bazola.spaceylife.gamemodel.players;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum FighterPlayerState implements State<FighterPlayer>{
	PATROLLING;

	@Override
	public void enter(FighterPlayer entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(FighterPlayer entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exit(FighterPlayer entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onMessage(FighterPlayer entity, Telegram telegram) {
		// TODO Auto-generated method stub
		return false;
	}
}
