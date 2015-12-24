package com.bazola.spaceylife;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.bazola.spaceylife.gamemodel.EnemyShip;

public class EnemyShipImage extends Image {

	private final EnemyShip enemyShip;
	
	public EnemyShipImage(TextureRegion texture, EnemyShip enemyShip) {
		super(texture);
		
		this.setOrigin(Align.center);
		
		this.enemyShip = enemyShip;
		
		this.update();
	}
	
	public void update() {
		this.setPosition(this.enemyShip.getPosition().x, this.enemyShip.getPosition().y);
		this.setRotation((float)enemyShip.getAngle());
	}
}
