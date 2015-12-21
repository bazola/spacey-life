package com.bazola.spaceylife;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.bazola.spaceylife.gamemodel.MainGame;
import com.bazola.spaceylife.gamemodel.MapPoint;
import com.bazola.spaceylife.gamemodel.Star;

public class GameScreen extends BZScreenAdapter {
	
	public final Random random = new Random();
	
	private final LibGDXGame libGDXGame;
	
    private double currentTime = 0;
    private double timeSinceLastRender = 0;
    private double timeBewteenRenders = 1 / 60;
    private double timeSinceLastUpdate = 0;
    private double timeBetweenUpdates = 100;
    
    private final List<Image> starImages = new ArrayList<Image>();
    
    private int WORLD_WIDTH = 4500;
    private int WORLD_HEIGHT = 3000;
    
    private final MainGame game;
    
    public GameScreen(LibGDXGame libGDXGame) {
    	this.libGDXGame = libGDXGame;
    	
    	this.game = new MainGame(this.WORLD_WIDTH, this.WORLD_HEIGHT, this);
    	
    	this.addActorsToStage();
    }
    
    private void addActorsToStage() {
    	Image gridImage = new Image(this.libGDXGame.gridBackground);
    	gridImage.setSize(this.WORLD_WIDTH, this.WORLD_HEIGHT);
    	this.libGDXGame.stage.addActor(gridImage);
    	
    	this.starImages.clear();

    	for (Entry<MapPoint, Star> star : this.game.universe.getStars().entrySet()) {
    		Image starImage = new Image(this.libGDXGame.starTextures.get(star.getValue().type));
    		starImage.setOrigin(Align.center);
    		starImage.setPosition(star.getKey().x, star.getKey().y);
    		
    		this.starImages.add(starImage);
    		this.libGDXGame.stage.addActor(starImage);
    	}
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
        	
        	this.game.update();
        }
	}

	@Override
	public void viewResized() {
		// TODO Auto-generated method stub
	}
}
