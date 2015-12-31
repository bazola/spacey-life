package com.bazola.spaceylife.uielements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.bazola.spaceylife.gamemodel.gamepieces.Alien;
import com.bazola.spaceylife.gamemodel.gamepieces.AlienState;

public class AlienImage extends Image {
	
	private final Alien alien;
	
	private Animation moveAnimation;
	private Animation eatAnimation;
	
	private Animation activeAnimation;
	
	private float stateTime = 0;
	
	public AlienImage(Texture texture, Alien alien) {
		super(texture);
		
		this.setOrigin(Align.center);
		
		this.alien = alien;
		this.alien.setRectangle(new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight()));
		
		this.update();
	}
	
	public void update() {
		this.setPosition(alien.getPosition().x, alien.getPosition().y);
		this.setRotation((float)alien.getAngle());
		
		if (this.alien.stateMachine.getCurrentState() == AlienState.EAT_STAR) {
			this.activeAnimation = this.eatAnimation;
		} else {
			this.activeAnimation = this.moveAnimation;
		}
	}
	
	public void setMoveAnimation(Animation animation) {
		this.moveAnimation = animation;
	}
	
	public void setEatAnimation(Animation animation) {
		this.eatAnimation = animation;
	}
	
	public Alien getAlien() {
		return this.alien;
	}
	
	/**
	 * This overridden act method allows the image to be animated
	 */
    @Override
    public void act(float delta) {
    	super.act(delta);
    	if (this.activeAnimation == null) {
    		return;
    	}
        ((TextureRegionDrawable)getDrawable()).setRegion(activeAnimation.getKeyFrame(stateTime+=delta, true));
    }
}
