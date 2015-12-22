package com.bazola.spaceylife.gamemodel;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class PlayerFlag {
	
	private MapPoint position;
	
	private Image image;
	
	public PlayerFlag(MapPoint position) {
		this.position = position;
	}
	
	public MapPoint getPosition() {
		return this.position;
	}

	public void setPosition(MapPoint position) {
		this.position = position;
		if (this.image != null) {
			this.image.setPosition(position.x, position.y);
		}
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
}
