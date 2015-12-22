package com.bazola.spaceylife;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.bazola.spaceylife.gamemodel.Alien;
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
    
    private SimpleDirectionGestureDetector swipeRecognizer;
    
    private final List<Image> starImages = new ArrayList<Image>();
    private final List<AlienImage> alienImages = new ArrayList<AlienImage>();
    
    private int WORLD_WIDTH = 4500;
    private int WORLD_HEIGHT = 3000;
    
    private final MainGame game;
    
    public GameScreen(LibGDXGame libGDXGame) {
    	this.libGDXGame = libGDXGame;
    	
    	this.game = new MainGame(this.WORLD_WIDTH, this.WORLD_HEIGHT, this);
    	
    	this.addSwipeRecognizer();
    	
    	this.addActorsToStage();
    }
    
	private void addSwipeRecognizer() {
		this.swipeRecognizer = new SimpleDirectionGestureDetector(new SimpleDirectionGestureDetector.DirectionListener() {
    		@Override
    		public void onUp() {
    		}
    		@Override
    		public void onRight() {
    		}
    		@Override
    		public void onLeft() {
    		}
    		@Override
    		public void onDown() {
    		}
    		@Override
    		public void singleTap(float x, float y) {
    			GameScreen.this.tappedScreen(x, y);
    		}
    	});
		this.libGDXGame.inputHandler.addProcessor(this.swipeRecognizer);
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
        	
        	for (AlienImage image : this.alienImages) {
        		image.update();
        	}
        	
        	this.timeSinceLastRender = 0;
        }
        
        if (this.timeSinceLastUpdate > this.timeBetweenUpdates) {
        	this.timeSinceLastUpdate = 0;
        	
        	this.game.update();
        	
        	if (!alienSpawned) {
        		this.game.spawnAlien();
        		alienSpawned = true;
        	}
        }
	}
	
	/**
	 * Just spawn one alien at the moment for testing
	 */
	private boolean alienSpawned = false;
	
	public void alienSpawned(Alien alien) {
		AlienImage image = new AlienImage(this.libGDXGame.alien01, alien);
		image.setAnimation(this.libGDXGame.alienMove01);
		this.alienImages.add(image);
		this.libGDXGame.stage.addActor(image);
	}
	
	private void tappedScreen(float x, float y) {
		//convert screen to stage coordinates
		Vector3 touchPoint = new Vector3();
		this.libGDXGame.camera.unproject(touchPoint.set(x, y, 0));
		this.game.setPlayerMarkedPoint(new MapPoint((int)touchPoint.x, (int)touchPoint.y));
		
		AnimatedImage flagImage = new AnimatedImage(this.libGDXGame.flagWave01);
		flagImage.setPosition(touchPoint.x, touchPoint.y);
		flagImage.setSize(this.WORLD_WIDTH/50, this.WORLD_WIDTH/50);
		this.libGDXGame.stage.addActor(flagImage);
	}

	@Override
	public void viewResized() {
		// TODO Auto-generated method stub
	}
}
