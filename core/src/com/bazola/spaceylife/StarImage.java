package com.bazola.spaceylife;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bazola.spaceylife.gamemodel.MapPoint;
import com.bazola.spaceylife.gamemodel.Star;

public class StarImage extends Image {

	private Image aiCoverImage;
	private Image playerCoverImage;
	
	private Star star;
	
	public StarImage(Star star, MapPoint position, TextureRegion starTexture, Texture aiCover, Texture playerCover, Stage stage) {
		super(starTexture);
		
		this.star = star;
		
		stage.addActor(this);
		
		this.setPosition(position.x, position.y);
		
		this.aiCoverImage = new Image(aiCover);
		this.aiCoverImage.setVisible(false);
		this.aiCoverImage.setPosition(position.x, position.y);
		stage.addActor(this.aiCoverImage);
		
		this.playerCoverImage = new Image(playerCover);
		this.playerCoverImage.setVisible(false);
		this.playerCoverImage.setPosition(position.x, position.y);
		stage.addActor(this.playerCoverImage);
	}
	
	public void update() {
		switch(this.star.getState()) {
		case NEUTRAL:
			this.playerCoverImage.setVisible(false);
			this.aiCoverImage.setVisible(false);
			break;
		case AI_CONTROLLED:
			this.aiCoverImage.setVisible(true);
			this.playerCoverImage.setVisible(false);
			break;
		case PLAYER_CONTROLLED:
			this.playerCoverImage.setVisible(true);
			this.aiCoverImage.setVisible(false);
			break;
		}
	}
}
