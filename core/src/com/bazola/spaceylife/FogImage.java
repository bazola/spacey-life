package com.bazola.spaceylife;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.bazola.spaceylife.gamemodel.MapPoint;

public class FogImage extends Image {
	
	public final MapPoint position;
	
	public final int size;
	
	/**
	 * Increase this to increase the range of uncovering fog
	 */
	public final int rangeMultiplier = 1;
	
	public FogImage(LibGDXGame libGDXGame, float size, int xPos, int yPos) {
		super(libGDXGame.blackCircle);
		this.setSize(size, size);
		this.setPosition(xPos, yPos);
		this.setOrigin(Align.center);
		this.position = new MapPoint(xPos, yPos);
		this.size = (int)size;
	}
	
	public boolean isNearby(MapPoint point) {
		if (point.x >= this.position.x - size * this.rangeMultiplier &&
			point.x <= this.position.x + size * this.rangeMultiplier &&
			point.y >= this.position.y - size * this.rangeMultiplier &&
			point.y <= this.position.y + size * this.rangeMultiplier) {
			return true;
		}
		return false;
	}
	
	public void fadeOut() {
		if (this.isVisible()) {
			SequenceAction sequence = new SequenceAction();
			sequence.addAction(Actions.fadeOut(1f));
			sequence.addAction(Actions.run(new Runnable() {
				@Override
				public void run() {
					FogImage.this.setVisible(false);
				}
			}));
			this.addAction(sequence);
		}
	}
	
	public void fadeIn() {
		if (!this.isVisible()) {
			this.setVisible(true);
			this.addAction(Actions.fadeIn(1f));
		}
	}
}
