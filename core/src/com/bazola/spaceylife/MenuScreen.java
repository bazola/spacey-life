package com.bazola.spaceylife;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class MenuScreen extends BZScreenAdapter {

	private final LibGDXGame libGDXGame;
	private final Random random;
	
	private Table buttonTable;
	
	public MenuScreen(LibGDXGame libGDXGame, Random random) {
		this.libGDXGame = libGDXGame;
		this.random = random;
		this.create();
	}
	
	private void create() {
		this.createBackground();
		this.createTitle();
		this.createButtons();
	}
	
    private void createBackground() {
    	Image background = new Image(this.libGDXGame.gridBackground);
    	background.setSize(LibGDXGame.STAGE_WIDTH, LibGDXGame.STAGE_HEIGHT);
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
		
		Label titleLabel = new Label("You Are The Aliens", this.libGDXGame.skin);
		titleLabel.setFontScale(4);
		this.buttonTable.add(titleLabel).top().expand();
		this.buttonTable.row();
	}
	
	private void createButtons() {
		TextButton playButton = new TextButton("Play", this.libGDXGame.skin);
		playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	MenuScreen.this.clickedPlay();
            }
		});
		this.buttonTable.add(playButton).width(LibGDXGame.HUD_WIDTH/2).height(LibGDXGame.HUD_HEIGHT/5);
	}
	
	private void clickedPlay() {
		this.libGDXGame.backgroundStage.clear();
		this.buttonTable.clear();
		this.libGDXGame.clickedPlayButton();
	}

	@Override
	public void viewResized() {
	}
}