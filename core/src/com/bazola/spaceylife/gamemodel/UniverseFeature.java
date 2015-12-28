package com.bazola.spaceylife.gamemodel;

import java.util.Random;

import com.badlogic.gdx.math.Circle;

public class UniverseFeature {
	
	public final Circle circle;
	public final UniverseFeatureType type;
	
	/**
	 * The circle will be compared to existing ones and
	 * overlapping ones will not be used, so it
	 * needs to be passed in to the class.
	 */
	public UniverseFeature(Circle circle, Random random) {
		this.circle = circle;
		this.type = UniverseFeatureType.values()[random.nextInt(UniverseFeatureType.values().length)];
	}
}
