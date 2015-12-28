package com.bazola.spaceylife.gamemodel;

public enum UniverseFeatureType {

	SPIRAL_ARM(500), //determine a path and place stars along it
	CORE(50), 		//more stars in a small cluster
	NEBULA(250), 	//less stars
	DARK_ZONE(150);  //fog of war that returns 
	
	/**
	 * Higher number will mean more space between stars
	 */
	public final int starDensity;
	
	private UniverseFeatureType(int starDensity) {
		this.starDensity = starDensity;
	}
}
