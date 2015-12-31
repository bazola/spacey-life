package com.bazola.spaceylife.uielements;

public enum StarType {
	
	BLUE01("blue01"),
	BROWN01("brown01"),
	GREEN01("green01"),
	GREEN02("green02"),
	ORANGE01("orange01"),
	PURPLE01("purple01"),
	RED01("red01"),
	WHITE01("white01"),
	YELLOW01("yellow01");
	
	public final String fileName;

	private StarType(String fileName) {
		this.fileName = fileName;
	}
}
