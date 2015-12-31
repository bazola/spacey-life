package com.bazola.spaceylife.camera;

import com.badlogic.gdx.input.GestureDetector;

public class SimpleDirectionGestureDetector extends GestureDetector {
	
	public interface DirectionListener {
		void onLeft();
		void onRight();
		void onUp();
		void onDown();
		void singleTap(float x, float y);
	}

	public SimpleDirectionGestureDetector(DirectionListener directionListener) {
		super(new DirectionGestureListener(directionListener));
	}

	private static class DirectionGestureListener extends GestureAdapter{
		DirectionListener directionListener;
		
		int minimumVelocityToActivate = 5;

		public DirectionGestureListener(DirectionListener directionListener){
			this.directionListener = directionListener;
		}

		@Override
		public boolean fling(float velocityX, float velocityY, int button) {
			if(Math.abs(velocityX)>Math.abs(velocityY)){
				if(velocityX>this.minimumVelocityToActivate){
					directionListener.onRight();
				}else{
                    directionListener.onLeft();
				}
			}else{
				if(velocityY>this.minimumVelocityToActivate){
                    directionListener.onDown();
				}else{           
                    directionListener.onUp();
				}
			}
			return super.fling(velocityX, velocityY, button);
		}
		
		 @Override
		 public boolean tap(float x, float y, int count, int button) {
			 directionListener.singleTap(x, y);
			 return super.tap(x, y, count, button);
		 }
	}
}