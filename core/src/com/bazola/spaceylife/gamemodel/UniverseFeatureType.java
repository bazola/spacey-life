package com.bazola.spaceylife.gamemodel;

public enum UniverseFeatureType {

	SPIRAL_ARM(500, "Spiral Arm"), //determine a path and place stars along it
	CORE(50, "Core Cluster"), 		//more stars in a small cluster
	NEBULA(250, "Nebula"), 	//less stars
	DARK_ZONE(150, "Dark Zone");  //fog of war that returns 
	
	public final String displayName;
	
	/**
	 * Higher number will mean more space between stars
	 */
	public final int starDensity;
	
	private UniverseFeatureType(int starDensity, String displayName) {
		this.starDensity = starDensity;
		this.displayName = displayName;
	}
}
