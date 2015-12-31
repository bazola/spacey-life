package com.bazola.spaceylife.gamemodel.gamepieces;

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
				case CHASE_ENEMY:
					entity.reachedEnemy();
					break;
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
	
	CHASE_ENEMY {
		@Override
		public void enter(Alien entity) {
			entity.setDestinationForChaseEnemy();
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
		
		//if not moving, but overlapping, resolve that before anything else
		if (entity.isOverlappingNeighbor() &&
			entity.stateMachine.getCurrentState() != AlienState.MOVE) {
			entity.stateMachine.changeState(AlienState.FIX_OVERLAP);
		
		} else {
			//search for enemies first
			if (entity.searchForNearbyEnemy()) {
				entity.stateMachine.changeState(AlienState.CHASE_ENEMY);
			//then for flags
			} else if (entity.isFlagAvailable() &&
				!entity.isFlagNearEnough()) {
				entity.stateMachine.changeState(AlienState.SEARCH_FLAG);
				
			//then eatable stars
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
