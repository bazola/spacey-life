package com.bazola.spaceylife.uielements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.bazola.spaceylife.gamemodel.MapPoint;
import com.bazola.spaceylife.gamemodel.gamepieces.Star;

public class StarImage extends Image {

	private Image fighterCoverImage;
	private Image alienCoverImage;
	private AnimatedImage traderCoverImage;
	
	private Star star;
	
	public StarImage(Star star, MapPoint position, TextureRegion starTexture, Texture fighterCover, Texture alienCover, Animation traderCover, Stage stage) {
		super(starTexture);
		
		this.star = star;
		
		stage.addActor(this);
		
		//the offset is important because it allows things to be
		//rendered correctly without affecting the positions
		//that are set by the game model
		float xOffset = this.getWidth() / 2;
		float yOffset = this.getHeight() / 2;
		this.setPosition(position.x - xOffset, position.y - yOffset);
		
		this.fighterCoverImage = new Image(fighterCover);
		this.fighterCoverImage.setVisible(false);
		this.fighterCoverImage.setPosition(position.x - xOffset, position.y - yOffset);
		stage.addActor(this.fighterCoverImage);
		
		this.alienCoverImage = new Image(alienCover);
		this.alienCoverImage.setVisible(false);
		this.alienCoverImage.setOrigin(Align.center);
		this.alienCoverImage.setPosition(position.x - xOffset, position.y - yOffset);
		this.addGrowShrinkActionToImage(this.alienCoverImage);
		stage.addActor(this.alienCoverImage);
		
		this.traderCoverImage = new AnimatedImage(traderCover);
		this.traderCoverImage.setVisible(false);
		this.traderCoverImage.setOrigin(Align.center);
		this.traderCoverImage.setSize(this.fighterCoverImage.getWidth(), this.fighterCoverImage.getHeight());
		this.traderCoverImage.setPosition(position.x - xOffset, position.y - yOffset);
		stage.addActor(this.traderCoverImage);
	}
	
	public void update() {
		switch(this.star.getOwner()) {
		case NONE:
			this.alienCoverImage.setVisible(false);
			this.fighterCoverImage.setVisible(false);
			this.traderCoverImage.setVisible(false);
			this.traderCoverImage.paused = true;
			break;
		case FIGHTER:
			this.fighterCoverImage.setVisible(true);
			this.alienCoverImage.setVisible(false);
			this.alienCoverImage.getActions().clear();
			this.traderCoverImage.setVisible(false);
			this.traderCoverImage.paused = true;
			break;
		case ALIEN:
			this.alienCoverImage.setVisible(true);
			if (this.alienCoverImage.getActions().size == 0) {
				this.addGrowShrinkActionToImage(this.alienCoverImage);
			}
			this.fighterCoverImage.setVisible(false);
			this.traderCoverImage.setVisible(false);
			this.traderCoverImage.paused = true;
			break;
		case TRADER:
			this.traderCoverImage.setVisible(true);
			this.traderCoverImage.paused = false;
			this.alienCoverImage.setVisible(false);
			this.fighterCoverImage.setVisible(false);
			break;
		}
	}
	
	private void addGrowShrinkActionToImage(Image image) {
		SequenceAction sequence = new SequenceAction();
		sequence.addAction(Actions.scaleBy(-0.1f, -0.1f, 1));
		sequence.addAction(Actions.scaleBy(0.1f, 0.1f, 1));
		image.addAction(Actions.forever(sequence));
	}
}
