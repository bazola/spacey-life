package com.bazola.spaceylife.gamemodel;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum EnemyState implements State<EnemyShip>{
	IDLE,
	
	FOLLOW_ALIEN {
		@Override
		public void enter(EnemyShip entity) {
			entity.startFollowingAlien();
		}
	},
	
	OPEN_FIRE {
		@Override
		public void update(EnemyShip entity) {
			
			if (!entity.searchForNearbyAlien()) {
				entity.stateMachine.changeState(IDLE);
			} else {
				entity.fireWeapon();
			}
			
		}
	},
	
	START_PATROL {
		@Override
		public void enter(EnemyShip entity) {
			entity.startNewPatrol();
		}
	},
	
	MOVE {
		@Override
		public void update(EnemyShip entity) {
			entity.move();
			
			if (entity.isAtDestination()) {
				
				switch(entity.stateMachine.getPreviousState()) {
				case FOLLOW_ALIEN:
					entity.stateMachine.changeState(EnemyState.OPEN_FIRE);
					break;
				default:
					entity.stateMachine.changeState(EnemyState.IDLE);
					break;
				}
			}
		}
	};

	@Override
	public void enter(EnemyShip entity) {
	}

	@Override
	public void update(EnemyShip entity) {
		if (entity.searchForNearbyAlien()) {
			entity.stateMachine.changeState(EnemyState.FOLLOW_ALIEN);
			
		} else if (entity.stateMachine.getCurrentState() == EnemyState.IDLE) {
			entity.stateMachine.changeState(EnemyState.START_PATROL);
		}
	}

	@Override
	public void exit(EnemyShip entity) {
	}

	@Override
	public boolean onMessage(EnemyShip entity, Telegram telegram) {
		return false;
	}
}
