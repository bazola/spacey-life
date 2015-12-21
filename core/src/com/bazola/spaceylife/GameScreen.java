package com.bazola.spaceylife;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen extends BZScreenAdapter {
	
	private final LibGDXGame libGDXGame;
	
    private double currentTime = 0;
    private double timeSinceLastRender = 0;
    private double timeBewteenRenders = 1 / 60;
    private double timeSinceLastUpdate = 0;
    private double timeBetweenUpdates = 100;
    
    public GameScreen(LibGDXGame libGDXGame) {
    	this.libGDXGame = libGDXGame;
    	
    	this.addActorsToStage();
    }
    
    private void addActorsToStage() {
    	Image gridImage = new Image(this.libGDXGame.gridBackground);
    	//gridImage.scaleBy(this.BACKGROUND_SCALE);
    	gridImage.setSize(LibGDXGame.STAGE_WIDTH, LibGDXGame.STAGE_HEIGHT);
    	this.libGDXGame.stage.addActor(gridImage);
    }
    
	@Override
	public void render (float delta) {
		//handle button presses
		/*
		if (Gdx.input.isKeyJustPressed(Keys.W) ||
			Gdx.input.isKeyJustPressed(Keys.UP)) {
			GameScreen.this.movePlayer(MapDirection.UP);
		}
		if (Gdx.input.isKeyJustPressed(Keys.A) ||
			Gdx.input.isKeyJustPressed(Keys.LEFT)) {
			GameScreen.this.movePlayer(MapDirection.LEFT);
		}
		if (Gdx.input.isKeyJustPressed(Keys.S) ||
			Gdx.input.isKeyJustPressed(Keys.DOWN)) {
			GameScreen.this.movePlayer(MapDirection.DOWN);
		}
		if (Gdx.input.isKeyJustPressed(Keys.D) ||
			Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
			GameScreen.this.movePlayer(MapDirection.RIGHT);
		}
		*/
		
        double newTime = TimeUtils.millis();
        double timeElapsed = newTime - this.currentTime;
        this.currentTime = newTime;
        this.timeSinceLastRender += timeElapsed;
        this.timeSinceLastUpdate += timeElapsed;
        
        if (this.timeSinceLastRender > this.timeBewteenRenders) {
        	this.timeSinceLastRender = 0;
        	
        	//update actors here
        }
        
        if (this.timeSinceLastUpdate > this.timeBetweenUpdates) {
        	this.timeSinceLastUpdate = 0;
        	
        	//update game here
        }
	}

	@Override
	public void viewResized() {
		// TODO Auto-generated method stub
	}
}
