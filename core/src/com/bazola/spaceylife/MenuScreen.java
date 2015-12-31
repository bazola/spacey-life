package com.bazola.spaceylife;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.bazola.spaceylife.uielements.AnimatedImage;
import com.bazola.spaceylife.uielements.TalkingAlienImage;

public class MenuScreen extends BZScreenAdapter {

	private final LibGDXGame libGDXGame;
	private final Random random;
	
	private Table buttonTable;
	
	private ButtonStyle greenButtonStyle;
	
	public MenuScreen(LibGDXGame libGDXGame, Random random) {
		this.libGDXGame = libGDXGame;
		this.random = random;
		
		this.greenButtonStyle = new ButtonStyle();
		this.greenButtonStyle.up = new NinePatchDrawable(this.libGDXGame.menuBackgroundSolid);
		this.greenButtonStyle.down = new NinePatchDrawable(this.libGDXGame.menuBackgroundDark);
		
		this.create();
		
		this.libGDXGame.musicPlayer.playTitle();
	}
	
	private void create() {
		this.createBackground();
		this.createTitle();
		this.createButtons();
	}
	
    private void createBackground() {
    	Image background = new Image(this.libGDXGame.gridBackground);
    	background.addAction(Actions.alpha(0.5f));
    	background.setSize(LibGDXGame.HUD_WIDTH, LibGDXGame.HUD_HEIGHT);
    	this.libGDXGame.backgroundStage.addActor(background);
    	
    	Table bgTable = new Table();
    	bgTable.setFillParent(true);
    	this.libGDXGame.backgroundStage.addActor(bgTable);
    	    	
    	Stack screenStack = new Stack();
    	bgTable.add(screenStack);
    	
    	AnimatedImage bgImage = new AnimatedImage(this.libGDXGame.screenBackgroundAnimation);
    	bgImage.setOrigin(Align.center);
    	bgImage.setScale(1.1f);
    	screenStack.add(bgImage);
    	
    	List<Animation> talkingAlienAnimations = new ArrayList<Animation>();
    	talkingAlienAnimations.add(this.libGDXGame.angry01Animation);
    	talkingAlienAnimations.add(this.libGDXGame.idle01Animation);
    	talkingAlienAnimations.add(this.libGDXGame.idle02Animation);
    	talkingAlienAnimations.add(this.libGDXGame.hello01Animation);
    	TalkingAlienImage talkingAlien = new TalkingAlienImage(talkingAlienAnimations, this.random);
    	talkingAlien.setOrigin(Align.center);
    	talkingAlien.setScale(1.1f);
    	screenStack.add(talkingAlien);
    	
    	Image screenOverlay = new Image(this.libGDXGame.screenOverlay01);
    	screenOverlay.setOrigin(Align.center);
    	screenOverlay.setScale(1.2f);
    	screenStack.add(screenOverlay);
    }
	
	private void createTitle() {
		this.buttonTable = new Table(this.libGDXGame.skin);
		this.buttonTable.setFillParent(true);
		this.libGDXGame.hudStage.addActor(this.buttonTable);
		
		this.buttonTable.add(" ").row(); //spacing
		this.buttonTable.add(" ").row(); //spacing
		Label titleLabel = new Label("You Are The Aliens", new LabelStyle(this.libGDXGame.titleFont, null));
		this.buttonTable.add(titleLabel).top().expand();
		this.buttonTable.row();
	}
	
	private void createButtons() {
		Label playButtonLabel = new Label("Play", new LabelStyle(this.libGDXGame.bigButtonFont, null));
		Button playButton = new Button(playButtonLabel, this.libGDXGame.skin);
		playButton.setStyle(this.greenButtonStyle);
		playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	MenuScreen.this.clickedPlay();
            }
		});
		
		Label creditsButtonLabel = new Label("Credits", new LabelStyle(this.libGDXGame.bigButtonFont, null));
		Button creditsButton = new Button(creditsButtonLabel, this.libGDXGame.skin);
		creditsButton.setStyle(this.greenButtonStyle);
		creditsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	MenuScreen.this.clickedCredits();
            }
		});
		
		this.buttonTable.add(playButton).width(LibGDXGame.HUD_WIDTH / 3).height(LibGDXGame.HUD_HEIGHT / 8);
		this.buttonTable.row();
		this.buttonTable.add(creditsButton).width(LibGDXGame.HUD_WIDTH / 3).height(LibGDXGame.HUD_HEIGHT / 8);
	}
	
	private void clickedPlay() {
		this.libGDXGame.backgroundStage.clear();
		this.buttonTable.clear();
		this.libGDXGame.musicPlayer.stopMusic();
		this.libGDXGame.clickedPlayButton();
	}
	
	private void clickedCredits() {
		this.buttonTable.clear();
		
		Table creditsTable = new Table(this.libGDXGame.skin);
		creditsTable.setBackground(new NinePatchDrawable(this.libGDXGame.menuBackgroundTransparent));
		this.buttonTable.add(creditsTable).width(LibGDXGame.HUD_WIDTH * 0.8f).height(LibGDXGame.HUD_HEIGHT * 0.8f);
	
		Label creditsLabel = new Label("Credits", new LabelStyle(this.libGDXGame.bigButtonFont, null));
		creditsTable.add(creditsLabel);
		creditsTable.row();
		creditsTable.add(" ").row();
		
		for (String string : this.creditStrings()) {
			Label label = new Label(string, new LabelStyle(this.libGDXGame.smallButtonFont, null));
			creditsTable.add(label);
			creditsTable.row();
		}
		
		creditsTable.add(" ").row();
		
		Label exitButtonLabel = new Label("Close", new LabelStyle(this.libGDXGame.bigButtonFont, null));
		Button exitButton = new Button(exitButtonLabel, this.libGDXGame.skin);
		exitButton.setBackground(new NinePatchDrawable(this.libGDXGame.menuBackgroundSolid));
		exitButton.setStyle(this.greenButtonStyle);
		exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	MenuScreen.this.clickedCloseCredits();
            }
		});
		creditsTable.add(exitButton).width(LibGDXGame.HUD_WIDTH / 4).height(LibGDXGame.HUD_HEIGHT / 8);
	}
	
	private void clickedCloseCredits() {
		this.libGDXGame.backgroundStage.clear();
		this.buttonTable.clear();
		this.create();
	}
	
	private List<String> creditStrings() {
		List<String> creditStrings = new ArrayList<String>();
		creditStrings.add("Game by bazola");
		creditStrings.add("Programmer Art by bazola");
		creditStrings.add("Art by Ivan Voirol");
		creditStrings.add("Art by Master484");
		creditStrings.add("UI Art by TokyoGeisha");
		creditStrings.add("Art by ChaosShark");
		creditStrings.add("Art by Bert-o-Naught");
		creditStrings.add("Art by Rawdanitsu");
		creditStrings.add("Art by Nekith");
		creditStrings.add("Art by n4pgamer");
		creditStrings.add("Music by Kevin MacLeod");
		return creditStrings;
	}
	
	private DirectionLeftRight leftRight = DirectionLeftRight.LEFT;
	private DirectionUpDown upDown = DirectionUpDown.DOWN;
	private enum DirectionLeftRight {
		LEFT(-1),
		RIGHT(1);
		public final int move;
		private DirectionLeftRight(int move) {
			this.move = move;
		}
	}
	private enum DirectionUpDown {
		UP(1),
		DOWN(-1);
		public final int move;
		private DirectionUpDown(int move) {
			this.move = move;
		}
	}
	
	@Override
	public void render(float delta) {
		
		this.libGDXGame.parallaxCamera.position.x += this.leftRight.move;
		this.libGDXGame.parallaxCamera.position.y += this.upDown.move;
		
		//change directions when camera moves past the top layer of parallax
		if (this.libGDXGame.parallaxCamera.position.x > this.libGDXGame.parallaxDrawer.topLayerWidth) {
			this.leftRight = DirectionLeftRight.LEFT;
		}
		if (this.libGDXGame.parallaxCamera.position.x < 0) {
			this.leftRight = DirectionLeftRight.RIGHT;
		}
		if (this.libGDXGame.parallaxCamera.position.y > this.libGDXGame.parallaxDrawer.topLayerHeight) {
			this.upDown = DirectionUpDown.DOWN;
		}
		if (this.libGDXGame.parallaxCamera.position.y < 0) {
			this.upDown = DirectionUpDown.UP;
		}
	}

	@Override
	public void viewResized() {
	}
}