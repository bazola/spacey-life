package com.bazola.spaceylife;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ParallaxDrawer {
	
	private final Random random;
	
	private final SpriteBatch batch;
	private final int stageWidth;
	private final int stageHeight;
	
	private final Texture starTexture;
	private final Texture starTextureTrans;
	
	private ParallaxSprite[][] layerOne;
	private ParallaxSprite[][] layerTwo;
	private ParallaxSprite[][] layerThree;
	
	public final int topLayerWidth;
	public final int topLayerHeight;
	
	public ParallaxDrawer(Random random, SpriteBatch targetBatch, Texture starTexture, Texture starTextureTrans, int worldWidth, int worldHeight, int stageWidth, int stageHeight) {
		this.random = random;
		this.batch = targetBatch;
		this.stageWidth = stageWidth;
		this.stageHeight = stageHeight;
		
		this.starTexture = starTexture;
		this.starTextureTrans = starTextureTrans;
		
		float width = starTexture.getWidth() * 1.5f;
		float height = starTexture.getHeight() * 1.5f;
		this.layerOne = new ParallaxSprite[(int) (worldWidth / width)][(int) (worldHeight / height)];
		for (int x = 0; x < this.layerOne.length; x++) {
			for (int y = 0; y < this.layerOne[x].length; y++) {
				this.layerOne[x][y] = new ParallaxSprite(this.starTextureTrans, 
														 new Color(this.random.nextFloat(), this.random.nextFloat(), this.random.nextFloat(), 1),
														 x * width, 
														 y * height, 
														 width, 
														 height);
			}
		}
		
		this.topLayerWidth = (int)(this.layerOne.length * width);
		this.topLayerHeight = (int)(this.layerOne[0].length * height);
		
		width = starTexture.getWidth() * 0.8f;
		height = starTexture.getHeight() * 0.8f;
		this.layerTwo = new ParallaxSprite[(int) (worldWidth / width)][(int) (worldHeight / height)];
		for (int x = 0; x < this.layerTwo.length; x++) {
			for (int y = 0; y < this.layerTwo[x].length; y++) {
				this.layerTwo[x][y] = new ParallaxSprite(this.starTextureTrans, 
														 new Color(this.random.nextFloat(), this.random.nextFloat(), this.random.nextFloat(), 1),
														 x * width, 
														 y * height, 
														 width, 
														 height);
			}
		}
		
		width = starTexture.getWidth() * 0.5f;
		height = starTexture.getHeight() * 0.5f;
		this.layerThree = new ParallaxSprite[(int) (worldWidth / width)][(int) (worldHeight / height)];
		for (int x = 0; x < this.layerThree.length; x++) {
			for (int y = 0; y < this.layerThree[x].length; y++) {
				this.layerThree[x][y] = new ParallaxSprite(this.starTexture, 
														   new Color(this.random.nextFloat(), this.random.nextFloat(), this.random.nextFloat(), 1), 
														   x * width, 
														   y * height, 
														   width, 
														   height);
			}
		}
	}
	
	public void drawBottomLayer() {
		this.batch.setColor(Color.WHITE); //bottom always white
		for (int x = 0; x < this.layerThree.length; x++) {
			for (int y = 0; y < this.layerThree[x].length; y++) {
				ParallaxSprite sprite = this.layerThree[x][y];
				//this.batch.setColor(sprite.color);
				this.batch.draw(sprite.texture, sprite.xPos - this.stageWidth, sprite.yPos - this.stageHeight, sprite.width, sprite.height);
			}
		}
	}
	
	public void drawMiddleLayer() {
		for (int x = 0; x < this.layerTwo.length; x++) {
			for (int y = 0; y < this.layerTwo[x].length; y++) {
				ParallaxSprite sprite = this.layerTwo[x][y];
				this.batch.setColor(sprite.color);
				this.batch.draw(sprite.texture, sprite.xPos - this.stageWidth, sprite.yPos - this.stageHeight, sprite.width, sprite.height);
			}
		}
	}
	
	public void drawTopLayer() {
		for (int x = 0; x < this.layerOne.length; x++) {
			for (int y = 0; y < this.layerOne[x].length; y++) {
				ParallaxSprite sprite = this.layerOne[x][y];
				this.batch.setColor(sprite.color);
				this.batch.draw(sprite.texture, sprite.xPos - this.stageWidth, sprite.yPos - this.stageHeight, sprite.width, sprite.height);
			}
		}
	}
}
