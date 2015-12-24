package com.bazola.spaceylife.gamemodel;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum AlienStateS implements State<Alien> {
	IDLE {
		@Override
		public void enter(Alien entity) {
			System.out.println("enter alien");
			//entity.animations.animate("armature|idle_stand", -1, 1, animationListener(entity), 0.2f);
		}
	};

	@Override
	public void enter(Alien entity) {
		System.out.println("this method overridden");
	}

	@Override
	public void update(Alien entity) {
		System.out.println("updating");
	}

	@Override
	public void exit(Alien entity) {	
	}

	@Override
	public boolean onMessage(Alien entity, Telegram telegram) {
		return false;
	}
}
