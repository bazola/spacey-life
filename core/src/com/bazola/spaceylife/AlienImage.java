package com.bazola.spaceylife;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bazola.spaceylife.gamemodel.Alien;

public class AlienImage extends Image {
	
	private final Alien alien;
	
	private Animation animation;
	private float stateTime = 0;
	
	//public Rectangle rectangle;

	public AlienImage(Texture texture, Alien alien) {
		super(texture);
		
		//this.rectangle = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		
		this.alien = alien;
		this.alien.setRectangle(new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight()));
		
		this.update();
	}
	
	public void update() {
		this.setPosition(alien.getPosition().x, alien.getPosition().y);
		this.setRotation((float)alien.getAngle());
	}
	
	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
	
	public Alien getAlien() {
		return this.alien;
	}
	
    @Override
    public void act(float delta) {
    	super.act(delta);
    	if (this.animation == null) {
    		return;
    	}
        ((TextureRegionDrawable)getDrawable()).setRegion(animation.getKeyFrame(stateTime+=delta, true));
    }
}
