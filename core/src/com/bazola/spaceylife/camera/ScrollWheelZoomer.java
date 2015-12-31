package com.bazola.spaceylife.camera;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class ScrollWheelZoomer implements InputProcessor {
	
    private final OrthographicCamera camera;
    private boolean isEnabled;
    
    public ScrollWheelZoomer(OrthographicCamera camera) {
        this.camera = camera;
        this.isEnabled = true;
    }
    
    public void setEnabled(boolean enabled) {
    	this.isEnabled = enabled;
    }
    
	@Override
	public boolean scrolled(int amount) {
		
    	if (!this.isEnabled) {
    		return false;
    	}
    	
		if (amount > 0 && camera.zoom < 10) {
			camera.zoom += 0.1f;
		}
		
        //Zoom in
		if (amount < 0 && camera.zoom > 0.1) {
			camera.zoom -= 0.1f;
		}
		return true;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}
	@Override
	public boolean keyUp(int keycode) {
		return false;
	}
	@Override
	public boolean keyTyped(char character) {
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}
}
