package com.bazola.spaceylife.gamemodel.players;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum AlienPlayerState implements State<AlienPlayer> {
	FEEDING;

	@Override
	public void enter(AlienPlayer entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(AlienPlayer entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exit(AlienPlayer entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onMessage(AlienPlayer entity, Telegram telegram) {
		// TODO Auto-generated method stub
		return false;
	}
}
