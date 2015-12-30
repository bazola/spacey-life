package com.bazola.spaceylife;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.bazola.spaceylife.gamemodel.Alien;
import com.bazola.spaceylife.gamemodel.EnemyShip;
import com.bazola.spaceylife.gamemodel.MainGame;
import com.bazola.spaceylife.gamemodel.MapPoint;
import com.bazola.spaceylife.gamemodel.PlayerFlag;
import com.bazola.spaceylife.gamemodel.Star;
import com.bazola.spaceylife.gamemodel.UniverseFeature;
import com.bazola.spaceylife.gamemodel.UniverseFeatureType;

public class GameScreen extends BZScreenAdapter {
	
	public final Random random;
	
	private final LibGDXGame libGDXGame;
	
	private Table buttonTable;
	private ButtonStyle greenButtonStyle;
	private Label resourceCountLabel;
	
    private double currentTime = 0;
    private double timeSinceLastRender = 0;
    private double timeBewteenRenders = 1 / 60;
    private double timeSinceLastUpdate = 0;
    private double timeBetweenUpdates = 100;
    
    private SimpleDirectionGestureDetector swipeRecognizer;
    
    private final List<StarImage> starImages = new ArrayList<StarImage>();
    private final List<AlienImage> alienImages = new ArrayList<AlienImage>();
    private final List<EnemyShipImage> enemyShipImages = new ArrayList<EnemyShipImage>();
    private final List<FogImage> fog = new ArrayList<FogImage>();
    
    /**
     * It makes sense to have these variables declared in this class, because
     * things like the flags and radar rings will actually be bigger if the
     * world size is bigger for aesthetic reasons.
     */
    private int WORLD_WIDTH = 4500;
    private int WORLD_HEIGHT = 3000;
    
    private final MainGame game;
    
    public GameScreen(LibGDXGame libGDXGame, Random random) {
    	this.libGDXGame = libGDXGame;
    	this.random = random;
    	
    	this.game = new MainGame(this.WORLD_WIDTH, this.WORLD_HEIGHT, this);
    	
		this.greenButtonStyle = new ButtonStyle();
		this.greenButtonStyle.up = new NinePatchDrawable(this.libGDXGame.menuBackgroundSolid);
		this.greenButtonStyle.down = new NinePatchDrawable(this.libGDXGame.menuBackgroundDark);
		
    	this.addSwipeRecognizer();
    	
    	this.addActorsToStage();
    	
    	this.createButtons();
    	
    	this.game.startGame();
    	
    	this.centerCameraOnPlayerHomeworld();
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
    	
    	this.addNebulaImagesToStage();
    	
    	this.addUniverseFeatureLabels();
    	
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
    	
    	this.addFog();
    }
    
    private void addNebulaImagesToStage() {
    	for (UniverseFeature feature : this.game.universe.getUniverseFeatures()) {
    		if (feature.type == UniverseFeatureType.NEBULA) {
    			Image nebulaImage = new Image(this.getRandomNebulaTexture());
    			float size = feature.circle.radius * 2;
    			nebulaImage.setPosition(feature.circle.x - size / 2, feature.circle.y - size / 2);
    			nebulaImage.setSize(size, size);
    			this.libGDXGame.stage.addActor(nebulaImage);
    		}
    	}
    }
    
    /**
     * This method is just for prototyping the images as I create them
     */
    private Texture getRandomNebulaTexture() {
    	int randomNumber = this.random.nextInt(4);
    	switch(randomNumber) {
    	case 0:
    		return this.libGDXGame.nebula02;
    	case 1:
    		return this.libGDXGame.nebula03;
    	case 2:
    		return this.libGDXGame.nebula04;
    	case 3:
    		return this.libGDXGame.nebula05;
    	default:
    		return this.libGDXGame.nebula05;
    	}
    }
    
    private void addUniverseFeatureLabels() {
    	for (UniverseFeature feature : this.game.universe.getUniverseFeatures()) {
    		Label label = new Label(feature.type.name(), this.libGDXGame.skin);
    		label.setPosition(feature.circle.x, feature.circle.y);
    		label.setFontScale(4);
    		this.libGDXGame.stage.addActor(label);
    	}
    }
    
    private void addFog() {
    	//fog squares start and end off the edge of the map
    	//also they overlap by half of their size
    	int fogSquareSize = this.WORLD_WIDTH / 12;
    	int halfFogSquareSize = fogSquareSize / 2;
    	for (int x = -fogSquareSize; x < this.WORLD_WIDTH + fogSquareSize; x+= halfFogSquareSize) {
    		for (int y = -fogSquareSize; y < this.WORLD_HEIGHT + fogSquareSize; y+= halfFogSquareSize) {
    			FogImage fogImage = new FogImage(this.libGDXGame, fogSquareSize, x, y);
    			this.libGDXGame.fogStage.addActor(fogImage);
    			this.fog.add(fogImage);
    		}
    	}
    }
    
    private void centerCameraOnPlayerHomeworld() {
    	this.libGDXGame.camera.position.x = this.game.getAlienHomeworld().getPosition().x;
    	this.libGDXGame.camera.position.y = this.game.getAlienHomeworld().getPosition().y;
    }
    
    private void createButtons() {
    	this.buttonTable = new Table(this.libGDXGame.skin);
    	this.buttonTable.setFillParent(true);
    	this.libGDXGame.hudStage.addActor(this.buttonTable);
    	
    	Label exitButtonLabel = new Label("Exit", new LabelStyle(this.libGDXGame.bigButtonFont, null));
    	Button exitButton = new Button(exitButtonLabel, this.libGDXGame.skin);
    	exitButton.setStyle(this.greenButtonStyle);
    	exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	GameScreen.this.clickedExitButton();
            }
    	});
    	this.buttonTable.add(exitButton).expand().top().left();
    	this.buttonTable.row();
    	
    	Table shipButtonsTable = new Table(this.libGDXGame.skin);
    	Label resourcesTitle = new Label("Resources:", new LabelStyle(this.libGDXGame.smallButtonFont, null));
    	shipButtonsTable.add(resourcesTitle);
    	shipButtonsTable.row();
    	this.resourceCountLabel = new Label("0", new LabelStyle(this.libGDXGame.smallButtonFont, null));
    	shipButtonsTable.add(this.resourceCountLabel);
    	shipButtonsTable.row();
    	
    	Stack smallAlienStack = new Stack();
    	smallAlienStack.add(new Image(this.libGDXGame.menuBackgroundSolid));
    	Image smallAlienImage = new Image(this.libGDXGame.alien01);
    	//smallAlienImage.setScale(0.5f); //cant get it to align properly
    	smallAlienStack.add(smallAlienImage);
    	smallAlienStack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	GameScreen.this.game.playerClickedSmallAlienButton();
            }
    	});
    	shipButtonsTable.add(smallAlienStack).width(LibGDXGame.HUD_WIDTH / 10).height(LibGDXGame.HUD_WIDTH / 10);
    	shipButtonsTable.row();
    	
    	shipButtonsTable.add(" ").row();

    	Stack largeAlienStack = new Stack();
    	largeAlienStack.add(new Image(this.libGDXGame.menuBackgroundSolid));
    	Image largeAlien = new Image(this.libGDXGame.bigAlienMove01.getKeyFrame(0));
    	largeAlienStack.add(largeAlien);
    	largeAlienStack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	GameScreen.this.game.playerClickedLargeAlienButton();
            }
    	});
    	shipButtonsTable.add(largeAlienStack).width(LibGDXGame.HUD_WIDTH / 10).height(LibGDXGame.HUD_WIDTH / 10);
    	this.buttonTable.add(shipButtonsTable).right();
    	this.buttonTable.row();
    	
    	Table flagButtonsTable = new Table(this.libGDXGame.skin);
    	int maxFlags = 3;
    	for (int i = 0; i < maxFlags; i++) {
    		Stack stack = new Stack();
    		stack.add(new Image(this.libGDXGame.menuBackgroundSolid));
    		Image flagImage = new Image(this.libGDXGame.flagWave01.getKeyFrame(0));
    		stack.add(flagImage);
    		final int index = i;
    		stack.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                	GameScreen.this.clickedFlagButtonAtIndex(index);
                }
        	});
    		flagButtonsTable.add(stack).width(LibGDXGame.HUD_WIDTH / 10).height(LibGDXGame.HUD_WIDTH / 10);
    	}
    	this.buttonTable.add(flagButtonsTable).bottom().left();
    }
    
    private void clickedFlagButtonAtIndex(int index) {
    	this.game.playerClickedFlagButtonAtIndex(index);
    }
    
    private void clickedExitButton() {
    	this.libGDXGame.exitToMainMenu();
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
        	
        	//do not draw actors outside of camera view
        	this.libGDXGame.stage.getRoot().setCullingArea(this.calculateCameraFrame());
        	this.libGDXGame.fogStage.getRoot().setCullingArea(this.calculateCameraFrame());
        	
        	for (AlienImage image : this.alienImages) {
        		image.update();
        	}
        	
        	for (EnemyShipImage image : this.enemyShipImages) {
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
        	
        	this.processFog();
        	
        	this.resourceCountLabel.setText(String.valueOf(this.game.getPlayerResources()));
        }
	}
	
	private Rectangle calculateCameraFrame() {
    	//increase rectangle size by ratio so that popin does not occur
    	//float ratio = 0.2f;
    	//super low zoom levels cause popin problems
    	float zoomLevel = Math.max(1, this.libGDXGame.camera.zoom);
    	float frameWidth = this.libGDXGame.camera.viewportWidth * zoomLevel;
    	//frameWidth = frameWidth + frameWidth * ratio;
    	float frameHeight = this.libGDXGame.camera.viewportHeight * zoomLevel;
    	//frameHeight = frameHeight + frameHeight * ratio; 
    	float frameX = this.libGDXGame.camera.position.x - frameWidth / 2;
    	//frameX = frameX - frameX * ratio;
    	float frameY = this.libGDXGame.camera.position.y - frameHeight / 2;
    	//frameY = frameY - frameY * ratio;
    	return new Rectangle(frameX, frameY, frameWidth, frameHeight);
	}
	
    private void processFog() {
    	List<MapPoint> pointsToReveal = new ArrayList<MapPoint>();
    	
    	for (Alien alien : this.game.getPlayerAliens()) {
    		pointsToReveal.add(alien.getPosition());
    	}
    	
    	pointsToReveal.add(this.game.getAlienHomeworld().getPosition());
    	
    	this.revealFog(pointsToReveal);
    }
    
    private void revealFog(List<MapPoint> pointsToReveal) {
    	Set<FogImage> fogToReveal = new HashSet<FogImage>();
    	for (FogImage fogImage : this.fog) {
    		for (MapPoint point : pointsToReveal) {
    			if (fogImage.isNearby(point)) {
    				fogToReveal.add(fogImage);
    			}
    		}
    	}
    	for (FogImage fogImage : fogToReveal) {
    		fogImage.fadeOut();
    	}
    	for (FogImage fogImage : this.fog) {
    		if (!fogToReveal.contains(fogImage) &&
    			this.game.positionIsDarkZone(fogImage.position)) {
    			fogImage.fadeIn();
    		}
    	}
    }
	
	public void alienSpawned(Alien alien) {
		AlienImage image = new AlienImage(this.libGDXGame.alien01, alien);
		image.setMoveAnimation(this.libGDXGame.alienMove01);
		image.setEatAnimation(this.libGDXGame.alienEat01);
		this.alienImages.add(image);
		this.libGDXGame.stage.addActor(image);
	}
	
	public void enemyShipSpawned(EnemyShip ship) {
		EnemyShipImage image = new EnemyShipImage(this.libGDXGame.shipTextures.get(ShipType.ENEMY_FIGHTER), ship);
		this.enemyShipImages.add(image);
		this.libGDXGame.stage.addActor(image);
	}
	
	public void enemyFiredWeaponAtAlien(EnemyShip enemy, final Alien alien) {
		Image laserImage = new Image(this.libGDXGame.laser01);
		laserImage.setPosition(enemy.getPosition().x, enemy.getPosition().y);
		SequenceAction sequence = new SequenceAction();
		sequence.addAction(Actions.moveTo(alien.getPosition().x, alien.getPosition().y, 0.5f));
		sequence.addAction(Actions.alpha(0, 0.5f));
		sequence.addAction(Actions.run(new Runnable() {
			@Override
			public void run() {
				GameScreen.this.alienKilled(alien);
			}
		}));
		laserImage.addAction(sequence);
		this.libGDXGame.stage.addActor(laserImage);
	}
	
	public void enemyKilled(EnemyShip enemyShip) {
		EnemyShipImage imageToRemove = null;
		for (EnemyShipImage image : this.enemyShipImages) {
			if (image.getEnemyShip().equals(enemyShip)) {
				imageToRemove = image;
			}
		}
		if (imageToRemove != null) {
			imageToRemove.remove();
			this.enemyShipImages.remove(imageToRemove);
		}
	}
	
	private void alienKilled(Alien alien) {
		AlienImage imageToRemove = null;
		for (AlienImage image : this.alienImages) {
			if (image.getAlien().equals(alien)) {
				imageToRemove = image;
			}
		}
		if (imageToRemove != null) {
			imageToRemove.remove();
			this.alienImages.remove(imageToRemove);
		}
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
