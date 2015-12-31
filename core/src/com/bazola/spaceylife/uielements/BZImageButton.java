package com.bazola.spaceylife.uielements;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class BZImageButton {
	
	private final Button button;
	private final Image image;
	
	public BZImageButton(NinePatch upTexture, 
						 NinePatch downTexture,
						 Image image, 
						 float scale,
						 Table table,
						 float width,
						 float height) {
		
		Stack stack = new Stack();
		
		ButtonStyle style = new ButtonStyle();
		style.up = new NinePatchDrawable(upTexture);
		style.down = new NinePatchDrawable(downTexture);
		this.button = new Button(style);
		
		stack.add(this.button);
		
		this.image = image;
		this.image.setScale(scale);
		this.image.setTouchable(Touchable.disabled);
		this.image.setOrigin(width / 2, height / 2);
		stack.add(this.image);		
		
		table.add(stack).width(width).height(height);
	}
	
	public Button getButton() {
		return this.button;
	}
}
