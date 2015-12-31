package com.bazola.spaceylife.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;

public class PinchZoomer extends GestureDetector.GestureAdapter {

    private final OrthographicCamera camera;

    private float startingInitialDistance;
    private float startingZoom;
    private boolean isEnabled;

    public PinchZoomer(OrthographicCamera camera) {
        this.camera = camera;
        this.isEnabled = true;
    }
    
    public void setEnabled(boolean enabled) {
    	this.isEnabled = enabled;
    }
    
    @Override
    public boolean zoom(float initialDistance, float distance) {
    	if (!this.isEnabled) {
    		return false;
    	}
        if (startingInitialDistance != initialDistance) {
            startingInitialDistance = initialDistance;
            startingZoom = camera.zoom;
        }
        camera.zoom = startingZoom * (initialDistance / distance);
        return true;
    }

}
