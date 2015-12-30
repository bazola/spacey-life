package com.bazola.spaceylife;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;

/**
 * Originally created by Zomis on 2014-11-12.
 */
public class CameraPanner extends GestureDetector.GestureAdapter {
    private final OrthographicCamera camera;
    private boolean enabled;
    
    private OrthographicCamera parallaxCamera;

    public CameraPanner(OrthographicCamera camera) {
        this.camera = camera;
    }
    
    public void setParallaxCamera(OrthographicCamera parallaxCamera) {
    	this.parallaxCamera = parallaxCamera;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if (!enabled) {
            return false;
        }
        
        //float moveX = -deltaX * camera.zoom;
        //float moveY = deltaY * camera.zoom;
        
        camera.position.add(-deltaX * camera.zoom, deltaY * camera.zoom, 0);
        
        //parallax camera ignores zoom for movement
        if (parallaxCamera != null) {
        	parallaxCamera.position.add(-deltaX, deltaY, 0);
        }
        
        return true;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
