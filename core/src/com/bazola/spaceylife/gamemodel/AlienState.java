package com.bazola.spaceylife.gamemodel;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum AlienState implements State<Alien> {
	IDLE,
	
	FIX_OVERLAP {
		@Override
		public void enter(Alien entity) {
			entity.setDestinationForOverlapMove();
		}
	},
	
	MOVE {
		@Override
		public void update(Alien entity) {
			entity.move();
			
			if (entity.isAtDestination()) {
				switch(entity.stateMachine.getPreviousState()) {
				case SEARCH_STAR:
					entity.stateMachine.changeState(AlienState.EAT_STAR);
					break;
				default:
					entity.stateMachine.changeState(AlienState.IDLE);
					break;
				}
			}
		}
	},
	
	SEARCH_FLAG {
		@Override
		public void enter(Alien entity) {
			entity.setDestinationForFlagMove();
		}
	},
	
	SEARCH_STAR {
		@Override
		public void enter(Alien entity) {
			entity.setDestinationForStarMove();
		}
	},
	
	EAT_STAR {
		@Override
		public void update(Alien entity) {
			if (entity.eatStar()) {
				entity.stateMachine.changeState(AlienState.IDLE);
			}
		}
	};
	
	@Override
	public void enter(Alien entity) {
	}

	@Override
	public void update(Alien entity) {
		
		if (entity.isOverlappingNeighbor() &&
			entity.stateMachine.getCurrentState() != AlienState.MOVE) {
			entity.stateMachine.changeState(AlienState.FIX_OVERLAP);
		
		} else {
			if (entity.isFlagAvailable() &&
				!entity.isFlagNearEnough()) {
				entity.stateMachine.changeState(AlienState.SEARCH_FLAG);
			} else {
				if (entity.searchForEatableStar()) {
					entity.stateMachine.changeState(AlienState.SEARCH_STAR);
				}
			}
		}
		
		//System.out.println(entity.stateMachine.getCurrentState().name());
	}

	@Override
	public void exit(Alien entity) {	
	}

	@Override
	public boolean onMessage(Alien entity, Telegram telegram) {
		return false;
	}
}
