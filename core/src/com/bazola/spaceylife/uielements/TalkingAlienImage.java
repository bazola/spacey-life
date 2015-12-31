package com.bazola.spaceylife.uielements;

import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TalkingAlienImage extends Image {
	
	private final Random random;
	
	private float stateTime = 0;
	private float timeBetweenAnimations = 0;
	private int numAnimationLoops = 7;
	
	private final List<Animation> animations;
	
	private int currentAnimationIndex;
	private Animation activeAnimation;

	public TalkingAlienImage(List<Animation> animations, Random random) {
		super(animations.get(0).getKeyFrames()[0]); //very first frame
		
		this.random = random;
		
		this.animations = animations;
		
		this.currentAnimationIndex = 0;
		this.activeAnimation = this.animations.get(this.currentAnimationIndex);
	}
	
    @Override
    public void act(float delta) {
    	//change the animation when it completes
    	this.timeBetweenAnimations += delta;
    	if (this.timeBetweenAnimations > this.activeAnimation.getAnimationDuration() * this.numAnimationLoops) {
    		this.timeBetweenAnimations = 0;
    		
    		//cycle animations in order
    		/*
    		this.currentAnimationIndex++;
    		if (this.currentAnimationIndex >= this.animations.size()) {
    			this.currentAnimationIndex = 0;
    		}
    		*/
    		
    		//random animation
    		this.currentAnimationIndex = this.random.nextInt(this.animations.size());
    		this.activeAnimation = this.animations.get(this.currentAnimationIndex);
    	}
    	
        ((TextureRegionDrawable)getDrawable()).setRegion(activeAnimation.getKeyFrame(stateTime+=delta, true));
        super.act(delta);
        
    }
}
