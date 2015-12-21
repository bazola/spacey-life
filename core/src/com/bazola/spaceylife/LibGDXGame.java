package com.bazola.spaceylife;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class LibGDXGame extends ApplicationAdapter {
	
	public SpriteBatch batch;
	
	public Map<StarType, TextureRegion> starTextures;
	public Map<ShipType, TextureRegion> shipTextures;
	public Map<PlanetType, TextureRegion> planetTextures;
	
	public Texture gridBackground;
	
	@Override
	public void create () {
		this.batch = new SpriteBatch();
	
		this.loadResources();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.batch.begin();
		this.batch.draw(this.gridBackground, 0, 0);
		this.batch.end();
	}
	
	private void loadResources() {
		this.gridBackground = new Texture("Grid3.png");
		
		this.starTextures = this.loadStarTextures();
		this.shipTextures = this.loadShipTextures();
		this.planetTextures = this.loadPlanetTextures();
	}
	
	private Map<StarType, TextureRegion> loadStarTextures() {
		Map<StarType, TextureRegion> textures =  new HashMap<StarType, TextureRegion>();
		TextureAtlas atlas = new TextureAtlas("stars01.atlas");
		for (StarType type : StarType.values()) {
			AtlasRegion region = atlas.findRegion(type.fileName);
			TextureRegion textureRegion = region;
			textures.put(type, textureRegion);
		}
		return textures;
	}
	
	private Map<ShipType, TextureRegion> loadShipTextures() {
		Map<ShipType, TextureRegion> textures =  new HashMap<ShipType, TextureRegion>();
		TextureAtlas atlas = new TextureAtlas("ships01.atlas");
		for (ShipType type : ShipType.values()) {
			AtlasRegion region = atlas.findRegion(type.fileName);
			TextureRegion textureRegion = region;
			textures.put(type, textureRegion);
		}
		return textures;
	}
	
	private Map<PlanetType, TextureRegion> loadPlanetTextures() {
		Map<PlanetType, TextureRegion> textures =  new HashMap<PlanetType, TextureRegion>();
		TextureAtlas atlas = new TextureAtlas("planets01.atlas");
		for (PlanetType type : PlanetType.values()) {
			AtlasRegion region = atlas.findRegion(type.fileName);
			TextureRegion textureRegion = region;
			textures.put(type, textureRegion);
		}
		return textures;
	}
}
