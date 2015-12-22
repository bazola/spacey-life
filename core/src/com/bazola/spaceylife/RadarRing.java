package com.bazola.spaceylife;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class RadarRing extends Image {
	
	public RadarRing(Texture texture, float size, float x, float y) {
		super(texture);
		
		this.setPosition(x, y);
		this.setSize(size, size);
		this.setOrigin(size / 2, size / 2);
		
		//add one action and then the sequence
		//so that the first two happen at the same time
		this.addAction(Actions.scaleBy(50, 50, 2f));
		SequenceAction sequence = new SequenceAction();
		sequence.addAction(Actions.fadeOut(2f));
		sequence.addAction(Actions.run(new Runnable() {
			@Override
			public void run() {
				RadarRing.this.remove();
			}
		}));
		this.addAction(sequence);
	}
}
