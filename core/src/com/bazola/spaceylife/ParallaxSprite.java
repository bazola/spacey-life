package com.bazola.spaceylife;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class ParallaxSprite {
	
	public final Texture texture;
	
	public float xPos;
	public float yPos;
	public float width;
	public float height;
	public final Color color;
	
	public ParallaxSprite(Texture texture, Color color, float xPos, float yPos, float width, float height) {
		this.texture = texture;
		this.color = color;
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
	}
}
