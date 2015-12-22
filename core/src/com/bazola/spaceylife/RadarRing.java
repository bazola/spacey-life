package com.bazola.spaceylife;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class RadarRing extends Image {
	
	public RadarRing(Texture texture, float size, float x, float y) {
		super(texture);
		
		this.setPosition(x, y);
		this.setSize(size, size);
		this.setOrigin(size / 2, size / 2);
		
		this.addAction(Actions.scaleBy(50, 50, 2f));
		this.addAction(Actions.fadeOut(2f));
	}
}
