package com.bazola.spaceylife;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.TimeUtils;
import com.bazola.spaceylife.gamemodel.Alien;
import com.bazola.spaceylife.gamemodel.MainGame;
import com.bazola.spaceylife.gamemodel.MapPoint;
import com.bazola.spaceylife.gamemodel.PlayerFlag;
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
    
    private final List<StarImage> starImages = new ArrayList<StarImage>();
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
    		StarImage starImage = new StarImage(star.getValue(),
    											star.getKey(), 
    											this.libGDXGame.starTextures.get(star.getValue().type),
    											this.libGDXGame.aiPlanetCover01,
    											this.libGDXGame.playerPlanetCover01,
    											this.libGDXGame.stage);
    		this.starImages.add(starImage);
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
        	
        	for (StarImage image : this.starImages) {
        		image.update();
        	}
        	
        	this.timeSinceLastRender = 0;
        }
        
        if (this.timeSinceLastUpdate > this.timeBetweenUpdates) {
        	this.timeSinceLastUpdate = 0;
        	
        	this.game.update();
        	
        	if (this.alienImages.size() < this.maxAliens) {
        		this.game.spawnAlien();
        	}
        }
	}
	
	private int maxAliens = 50;
	
	public void alienSpawned(Alien alien) {
		AlienImage image = new AlienImage(this.libGDXGame.alien01, alien);
		image.setAnimation(this.libGDXGame.alienMove01);
		this.alienImages.add(image);
		this.libGDXGame.stage.addActor(image);
	}
	
	public void flagSpawned(PlayerFlag flag) {
		AnimatedImage flagImage = new AnimatedImage(this.libGDXGame.flagWave01);
		flagImage.setPosition(flag.getPosition().x, flag.getPosition().y);
		flagImage.setSize(this.WORLD_WIDTH/50, this.WORLD_WIDTH/50);
		this.libGDXGame.stage.addActor(flagImage);
		
		flag.setImage(flagImage);
		
		this.spawnRadarRingsAtFlagPlace(flag);
	}
	
	public void spawnRadarRingsAtFlagPlace(PlayerFlag flag) {
		
		RadarRing radarRing = new RadarRing(this.libGDXGame.radarRing01, 
											this.WORLD_WIDTH / 200, 
											flag.getPosition().x, 
											flag.getPosition().y);
		
		//instead of creating a delay action and making a second ring,
		//create a smaller one with an offset at the same time
		float offset = 5;
		RadarRing radarRing2 = new RadarRing(this.libGDXGame.radarRing01, 
				 							 this.WORLD_WIDTH / 160,
				 							 flag.getPosition().x - offset, 
				 							 flag.getPosition().y - offset);
		
		this.libGDXGame.stage.addActor(radarRing);
		this.libGDXGame.stage.addActor(radarRing2);
	}
	
	private void tappedScreen(float x, float y) {
		//convert screen to stage coordinates
		Vector3 touchPoint = new Vector3();
		this.libGDXGame.camera.unproject(touchPoint.set(x, y, 0));
		this.game.setPlayerMarkedPoint(new MapPoint((int)touchPoint.x, (int)touchPoint.y));
	}

	@Override
	public void viewResized() {
		// TODO Auto-generated method stub
	}
}
